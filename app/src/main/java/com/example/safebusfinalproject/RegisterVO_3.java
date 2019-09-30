package com.example.safebusfinalproject;

import java.util.Date;

public class RegisterVO_3 {
    String memberID = null;
    String memberPW = null;
    String memberNum = null;
    String driverName = null;
    String driverTel = null;
    String driverLicense = null;
    String carNum = null;
    String driverPicture = null;
    Date registerDate = null;

    public RegisterVO_3() {
    }

    public RegisterVO_3(String memberID, String memberPW, String memberNum, String driverName, String driverTel, String driverLicense, String carNum, String driverPicture, Date registerDate) {
        this.memberID = memberID;
        this.memberPW = memberPW;
        this.memberNum = memberNum;
        this.driverName = driverName;
        this.driverTel = driverTel;
        this.driverLicense = driverLicense;
        this.carNum = carNum;
        this.driverPicture = driverPicture;
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "RegisterVO_3{" +
                "memberID='" + memberID + '\'' +
                ", memberPW='" + memberPW + '\'' +
                ", memberNum='" + memberNum + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverTel='" + driverTel + '\'' +
                ", driverLicense='" + driverLicense + '\'' +
                ", carNum='" + carNum + '\'' +
                ", driverPicture='" + driverPicture + '\'' +
                ", registerDate=" + registerDate +
                '}';
    }

    public String getmemberID() {
        return memberID;
    }

    public void setmemberID(String memberID) {
        this.memberID = memberID;
    }


    public String getmemberPW() {
        return memberPW;
    }

    public void setmemberPW(String memberPW) {
        this.memberPW = memberPW;
    }


    public String getmemberNum() {
        return memberNum;
    }

    public void setmemberNum(String memberNum) {
        this.memberNum = memberNum;
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




    public Date getregisterDate() {
        return registerDate;
    }

    public void setregisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}
