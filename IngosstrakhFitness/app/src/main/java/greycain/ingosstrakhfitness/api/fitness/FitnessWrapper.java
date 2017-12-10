package greycain.ingosstrakhfitness.api.fitness;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.*;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class FitnessWrapper {
    private GoogleApiClient googleClient;
    private boolean authInProgress;
    private List<String> datasources = new ArrayList<>();

    public FitnessWrapper(Activity activity) {
        switch (detectCorrectApi(activity)) {
            case GoogleFitness:
                googleClient = initGoogleClient(activity);
                break;

            case JawBone:

                break;

            case SomethingElse:

                break;

            case Unsupported:

                break;
        }
    }

    public void initSensors() {

        Fitness.SensorsApi.findDataSources(googleClient, new DataSourcesRequest.Builder()
                .setDataTypes(
                        DataType.TYPE_LOCATION_SAMPLE,
                        DataType.TYPE_STEP_COUNT_DELTA,
                        DataType.TYPE_DISTANCE_DELTA,
                        DataType.TYPE_HEART_RATE_BPM)
                .setDataSourceTypes(DataSource.TYPE_RAW, DataSource.TYPE_DERIVED)
                .build())
                .setResultCallback(new ResultCallback<DataSourcesResult>() {
                    @Override
                    public void onResult(DataSourcesResult dataSourcesResult) {

                        datasources.clear();
                        for (DataSource dataSource : dataSourcesResult.getDataSources()) {
                            Device device = dataSource.getDevice();
                            String fields = dataSource.getDataType().getFields().toString();
                            datasources.add(device.getManufacturer() + " " + device.getModel() + " [" + dataSource.getDataType().getName() + " " + fields + "]");

                            final DataType dataType = dataSource.getDataType();
                            if (    dataType.equals(DataType.TYPE_LOCATION_SAMPLE) ||
                                    dataType.equals(DataType.TYPE_STEP_COUNT_DELTA) ||
                                    dataType.equals(DataType.TYPE_DISTANCE_DELTA) ||
                                    dataType.equals(DataType.TYPE_HEART_RATE_BPM)) {

                                Fitness.SensorsApi.add(googleClient,
                                        new SensorRequest.Builder()
                                                .setDataSource(dataSource)
                                                .setDataType(dataSource.getDataType())
                                                .setSamplingRate(5, TimeUnit.SECONDS)
                                                .build(),
                                        new OnDataPointListener() {
                                            @Override
                                            public void onDataPoint(DataPoint dataPoint) {
                                                String msg = "onDataPoint: ";
                                                for (Field field : dataPoint.getDataType().getFields()) {
                                                    Value value = dataPoint.getValue(field);
                                                    msg += "onDataPoint: " + field + "=" + value + ", ";
                                                }
                                                // display.show(msg);
                                            }
                                        })
                                        .setResultCallback(new ResultCallback<Status>() {
                                            @Override
                                            public void onResult(Status status) {
                                                if (status.isSuccess()) {
                                                    // display.show("Listener for " + dataType.getName() + " registered");
                                                } else {
                                                    //display.show("Failed to register listener for " + dataType.getName());
                                                }
                                            }
                                        });
                            }
                        }
                        //datasourcesListener.onDatasourcesListed();
                    }
                });
    }

    private GoogleApiClient initGoogleClient(final Activity activity) {
        new GoogleApiClient.Builder(activity)
                .addApi(Fitness.RECORDING_API)
                //.addApi(Fitness.API)
                .addScope(Fitness.SCOPE_LOCATION_READ)
                .addScope(Fitness.SCOPE_ACTIVITY_READ)
                .addScope(Fitness.SCOPE_BODY_READ_WRITE)
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {

                            @Override
                            public void onConnected(Bundle bundle) {
                                // display.show("Connected");
                                // connection.onConnected();
                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                                //display.show("Connection suspended");
                                if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                    // display.show("Connection lost. Cause: Network Lost.");
                                } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                    //  display.show("Connection lost. Reason: Service Disconnected");
                                }
                            }
                        }
                )
                .addOnConnectionFailedListener(
                        new GoogleApiClient.OnConnectionFailedListener() {
                            // Called whenever the API client fails to connect.
                            @Override
                            public void onConnectionFailed(ConnectionResult result) {
                                // display.log("Connection failed. Cause: " + result.toString());
                                if (!result.hasResolution()) {
                                    GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), activity, 0).show();
                                    return;
                                }

                                if (!authInProgress) {
                                    try {
                                        // display.show("Attempting to resolve failed connection");
                                        // authInProgress = true;
                                        result.startResolutionForResult(activity, FitnessWrapperConstants.REQUEST_OAUTH);
                                    } catch (IntentSender.SendIntentException e) {
                                        // display.show("Exception while starting resolution activity: " + e.getMessage());
                                    }
                                }
                            }
                        }
                )
                .build();

        return googleClient;
    }

    private static FitnessApiType detectCorrectApi(Activity activity) {
        return FitnessApiType.GoogleFitness;
    }

    // @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FitnessWrapperConstants.REQUEST_OAUTH) {
            // display.log("onActivityResult: REQUEST_OAUTH");
            authInProgress = false;
            if (resultCode == Activity.RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                //if (!client.isConnecting() && !client.isConnected()) {
                //    display.log("onActivityResult: client.connect()");
                //    client.connect();
                // }
            }
        }
    }
}
