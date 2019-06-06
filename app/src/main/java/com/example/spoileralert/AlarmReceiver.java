
package com.example.spoileralert;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class AlarmReceiver extends BroadcastReceiver{
    private static final String CHANNEL_ID = "channelId";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context, CHANNEL_ID);

        String contentTitle = SpoilsNotification.spoiledFoodDisplayMessage(MainActivity.getFood_list());

        Notification notification = builder.setContentTitle(contentTitle)
                .setCategory(Notification.CATEGORY_REMINDER)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setContentText("Open Spoiler Alert for more information.")
                .setTicker("New Message From Spoiler Alert!")
                .setSmallIcon(R.drawable.appel)
                .setContentIntent(pendingIntent).build();


        builder.setChannelId(CHANNEL_ID);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Spoil Alert",
                NotificationManager.IMPORTANCE_HIGH
        );
        channel.enableVibration(true);
        channel.enableLights(true);
        channel.setLightColor(Color.MAGENTA);
        channel.setLockscreenVisibility(1);
        channel.setLightColor(3);


        notificationManager.createNotificationChannel(channel);

        notificationManager.notify(0, notification);
    }
}