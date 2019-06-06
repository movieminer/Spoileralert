package com.example.spoileralert;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;


public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String SWITCH = "switch";
    private static LinkedList<Food> food_list =new LinkedList<>();
    private ArrayList<TextView> text= new ArrayList<>();
    private ArrayList<ImageView> frames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        getFrameArr();
        getTextArr();

        FloatingActionButton add_button = findViewById(R.id.add_button);

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

        show_food();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        boolean switchOnOff = sharedPreferences.getBoolean(SWITCH, true);

        if(switchOnOff) {
            String time = sharedPreferences.getString(TEXT, "15:00");

            int hour = 15;
            int min = 0;

            if(time != null) {
                hour = Integer.parseInt(sharedPreferences.getString(TEXT, "15:00").substring(0, 2));
                min = Integer.parseInt(sharedPreferences.getString(TEXT, "15:00").substring(3, 5));
            }
            Calendar cal = Calendar.getInstance();
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hour, min);


            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent notificationIntent = new Intent(this, AlarmReceiver.class);
            PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        }
    }




    public void openActivityADD() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
    public void openActivitySet() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    public static void add_food(Food f){
            food_list.add(f);
    }

    public static LinkedList<Food> getFood_list() {
        return food_list;
    }

    private void getFrameArr(){
        TableLayout tl = findViewById(R.id.FoodLayout);

        for(int i=0; i<4; i++){
            ImageView iv;
            TableRow tr = (TableRow) tl.getChildAt(1);
            iv = (ImageView) tr.getChildAt(i);
            System.out.println(i);
            frames.add(iv);
        }
        for(int i=0; i<4; i++){
            ImageView iv;
            TableRow tr = (TableRow) tl.getChildAt(3);
            iv = (ImageView) tr.getChildAt(i);
            frames.add(iv);
        }
        for(int i=0; i<4; i++){
            ImageView iv;
            TableRow tr = (TableRow) tl.getChildAt(5);
            iv = (ImageView) tr.getChildAt(i);
            frames.add(iv);
        }
        for(int i=0; i<4; i++){
            ImageView iv;
            TableRow tr = (TableRow) tl.getChildAt(7);
            iv = (ImageView) tr.getChildAt(i);
            frames.add(iv);
        }
    }
    private void getTextArr(){
        TableLayout tl = findViewById(R.id.FoodLayout);

        for(int i=0; i<4; i++){
            TextView tv;
            TableRow tr = (TableRow) tl.getChildAt(0);
            tv = (TextView) tr.getChildAt(i);
            text.add(tv);
        }
        for(int i=0; i<4; i++){
            TextView tv;
            TableRow tr = (TableRow) tl.getChildAt(2);
            tv = (TextView) tr.getChildAt(i);
            text.add(tv);
        }
        for(int i=0; i<4; i++){
            TextView tv;
            TableRow tr = (TableRow) tl.getChildAt(4);
            tv = (TextView) tr.getChildAt(i);
            text.add(tv);
        }
        for(int i=0; i<4; i++){
            TextView tv;
            TableRow tr = (TableRow) tl.getChildAt(6);
            tv = (TextView) tr.getChildAt(i);
            text.add(tv);
        }
    }

    public void show_food(){
        Calendar cal = Calendar.getInstance();
        for(int i=0; i<food_list.size(); i++){
            switch (food_list.get(i).getCategory()) {
                case "Meat":
                    frames.get(i).setImageResource(R.drawable.meat);
                    text.get(i).setText(food_list.get(i).getName());
                    text.get(i).setGravity(Gravity.CENTER);
                    break;
                case "Vegetables":
                    frames.get(i).setImageResource(R.drawable.vegetables);
                    text.get(i).setText(food_list.get(i).getName());
                    text.get(i).setGravity(Gravity.CENTER);
                    break;
                case "Liquids":
                    frames.get(i).setImageResource(R.drawable.liquids);
                    text.get(i).setText(food_list.get(i).getName());
                    text.get(i).setGravity(Gravity.CENTER);
                    break;
                default:
                    frames.get(i).setImageResource(R.drawable.dairy);
                    text.get(i).setText(food_list.get(i).getName());
                    text.get(i).setGravity(Gravity.CENTER);
                    break;
            }

            if(food_list.get(i).spoilsToday(cal)){
                text.get(i).setTextColor(Color.rgb(255, 153, 0));
            }else if(food_list.get(i).alreadySpoiled(cal)){
                text.get(i).setTextColor(Color.rgb(226, 29, 29));
            }
        }
        for(int i=food_list.size(); i<16; i++){
            frames.get(i).setImageResource(R.drawable.empty);
            text.get(i).setText("");
        }
    }
}

