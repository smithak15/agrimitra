package com.net.mercuryworld.chsc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.CalendarContract;
import android.provider.Settings;

import com.net.mercuryworld.chsc.db.CreateBookingContract.*;
import com.net.mercuryworld.chsc.identity.Booking;
import com.net.mercuryworld.chsc.identity.Center;
import com.net.mercuryworld.chsc.identity.Farmer;
import com.net.mercuryworld.chsc.identity.Implement;
import com.net.mercuryworld.chsc.identity.Tractor;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Smitha on 5/1/2017.
 */

public class CreateBookingDbHelper extends SQLiteOpenHelper {



    private static final String DATABASE_NAME = "mercurychsc.db";
    private static final int DATABASE_VERSION = 3;

    public CreateBookingDbHelper(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        final String SQL_CREATE_FARMER_TABLE = "CREATE TABLE "+
                FarmerEntry.TABLE_NAME + " (" +
                FarmerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FarmerEntry.COLUMN_FARMER_NAME + " TEXT NOT NULL," +
                FarmerEntry.COLUMN_FARMER_PHONE + " INTEGER NOT NULL," +
                FarmerEntry.COLUMN_FARMER_VILLAGE + " TEXT NOT NULL" +
                ");";

        final String SQL_CREATE_CENTER_TABLE = "CREATE TABLE "+
                CenterEntry.TABLE_NAME + " (" +
                CenterEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CenterEntry.COLUMN_CENTER_NAME + " TEXT NOT NULL" +
                ");";

        final String SQL_CREATE_TRACTOR_TABLE = "CREATE TABLE "+
                TractorEntry.TABLE_NAME + " (" +
                TractorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                TractorEntry.COLUMN_TRACTOR_NAME + " TEXT NOT NULL,"+
                TractorEntry.COLUMN_TRACTOR_TYPE + " TEXT NOT NULL,"+
                TractorEntry.COLUMN_CENTER_ID + " INTEGER NOT NULL,"+
                " FOREIGN KEY(" + TractorEntry.COLUMN_CENTER_ID +") REFERENCES "+
                CenterEntry.TABLE_NAME+"(" + CenterEntry._ID + "));";

        final String SQL_CREATE_IMPLEMENT_TABLE = "CREATE TABLE "+
                ImplementEntry.TABLE_NAME + " (" +
                ImplementEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                ImplementEntry.COLUMN_IMPLEMENT_TYPE + " TEXT NOT NULL,"+
                ImplementEntry.COLUMN_HOURLY_PRICE + " REAL NOT NULL);";

                /*ImplementEntry.COLUMN_CENTER_ID + " INTEGER NOT NULL," +
                " FOREIGN KEY(" + ImplementEntry.COLUMN_CENTER_ID +") REFERENCES "+
                CenterEntry.TABLE_NAME+"(" + ImplementEntry.COLUMN_CENTER_ID + "));"*/

        final String SQL_CREATE_BOOKING_TABLE = "CREATE TABLE "+
                BookingEntry.TABLE_NAME + " ("+
                BookingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                BookingEntry.COLUMN_BOOKING_ID + " TEXT NOT NULL UNIQUE,"+
                BookingEntry.COLUMN_CENTER_ID + " INTEGER NOT NULL,"+
                BookingEntry.COLUMN_DRIVER + " TEXT,"+
                BookingEntry.COLUMN_FARMER_ID + " INTEGER NOT NULL,"+
                BookingEntry.COLUMN_TRACTOR_ID + " INTEGER NOT NULL,"+
                BookingEntry.COLUMN_IMPLEMENT_ID + " INTEGER NOT NULL,"+
                BookingEntry.COLUMN_CROP_NAME + " TEXT,"+
                BookingEntry.COLUMN_LAND_SIZE + " REAL,"+
                BookingEntry.COLUMN_METER_START + " REAL,"+
                BookingEntry.COLUMN_METER_END + " REAL,"+
                BookingEntry.COLUMN_START_DATE_TIME + " TEXT,"+
                BookingEntry.COLUMN_END_DATE_TIME + " TEXT,"+
                BookingEntry.COLUMN_WORKING_HOURS + " REAL,"+
                BookingEntry.COLUMN_TRANSPORT_HOURS + " REAL,"+
                BookingEntry.COLUMN_WORKING_CHARGE + " REAL,"+
                BookingEntry.COLUMN_TRANSPORT_CHARGE + " REAL,"+
                BookingEntry.COLUMN_TOTAL_KMS + " REAL,"+
                BookingEntry.COLUMN_TOTAL_AMOUNT + " REAL,"+
                " FOREIGN KEY(" + BookingEntry.COLUMN_FARMER_ID + ") REFERENCES "+
                FarmerEntry.TABLE_NAME + "("+FarmerEntry._ID + "),"+
                " FOREIGN KEY(" + BookingEntry.COLUMN_TRACTOR_ID + ") REFERENCES "+
                TractorEntry.TABLE_NAME + "("+TractorEntry._ID + "),"+
                " FOREIGN KEY(" + BookingEntry.COLUMN_IMPLEMENT_ID + ") REFERENCES "+
                ImplementEntry.TABLE_NAME + "("+ImplementEntry._ID + "),"+
                " FOREIGN KEY(" + BookingEntry.COLUMN_CENTER_ID +") REFERENCES "+
                CenterEntry.TABLE_NAME+"(" + CenterEntry._ID + "));";

       /* System.out.println(SQL_CREATE_CENTER_TABLE);
        System.out.println(SQL_CREATE_FARMER_TABLE);
        System.out.println(SQL_CREATE_TRACTOR_TABLE);
        System.out.println(SQL_CREATE_IMPLEMENT_TABLE);*/
        System.out.println(SQL_CREATE_BOOKING_TABLE);

        sqLiteDatabase.execSQL(SQL_CREATE_CENTER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FARMER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TRACTOR_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_IMPLEMENT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_BOOKING_TABLE);
        insertSetupData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int oldversion,int newversion){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+CenterEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FarmerEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TractorEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ImplementEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+BookingEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }



    public static void insertSetupData(SQLiteDatabase db) {
        if (db == null) {
            return;
        }
        List<ContentValues> centerList = new ArrayList<>();
        List<ContentValues> tractorList = new ArrayList<>();
        List<ContentValues> implementList = new ArrayList<>();


        ContentValues cv = new ContentValues();
        cv = new ContentValues();
        cv.put(CreateBookingContract.CenterEntry.COLUMN_CENTER_NAME, "AVANI");
        centerList.add(cv);
        cv = new ContentValues();
        cv.put(CreateBookingContract.CenterEntry.COLUMN_CENTER_NAME, "BYRAKUR");
        centerList.add(cv);
        cv = new ContentValues();
        cv.put(CreateBookingContract.CenterEntry.COLUMN_CENTER_NAME, "DALASNUR");
        centerList.add(cv);
        cv = new ContentValues();
        cv.put(CreateBookingContract.CenterEntry.COLUMN_CENTER_NAME, "DIGUVAPALLI");
        centerList.add(cv);
        cv = new ContentValues();
        cv.put(CreateBookingContract.CenterEntry.COLUMN_CENTER_NAME, "HUTTURU");
        centerList.add(cv);
        cv = new ContentValues();
        cv.put(CreateBookingContract.CenterEntry.COLUMN_CENTER_NAME, "KYALNURU");
        centerList.add(cv);
        cv = new ContentValues();
        cv.put(CreateBookingContract.CenterEntry.COLUMN_CENTER_NAME, "KYASAMBALLI");
        centerList.add(cv);
        cv = new ContentValues();
        cv.put(CreateBookingContract.CenterEntry.COLUMN_CENTER_NAME, "LAKKUR");
        centerList.add(cv);
        cv = new ContentValues();
        cv.put(CreateBookingContract.CenterEntry.COLUMN_CENTER_NAME, "MANADINNERAYAL");
        centerList.add(cv);
        cv = new ContentValues();
        cv.put(CreateBookingContract.CenterEntry.COLUMN_CENTER_NAME, "NARASAPURA");
        centerList.add(cv);
        cv = new ContentValues();
        cv.put(CreateBookingContract.CenterEntry.COLUMN_CENTER_NAME, "NELAVANIKI");
        centerList.add(cv);
        cv = new ContentValues();
        cv.put(CreateBookingContract.CenterEntry.COLUMN_CENTER_NAME, "RAYALPADU");
        centerList.add(cv);
        cv = new ContentValues();
        cv.put(CreateBookingContract.CenterEntry.COLUMN_CENTER_NAME, "RONUR");
        centerList.add(cv);
        cv = new ContentValues();
        cv.put(CreateBookingContract.CenterEntry.COLUMN_CENTER_NAME, "TEKAL");
        centerList.add(cv);
        cv = new ContentValues();
        cv.put(CreateBookingContract.CenterEntry.COLUMN_CENTER_NAME, "THOTLI");
        centerList.add(cv);
        cv = new ContentValues();
        cv.put(CreateBookingContract.CenterEntry.COLUMN_CENTER_NAME, "VEMGAL");
        centerList.add(cv);


        //tractors
        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "NAR-TRA-45D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "NAR-TRA-45D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "NAR-TRA-50D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");

        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "NAR-TRA-50D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        //tractors
        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "THO-TRA-45D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "THO-TRA-45D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "THO-TRA-50D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "THO-TRA-50D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        //tractors
        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "RON-TRA-45D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "RON-TRA-45D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "RON-TRA-50D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "RON-TRA-50D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        //tractors
        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "NEL-TRA-45D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "NEL-TRA-45D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "NEL-TRA-50D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "NEL-TRA-50D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        //tractors
        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "DAL-TRA-45D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "DAL-TRA-45D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "DAL-TRA-50D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "DAL-TRA-50D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        //tractors
        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "BYR-TRA-45D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "BYR-TRA-45D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "BYR-TRA-50D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "BYR-TRA-50D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        //tractors
        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "HUT-TRA-45D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "HUT-TRA-45D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "HUT-TRA-50D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "HUT-TRA-50D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);



//tractors
        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "TEK-TRA-45D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "TEK-TRA-45D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "TEK-TRA-50D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "TEK-TRA-50D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        //tractors
        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "KYA-TRA-45D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "KYA-TRA-45D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "KYA-TRA-50D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "KYA-TRA-50D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        //tractors
        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "LAK-TRA-45D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "LAK-TRA-45D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "LAK-TRA-50D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "LAK-TRA-50D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        //tractors
        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "VEM-TRA-45D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "VEM-TRA-45D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "45D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "VEM-TRA-50D-1");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME, "VEM-TRA-50D-2");
        cv.put(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE, "50D");
        tractorList.add(cv);

        //add implements

        cv = new ContentValues();
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_IMPLEMENT_TYPE, "Bed preparation machine");
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_HOURLY_PRICE,1300);
        implementList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_IMPLEMENT_TYPE, "Cultivator-9 tyne");
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_HOURLY_PRICE,400);
        implementList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_IMPLEMENT_TYPE, "Diesel pumpset");
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_HOURLY_PRICE,200);
        implementList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_IMPLEMENT_TYPE, "Disc Harrow");
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_HOURLY_PRICE,475);
        implementList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_IMPLEMENT_TYPE, "Furrow Opener-5 tyne");
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_HOURLY_PRICE,500);
        implementList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_IMPLEMENT_TYPE, "Halube");
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_HOURLY_PRICE,350);
        implementList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_IMPLEMENT_TYPE, "MB Plough 1 bottom");
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_HOURLY_PRICE,450);
        implementList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_IMPLEMENT_TYPE, "MB Plough 3 bottom");
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_HOURLY_PRICE,450);
        implementList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_IMPLEMENT_TYPE, "Multicrop Thresher");
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_HOURLY_PRICE,50);
        implementList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_IMPLEMENT_TYPE, "Polythene Mulcher");
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_HOURLY_PRICE,500);
        implementList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_IMPLEMENT_TYPE, "Portable Knapsack Sprayer");
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_HOURLY_PRICE,150);
        implementList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_IMPLEMENT_TYPE, "Post Hole Digger");
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_HOURLY_PRICE,15);
        implementList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_IMPLEMENT_TYPE, "Power Sprayer");
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_HOURLY_PRICE,150);
        implementList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_IMPLEMENT_TYPE, "Rotary Tiller");
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_HOURLY_PRICE,300);
        implementList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_IMPLEMENT_TYPE, "Rotovator-42 blades");
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_HOURLY_PRICE,600);
        implementList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_IMPLEMENT_TYPE, "Seed cum Fertilizer drill");
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_HOURLY_PRICE,550);
        implementList.add(cv);

        cv = new ContentValues();
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_IMPLEMENT_TYPE, "Sekeecher");
        cv.put(CreateBookingContract.ImplementEntry.COLUMN_HOURLY_PRICE,50);
        implementList.add(cv);


        try {
            //Insert centers into center table
            //db.beginTransaction();
            //clear the table first
            db.delete(CreateBookingContract.CenterEntry.TABLE_NAME, null, null);
            //go through the list and add one by one
            for (ContentValues cc : centerList) {
                db.insert(CreateBookingContract.CenterEntry.TABLE_NAME, null, cc);
            }

            db.delete(CreateBookingContract.ImplementEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for (ContentValues ci : implementList) {
                db.insert(CreateBookingContract.ImplementEntry.TABLE_NAME, null, ci);
            }

            List<Center> centers = getAllCenters(db);
            //db.beginTransaction();
            db.delete(CreateBookingContract.TractorEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for (ContentValues ct : tractorList) {
                for(Center c: centers){
                    if(ct.get(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME).toString().startsWith(c.getCenterName().substring(0,2))){
                        ct.put(CreateBookingContract.TractorEntry.COLUMN_CENTER_ID,c.getCenterId());
                    }
                }

                db.insert(CreateBookingContract.TractorEntry.TABLE_NAME, null, ct);
            }


            //db.setTransactionSuccessful();

        } catch (SQLException e) {
            e.printStackTrace();
            //too bad :(
        } finally {
            //db.endTransaction();
        }
    }

    private static List<Center> getAllCenters(SQLiteDatabase mDb){
        //mDb.beginTransaction();
        ArrayList<Center> list=new ArrayList<Center>();
        try{
            Cursor cursor =  mDb.query(CreateBookingContract.CenterEntry.TABLE_NAME,null,null,null,null,null,null);
            if(cursor.getCount() >0) {
                while (cursor.moveToNext()) {
                    Integer centerId = cursor.getInt(cursor.getColumnIndex(CreateBookingContract.CenterEntry._ID));
                    String centerName = cursor.getString(cursor.getColumnIndex(CreateBookingContract.CenterEntry.COLUMN_CENTER_NAME));
                    Center center = new Center(centerId,centerName);
                    list.add(center);
                }
            }
            cursor.close();

            // mDb.setTransactionSuccessful();
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }
        finally
        {

            //mDb.endTransaction();
            // End the transaction.
            // mDb.close();
            // Close database
        }
        return list;
    }
}
