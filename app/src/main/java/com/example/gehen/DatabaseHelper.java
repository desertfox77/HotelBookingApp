package com.example.gehen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "hotel.db";

    public static final String TABLE_NAME = "member_table";
    public static final String COL1 = "MEMBERID";
    public static final String COL2 = "MEMBERNAME";
    public static final String COL3 = "MEMBEREMAIL";
    public static final String COL4 = "MEMBERPASSWORD";
    public static final String COL5 = "MEMBERPHONE";

    public static final String TABLE_NAME2 = "booking_table";
    public static final String KOL1 = "BOOKID";
    public static final String KOL2 = "MEMBERID";
    public static final String KOL3 = "HOTELID";
    public static final String KOL4 = "STARTDATE";
    public static final String KOL5 = "ENDDATE";
    public static final String KOL6 = "TOTALPRICE";

    public static final String createBooking = "CREATE TABLE " + TABLE_NAME2 + " (BOOKID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "MEMBERID INTEGER, HOTELID INTEGER, STARTDATE TEXT, ENDDATE TEXT, TOTALPRICE INTEGER)";





    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createBooking);
        db.execSQL("INSERT INTO booking_table (BOOKID, MEMBERID, HOTELID, STARTDATE, ENDDATE, TOTALPRICE) VALUES(1, 1, 3, '23-11-2019', '24-11-2019', 3509000) ");
        db.execSQL("INSERT INTO booking_table (BOOKID, MEMBERID, HOTELID, STARTDATE, ENDDATE, TOTALPRICE) VALUES(2, 1, 2, '24-12-2019', '28-12-2019', 12105260) ");
        db.execSQL("INSERT INTO booking_table (BOOKID, MEMBERID, HOTELID, STARTDATE, ENDDATE, TOTALPRICE) VALUES(3, 2, 1, '24-12-2019', '25-12-2019', 2896341) ");

        String createTable = "CREATE TABLE " + TABLE_NAME + " (MEMBERID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MEMBERNAME TEXT, MEMBEREMAIL TEXT, MEMBERPASSWORD TEXT, MEMBERPHONE TEXT)";
        db.execSQL(createTable);
        db.execSQL("INSERT INTO member_table (MEMBERID, MEMBERNAME, MEMBEREMAIL, MEMBERPASSWORD, MEMBERPHONE) VALUES(1,'New Admin', 'admin@gmail.com', 'Pika123', '087880373678') ");
        db.execSQL("INSERT INTO member_table (MEMBERID, MEMBERNAME, MEMBEREMAIL, MEMBERPASSWORD, MEMBERPHONE) VALUES(2,'Lala Luna', 'lala@gmail.com', 'Luna345', '081362112321') ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME2);
        onCreate(db);
    }
    public boolean addData(String name, String email, String password, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, name);
        contentValues.put(COL3, email);
        contentValues.put(COL4, password);
        contentValues.put(COL5, phone);

        long result = db.insert(TABLE_NAME,null,contentValues);

        if (result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean addDataBook(String memberid, String hotelid, String startdate, String enddate, String totalprice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KOL2, memberid);
        contentValues.put(KOL3, hotelid);
        contentValues.put(KOL4, startdate);
        contentValues.put(KOL5, enddate);
        contentValues.put(KOL6, totalprice);

        long result = db.insert(TABLE_NAME2,null,contentValues);

        if (result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean cekemail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM member_table WHERE MEMBEREMAIL =?", new String[]{email});
        if(cursor.getCount()>0) return false;
        else return true;
    }

    public Boolean login(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM member_table WHERE MEMBEREMAIL =? AND MEMBERPASSWORD =?", new String[]{email,password});
        if(cursor.getCount()>0) return true;
        else {
            return false;
        }

    }
    public Cursor getMemberId(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM member_table WHERE MEMBEREMAIL =? AND MEMBERPASSWORD =?", new String[]{email,password});
        return cursor;
    }

    public Cursor getBook(String memberid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM booking_table WHERE MEMBERID =?", new String[]{memberid});
        return cursor;
    }


    public void deleteNote(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME2, KOL1 + "=" + id , null);
        db.close();
    }

}
