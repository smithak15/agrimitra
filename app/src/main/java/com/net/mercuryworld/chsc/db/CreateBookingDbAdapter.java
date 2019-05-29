package com.net.mercuryworld.chsc.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Smitha on 5/14/2017.
 */

public class CreateBookingDbAdapter {
    private Context context;
    private SQLiteDatabase database;
    private CreateBookingDbHelper dbHelper;

    public CreateBookingDbAdapter(Context context) {
        this.context = context;
    }

    public CreateBookingDbAdapter open() throws SQLException {
        dbHelper = new CreateBookingDbHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void deleteTable(String tablename){
        database.execSQL("drop table if exists "+tablename+';');
    }

    public void createIndividualTable(String query){
        database.execSQL(query);
    }




}
