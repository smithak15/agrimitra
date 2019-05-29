package com.net.mercuryworld.chsc;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import com.net.mercuryworld.chsc.db.CreateBookingContract;
import com.net.mercuryworld.chsc.db.CreateBookingDbHelper;
import com.net.mercuryworld.chsc.identity.Booking;


public class BookingHistoryActivity extends AppCompatActivity {

    private BookingHistoryAdapter mAdapter;
    private SQLiteDatabase mDb;
    private RecyclerView recyclerView;
    private final static String LOG_TAG = BookingHistoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        RecyclerView bookingHistoryRecyclerView;

        bookingHistoryRecyclerView = (RecyclerView) this.findViewById(R.id.booking_recycler_view);
        bookingHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        CreateBookingDbHelper dbHelper = new CreateBookingDbHelper(this);

        mDb = dbHelper.getWritableDatabase();

        Cursor cursor = getAllBookingsHistoryForToday();

        mAdapter = new BookingHistoryAdapter(this,cursor);

        bookingHistoryRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_booking_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_create_booking:
                Intent intent = new Intent(getApplicationContext(), CreateBookingActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Cursor getAllBookingsHistoryForToday() {
        try {
            Cursor cursor = mDb.query(CreateBookingContract.BookingEntry.TABLE_NAME, null, null, null, null, null, CreateBookingContract.BookingEntry.COLUMN_START_DATE_TIME + " DESC");
            if (cursor.getCount() > 0) {
                return cursor;
            }else {
                return null;
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}

