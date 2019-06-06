package com.example.spoileralert;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {

    private TextView view;
    private Context context;
    private Switch toggle;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String SWITCH = "switch";

    public static String time;
    public static boolean switchOnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);
        view = findViewById(R.id.time_view);
        context=this;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            TimePickerDialog TimePicker;
            TimePicker =new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet (TimePicker timePicker, int selectedHour, int selectedMinute){

                    String hour;
                    String minute;

                    if(selectedHour<10) {
                        hour = "0" + selectedHour;
                        if (selectedMinute < 10) {
                            minute = "0" + selectedMinute;
                        }
                        else{
                            minute= String.valueOf(selectedMinute);
                        }
                    }
                    else {
                        hour = String.valueOf(selectedHour);
                            if (selectedMinute < 10) {
                                minute = "0" + selectedMinute;
                            } else {
                                minute = String.valueOf(selectedMinute);
                            }
                        }
                    view.setText(hour + ":" + minute);
                }
            },hour,minute,true);//Yes 24 hour time
                TimePicker.setTitle("Select Time");
                TimePicker.show();
            }
        });

        toggle = findViewById(R.id.switch1);
        toggle.setChecked(true);

        Button save_settings = findViewById(R.id.save_settings);

        save_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        loadData();
        updateViews();
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT, view.getText().toString());
        editor.putBoolean(SWITCH, toggle.isChecked());

        editor.apply();

        Toast.makeText(this, "Settings saved successfully", Toast.LENGTH_SHORT).show();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        time = sharedPreferences.getString(TEXT, "15:00");
        switchOnOff = sharedPreferences.getBoolean(SWITCH, true);
    }

    public void updateViews(){
        view.setText(time);
        toggle.setChecked(switchOnOff);
    }

    public void openActivityBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
