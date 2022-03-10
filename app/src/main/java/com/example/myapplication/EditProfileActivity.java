package com.example.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity {
private final String URL = "http://192.168.56.1/API/UpdateProfile.php";
EditText firstname,lastname,email;
Button SubmitButton;
ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
    }

    void init()
    {
        firstname = findViewById(R.id.Editfirstname);
        lastname = findViewById(R.id.EditLastname);
        email = findViewById(R.id.EditEmail);
        SubmitButton = findViewById(R.id.EditSubmitButton);
        String newString;
        Bundle extras = getIntent().getExtras();
        newString= extras.getString("username");
        bar = findViewById( R.id.progressbar);
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstname.getText().toString().isEmpty())
                {
                    firstname.setError("Det här fältet är obligatoriskt");
                    firstname.requestFocus();
                }
                else if(lastname.getText().toString().isEmpty())
                {
                    lastname.setError("Det här fältet är obligatoriskt");
                    lastname.requestFocus();
                }
                else if(email.getText().toString().isEmpty())
                {
                    email.setError("Det här fältet är obligatoriskt");
                    email.requestFocus();
                }
                else {
                    bar.setVisibility(View.VISIBLE);
                    putdata(newString);
                }
            }
        });
    }
    void putdata(String username)
    {

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String [] inputfields = new String[4];
                inputfields[0]= "username";
                inputfields[1]="firstname";
                inputfields[2]="secondname";
                inputfields[3]="email";

                String [] data = new String[4];
                data[0]=username;
                data[1]=firstname.getText().toString().trim();
                data[2]=lastname.getText().toString().trim();
                data[3]=email.getText().toString().trim();

                PutData putData = new PutData(URL,"POST",inputfields,data);
                if(putData.startPut())
                {   if(putData.onComplete())
                    {   String res = putData.getResult();
                        Log.d("RESUlT",res);
                        if(res.equals("Data updated"))
                        {
                            bar.setVisibility(View.GONE);
                            Toast.makeText(EditProfileActivity.this, "Profilinfo uppdaterades", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),OptionsPage.class).putExtra("username",username));
                        }
                    }
                }
            }
        });
    }
}