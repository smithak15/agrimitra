package com.net.mercuryworld.chsc.identity;

/**
 * Created by Smitha on 5/4/2017.
 */

public class Center {
    private Integer centerId;
    private String centerName;

    public Center(Integer centerId,String centerName){
        this.centerId = centerId;
        this.centerName = centerName;
    }

    public Integer getCenterId() {
        return centerId;
    }

    public void setCenterId(Integer centerId) {
        this.centerId = centerId;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    @Override
    public String toString() {
        return centerName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Center){
            Center c = (Center )obj;
            if(c.getCenterName().equals(centerName) && c.getCenterId()==centerId ) return true;
        }
        return false;
    }
}
