package com.example.spoileralert;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.Random;

public class PopRecipeActivity extends AppCompatActivity {

    private int index;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        context=this;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));
        index = getIntent().getIntExtra("index",0);

        getRequest();

        FloatingActionButton new_one = findViewById(R.id.new_one);
        new_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRequest();
            }
        });
    }

    public void getRequest(){
        Food foo = MainActivity.getFood_list().get(index);

        JSONObject arr = foo.getThread();
            JSONArray recipes = null;
            try {
                recipes = arr.getJSONArray("recipes");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject object = null;
            final Random rand = new Random();
            try {
                object = recipes.getJSONObject(rand.nextInt(10));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final TextView name = findViewById(R.id.recipe_place);
        if (object==null) {
            Toast.makeText(context, "Can't find recipe", Toast.LENGTH_LONG).show();
            this.finish();
        }else{
            try {
                name.setText(object.getString("title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            name.setGravity(Gravity.CENTER);

            TextView url = findViewById(R.id.url_place);
            try {
                url.setText(object.getString("source_url"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            url.setGravity(Gravity.CENTER);

            String imgURL = null;
            try {
                imgURL = object.getString("image_url");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ImageView food = findViewById(R.id.recipe_view);
            final String finalImgURL1 = imgURL;
            final Bitmap[] bit = new Bitmap[1];
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    API api = new API();
                    bit[0] = api.getPicFromUrl(finalImgURL1);
                }
            });
            t1.start();
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            food.setImageBitmap(bit[0]);
        }
    }
}
