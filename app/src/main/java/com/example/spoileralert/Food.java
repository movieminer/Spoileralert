package com.example.spoileralert;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Random;

public class Food {

    private int quantity;
    private String name;
    private String category;
    private Calendar spoil;
    private String URL = "https://www.food2fork.com/api/search?key=18e03eaa954ff60c4589c9766e5825b1";

    public Food(int quantity, String category, String name, Calendar spoil) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.spoil = spoil;
    }

    public String getThread(){

        final StringBuilder s =  new StringBuilder();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                s.append(getRecipe());
            }
        });
        t1.start();
        return s.toString();
    }
    public String getRecipe() {

        Random rnd = new Random();
        try {
            String url = URL + "&q=" + name + "&page=" + rnd.nextInt(4);
            return API.downloadDataFromUrl(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean spoilsToday(Calendar currentDate){
        return (currentDate.get(Calendar.DAY_OF_MONTH)== spoil.get(Calendar.DAY_OF_MONTH) &&
                currentDate.get(Calendar.MONTH) == spoil.get(Calendar.MONTH) &&
                currentDate.get(Calendar.YEAR) == spoil.get(Calendar.YEAR));
    }

    public boolean alreadySpoiled(Calendar currentDate){

        if(currentDate.get(Calendar.YEAR) < spoil.get(Calendar.YEAR)){
            return true;
        }else if(currentDate.get(Calendar.YEAR) > spoil.get(Calendar.YEAR)){
            return false;
        }
        if(currentDate.get(Calendar.MONTH) < spoil.get(Calendar.MONTH)){
            return true;
        }else if(currentDate.get(Calendar.MONTH) > spoil.get(Calendar.MONTH)){
            return false;
        }
        if(currentDate.get(Calendar.DAY_OF_MONTH) < spoil.get(Calendar.DAY_OF_MONTH)){
            return true;
        }else if(currentDate.get(Calendar.DAY_OF_MONTH) > spoil.get(Calendar.DAY_OF_MONTH)){
            return false;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public Calendar getSpoil() {
        return spoil;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }
}
