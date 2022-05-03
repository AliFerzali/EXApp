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

public class NewPasswordActivity extends AppCompatActivity {
EditText password,confirmation;
Button submit;
private final String URL = "http://193.10.223.115/API/API/PasswordUpdate.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        init();
    }

    void init()
    {
        password = findViewById(R.id.new_PasswordText);
        confirmation = findViewById(R.id.editText2);
        submit = findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitChanges();
            }
        });
    }

    void SubmitChanges()
    {
        String pass,Conf;
        pass = password.getText().toString().trim();
        Conf =confirmation.getText().toString().trim();
        String newString;
        Bundle extras = getIntent().getExtras();
        newString= extras.getString("username");
        if(pass.isEmpty())
        {
            password.setError("Fyll i detta fält");
            password.requestFocus();
        }
        else if (Conf.isEmpty() || !(pass.equals(Conf)))
        {
            confirmation.setError("Fyll i detta fält");
            confirmation.requestFocus();
        }
        else
        {
            putdata(newString,pass);
        }
    }

    void putdata(String username,String password)
    {

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String [] inputfields = new String[2];
                inputfields[0]= "username";
                inputfields[1]="password";


                String [] data = new String[2];
                data[0]=username;
                data[1]=password;


                PutData putData = new PutData(URL,"POST",inputfields,data);
                if(putData.startPut())
                {   if(putData.onComplete())
                {   String res = putData.getResult();
                    Log.d("RESUlT",res);
                    if(res.equals("Password updated"))
                    {
                        Toast.makeText(NewPasswordActivity.this, "Lösenord uppdaterades", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),OptionsPage.class).putExtra("username",username));
                    }
                    else
                        Toast.makeText(NewPasswordActivity.this, "Ett fel uppstod, testa senare.", Toast.LENGTH_SHORT).show();
                }
                }
            }
        });
    }
}