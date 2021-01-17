package com.example.gehen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PagesTwo extends AppCompatActivity {

        TextView welcome, k;
        Button l, r;
        pl.droidsonroids.gif.GifImageView abc;

    final int SEND_PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages_two);

                welcome = findViewById(R.id.textView3);
                k = findViewById(R.id.textView);
                l = findViewById(R.id.login);
                r = findViewById(R.id.Register);
                abc = findViewById(R.id.imageView);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_PERMISSION_REQUEST_CODE);

                l.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent japan = new Intent(PagesTwo.this, Login.class);
                        startActivity(japan);
                    }
                });

                r.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent japan = new Intent(PagesTwo.this, MainActivity.class);
                        startActivity(japan);
                    }
                });



    }

    }

