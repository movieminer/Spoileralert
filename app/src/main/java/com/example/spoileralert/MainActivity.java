package com.example.spoileralert;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;


public class MainActivity extends AppCompatActivity {

    private static LinkedList<Food> food_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton add_button = findViewById(R.id.add_button);
        food_list = new LinkedList<>();
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityADD();
            }
        });

        try {
            start();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void openActivityADD() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    private void start() throws ParseException {
        //Food test;
        //test = new Food(600, "chicken", new SimpleDateFormat("dd/MM/yyyy").parse("12/05/2015"));

       // String s = test.getThread();
        //Log.d("API", s);
    }
    public static void add_food(Food f){
            food_list.add(f);
    }
}
