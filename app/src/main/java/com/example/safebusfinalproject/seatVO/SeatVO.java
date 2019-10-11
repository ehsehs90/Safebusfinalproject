package com.example.safebusfinalproject.seatVO;

public class SeatVO {

    String Seat1 = null;
    String Seat2 = null;


    @Override
    public String toString() {
        return "SeatVO{" +
                "Seat1='" + Seat1 + '\'' +
                ", Seat2='" + Seat2 + '\'' +
                '}';
    }

    public SeatVO() {
    }

    public SeatVO(String seat1, String seat2) {
        Seat1 = seat1;
        Seat2 = seat2;
    }

    public void setSeat1(String seat1) {
        Seat1 = seat1;
    }

    public void setSeat2(String seat2) {
        Seat2 = seat2;
    }

    public String getSeat1() {
        return Seat1;
    }

    public String getSeat2() {
        return Seat2;
    }
}
