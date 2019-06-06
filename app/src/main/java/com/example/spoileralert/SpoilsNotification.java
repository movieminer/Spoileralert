package com.example.spoileralert;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static android.support.v4.content.ContextCompat.getSystemService;

public class SpoilsNotification {

    private static LinkedList<Food> spoilsToday =  new LinkedList<>();
    private static String display;

    public SpoilsNotification(){
    }

    public static String spoiledFoodDisplayMessage(LinkedList<Food> currentFoods){
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


        return display;

    }

}
