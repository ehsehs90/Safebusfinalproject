package com.example.safebusfinalproject.registerVO;

public class RParentsVO {

    String babyName = null;
    String babyGender = null;
    String address = null;
    //String RFID = null;
    String station = null;

    String memberID = null;
    String memberPW = null;
    String memberName = null;
    String memberTel = null;
    String memberinfo = null;
    String registerDate = null;

    public RParentsVO() {
        super();
    }

    public RParentsVO(String babyName, String babyGender, String address, String station, String memberID,
                      String memberPW, String memberName, String memberTel, String memberinfo, String registerDate) {
        super();
        this.babyName = babyName;
        this.babyGender = babyGender;
        this.address = address;
        this.station = station;
        this.memberID = memberID;
        this.memberPW = memberPW;
        this.memberName = memberName;
        this.memberTel = memberTel;
        this.memberinfo = memberinfo;
        this.registerDate = registerDate;
    }


    @Override
    public String toString() {
        return "RParentsVO [babyName=" + babyName + ", babyGender=" + babyGender + ", address=" + address + ", station="
                + station + ", memberID=" + memberID + ", memberPW=" + memberPW + ", memberName=" + memberName
                + ", memberTel=" + memberTel + ", memberinfo=" + memberinfo + ", registerDate=" + registerDate + "]";
    }


    public String getBabyName() {
        return babyName;
    }


    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }


    public String getBabyGender() {
        return babyGender;
    }


    public void setBabyGender(String babyGender) {
        this.babyGender = babyGender;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public String getStation() {
        return station;
    }


    public void setStation(String station) {
        this.station = station;
    }


    public String getMemberID() {
        return memberID;
    }


    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }


    public String getMemberPW() {
        return memberPW;
    }


    public void setMemberPW(String memberPW) {
        this.memberPW = memberPW;
    }


    public String getMemberName() {
        return memberName;
    }


    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }


    public String getMemberTel() {
        return memberTel;
    }


    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }


    public String getMemberinfo() {
        return memberinfo;
    }


    public void setMemberinfo(String memberinfo) {
        this.memberinfo = memberinfo;
    }


    public String getRegisterDate() {
        return registerDate;
    }


    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }


}