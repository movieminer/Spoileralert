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
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity {

    private String name= "state";
    private String category="placeholder";

    //requests the first user input, namely the name and catagory

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_food);

        Spinner spinner1 = findViewById(R.id.spinner1);




        String[] list = {"Meat", "Vegetables", "Liquids", "Dairy/Eggs" };


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner1.setAdapter(dataAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            String[] Meat = {"Beef", "Chicken", "Fish", "Pork"};
            String[] Vegetables = {"Broccoli", "Cucumber", "Lettuce", "Paprika"};
            String[] Liquids = {"Beer", "Fruit Juice", "Orange Juice"};
            String[] DairyEggs = { "Butter", "Cheese", "Crème fraîche", "Eggs", "Milk", "Whipped Cream", "Yogurt"};

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    category="Meat";
                   setSpinner2(Meat);
                }
                else if(position==1){
                    category="Vegetables";
                    setSpinner2(Vegetables);
                }
                else if(position==2){
                    category="Liquids";
                    setSpinner2(Liquids);
                }
                else if(position==3){
                    category="DiaryEggs";
                    setSpinner2(DairyEggs);
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
    //opens the new add activity and gives the user input along with the intent
    public void openActivityADD() {
        Intent intent = new Intent(this, Add_2_Activity.class);
        intent.putExtra("name", name);
        intent.putExtra("category", category);
        startActivity(intent);
        this.finish();

    }

    //sets array on the second spinner, uses array input to make it so that it relies on the choice of the first spinner
    public void setSpinner2(String[] arr){

        final Spinner spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(this,
                R.layout.spinner_layout, arr);
        dataAdapter2.setDropDownViewResource(R.layout.spinner_layout);
        spinner2.setAdapter(dataAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                name=spinner2.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
