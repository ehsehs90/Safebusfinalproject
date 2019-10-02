package com.example.safebusfinalproject.trash;

public class RegisterParentsVO {

    String babyName = null;
    String babyGender = null;
    String address = null;
    //String RFID = null;
    String station = null;

    @Override
    public String toString() {
        return "RegisterParentsVO{" +
                ", babyName='" + babyName + '\'' +
                ", babyGender='" + babyGender + '\'' +
                ", address='" + address + '\'' +
                //", RFID='" + RFID + '\'' +
                ", station='" + station + '\'' +
                '}';
    }

    public RegisterParentsVO(String babyName, String babyGender, String address, String station) {
        this.babyName = babyName;
        this.babyGender = babyGender;
        this.address = address;
        //this.RFID = RFID;
        this.station = station;
}

    public RegisterParentsVO() {
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


    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }


//    public String getRFID() {
//        return RFID;
//    }
//
//    public void setRFID(String RFID) {
//        this.RFID = RFID;
//    }


    public String getstation() {
        return station;
    }

    public void setstation(String station) {
        this.station = station;
    }

}
