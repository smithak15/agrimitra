package com.net.mercuryworld.chsc.identity;

/**
 * Created by Smitha on 5/14/2017.
 */

public class Implement {
    private Integer implementId;
    private String implementType;
    private Integer hourlyPrice;

    public Implement(Integer implementId, String implementType, Integer hourlyPrice){
        this.implementId = implementId;
        this.implementType = implementType;
        this.hourlyPrice = hourlyPrice;
    }

    public Implement(Integer implementId, String implementType){
        this.implementId = implementId;
        this.implementType = implementType;
        //this.hourlyPrice = hourlyPrice;
    }

    public Integer getImplementId() {
        return implementId;
    }

    public void setImplementId(Integer implementId) {
        this.implementId = implementId;
    }

    public String getImplementType() {
        return implementType;
    }

    public void setImplementType(String implementType) {
        this.implementType = implementType;
    }

    public Integer getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice(Integer hourlyPrice) {
        this.hourlyPrice = hourlyPrice;
    }

    @Override
    public String toString() {
        return implementType;
    }
}
