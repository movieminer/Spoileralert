package com.example.spoileralert;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;


public class MainActivity extends AppCompatActivity {

    private static LinkedList<Food> food_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        FloatingActionButton add_button = findViewById(R.id.add_button);
        food_list = new LinkedList<>();
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityADD();
            }
        });

        FloatingActionButton setting_button = findViewById(R.id.setting_button);
        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivitySet();
            }
        });

        try {
            start();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //alarmservice
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 15);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
    }




    public void openActivityADD() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
    public void openActivitySet() {
        Intent intent = new Intent(this, SettingActivity.class);
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

    public static LinkedList<Food> getFood_list() {
        return food_list;
    }
}
