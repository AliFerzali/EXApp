package com.example.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DistansPage extends AppCompatActivity {
    Button Send,Back;
    EditText Text;
    String username, meddelande, URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distans_page);
        Bundle extras = getIntent().getExtras();
        username= extras.getString("username");
        URL = "http://193.10.223.254/API/API/distans.php";
        Send = (Button)findViewById(R.id.distans_send_button);
        Back = (Button)findViewById(R.id.back_button);
        Text = (EditText) findViewById(R.id.distans_edit_text);


        Send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!Text.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...",Toast.LENGTH_SHORT).show();
                    meddelande = Text.getText().toString();
                    Log.d("The message is ", "updateLabel2: "+ meddelande );
                    putdata();
                    moveToVerificationDistans(username);
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Wrong Credentials",Toast.LENGTH_SHORT).show();}
                //moveToVerificationDistans(username);
            } });
        Back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                moveToOptionsPage(username);
            } });

    }
    public void moveToVerificationDistans(String str){
        Intent intent = new Intent(this, VerificationDistans.class);
        startActivity(intent.putExtra("username",str));
    }
    public void moveToOptionsPage(String str){
        Intent intent = new Intent(this, OptionsPage.class);
        startActivity(intent.putExtra("username",str));
    }
    void putdata()
    {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String [] inputfields = new String[2];
                inputfields[0]= "username";
                inputfields[1]= "meddelande";
                String [] data = new String[2];
                data[0]=username;
                data[1]=meddelande;
                PutData putData = new PutData(URL,"POST",inputfields,data);
                if(putData.startPut())
                {   if(putData.onComplete())
                {   String res = putData.getResult();
                    Log.d("RESUlT",res);
                    if(res.equals("Inserted successfully"))
                    {
                        moveToOptionsPage(username);
                    }else{
                        Toast.makeText(getApplicationContext(),"Failed to insert to semestertabell",Toast.LENGTH_SHORT).show();
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