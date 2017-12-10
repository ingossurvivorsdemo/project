package greycain.ingosstrakhfitness.api.fitness;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessActivities;
import com.google.android.gms.fitness.SessionsApi;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;
import com.google.android.gms.fitness.result.SessionReadResult;
import com.google.android.gms.location.ActivityRecognition;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FitnessService extends IntentService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleClient;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FitnessService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }


    private final static String[] SLEEP_DETECTORS = new String[]{FitnessActivities.SLEEP, FitnessActivities.SLEEP_AWAKE,
            FitnessActivities.SLEEP_DEEP, FitnessActivities.SLEEP_REM};

    private final static String[] RUNNING = new String[]{FitnessActivities.RUNNING,};

    private void getDataBucketFromGoogleFit(Intent intent) {
        DataReadRequest request = setQueryForSleep(getLastSyncDateAsCalendar(), getCurrentDateAsMillis());
        DataReadResult dr = Fitness.HistoryApi.readData(googleClient, request).await(150, TimeUnit.SECONDS);

        for (Bucket bucket : dr.getBuckets()) {
            String bucketActivity = bucket.getActivity();

            for (String activity : SLEEP_DETECTORS) {
                if (activity.equalsIgnoreCase(bucketActivity)) {
                    bucket.getDataSets();
                }
            }

        }
    }

    /**
     * @return
     */
    private long getLastSyncDateAsCalendar() {
        // TODO получаем с сервера данные
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        return calendar.getTimeInMillis();
    }

    private long getCurrentDateAsMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.getTimeInMillis();
    }

    private DataReadRequest setQueryForSleep(long startDate, long endDate) {
        return new DataReadRequest.Builder().setTimeRange(startDate, endDate, TimeUnit.MILLISECONDS)
                .read(DataType.TYPE_ACTIVITY_SEGMENT)
                .enableServerQueries()
                .bucketByActivityType(1, TimeUnit.HOURS)
                .setLimit(250)
                .build();
    }

    private void retrieveSessionsFromGoogleFit(GoogleApiClient client, long startDate, long endTime) {
        final SessionReadRequest.Builder sessionBuilder = new SessionReadRequest.Builder()

                .setTimeInterval(startDate, endTime, TimeUnit.MILLISECONDS)
                .read(DataType.TYPE_ACTIVITY_SEGMENT)
                .readSessionsFromAllApps()
                .enableServerQueries();

        final SessionReadRequest readRequest = sessionBuilder.build();

// Invoke the Sessions API to fetch the session with the query and wait for the result
// of the read request.

        SessionReadResult sessionReadResult = Fitness.SessionsApi.readSession(client, readRequest).await(120, TimeUnit.SECONDS);

        String sample = sessionReadResult.getSessions().get(0).getActivity();

        Status status = sessionReadResult.getStatus();
    }

    private GoogleApiClient initGoogleApiClient(Activity activity) {
        return new GoogleApiClient.Builder(activity)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}