package com.example.spoileralert;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_food);

        Spinner spinner1 = findViewById(R.id.spinner1);


        String[] list = {"Meat", "Vegetables","Liquids", "Breakfast"};


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner1.setAdapter(dataAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            String[] Meat = {"Cow", "Pork", "Chicken", "Fish"};
            String[] Vegetables = {"Lettuce", "Broccoli", "Paprika", "Cucumber"};
            String[] Liquids = {"Milk", "Yoghurt", "Orange juice"};
            String[] Breakfast = {"Eggs", "Cheese", "Butter?"};

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                   setSpinner2(Meat);
                }
                else if(position==1){
                    setSpinner2(Vegetables);
                }
                else if(position==2){
                    setSpinner2(Liquids);
                }
                else if(position==3){
                    setSpinner2(Breakfast);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton add_button = findViewById(R.id.add_button_1);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityADD();
            }
        });
}

    public void openActivityADD() {
        Intent intent = new Intent(this, Add_2_Activity.class);
        startActivity(intent);
    }

    public void setSpinner2(String[] arr){

        Spinner spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                R.layout.spinner_layout, arr);
        dataAdapter2.setDropDownViewResource(R.layout.spinner_layout);
        spinner2.setAdapter(dataAdapter2);
    }
}
