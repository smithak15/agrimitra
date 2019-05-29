package com.net.mercuryworld.chsc.identity;

/**
 * Created by Smitha on 5/25/2017.
 */

public class Farmer {
    private Integer farmerId;
    private String farmerName;
    private Long farmerPhone;
    private String farmerVillage;

    public Farmer(String farmerName,Long farmerPhone,String farmerVillage){
        this.farmerName = farmerName;
        this.farmerPhone = farmerPhone;
        this.farmerVillage = farmerVillage;
    }

    public Integer getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public Long getFarmerPhone() {
        return farmerPhone;
    }

    public void setFarmerPhone(Long farmerPhone) {
        this.farmerPhone = farmerPhone;
    }

    public String getFarmerVillage() {
        return farmerVillage;
    }

    public void setFarmerVillage(String farmerVillage) {
        this.farmerVillage = farmerVillage;
    }
}
