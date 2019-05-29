package com.net.mercuryworld.chsc.db;


import android.provider.BaseColumns;

/**
 * Created by Smitha on 5/1/2017.
 */

public final class CreateBookingContract {

    private CreateBookingContract(){};

    //TODO#12: Make a unit test class to test these

    public static class FarmerEntry implements BaseColumns{
        public static final String TABLE_NAME = "farmer";
        public static final String _ID = "farmerId";
        public static final String COLUMN_FARMER_NAME = "farmerName";
        public static final String COLUMN_FARMER_PHONE = "farmerPhone";
        public static final String COLUMN_FARMER_VILLAGE = "farmerVillage";
    }

    public static class TractorEntry implements BaseColumns{
        public static final String TABLE_NAME = "tractor";
        public static final String _ID = "tractorId";
        public static final String COLUMN_TRACTOR_NAME = "tractorName";
        public static final String COLUMN_TRACTOR_TYPE = "tractorType";
        public static final String COLUMN_CENTER_ID = "centerId";
    }

    public static class ImplementEntry implements BaseColumns{
        public static final String TABLE_NAME = "implement";
        public static final String _ID = "implementId";
        public static final String COLUMN_IMPLEMENT_TYPE = "implementType";
        public static final String COLUMN_HOURLY_PRICE = "hourlyPrice";
    }

    public static class CenterEntry implements BaseColumns{
        public static final String TABLE_NAME = "center";
        public static final String _ID  = "centerId";
        public static final String COLUMN_CENTER_NAME = "centerName";
    }

    public static class BookingEntry implements BaseColumns{
        public static final String TABLE_NAME = "booking";
        public static final String _ID = "id";
        public static final String COLUMN_BOOKING_ID = "bookingId";
        public static final String COLUMN_CENTER_ID  = "centerId";
        public static final String COLUMN_DRIVER = "driver";
        public static final String COLUMN_FARMER_ID = "farmerId";
        public static final String COLUMN_TRACTOR_ID = "tractorId";
        public static final String COLUMN_IMPLEMENT_ID = "implementId";
        public static final String COLUMN_CROP_NAME = "cropName";
        public static final String COLUMN_LAND_SIZE = "landSize";
        public static final String COLUMN_METER_START = "meterStart";
        public static final String COLUMN_METER_END = "meterEnd";
        public static final String COLUMN_START_DATE_TIME = "startDateTime";
        public static final String COLUMN_END_DATE_TIME = "endDateTime";
        public static final String COLUMN_WORKING_HOURS = "workingHours";
        public static final String COLUMN_TRANSPORT_HOURS = "transportHours";
        public static final String COLUMN_WORKING_CHARGE = "workingCharge";
        public static final String COLUMN_TRANSPORT_CHARGE = "transportCharge";
        public static final String COLUMN_TOTAL_KMS = "totalKms";
        public static final String COLUMN_TOTAL_AMOUNT = "totalAmount";
        //TODO: Add column balance in next version
        //public static final String COLUMN_BALANCE = "balance";
    }

}
