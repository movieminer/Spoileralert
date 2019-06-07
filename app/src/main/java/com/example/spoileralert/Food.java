package com.example.spoileralert;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Random;

// this classes represents a food item which has all details like the iten name and amount.

public class Food implements Serializable, Comparable<Food> {

    private int quantity;
    private final String name;
    private final String category;
    private final Calendar spoil;
    private String URL = "https://www.food2fork.com/api/search?key=18e03eaa954ff60c4589c9766e5825b1";

    private String url_recipe_name;
    private String recipe_url;
    Food(int quantity, String category, String name, Calendar spoil) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.spoil = spoil;
    }

    //creates a thread to do the API request and returns a json file

    public JSONObject getThread(){
        url_recipe_name = name.replaceAll(" ", "%20");
        final JSONObject[] jay_son = new JSONObject[1];
        final Random rand = new Random();
        final API api = new API();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                jay_son[0] = api.getJSONFromUrl(URL+"&q=" + url_recipe_name+"&page="+ rand.nextInt(5));
            }
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jay_son[0];
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    //returns true if the items spoils on the current day.
    boolean spoilsToday(Calendar currentDate){
        return (currentDate.get(Calendar.DAY_OF_MONTH)== spoil.get(Calendar.DAY_OF_MONTH) &&
                currentDate.get(Calendar.MONTH) == spoil.get(Calendar.MONTH) &&
                currentDate.get(Calendar.YEAR) == spoil.get(Calendar.YEAR));
    }
    // returns if the item is already spoiled.
    boolean alreadySpoiled(Calendar currentDate){

        if(currentDate.get(Calendar.YEAR) > spoil.get(Calendar.YEAR)){
            return true;
        }else if(currentDate.get(Calendar.YEAR) < spoil.get(Calendar.YEAR)){
            return false;
        }
        if(currentDate.get(Calendar.MONTH) > spoil.get(Calendar.MONTH)){
            return true;
        }else if(currentDate.get(Calendar.MONTH) < spoil.get(Calendar.MONTH)){
            return false;
        }
        if(currentDate.get(Calendar.DAY_OF_MONTH) > spoil.get(Calendar.DAY_OF_MONTH)){
            return true;
        }else if(currentDate.get(Calendar.DAY_OF_MONTH) < spoil.get(Calendar.DAY_OF_MONTH)){
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

    String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public int compareTo(Food food) {
        return (this.getSpoil().compareTo(food.getSpoil()));
    }
}
