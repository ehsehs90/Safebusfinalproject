package com.example.safebusfinalproject;

import java.util.Date;

public class RegisterVO_1 {
    String memberID = null;
    String memberPW = null;
    String memberNum = null;
    String babyName = null;
    String babyGender = null;
    String parentName = null;


    String parentNum = null;
    String address = null;
    String RFID = null;
    String station = null;
    Date registerDate = null;


    @Override
    public String toString() {
        return "RegisterVO_1{" +
                "memberID='" + memberID + '\'' +
                ", memberPW='" + memberPW + '\'' +
                ", memberNum='" + memberNum + '\'' +
                ", babyName='" + babyName + '\'' +
                ", babyGender='" + babyGender + '\'' +
                ", parentName='" + parentName + '\'' +
                ", parentNum='" + parentNum + '\'' +
                ", address='" + address + '\'' +
                ", RFID='" + RFID + '\'' +
                ", station='" + station + '\'' +
                ", registerDate=" + registerDate +
                '}';
    }

    public RegisterVO_1(String memberID, String memberPW, String memberNum, String babyName, String babyGender, String parentName, String parentNum, String address, String RFID, String station, Date registerDate) {
        this.memberID = memberID;
        this.memberPW = memberPW;
        this.memberNum = memberNum;
        this.babyName = babyName;
        this.babyGender = babyGender;
        this.parentName = parentName;
        this.parentNum = parentNum;
        this.address = address;
        this.RFID = RFID;
        this.station = station;
        this.registerDate = registerDate;
}

    public RegisterVO_1() {
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



    public String getbabyName() {
        return babyName;
    }

    public void setbabyName(String babyName) {
        this.babyName = babyName;
    }


    public String getbabyGender() {
        return babyGender;
    }

    public void setbabyGender(String babyGender) {
        this.babyGender = babyGender;
    }


    public String getparentName() {
        return parentName;
    }

    public void setparentName(String parentName) {
        this.parentName = parentName;
    }


    public String getparentNum() {
        return parentNum;
    }

    public void setparentNum(String parentNum) {
        this.parentNum = parentNum;
    }


    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }


    public String getRFID() {
        return RFID;
    }

    public void setRFID(String RFID) {
        this.RFID = RFID;
    }


    public String getstation() {
        return station;
    }

    public void setstation(String station) {
        this.station = station;
    }


    public Date getregisterDate() {
        return registerDate;
    }

    public void setregisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}
