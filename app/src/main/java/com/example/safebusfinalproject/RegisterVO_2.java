package com.example.safebusfinalproject;

import java.util.Date;

public class RegisterVO_2 {
    String memberID = null;
    String memberPW = null;
    String memberNum = null;
    String teacherName = null;
    String teacherTel = null;
    Date registerDate = null;


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


    public String getteacherName() {
        return teacherName;
    }

    public void setteacherName(String teacherName) {
        this.teacherName = teacherName;
    }


    public String getteacherTel() {
        return teacherTel;
    }

    public void setteacherTel(String teacherTel) {
        this.teacherTel = teacherTel;
    }


    public Date getregisterDate() {
        return registerDate;
    }

    public void setregisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}
