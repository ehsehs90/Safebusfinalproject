package com.example.safebusfinalproject.trash;

public class RegisterDriverVO {

    String driverLicense = null;
    String carNum = null;
    String driverPicture = null;

    public RegisterDriverVO() {
    }

    public RegisterDriverVO(String driverLicense, String carNum, String driverPicture) {
        this.driverLicense = driverLicense;
        this.carNum = carNum;
        this.driverPicture = driverPicture;
    }

    @Override
    public String toString() {
        return "RegisterDriverVO{" +
                "driverLicense='" + driverLicense + '\'' +
                ", carNum='" + carNum + '\'' +
                ", driverPicture='" + driverPicture + '\'' +
                '}';
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getDriverPicture() {
        return driverPicture;
    }

    public void setDriverPicture(String driverPicture) {
        this.driverPicture = driverPicture;
    }
}
