package com.example.spoileralert;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PopEditActivity extends AppCompatActivity {

    private int index;
    private Food foo;
    private Context context;
    private Calendar cldr;
    private String date_product;
    private String quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_edit);
        context=this;
        index = getIntent().getIntExtra("index", 0);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        foo = MainActivity.getFood_list().get(index);

        TextView product = findViewById(R.id.edit_title_thing);
        product.setText(foo.getName());

        final Spinner spinner = findViewById(R.id.quantity_holder);

        Integer[] list = {100, 200,300, 400, 500, 600, 700, 800, 900, 1000, 1500, 2000};

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(dataAdapter);

        int pos = foo.getQuantity()/100;
        if(pos>10){
            if(pos==15){
                pos=11;
            }
            else{
                pos=12;
            }
        }
        pos= pos-1;

        spinner.setSelection(pos);

        final TextView text = findViewById(R.id.expire_date_holder_edit);
        text.setGravity(Gravity.CENTER);

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
                new DatePickerDialog(context, date, cldr
                        .get(Calendar.YEAR), cldr.get(Calendar.MONTH),
                        cldr.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        FloatingActionButton save_button = findViewById(R.id.save_button_edit);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity=spinner.getSelectedItem().toString();
                if(text.getText().equals("Spoil date")){
                    String myFormat = "dd-MM-yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                    Calendar cal = foo.getSpoil();
                    date_product=sdf.format(cal.getTime());
                }
                openActivitySAVE();
            }
        });
    }

    private void openActivitySAVE() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(date_product));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, MainActivity.class);
        MainActivity.removefood(index);
        MainActivity.add_food(new Food(Integer.parseInt(quantity), foo.getCategory(), foo.getName(), cal));
        startActivity(intent);
        this.finish();
    }

    private void updateLabel() {
        TextView text = findViewById(R.id.expire_date_holder_edit);
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        date_product=sdf.format(cldr.getTime());
        text.setText(sdf.format(cldr.getTime()));
    }
}
