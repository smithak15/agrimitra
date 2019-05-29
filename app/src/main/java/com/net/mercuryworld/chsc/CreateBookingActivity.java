package com.net.mercuryworld.chsc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.net.mercuryworld.chsc.db.CreateBookingContract;
import com.net.mercuryworld.chsc.db.CreateBookingDbAdapter;
import com.net.mercuryworld.chsc.db.CreateBookingDbHelper;
import com.net.mercuryworld.chsc.identity.Booking;
import com.net.mercuryworld.chsc.identity.Center;
import com.net.mercuryworld.chsc.identity.Farmer;
import com.net.mercuryworld.chsc.identity.Implement;
import com.net.mercuryworld.chsc.identity.Tractor;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateBookingActivity extends AppCompatActivity implements OnItemSelectedListener,View.OnClickListener{

    private CreateBookingDbAdapter bookingDbAdapter;
    private SQLiteDatabase mDb;


    private static Spinner centerSpinner, implementSpinner, tractorSpinner;
    private static EditText farmerNameText,farmerPhoneText, farmerVillageText, landSizeText ,cropText ,driverNameText ,meterStartText, hoursText;
    private static Button bookingDateBtn ,createBookingBtn, startTimeBtn;
    private static Calendar calendar;
    private static DateTime startDateTime;
    private View createBookingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_booking);

        // Create an adapter for that cursor to display the data
        bookingDbAdapter = new CreateBookingDbAdapter(this);

        CreateBookingDbHelper dbHelper = new CreateBookingDbHelper(this);

        mDb = dbHelper.getWritableDatabase();

        mDb.enableWriteAheadLogging();
        //insertSetupData(mDb);
        //Cursor cursor = getAllCenters();
        centerSpinner = (Spinner) findViewById(R.id.centerId);
        implementSpinner = (Spinner) findViewById(R.id.implementId);
        tractorSpinner = (Spinner) findViewById(R.id.tractorId);
        farmerNameText = (EditText) findViewById(R.id.farmerName);
        farmerPhoneText = (EditText) findViewById(R.id.farmerPhone);
        farmerVillageText = (EditText) findViewById(R.id.farmerVillage);
        landSizeText = (EditText) findViewById(R.id.landSize);
        cropText = (EditText) findViewById(R.id.crop);
        driverNameText = (EditText) findViewById(R.id.driverName);
        //meterStartText = (EditText) findViewById(R.id.meterStart);
        hoursText = (EditText) findViewById(R.id.hours);
        bookingDateBtn = (Button) findViewById(R.id.bookingDate);
        //startTimeBtn = (Button) findViewById(R.id.startTime);
        createBookingBtn = (Button) findViewById(R.id.createBookingButton);
        createBookingBtn.setOnClickListener(this);
        centerSpinner.setOnItemSelectedListener(this);
        loadSpinnerData(dbHelper);
        calendar = Calendar.getInstance();
        createBookingView = findViewById(R.id.create_booking_form_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_booking, menu);
        return true;
    }

    private void loadSpinnerData(CreateBookingDbHelper dbHelper){
        List<Center> centers = getAllCenters(mDb);
        // Creating adapter for spinner
        ArrayAdapter<Center> centerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, centers);
        // Drop down layout style - list view with radio button
        centerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        centerSpinner.setAdapter(centerAdapter);


        List<Implement> implementList = getAllImplements(mDb);
        // Creating adapter for spinner
        ArrayAdapter<Implement> implementArrayAdapter = new ArrayAdapter<Implement>(this, android.R.layout.simple_spinner_item, implementList);
        // Drop down layout style - list view with radio button
        implementArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        implementSpinner.setAdapter(implementArrayAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        centerSpinner = (Spinner) parent;
        if(centerSpinner.getId() == R.id.centerId)
        {
            Center center = (Center) parent.getSelectedItem();
            loadTractorData(center.getCenterId());
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void loadTractorData(Integer centerId){
        List<Tractor> tractorList = getCenterTractors(mDb,centerId);
        //Creating adapter for spinner
        ArrayAdapter<Tractor> tractorAdapter = new ArrayAdapter<Tractor>(this, android.R.layout.simple_spinner_item, tractorList);
        //Drop down layout style
        tractorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //attaching data adapter to spinner
        tractorSpinner.setAdapter(tractorAdapter);
    }

    @Override
    public void onClick(View view) {
        createBookingEntry();
    }


    public void createBookingEntry(){
        Farmer farmer = new Farmer(farmerNameText.getText().toString(), Long.parseLong(farmerPhoneText.getText().toString()), farmerVillageText.getText().toString());
        Integer farmerId = createFarmer(mDb,farmer);
        Center center = (Center) centerSpinner.getSelectedItem();
        Implement implement = (Implement) implementSpinner.getSelectedItem();
        Tractor tractor = (Tractor) tractorSpinner.getSelectedItem();
        Float landSize = Float.parseFloat(landSizeText.getText().toString());
        Float hours = Float.parseFloat(hoursText.getText().toString());
        String driverName = driverNameText.getText().toString();
        String crop = cropText.getText().toString();
        startDateTime = new DateTime(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE));
        String bookingId = center.getCenterName().substring(0,2).toUpperCase() +
                implement.getImplementType().substring(0,5).toUpperCase() +
                tractor.getTractorType().toUpperCase()+
                calendar.get(Calendar.MONTH)+calendar.get(Calendar.DAY_OF_MONTH)+calendar.get(Calendar.HOUR_OF_DAY)+
                calendar.get(Calendar.MINUTE);
        Booking booking = new Booking(bookingId,center.getCenterId(),implement.getImplementId(),tractor.getTractorId(),farmerId,landSize,crop,driverName,hours,startDateTime);
        bookingId = createBooking(mDb,booking);
        Toast.makeText(getApplicationContext(), "Booking created        :"+bookingId,
                Toast.LENGTH_SHORT).show();
        clearForm((ViewGroup) findViewById(R.id.create_booking_form));
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            calendar.set(year,month,day);
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private void clearForm(ViewGroup group)
    {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }

            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
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


    public List<Tractor> getCenterTractors(SQLiteDatabase mDb, Integer centerId){
        //mDb.beginTransaction();
        ArrayList<Tractor> list=new ArrayList<>();
        try{
            Cursor cursor =  mDb.query(CreateBookingContract.TractorEntry.TABLE_NAME,
                    null,CreateBookingContract.TractorEntry.COLUMN_CENTER_ID + "=" + centerId,null,null,null,null);
            if(cursor.getCount() >0) {
                while (cursor.moveToNext()) {
                    //Integer centerId = cursor.getInt(cursor.getColumnIndex(CreateBookingContract.TractorEntry.COLUMN_CENTER_ID));
                    Integer tractorId = cursor.getInt(cursor.getColumnIndex(CreateBookingContract.TractorEntry._ID));
                    String tractorName = cursor.getString(cursor.getColumnIndex(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_NAME));
                    String tractorType = cursor.getString(cursor.getColumnIndex(CreateBookingContract.TractorEntry.COLUMN_TRACTOR_TYPE));
                    Tractor tractor = new Tractor(tractorId,tractorName,tractorType,centerId);
                    list.add(tractor);
                }
            }
            cursor.close();

           //mDb.setTransactionSuccessful();
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

    public List<Implement> getAllImplements(SQLiteDatabase mDb){
        //mDb.beginTransaction();
        ArrayList<Implement> list=new ArrayList<Implement>();
        try{
            Cursor cursor =  mDb.query(CreateBookingContract.ImplementEntry.TABLE_NAME,null,null,null,null,null,null);
            if(cursor.getCount() >0) {
                while (cursor.moveToNext()) {
                    Integer implementId = cursor.getInt(cursor.getColumnIndex(CreateBookingContract.ImplementEntry._ID));
                    String implementType = cursor.getString(cursor.getColumnIndex(CreateBookingContract.ImplementEntry.COLUMN_IMPLEMENT_TYPE));
                    Integer hourlyPrice = cursor.getInt(cursor.getColumnIndex(CreateBookingContract.ImplementEntry.COLUMN_HOURLY_PRICE));
                    Implement imp = new Implement(implementId,implementType,hourlyPrice);
                    list.add(imp);
                }
            }
            cursor.close();

            //mDb.setTransactionSuccessful();
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

    public Integer createFarmer(SQLiteDatabase mDb, Farmer farmer){
        Integer farmerId = 0;
        try {
            //mDb.beginTransaction();
            farmerId = getFarmerByPhone(mDb,farmer.getFarmerPhone());
            if(farmerId == 0) {
                ContentValues cv = new ContentValues();
                cv.put(CreateBookingContract.FarmerEntry.COLUMN_FARMER_NAME, farmer.getFarmerName());
                cv.put(CreateBookingContract.FarmerEntry.COLUMN_FARMER_PHONE, farmer.getFarmerPhone());
                cv.put(CreateBookingContract.FarmerEntry.COLUMN_FARMER_VILLAGE, farmer.getFarmerVillage());

                mDb.insert(CreateBookingContract.FarmerEntry.TABLE_NAME, null, cv);
            }
            farmerId = getFarmerByPhone(mDb,farmer.getFarmerPhone());
           // mDb.setTransactionSuccessful();
        }catch (SQLiteException e){
            e.printStackTrace();
        }finally {
            //mDb.endTransaction();
        }
        return farmerId;
    }

    public Integer getFarmerByPhone(SQLiteDatabase mDb,Long farmerPhone){
        Integer farmerId = 0;
        try{
            Cursor c = mDb.query(CreateBookingContract.FarmerEntry.TABLE_NAME,new String[]{CreateBookingContract.FarmerEntry._ID}, CreateBookingContract.FarmerEntry.COLUMN_FARMER_PHONE+"=?",new String[]{farmerPhone.toString()},null,null,null);
            if(c.getCount() > 0){
                while (c.moveToNext()){
                    farmerId = c.getInt(c.getColumnIndex(CreateBookingContract.FarmerEntry._ID));
                }
            }
        }catch (SQLiteException e){
            e.printStackTrace();
        }
        return farmerId;
    }

    public String createBooking(SQLiteDatabase mDb, Booking booking){
        try{
            //mDb.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(CreateBookingContract.BookingEntry.COLUMN_BOOKING_ID,booking.getBookingId());
            cv.put(CreateBookingContract.BookingEntry.COLUMN_CENTER_ID,booking.getCenterId());
            cv.put(CreateBookingContract.BookingEntry.COLUMN_IMPLEMENT_ID,booking.getImplementId());
            cv.put(CreateBookingContract.BookingEntry.COLUMN_TRACTOR_ID,booking.getTractorId());
            cv.put(CreateBookingContract.BookingEntry.COLUMN_FARMER_ID,booking.getFarmerId());
            cv.put(CreateBookingContract.BookingEntry.COLUMN_DRIVER,booking.getDriverName());
            cv.put(CreateBookingContract.BookingEntry.COLUMN_LAND_SIZE,booking.getLandSize());
            cv.put(CreateBookingContract.BookingEntry.COLUMN_CROP_NAME,booking.getCrop());
            cv.put(CreateBookingContract.BookingEntry.COLUMN_WORKING_HOURS,booking.getWorkingHours());
            cv.put(CreateBookingContract.BookingEntry.COLUMN_START_DATE_TIME,getSqlTimeString(booking.getStartDateTime()));
            mDb.insert(CreateBookingContract.BookingEntry.TABLE_NAME, null, cv);
            //mDb.setTransactionSuccessful();
        }catch (SQLiteException e){
            e.printStackTrace();
        }finally {
           // mDb.endTransaction();
        }
        return booking.getBookingId();
    }



    public String getSqlTimeString(DateTime dt) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String sqlTimeString = fmt.print(dt);
        return sqlTimeString;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_booking_history:
                Intent intent = new Intent(getApplicationContext(), BookingHistoryActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
