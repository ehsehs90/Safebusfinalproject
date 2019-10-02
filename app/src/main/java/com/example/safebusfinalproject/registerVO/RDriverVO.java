package com.example.safebusfinalproject.registerVO;

public class RDriverVO {

    String driverLicense = null;
    //String driverPhone = null;
    String carNumber = null;
    String driverPicture= null;

    String memberID = null;
    String memberPW = null;
    String memberName = null;
    String memberTel = null;
    String memberinfo = null;
    String registerDate = null;


    public RDriverVO() {
        super();
    }


    public RDriverVO(String driverLicense, String carNumber, String driverPicture, String memberID, String memberPW,
                     String memberName, String memberTel, String memberinfo, String registerDate) {
        super();
        this.driverLicense = driverLicense;
        this.carNumber = carNumber;
        this.driverPicture = driverPicture;
        this.memberID = memberID;
        this.memberPW = memberPW;
        this.memberName = memberName;
        this.memberTel = memberTel;
        this.memberinfo = memberinfo;
        this.registerDate = registerDate;
    }


    @Override
    public String toString() {
        return "RDriverVO [driverLicense=" + driverLicense + ", carNumber=" + carNumber + ", driverPicture="
                + driverPicture + ", memberID=" + memberID + ", memberPW=" + memberPW + ", memberName=" + memberName
                + ", memberTel=" + memberTel + ", memberinfo=" + memberinfo + ", registerDate=" + registerDate + "]";
    }


    public String getDriverLicense() {
        return driverLicense;
    }


    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }


    public String getCarNumber() {
        return carNumber;
    }


    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }


    public String getDriverPicture() {
        return driverPicture;
    }


    public void setDriverPicture(String driverPicture) {
        this.driverPicture = driverPicture;
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
