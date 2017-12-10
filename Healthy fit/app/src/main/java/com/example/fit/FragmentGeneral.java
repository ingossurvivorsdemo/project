package com.example.fit;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FragmentGeneral extends Fragment implements View.OnClickListener {

    ImageView imgv1;
    ImageView imgv2;
    ImageView imgv3;

    LinearLayout ll1;
    LinearLayout ll2;
    LinearLayout ll3;

    Button btn_notif;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_general, container, false);

        imgv1 = (ImageView) rootView.findViewById(R.id.imageView1);
        imgv2 = (ImageView) rootView.findViewById(R.id.imageView2);
        imgv3 = (ImageView) rootView.findViewById(R.id.imageView3);

        ll1 = (LinearLayout) rootView.findViewById(R.id.ll1);
        ll2 = (LinearLayout) rootView.findViewById(R.id.ll2);
        ll3 = (LinearLayout) rootView.findViewById(R.id.ll3);

        imgv1.setOnClickListener(this);
        imgv2.setOnClickListener(this);
        imgv3.setOnClickListener(this);

        btn_notif = (Button) rootView.findViewById(R.id.button_notify);
        btn_notif.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.imageView1:{
                if (ll1.isShown()) {
                    ll1.setVisibility(View.GONE);
                    imgv1.setImageResource(R.mipmap.up);
                    slide_up(this, ll1);
                } else {
                    ll1.setVisibility(View.VISIBLE);
                    imgv1.setImageResource(R.mipmap.down);
                    slide_down(this, ll1);
                }
            }
                break;
            case R.id.imageView2: {
                if (ll2.isShown()) {
                    ll2.setVisibility(View.GONE);
                    imgv2.setImageResource(R.mipmap.up);
                    slide_up(this, ll2);
                } else {
                    ll2.setVisibility(View.VISIBLE);
                    imgv2.setImageResource(R.mipmap.down);
                    slide_down(this, ll2);
                }
            }
                break;
            case R.id.imageView3: {
                if (ll3.isShown()) {
                    ll3.setVisibility(View.GONE);
                    imgv3.setImageResource(R.mipmap.up);
                    slide_up(this, ll3);
                } else {
                    ll3.setVisibility(View.VISIBLE);
                    imgv3.setImageResource(R.mipmap.down);
                    slide_down(this, ll3);
                }
            }
                break;
            case R.id.button_notify:
                sendNotification(this.getActivity(), "Сообщение:", "Вы мало спите!");
                break;
        }


    }

    private void sendNotification(Activity activity, String title, String text) {
        Context context = activity.getApplicationContext();
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent
                .getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Resources res = context.getResources();
        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setTicker(text)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(false)
                .setContentText(text);

        Notification notification = builder.build();

        notification.flags = notification.flags | Notification.FLAG_INSISTENT;
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        try {
            notificationManager.notify(1111, notification);
        } catch (Exception ex) {
            Log.i("Напоминание", "Ошибка" + ex.getMessage());
        }
    }

    public static void slide_down(FragmentGeneral ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx.getContext(), R.anim.slide_down);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    public static void slide_up(FragmentGeneral ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx.getContext(), R.anim.slide_up);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }


}
