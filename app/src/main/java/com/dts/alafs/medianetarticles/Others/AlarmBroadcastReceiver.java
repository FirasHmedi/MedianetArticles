package com.dts.alafs.medianetarticles.others;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.dts.alafs.medianetarticles.R;
import com.dts.alafs.medianetarticles.mainactivities.HomeActivity;

import static android.app.AlarmManager.INTERVAL_DAY;


public class AlarmBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("AlarmBroadcast","onreceive is launched");

        HomeActivity.startAlarmBroadcastReceiver(context,INTERVAL_DAY);

        // Create an explicit intent for an Activity in your app
        Intent intent2 = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, 0);

        NotificationManager  mNotificationManager =(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notify = new NotificationCompat.Builder(context, "default");

        notify.setContentTitle("Exciting articles")
                .setContentText("Want to read some articles ?")
                .setSmallIcon(R.drawable.favorite_filled)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX);


        if (Build.VERSION.SDK_INT >= 26)
        {
            String channelId = "01";
            NotificationChannel channel = new NotificationChannel(channelId,"Channel human readable title",NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
            notify.setChannelId(channelId);
        }

        mNotificationManager.notify(0, notify.build());

    }

}