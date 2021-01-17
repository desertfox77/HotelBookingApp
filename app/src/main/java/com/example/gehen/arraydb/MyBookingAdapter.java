package com.example.gehen.arraydb;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.gehen.DatabaseHelper;
import com.example.gehen.R;

import java.util.Vector;

public class MyBookingAdapter extends BaseAdapter {


    private static final String TAG  = "Indonesia";
    Context context;
    Vector<MyBooking> myBookingVector;
    DatabaseHelper hoteldb;



    public MyBookingAdapter(Context context, Vector<MyBooking> myBookingVector) {
        this.context = context;
        this.myBookingVector = myBookingVector;
        hoteldb = new DatabaseHelper(this.context);
    }

    @Override
    public int getCount() {
        return myBookingVector.size();
    }

    @Override
    public Object getItem(int i) {
        return myBookingVector.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.row, viewGroup, false);

        Button cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog(i,"Are you sure?", "Ok to cancel the booking, cancel to dismiss ", "cancelMethod1", "okMethod1");
            }
        });

        MyBooking p = myBookingVector.get(i);
        TextView leo1 = view.findViewById(R.id.pertama);
        TextView leo2 = view.findViewById(R.id.kedua);
        TextView leo3 = view.findViewById(R.id.ketiga);
        TextView leo4 = view.findViewById(R.id.keempat);
        TextView leo5 = view.findViewById(R.id.kelima);

        leo1.setText(p.getHotelnama());
        leo2.setText(p.getHotelalamat());
        leo3.setText(p.getStartdate());
        leo4.setText(p.getEnddate());
        leo5.setText("Rp " + p.getTotalprice());

        return view;
    }

    private void cancelMethod1(){
        Log.d(TAG, "cancelMethod1: Called. ");
        toastMessage("Your booking is looking great!");
    }

    private void okMethod1(){
        Log.d(TAG, "okMethod1: Called.");
        toastMessage("Sucessful canceling your booking!");
    }


    public void customDialog(int i, String title, String message, final String cancelMethod, final String okMethod ){
        final androidx.appcompat.app.AlertDialog.Builder builderSingle = new androidx.appcompat.app.AlertDialog.Builder(context);
        builderSingle.setIcon(R.drawable.ic_stat_name);
        builderSingle.setTitle(title);
        builderSingle.setMessage(message);
        final int positionToRemove = i;


        builderSingle.setNegativeButton(
                "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG,"onClick: Cancel Called.");
                        if(cancelMethod.equals("cancelMethod1")) {
                            cancelMethod1();
                        }
                    }
                });

        builderSingle.setPositiveButton(
                "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: Ok Called.");
                        if(okMethod.equals("okMethod1")){

                            hoteldb.deleteNote(Integer.toString(myBookingVector.get(positionToRemove).getMybookingid()));
                            myBookingVector.remove(positionToRemove);

                            notifyDataSetChanged();

                            okMethod1();

                        }
                    }
                });

        builderSingle.show();

    }

    public void toastMessage(String message){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
    }
}
