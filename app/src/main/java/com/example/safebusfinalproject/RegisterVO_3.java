package com.example.safebusfinalproject;

import java.util.Date;

public class RegisterVO_3 {

    String driverName = null;
    String driverTel = null;
    String driverLicense = null;
    String carNum = null;
    String driverPicture = null;

    public RegisterVO_3() {
    }

    public RegisterVO_3(String memberID, String memberPW, String memberNum, String driverName, String driverTel, String driverLicense, String carNum, String driverPicture, Date registerDate) {
        this.driverName = driverName;
        this.driverTel = driverTel;
        this.driverLicense = driverLicense;
        this.carNum = carNum;
        this.driverPicture = driverPicture;
    }

    @Override
    public String toString() {
        return "RegisterVO_3{" +
                ", driverName='" + driverName + '\'' +
                ", driverTel='" + driverTel + '\'' +
                ", driverLicense='" + driverLicense + '\'' +
                ", carNum='" + carNum + '\'' +
                ", driverPicture='" + driverPicture + '\'' +
                '}';
    }

    public String getdriverName() {
        return driverName;
    }

    public void setdriverName(String driverName) {
        this.driverName = driverName;
    }


    public String getdriverTel() {
        return driverTel;
    }

    public void setdriverTel(String driverTel) {
        this.driverTel = driverTel;
    }



    public String getdriverLicense() {
        return driverLicense;
    }

    public void setdriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }



    public String getcarNum() {
        return carNum;
    }

    public void setcarNum(String carNum) {
        this.carNum = carNum;
    }




    public String getdriverPicture() {
        return driverPicture;
    }

    public void setdriverPicture(String driverPicture) {
        this.driverPicture = driverPicture;
    }


}
