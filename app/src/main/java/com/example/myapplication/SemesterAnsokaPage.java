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
        String newString;
        Bundle extras = getIntent().getExtras();
        newString= extras.getString("username");
        Back = (Button)findViewById(R.id.back_button);
        Back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                moveToOptionsPage();
            } });
        Day = (Button)findViewById(R.id.day_button);
        Day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToSemesterInfo(newString);
            }
        });
        Days = (Button) findViewById(R.id.days_button);
        Days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToSemesterInfofler(newString);
            }
        });
    }
    public void moveToOptionsPage(){
        Intent intent = new Intent(this, OptionsPage.class);
        startActivity(intent);
    }
    public void moveToSemesterInfo(String username){
        Intent intent = new Intent(this, semesterInfo.class);
        startActivity(intent.putExtra("username", username));
    }
    public void moveToSemesterInfofler(String username){
        Intent intent = new Intent(this, SemesterinfoflerActivity.class);
        startActivity(intent.putExtra("username", username));
    }
}