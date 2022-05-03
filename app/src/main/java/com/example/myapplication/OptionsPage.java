package com.example.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class OptionsPage extends AppCompatActivity {
    Button Distans;
    Button semester,Vabb,sjukfranvaro, redanFinns;
    ImageButton profileButton;
    String url,url1;
    String newString;
    String username, quantity, start_date, end_date,URL,str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_page);
        Distans = (Button)findViewById(R.id.distansarbete_button);
        profileButton = findViewById(R.id.mini_profile_icon);
        Bundle extras = getIntent().getExtras();
        url = "http://192.168.0.9/API/API/option.php";
        url1 = "http://192.168.0.9/API/API/avslutaVabb.php";
        newString= extras.getString("username");
        Distans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToDistansPage(newString);
            }});
        semester = (Button)findViewById(R.id.semesteransokan_button);
        semester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToSemesterAnsokaPage(newString);
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
                putdata();
                //moveToVabb(newString);
            }
        });
        redanFinns = (Button)findViewById(R.id.redanFinnsVabb_button);
        redanFinns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avslutaVabb(newString);
            }
        });
        sjukfranvaro = (Button) findViewById(R.id.sjukfranvaro_button);
        sjukfranvaro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToSjukfranvaroAnsoka(newString);
            }
        });
    }
    public void  moveToDistansPage(String username){
        Intent intent = new Intent(this, DistansPage.class);
        startActivity(intent.putExtra("username",username));
    }
    public void avslutaVabb(String username){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String [] inputfields = new String[2];
                inputfields[0]= "username";
                inputfields[1]="avsluta";
                String [] data = new String[2];
                data[0]=newString;
                data[1]= "avsluta";


                PutData putData = new PutData(url1,"POST",inputfields,data);
                if(putData.startPut())
                {   if(putData.onComplete())
                {   String res = putData.getResult();
                    Log.d("RESUlT",res);
                    if(res.equals("DO UR THING"))
                    {
                        redanFinns.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Du har avslutat din aktivitet",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getApplicationContext(),"Finns redan",Toast.LENGTH_SHORT).show();
                        redanFinns.setVisibility(View.VISIBLE);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"On complete is not completed",Toast.LENGTH_SHORT).show();
                }
                }else{
                    Toast.makeText(getApplicationContext(),"failed putting data",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    public void  moveToSemesterAnsokaPage(String username){
        Intent intent = new Intent(this, SemesterAnsokaPage.class);
        startActivity(intent.putExtra("username",username));
    }
    public void  moveToSjukfranvaroAnsoka(String username){
        Intent intent = new Intent(this, sjukfranvaro_ansoka.class);
        startActivity(intent.putExtra("username",username));
    }
    private void moveToProfile(String uname)
    {
        startActivity(new Intent(getApplicationContext(),ProfileInfo.class).putExtra("username",uname));
    }
    public void  moveToVabb(String username){
        Intent intent = new Intent(this, vabb.class);
        startActivity(intent.putExtra("username",username));
    }

    void putdata()
    {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String [] inputfields = new String[2];
                inputfields[0]= "username";
                inputfields[1]="vabbCheck";
                String [] data = new String[2];
                data[0]=newString;
                data[1]= "vabbCheck";


                PutData putData = new PutData(url,"POST",inputfields,data);
                if(putData.startPut())
                {   if(putData.onComplete())
                {   String res = putData.getResult();
                    Log.d("RESUlT",res);
                    if(res.equals("DO UR THING"))
                    {
                        moveToVabb(newString);
                    }else{
                        Toast.makeText(getApplicationContext(),"Finns redan",Toast.LENGTH_SHORT).show();
                        redanFinns.setVisibility(View.VISIBLE);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"On complete is not completed",Toast.LENGTH_SHORT).show();
                }
                }else{
                    Toast.makeText(getApplicationContext(),"failed putting data",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}

