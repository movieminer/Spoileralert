package com.example.spoileralert;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static android.support.v4.content.ContextCompat.getSystemService;

public class SpoilsNotification {

    private static String display;

    public SpoilsNotification(){
    }

    public static String spoiledFoodDisplayMessage(LinkedList<Food> currentFoods){
        int spoiled=0;
        Date currentTime = new java.util.Date();
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(currentTime);
        for(Food food : currentFoods){
            if(food.spoilsToday(currentDate)){
               spoiled++;
            }
        }

        if(spoiled == 1){
            display = "You have " + spoiled + " product that will spoil today.";
        } else {
            display = "You have " + spoiled + " products that will spoil today.";
        }


        return display;
    }

}
