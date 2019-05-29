package com.net.mercuryworld.chsc.identity;

/**
 * Created by Smitha on 5/13/2017.
 */

public class Tractor {
    private Integer tractorId;
    private String tractorName;
    private String tractorType;
    private Integer centerId;

    public Tractor(Integer tractorId,String tractorName,String tractorType,Integer centerId){
        this.tractorId = tractorId;
        this.tractorName = tractorName;
        this.tractorType = tractorType;
        this.centerId = centerId;
    }

    public Integer getTractorId() {
        return tractorId;
    }

    public void setTractorId(Integer tractorId) {
        this.tractorId = tractorId;
    }

    public String getTractorType() {
        return tractorType;
    }

    public void setTractorType(String tractorType) {
        this.tractorType = tractorType;
    }

    public Integer getCenterId() {
        return centerId;
    }

    public void setCenterId(Integer centerId) {
        this.centerId = centerId;
    }

    public String getTractorName() {
        return tractorName;
    }

    public void setTractorName(String tractorName) {
        this.tractorName = tractorName;
    }

    @Override
    public String toString() {
        return tractorType + ": "+tractorName;
    }
}
