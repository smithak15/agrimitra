package com.net.mercuryworld.chsc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.net.mercuryworld.chsc.db.CreateBookingContract;
import com.net.mercuryworld.chsc.db.CreateBookingDbAdapter;
import com.net.mercuryworld.chsc.db.CreateBookingDbHelper;
import com.net.mercuryworld.chsc.util.TimePickerFragment;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;

import static android.graphics.BlurMaskFilter.Blur.INNER;
import static com.net.mercuryworld.chsc.R.id.centerId;
import static com.net.mercuryworld.chsc.R.id.crop;
import static com.net.mercuryworld.chsc.R.id.driverName;
import static com.net.mercuryworld.chsc.R.id.farmerVillage;
import static com.net.mercuryworld.chsc.R.id.landSize;
import static com.net.mercuryworld.chsc.R.id.meterEnd;
import static com.net.mercuryworld.chsc.R.id.meterStart;

public class UpdateBookingActivity extends AppCompatActivity implements View.OnClickListener,TimePickerFragment.TimePickedListener{

    private CreateBookingDbAdapter bookingDbAdapter;
    private SQLiteDatabase mDb;


    private static EditText
            farmerNameText,farmerPhoneText, farmerVillageText,
            landSizeText ,cropText ,driverNameText ,meterStartText, meterEndText,
            centerText, tractorText, implementText, bookingDateText, hoursText;
    private static Button updateBookingBtn, cancelBtn, startTimeBtn, endTimeBtn;
    private static DateTime startDateTime, endDateTime;
    private static Calendar calStart, calEnd;
    private View updateBookingView;
    private Long id = Long.valueOf(0);
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_booking);
        bookingDbAdapter = new CreateBookingDbAdapter(this);
        CreateBookingDbHelper dbHelper = new CreateBookingDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        mDb.enableWriteAheadLogging();
        Intent intent = getIntent();
        id = intent.getLongExtra("ID",0);

        //Cursor mCursor = getCurrentBooking(id);
        centerText = (EditText) findViewById(R.id.centerName);
        implementText = (EditText) findViewById(R.id.implement);
        tractorText = (EditText) findViewById(R.id.tractor);
        farmerNameText = (EditText) findViewById(R.id.farmerName);
        farmerPhoneText = (EditText) findViewById(R.id.farmerPhone);
        farmerVillageText = (EditText) findViewById(R.id.farmerVillage);
        landSizeText = (EditText) findViewById(landSize);
        cropText = (EditText) findViewById(crop);
        driverNameText = (EditText) findViewById(driverName);
        hoursText = (EditText) findViewById(R.id.hours);
        meterStartText = (EditText) findViewById(meterStart);
        meterEndText = (EditText) findViewById(meterEnd);
        bookingDateText = (EditText) findViewById(R.id.bookingDate);
        startTimeBtn = (Button) findViewById(R.id.startTime);
        endTimeBtn = (Button) findViewById(R.id.endTime);
        updateBookingBtn = (Button) findViewById(R.id.updateBookingButton);
        cancelBtn = (Button) findViewById(R.id.cancelButton);
        updateBookingBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        startTimeBtn.setOnClickListener(this);
        endTimeBtn.setOnClickListener(this);
        prepopulateData(mDb,id);
        calStart = Calendar.getInstance();
        calEnd = Calendar.getInstance();
        updateBookingView = findViewById(R.id.update_booking_form_main);
        mProgressView = findViewById(R.id.login_progress);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.updateBookingButton:
                updateBooking(view);
                break;

            case R.id.cancelButton:
                cancelAction(view);
                break;

            case R.id.startTime:
                DialogFragment newFragmentStart = new TimePickerFragment();
                newFragmentStart.show(getFragmentManager(), "timePickerStart");
                break;

            case R.id.endTime:
                DialogFragment newFragmentEnd = new TimePickerFragment();
                newFragmentEnd.show(getFragmentManager(), "timePickerEnd");
                break;

            default:
                break;
        }
    }

    @Override
    public void onTimePicked(Calendar time,String id)
    {
        // display the selected time in the TextView
        if(id.equals("timePickerStart")) {
            calStart.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
            calStart.set(Calendar.MINUTE,time.get(Calendar.MINUTE));
        }else if(id.equals("timePickerEnd")){
            calEnd.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
            calEnd.set(Calendar.MINUTE,time.get(Calendar.MINUTE));
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            updateBookingView.setVisibility(show ? View.GONE : View.VISIBLE);
            updateBookingView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    updateBookingView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            updateBookingView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private Cursor getCurrentBooking(Long id) {
        try {
            String GET_BOOKING_RECORD = "SELECT be.bookingId, ce.centerName, fe.farmerName, fe.farmerPhone, fe.farmerVillage, be.landSize, be.cropName, be.driver, te.tractorType, ie.implementType, be.workingHours, be.startDateTime "
            + "FROM booking be "
            + "INNER JOIN center ce ON be.centerId = ce.centerId "
            + "INNER JOIN farmer fe ON be.farmerId = fe.farmerId "
            + "INNER JOIN tractor te ON be.tractorId = te.tractorId "
            + "INNER JOIN implement ie ON be.implementId = ie.implementId "
            + "WHERE be.id = ?";
            Cursor cursor = mDb.rawQuery(GET_BOOKING_RECORD,new String[]{String.valueOf(id)});
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

    private void updateBooking(View button){
        Float landSize = Float.valueOf(0);
        Float meterStart = Float.valueOf(0);
        Float meterEnd = Float.valueOf(0);
        String crop = "";
        String driverName = "";
        if(null!=landSizeText.getText().toString()) {
            landSize = Float.parseFloat(landSizeText.getText().toString());
        }
        if(null!=cropText.getText().toString()){
            crop = cropText.getText().toString();
        }
        if(null!=driverNameText.getText().toString()) {
            driverName = driverNameText.getText().toString();
        }
        if(null!=meterStartText.getText().toString()) {
            meterStart = Float.parseFloat(meterStartText.getText().toString());
        }
        if(null!=meterEndText.getText().toString()) {
            meterEnd = Float.parseFloat(meterEndText.getText().toString());
        }

        String[] date = bookingDateText.getText().toString().split("-");

        startDateTime = new DateTime(Integer.valueOf(date[0]),Integer.valueOf(date[1]),
                Integer.valueOf(date[1]),calStart.get(Calendar.HOUR_OF_DAY),
                calStart.get(Calendar.MINUTE));

        endDateTime = new DateTime(Integer.valueOf(date[0]),Integer.valueOf(date[1]),
                Integer.valueOf(date[1]),calEnd.get(Calendar.HOUR_OF_DAY),
                calEnd.get(Calendar.MINUTE));

        try{
            mDb.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(CreateBookingContract.BookingEntry.COLUMN_LAND_SIZE,landSize);
            cv.put(CreateBookingContract.BookingEntry.COLUMN_CROP_NAME,crop);
            cv.put(CreateBookingContract.BookingEntry.COLUMN_DRIVER,driverName);
            cv.put(CreateBookingContract.BookingEntry.COLUMN_METER_START,meterStart);
            cv.put(CreateBookingContract.BookingEntry.COLUMN_METER_END,meterEnd);
            cv.put(CreateBookingContract.BookingEntry.COLUMN_START_DATE_TIME,getSqlTimeString(startDateTime));
            cv.put(CreateBookingContract.BookingEntry.COLUMN_END_DATE_TIME,getSqlTimeString(endDateTime));
            mDb.update(CreateBookingContract.BookingEntry.TABLE_NAME, cv, "id="+id,null);
            mDb.setTransactionSuccessful();
        }catch (SQLiteException e){
            e.printStackTrace();
        }finally {
            mDb.endTransaction();
        }
        showProgress(true);
        finish();
    }

    private void cancelAction(View button){
        clearForm((ViewGroup) findViewById(R.id.update_booking_form));
    }

    private void clearForm(ViewGroup group)
    {
        landSizeText.setText("");
        cropText.setText("");
        driverNameText.setText("");
        meterStartText.setText("");
        meterEndText.setText("");
        startTimeBtn.setText("");
        endTimeBtn.setText("");
    }

    public String getSqlTimeString(DateTime dt) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String sqlTimeString = fmt.print(dt);
        return sqlTimeString;
    }




    public void prepopulateData(SQLiteDatabase db, Long id) {
        Cursor cursor = getCurrentBooking(id);
        cursor.moveToFirst();
        centerText.setText(cursor.getString(1));
        farmerNameText.setText(cursor.getString(2));
        farmerPhoneText.setText(cursor.getString(3));
        farmerVillageText.setText(cursor.getString(4));
        landSizeText.setText(String.valueOf(cursor.getFloat(5)));
        cropText.setText(cursor.getString(6));
        driverNameText.setText(cursor.getString(7));
        tractorText.setText(cursor.getString(8));
        implementText.setText(cursor.getString(9));
        hoursText.setText(cursor.getString(10));
        bookingDateText.setText(cursor.getString(11).substring(0,10));
    }
}
