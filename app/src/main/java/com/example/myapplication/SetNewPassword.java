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

public class SetNewPassword extends AppCompatActivity {
    EditText newPassword, verifyingPassword;
    String newString;
    Button btn;
    String URL = "http://193.10.223.254/API/API/PasswordUpdate.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);
        verifyingPassword = findViewById(R.id.Verfyingpassword);
        newPassword = findViewById(R.id.newPasswordinput);
        btn = findViewById(R.id.setpasswordButto);
        Bundle extras = getIntent().getExtras();
        newString= extras.getString("username");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(newPassword.getText().toString().equals(verifyingPassword.getText().toString()))){
                    verifyingPassword.setError("Matchar inte lösenordet du anget");
                    verifyingPassword.requestFocus();
                }else if(newPassword.getText().toString().isEmpty()){
                    newPassword.setError("Obligatoriskt fält");
                    newPassword.requestFocus();
                } else if(verifyingPassword.getText().toString().isEmpty()) {
                    verifyingPassword.setError("Obligatoriskt fält");
                    verifyingPassword.requestFocus();
                }
                else
                    putData();
            }
        });


    }
    void putData(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String [] inputfields = new String[2];
                inputfields[0]= "username";
                inputfields[1]= "password";
                String [] data = new String[4];
                data[0]=newString;
                data[1]=newPassword.getText().toString();
                PutData putData = new PutData(URL,"POST",inputfields,data);
                if(putData.startPut())
                {   if(putData.onComplete())
                {   String res = putData.getResult();
                    Log.d("RESUlT",res);
                    if(res.equals("Password updated"))
                    {
                        Toast.makeText(getApplicationContext(),"Nytt lösenord registrerades",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(),"reset failed",Toast.LENGTH_SHORT).show();
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