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
        return currentDate.DAY_OF_MONTH == this.spoil.DAY_OF_MONTH && currentDate.MONTH == this.spoil.MONTH && currentDate.YEAR == this.spoil.YEAR;
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
