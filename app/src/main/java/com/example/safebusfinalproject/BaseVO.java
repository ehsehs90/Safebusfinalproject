package com.example.safebusfinalproject;

import java.util.Date;

public class BaseVO {

    String memberID = null;
    String memberPW = null;
    String memberNum = null;
    String memberTel = null;
    Date registerDate = null;

    public BaseVO() {
    }

    public BaseVO(String memberID, String memberPW, String memberNum, String memberTel, Date registerDate) {
        this.memberID = memberID;
        this.memberPW = memberPW;
        this.memberNum = memberNum;
        this.memberTel = memberTel;
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "BaseVO{" +
                "memberID='" + memberID + '\'' +
                ", memberPW='" + memberPW + '\'' +
                ", memberNum='" + memberNum + '\'' +
                ", memberTel='" + memberTel + '\'' +
                ", registerDate=" + registerDate +
                '}';
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

    public String getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(String memberNum) {
        this.memberNum = memberNum;
    }

    public String getMemberTel() {
        return memberTel;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}
