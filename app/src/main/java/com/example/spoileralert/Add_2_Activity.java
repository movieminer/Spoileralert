package com.example.spoileralert;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Add_2_Activity extends AppCompatActivity {

    private Context context = this;
    private Calendar cldr;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_add_food_2);


        Spinner spinner1 = findViewById(R.id.spinner1);


        Integer[] list = {100, 200,300, 400, 500, 600, 700, 800, 900, 1000, 1500};

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner1.setAdapter(dataAdapter);

        TextView text = findViewById(R.id.expireDate);

        //text.setInputType(InputType.TYPE_NULL);
        cldr = Calendar.getInstance();

               // date picker dialog
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                cldr.set(Calendar.YEAR, year);
                cldr.set(Calendar.MONTH, monthOfYear);
                cldr.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date, cldr
                        .get(Calendar.YEAR), cldr.get(Calendar.MONTH),
                        cldr.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //add_button.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View v) {
              //  openActivityADD();
            //}
        //});
    }

    public void openActivityADD() {
        Intent intent = new Intent(this, Add_2_Activity.class);
        //intent.putExtra("date", name);
       // intent.putExtra("quantity", )
        startActivity(intent);
    }


    private void updateLabel() {
        TextView text = findViewById(R.id.expireDate);
        String myFormat = "dd-MM-yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        text.setText(sdf.format(cldr.getTime()));
    }
}
