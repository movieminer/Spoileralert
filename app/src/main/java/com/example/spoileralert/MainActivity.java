package com.example.spoileralert;


import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    //all of the variables that are needed including lists and constants we use in the functions.

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String SWITCH = "switch";
    private static ArrayList<Food> food_list =new ArrayList<>();
    private ArrayList<TextView> text= new ArrayList<>();
    private ArrayList<ImageView> frames = new ArrayList<>();
    private static final String KEY = "food_list";
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    //in the onCreate() we first try to retrive data from storage and then sort the list,
    // we then makes sure all of the articles are shown including the names.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        context = this;
        try {
            food_list = (ArrayList<Food>) InternalStorage.readObject(this, KEY);
            food_list.sort(new Comparator<Food>() {
                @Override
                public int compare(Food o1, Food o2) {
                    return o1.getSpoil().getTime().compareTo(o2.getSpoil().getTime());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        getFrameArr();
        getTextArr();

        FloatingActionButton add_button = findViewById(R.id.add_button);
        // the listener for the settings add button.
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityADD();
            }
        });
        //the listener for the settings button.
        FloatingActionButton setting_button = findViewById(R.id.setting_button);
        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivitySet();
            }
        });

        show_food();
        createListeners();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        boolean switchOnOff = sharedPreferences.getBoolean(SWITCH, true);

        // checks for the settings in the preferences and the notifications are null which it then should use its default settings.

        if(switchOnOff) {
            String time = sharedPreferences.getString(TEXT, "15:00");

            int hour = 15;
            int min = 0;

            if(time != null) {
                hour = Integer.parseInt(Objects.requireNonNull(sharedPreferences.getString(TEXT, "15:00")).substring(0, 2));
                min = Integer.parseInt(Objects.requireNonNull(sharedPreferences.getString(TEXT, "15:00")).substring(3, 5));
            }
            Calendar cal = Calendar.getInstance();
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hour, min);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent notificationIntent = new Intent(this, AlarmReceiver.class);
            PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        }
    }

    //function to open the add activity to be used in the listener.

    private void openActivityADD() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    //the same as add but for the settings listener.
    private void openActivitySet() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    // function to open the popup menu after clicking an item in the fridge.
    private void openActivityPop(int i){
        Intent intent = new Intent(this, PopActivity.class);
        intent.putExtra("index",i);
        startActivity(intent);
    }
    // adds an item to the list of food, and tries to save the list to memory.
    public static void add_food(Food f){
            food_list.add(f);
        try {
            InternalStorage.writeObject(context, KEY, food_list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //creates the listeners for the imageviews filled with products

    private void createListeners(){
        int size=16;
        if(food_list.size()<16){
            size=food_list.size();
        }
        for(int i=0; i<size;i++){
            final int finalI = i;
            frames.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivityPop(finalI);
                }
            });
        }
    }

    public static void removefood(int i){
        food_list.remove(i);
        try {
            InternalStorage.writeObject(context, KEY, food_list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Food> getFood_list() {
        return food_list;
    }

    // retrieves the image views to be changed from the given layout.

    private void getFrameArr(){
        TableLayout tl = findViewById(R.id.FoodLayout);

        for(int i=0; i<4; i++){
            ImageView iv;
            TableRow tr = (TableRow) tl.getChildAt(1);
            iv = (ImageView) tr.getChildAt(i);
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

    // retrieves the text views to be altered.
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

    // shows the food in the fridge using the lists with the corresponding views.

    public void show_food() {
        Calendar cal = Calendar.getInstance();
        int size=16;
        if(food_list.size()<16){
            size=food_list.size();
        }
        for (int i = 0; i < size; i++) {
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

            if (food_list.get(i).spoilsToday(cal)) {
                text.get(i).setTextColor(Color.rgb(255, 255, 0));
            } else if (food_list.get(i).alreadySpoiled(cal)) {
                text.get(i).setTextColor(Color.rgb(255, 0, 0));
            }
        }
        if(food_list.size()<16){
        for (int i = food_list.size(); i < 16; i++) {
            frames.get(i).setImageResource(R.drawable.empty);
            text.get(i).setText("");
        }
        }
    }
}

