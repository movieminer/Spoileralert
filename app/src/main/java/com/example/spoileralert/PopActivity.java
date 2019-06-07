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

    //creates a pop up to edit, delete or get a recipe from the selected product

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
                openActivityDelete();
            }
        });

        FloatingActionButton recipe_button = findViewById(R.id.recipe_button);

        recipe_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityRecipe();
            }
        });

        FloatingActionButton edit_button = findViewById(R.id.edit_button);

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityEdit();
            }
        });
    }

    private void openActivityDelete() {
        Intent intent = new Intent(this, DeleteActivity.class);
        startActivity(intent);
    }

    private void openActivityRecipe() {
        Intent intent = new Intent(this, PopRecipeActivity.class);
        intent.putExtra("index", index);
        startActivity(intent);
    }

    private void openActivityEdit() {
        Intent intent = new Intent(this, PopEditActivity.class);
        intent.putExtra("index", index);
        startActivity(intent);
        this.finish();
    }
}
