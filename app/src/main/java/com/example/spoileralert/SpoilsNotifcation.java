package com.example.spoileralert;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class SpoilsNotifcation {

    private static LinkedList<Food> spoilsToday;
    String display;

    public SpoilsNotifcation(){
        this.spoilsToday =  new LinkedList<>();
    }

    public void checkForSpoiledFood(LinkedList<Food> currentFoods){
        Calendar currentDate = Calendar.getInstance();
        for(Food food : currentFoods){
            if(food.spoilsToday(currentDate)){
                spoilsToday.add(food);
            }
        }

        if(spoilsToday.size() == 1){
            display = "You have " + spoilsToday.size() + " product that will spoil today.";
        } else {
            display = "You have " + spoilsToday.size() + " products that will spoil today.";
        }

        if(spoilsToday.size()>0) {
            sentNotification(display);
        }
    }

    private void sentNotification(String display){

            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "channel";
                String description = "description";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("id", name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification noti = new Notification.Builder(this, channelId)
                .setContentTitle(display)
                .setContentText("Open Spoiler Alert for more info")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .build();

    }


}
