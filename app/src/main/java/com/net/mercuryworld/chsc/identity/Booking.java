package com.net.mercuryworld.chsc.identity;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Smitha on 5/25/2017.
 */

public class Booking {
    private Integer id;
    private String bookingId;
    private Integer centerId;
    private Integer implementId;
    private Integer tractorId;
    private Integer farmerId;
    private Float landSize;
    private String crop;
    private String driverName;
    private Float meterStart;
    private Float meterEnd;
    private Float workingHours;
    private Float transportHours;
    private Float workingCharge;
    private Float transportCharge;
    private Float totalKms;
    private Float totalAmount;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private String farmerName;

    public Booking(String bookingId, Integer centerId,Integer implementId, Integer tractorId, Integer farmerId,
                        Float landSize, String crop, String driverName, Float workingHours, DateTime startDateTime){
        this.bookingId = bookingId;
        this.centerId = centerId;
        this.implementId = implementId;
        this.tractorId = tractorId;
        this.farmerId = farmerId;
        this.landSize = landSize;
        this.crop = crop;
        this.driverName = driverName;
        this.workingHours = workingHours;
        this.startDateTime = startDateTime;
    }


    public Booking(String bookingId, Integer centerId,Integer implementId, Integer tractorId, Integer farmerId,
                        Float landSize, String crop, String driverName, DateTime startDateTime){
        this.bookingId = bookingId;
        this.centerId = centerId;
        this.implementId = implementId;
        this.tractorId = tractorId;
        this.farmerId = farmerId;
        this.landSize = landSize;
        this.crop = crop;
        this.driverName = driverName;
        this.startDateTime = startDateTime;
    }




    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    //TODO: Create farmer first. Farmer class
    //TODO: Create booking later
    //


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getCenterId() {
        return centerId;
    }

    public void setCenterId(Integer centerId) {
        this.centerId = centerId;
    }

    public Integer getImplementId() {
        return implementId;
    }

    public void setImplementId(Integer implementId) {
        this.implementId = implementId;
    }

    public Integer getTractorId() {
        return tractorId;
    }

    public void setTractorId(Integer tractorId) {
        this.tractorId = tractorId;
    }

    public void setTractorName(Integer tractorId) {
        this.tractorId = tractorId;
    }

    public Integer getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
    }

    public Float getLandSize() {
        return landSize;
    }

    public void setLandSize(Float landSize) {
        this.landSize = landSize;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Float getMeterStart() {
        return meterStart;
    }

    public void setMeterStart(Float meterStart) {
        this.meterStart = meterStart;
    }

    public Float getMeterEnd() {
        return meterEnd;
    }

    public void setMeterEnd(Float meterEnd) {
        this.meterEnd = meterEnd;
    }

    public Float getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Float workingHours) {
        this.workingHours = workingHours;
    }

    public Float getTransportHours() {
        return transportHours;
    }

    public void setTransportHours(Float transportHours) {
        this.transportHours = transportHours;
    }

    public Float getWorkingCharge() {
        return workingCharge;
    }

    public void setWorkingCharge(Float workingCharge) {
        this.workingCharge = workingCharge;
    }

    public Float getTransportCharge() {
        return transportCharge;
    }

    public void setTransportCharge(Float transportCharge) {
        this.transportCharge = transportCharge;
    }

    public Float getTotalKms() {
        return totalKms;
    }

    public void setTotalKms(Float totalKms) {
        this.totalKms = totalKms;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBookingId(){
        return bookingId;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }


}
