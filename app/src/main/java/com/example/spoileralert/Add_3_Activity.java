package com.example.spoileralert;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Add_3_Activity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_add_food_3);

        TextView product_place = findViewById(R.id.product_place);
        product_place.setText(getIntent().getStringExtra("name"));

        TextView quantity_place = findViewById(R.id.quantity_place);
        quantity_place.setText(getIntent().getStringExtra("quantity"));

        TextView expdate_place = findViewById(R.id.expiredate_place);
        expdate_place.setText(getIntent().getStringExtra("date"));

        FloatingActionButton add_button = findViewById(R.id.add_button_3);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityADD();
            }
        });
    }

    //Adds the newly created food to the list, creates a new main activity and closes the add activity
    private void openActivityADD() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(getIntent().getStringExtra("date")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        MainActivity.add_food(new Food(Integer.parseInt(getIntent().getStringExtra("quantity")),getIntent().getStringExtra("category"), getIntent().getStringExtra("name"), cal));
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
        this.finish();
    }

}
