package com.example.safebusfinalproject.businfoVO;

public class BusinfoVO {

    String temp = null;
    String humidity = null;
    String velocity = null;
    String longitude = null; // 경도
    String latitude = null; // 위도

    public BusinfoVO() {
    }

    public BusinfoVO(String temp, String humidity, String velocity, String longitude, String latitude) {
        super();
        this.temp = temp;
        this.humidity = humidity;
        this.velocity = velocity;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "BusinfoVO [temp=" + temp + ", humidity=" + humidity + ", velocity=" + velocity + ", longitude="
                + longitude + ", latitude=" + latitude + "]";
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVelocity() {
        return velocity;
    }

    public void setVelocity(String velocity) {
        this.velocity = velocity;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

}