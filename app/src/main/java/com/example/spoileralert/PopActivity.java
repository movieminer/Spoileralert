package com.example.spoileralert;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class PopActivity extends AppCompatActivity {

    private int index;
    private Food foo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        index = getIntent().getIntExtra("index",0);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8),(int) (height*.6));

        foo = MainActivity.getFood_list().get(index);

        TextView product = findViewById(R.id.product_place_pop);
        product.setText(foo.getName());

        TextView quantity = findViewById(R.id.quantity_place_pop);
        quantity.setText(String.valueOf(foo.getQuantity()));

        TextView expDate = findViewById(R.id.exp_date_pop);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        expDate.setText(sdf.format(foo.getSpoil().getTime()));

        FloatingActionButton delete_button = findViewById(R.id.remove_button);

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.removefood(index);
                openActivityMain();
            }
        });
    }

    private void openActivityMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
