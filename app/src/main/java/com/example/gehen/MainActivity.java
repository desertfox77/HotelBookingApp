package com.example.gehen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gehen.arraydb.ApplicationDatabase;


public class MainActivity extends AppCompatActivity {

    TextView registrasi;
    EditText fullname, email, password, phonenumber;
    Button register;
    DatabaseHelper hoteldb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hoteldb = new DatabaseHelper(this);
        registrasi = (TextView) findViewById(R.id.registrasi);
        fullname = (EditText)findViewById(R.id.fullname);
        email = (EditText)findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        phonenumber =(EditText) findViewById(R.id.phonenumber);
        register = (Button)findViewById(R.id.register);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s1 = fullname.getText().toString();
                String s2 = email.getText().toString();
                String s3 = password.getText().toString();
                String s4 = phonenumber.getText().toString();

                if(s1.isEmpty() || s2.isEmpty() || s3.isEmpty() || s4.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Fields are empty", Toast.LENGTH_SHORT).show();
                }else if(!s1.contains(" ")){
                    Toast.makeText(getApplicationContext(),"Two Words!", Toast.LENGTH_SHORT).show();
                }else if(!emailValidation(s2)) {
                    Toast.makeText(getApplicationContext(),"Invalid Email!", Toast.LENGTH_SHORT).show();
                }else if(!passwordValidation(s3)){
                    Toast.makeText(getApplicationContext(),"Invalid Password!", Toast.LENGTH_SHORT).show();
                }else if(!s4.startsWith("+62")){
                    Toast.makeText(getApplicationContext(),"Invalid Phonenumber!", Toast.LENGTH_SHORT).show();
                }else {
                    addingUser(s1,s2,s3,s4);
                }

            }
        });
    }

    private static boolean passwordValidation (String pass)
    {
        if ( pass.length() < 5) {
            return false;
        }

            boolean hurufbesar=false;
            boolean hurufkecil=false;
            boolean angka=false;

        for(char character : pass.toCharArray())
        {
            if (character >='a'&& character <='z'){
                hurufkecil = true;
            }else if (character >='A'&& character <='Z'){
                hurufbesar =true;
            }else if ( character >='0' && character<='9'){
                angka = true;
            }

        }
        return (hurufbesar && hurufkecil && angka);
    }

    private boolean emailValidation(String email){

        if (email.startsWith("@") || email.startsWith(".")){
            return false;
        }
        if (email.endsWith(".") || email.endsWith("@")){
            return false;
        }
        if(!email.contains("@")){
            return false;
        }
        if (email.contains("@.")){
            return false;
        }

        for (int i = 0; i < email.lastIndexOf("@"); i++){
            if (!Character.isAlphabetic(email.charAt(i)) &&
                    !Character.isDigit(email.charAt(i)) && email.charAt(i)== '@'){

                return false;
            }
        }

        return true;
    }


    public void addingUser(String s1, String s2, String s3, String s4 ){
        Boolean cekemail = hoteldb.cekemail(s2);
        if(cekemail == true) {
            Boolean adddata = hoteldb.addData(s1, s2, s3, s4);
            if (adddata == true) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Succesful Registration!", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
