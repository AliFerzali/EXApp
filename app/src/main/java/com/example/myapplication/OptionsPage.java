package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class OptionsPage extends AppCompatActivity {
    Button Distans;
    Button semester;
    ImageButton myProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_page);
        String newString;
        Bundle extras = getIntent().getExtras();
        newString= extras.getString("username");
        Distans = (Button)findViewById(R.id.distansarbete_button);
        myProfileButton = (ImageButton)findViewById(R.id.mini_profile_icon);

        Distans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToDistansPage();
            }});
        semester = (Button)findViewById(R.id.semesteransokan_button);
        semester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToSemesterAnsokaPage();
            }});
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToProfileInfo(newString);
            }
        });

    }
    public void  moveToDistansPage(){
        Intent intent = new Intent(this, DistansPage.class);
        startActivity(intent);
    }
    public void  moveToSemesterAnsokaPage(){
        Intent intent = new Intent(this, SemesterAnsokaPage.class);
        startActivity(intent);
    }
    public void moveToProfileInfo(String uname)
    {
        startActivity(new Intent(this, ProfileInfo.class).putExtra("username",uname));
    }
}