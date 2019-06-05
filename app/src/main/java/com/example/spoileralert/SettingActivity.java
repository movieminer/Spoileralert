package com.example.spoileralert;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {

    private TextView view;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);
        view = findViewById(R.id.time_view);
        FloatingActionButton back_button = findViewById(R.id.back_button);
        context=this;

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityBack();
            }
        });

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

        Switch toggle = findViewById(R.id.switch1);
        toggle.setChecked(true);
    }

    public void openActivityBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
