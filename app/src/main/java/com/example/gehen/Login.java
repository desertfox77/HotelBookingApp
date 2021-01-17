package com.example.gehen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gehen.arraydb.ApplicationDatabase;

public class Login extends AppCompatActivity{

    TextView welcome;
    EditText email, password;
    Button login;
    String s1,s2;
    DatabaseHelper hoteldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        hoteldb = new DatabaseHelper(this);
        welcome = (TextView) findViewById(R.id.welcome);
        email =(EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.sherrile);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1 = email.getText().toString();
                s2 = password.getText().toString();
                login(s1,s2);
            }
        });
    }

    public void login(String s1, String s2){
        Cursor data = hoteldb.getMemberId(s1,s2);
        Boolean emailpass = hoteldb.login(s1,s2);
        if(emailpass == true){
            Toast.makeText(getApplicationContext(),"Login Succesful!", Toast.LENGTH_SHORT).show();
            Intent j = new Intent(Login.this, mainhome.class);

            if(data.getCount()==0){
                Toast.makeText(getApplicationContext(),"No Data!", Toast.LENGTH_SHORT).show();
            }else{
                while(data.moveToNext()){
                    Toast.makeText(getApplicationContext(),"Login Success!", Toast.LENGTH_SHORT).show();
                    ApplicationDatabase.leo = Integer.parseInt(data.getString(0));
                    ApplicationDatabase.phonenumber = data.getString(4);
                }
            }
            startActivity(j);

            } else if (s1.isEmpty() || s2.isEmpty()) {
                Toast.makeText(getApplicationContext(),"Email or Password is empty!", Toast.LENGTH_SHORT).show();

            }else{
            Toast.makeText(getApplicationContext(),"Wrong Email or Password!", Toast.LENGTH_SHORT).show();
        }
        }
    }




