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
    Button semester,Vabb;
    ImageButton profileButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_page);
        Distans = (Button)findViewById(R.id.distansarbete_button);
        profileButton = findViewById(R.id.mini_profile_icon);
        String newString;
        Bundle extras = getIntent().getExtras();
        newString= extras.getString("username");
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
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToProfile(newString);
            }
        });
        Vabb = (Button)findViewById(R.id.vabb_button);
        Vabb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToVabb();
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
    private void moveToProfile(String uname)
    {
        startActivity(new Intent(getApplicationContext(),ProfileInfo.class).putExtra("username",uname));
    }
    public void  moveToVabb(){
        Intent intent = new Intent(this, vabb.class);
        startActivity(intent);
    }
}

