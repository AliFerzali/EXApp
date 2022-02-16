package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SemesterAnsokaPage extends AppCompatActivity {
    Button Day,Days, Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester_ansoka_page);
        Back = (Button)findViewById(R.id.back_button);
        Back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                moveToOptionsPage();
            } });
    }
    public void moveToOptionsPage(){
        Intent intent = new Intent(this, OptionsPage.class);
        startActivity(intent);
    }
}