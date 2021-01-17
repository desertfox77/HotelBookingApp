package com.example.gehen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.Bundle;

import android.widget.Button;
import android.widget.Toast;

import com.example.gehen.arraydb.ApplicationDatabase;

import android.widget.ListView;
import com.example.gehen.arraydb.MyBooking;
import com.example.gehen.arraydb.MyBookingAdapter;

import java.util.Vector;

public class Indonesia extends AppCompatActivity {


    DatabaseHelper hoteldb;
    ListView indonesia;
    Vector<MyBooking> myBookingVector = new Vector<>();
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indonesia);

        hoteldb = new DatabaseHelper(this);
        Cursor data = hoteldb.getBook(Integer.toString(ApplicationDatabase.leo));


        cancel = (Button) findViewById(R.id.cancel);

        if(data.getCount()==0){
            Toast.makeText(getApplicationContext(),"No Data!", Toast.LENGTH_SHORT).show();
        }else{
            while(data.moveToNext()){

                MyBooking m = new MyBooking();

                m.setMybookingid(Integer.parseInt(data.getString(0)));
                m.setHotelnama(mainhome.hotelJSONList.get(Integer.parseInt(data.getString(2))).getHotelname());
                m.setHotelalamat(mainhome.hotelJSONList.get((Integer.parseInt(data.getString(2)))).getHoteladdress());
                m.setStartdate(data.getString(3));
                m.setEnddate(data.getString(4));
                m.setTotalprice(Integer.parseInt(data.getString(5)));

                myBookingVector.add(m);

            }
        }

        indonesia = findViewById(R.id.indonesia);
        MyBookingAdapter myBookingAdapter = new MyBookingAdapter(Indonesia.this, myBookingVector);
        indonesia.setAdapter(myBookingAdapter);


    }

}
