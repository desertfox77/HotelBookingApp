package com.example.gehen;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gehen.arraydb.ApplicationDatabase;
import com.example.gehen.arraydb.MyBooking;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class GrandSetiabudi extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, OnMapReadyCallback {
    int abc ;
    GoogleMap map;
    Vector <MyBooking> myBookingVector = new Vector<>();
    DatabaseHelper hoteldb;

    Date chosenDate, chosenDate2;
    int sherrile = 0;
    int sheren = 0;
    int anjay;
    String s,e;


    TextView eins,zwei,drei,vier,funf,sechs,sieben,acht;

    Button checkin,checkout,book;

    public boolean isMessageAllowed(String permission){
        int allowed = ContextCompat.checkSelfPermission(this, permission);
        return (allowed == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grand_setiabudi);

        SupportMapFragment mapFragment =(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        hoteldb = new DatabaseHelper(this);
        eins = findViewById(R.id.pertama);
        zwei = findViewById(R.id.kedua);
        drei = findViewById(R.id.ketiga);
        vier = findViewById(R.id.keempat);
        funf = findViewById(R.id.kelima);
        sechs = findViewById(R.id.keenam);
        sieben = findViewById(R.id.ketujuh);
        acht = findViewById(R.id.kedelapan);

        checkin = findViewById(R.id.cancel);
        checkout = findViewById(R.id.cancel2);
        book = findViewById(R.id.cancel3);

        eins.setText(mainhome.hotelJSONList.get(ApplicationDatabase.cekhotel).getHotelname());
        zwei.setText("Phone: " + mainhome.hotelJSONList.get(ApplicationDatabase.cekhotel).getHotelphone());
        drei.setText("Address: "+ mainhome.hotelJSONList.get(ApplicationDatabase.cekhotel).getHoteladdress());
//        vier.setText("Latitude: " + ApplicationDatabase.dh.get(ApplicationDatabase.cekhotel).getLatitude());
//        funf.setText("Longitude: " + ApplicationDatabase.dh.get(ApplicationDatabase.cekhotel).getLongitude() );




        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abc=0;
                showDatePickerDialog();
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abc=1;
                if(sherrile == 1){
                    showDatePickerDialog();
                    sheren =1;
                }else {
                    Toast.makeText(getApplicationContext(), "Please input checkin date first!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sherrile==1 && sheren==1){

                    String memberid = Integer.toString(ApplicationDatabase.leo);
                    String hotelid = Integer.toString(mainhome.hotelJSONList.get(ApplicationDatabase.cekhotel).getHotelid()-1);
                    String totalprice = Integer.toString(anjay);

                    String message = mainhome.hotelJSONList.get(ApplicationDatabase.cekhotel).getHotelname() + " " +
                              s + " "
                            + e + " " + totalprice;
                    String nomor = ApplicationDatabase.phonenumber;

                    Boolean adddata = hoteldb.addDataBook(memberid,hotelid ,s, e,totalprice );
                    if(adddata == true){

                        Intent japan = new Intent(GrandSetiabudi.this, mainhome.class);
                        startActivity(japan);
                        Toast.makeText(getApplicationContext(), "Success first!", Toast.LENGTH_SHORT).show();
                        if(isMessageAllowed(Manifest.permission.SEND_SMS)){
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(nomor, null, message, null, null);
                            Toast.makeText(GrandSetiabudi.this, "Success Book and Message", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(GrandSetiabudi.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Please input checkout date first!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        if(abc==0){
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
        }else if(abc==1){
            datePickerDialog.getDatePicker().setMinDate(chosenDate.getTime()+86400000);
        }

        datePickerDialog.show();
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "-" +(month+1)+ "-"+year;
        if(abc==0){
            sechs.setText("Checkin: "+ date);
            s = date;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, dayOfMonth, 0, 0, 0);
            chosenDate = cal.getTime();

            sherrile = 1;



        }else if(abc==1){
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, dayOfMonth, 0, 0, 0);
            chosenDate2 = cal.getTime();
            e = date;
            long diff = chosenDate2.getTime() - chosenDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            sieben.setText("Checkout: " +date);

            long b = diffDays * mainhome.hotelJSONList.get(ApplicationDatabase.cekhotel).getHotelprice();
            anjay =(int) b;
            acht.setText("Total Price: "+b);

        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        double leo = mainhome.hotelJSONList.get(ApplicationDatabase.cekhotel).getLatitude();
        double leo2 = mainhome.hotelJSONList.get(ApplicationDatabase.cekhotel).getLongitude();
        LatLng sherly = new LatLng(leo, leo2);
        map.addMarker(new MarkerOptions().position(sherly).title("Pertama"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sherly));
    }
}
