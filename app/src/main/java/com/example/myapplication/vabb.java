package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class vabb extends AppCompatActivity {
    Button Day,Days, Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vabb);
        String newString;
        Bundle extras = getIntent().getExtras();
        newString= extras.getString("username");
        Back = (Button)findViewById(R.id.back_button);
        /*Back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                moveToOptionsPage();
            } });*/
        Day = (Button)findViewById(R.id.vabbOneDay_button);
        Day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToVabbInfo(newString);
            }
        });
        Days = (Button) findViewById(R.id.VabbManydays_button);
        Days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToVabbInfoflerFler(newString);
            }
        });
    }

    public void moveToOptionsPage(){
        Intent intent = new Intent(this, OptionsPage.class);
        startActivity(intent);
    }
    public void moveToVabbInfo(String username){
        Intent intent = new Intent(this, vabbInfo.class);
        startActivity(intent.putExtra("username", username));
    }
    public void moveToVabbInfoflerFler(String username){
        Intent intent = new Intent(this, vabbInfoFler.class);
        startActivity(intent.putExtra("username", username));
    }
}