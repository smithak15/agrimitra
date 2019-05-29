package com.net.mercuryworld.chsc;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.net.mercuryworld.chsc.db.CreateBookingContract;
import com.net.mercuryworld.chsc.identity.Booking;


/**
 * Created by Smitha on 10/1/2017.
 */

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.BookingViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public BookingHistoryAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public BookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.booking_row_layout, parent, false);
        return new BookingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookingViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        // Update the view holder with the information needed to display
        String driverName = mCursor.getString(mCursor.getColumnIndex(CreateBookingContract.BookingEntry.COLUMN_DRIVER));
        String bookingId = mCursor.getString(mCursor.getColumnIndex(CreateBookingContract.BookingEntry.COLUMN_BOOKING_ID));
        // COMPLETED (6) Retrieve the id from the cursor and
        int hours = mCursor.getInt(mCursor.getColumnIndex(CreateBookingContract.BookingEntry.COLUMN_WORKING_HOURS));
        long id = mCursor.getLong(mCursor.getColumnIndex(CreateBookingContract.BookingEntry._ID));

        // Display the guest name
        holder.driverTextView.setText(driverName);
        // Display the party count
        holder.bookingIdTextView.setText(bookingId);
        holder.hoursTextView.setText(String.valueOf(hours));
        // COMPLETED (7) Set the tag of the itemview in the holder to the id
        holder.itemView.setTag(id);
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView driverTextView, bookingIdTextView, hoursTextView;

        public BookingViewHolder(View itemView) {
            super(itemView);
            driverTextView = (TextView) itemView.findViewById(R.id.driverName);
            bookingIdTextView = (TextView) itemView.findViewById(R.id.bookingId);
            hoursTextView = (TextView) itemView.findViewById(R.id.hours);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            long id = (long) itemView.getTag();
            Log.d("TAG", "onClick " + getAdapterPosition() + " " + itemView.getTag());
            /*Toast.makeText(mContext, "Single Click on position        :"+String.valueOf(id),
                    Toast.LENGTH_SHORT).show();*/
            Intent intent = new Intent(mContext, UpdateBookingActivity.class);
            intent.putExtra("ID",id);
            mContext.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
}
