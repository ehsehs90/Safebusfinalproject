package com.example.safebusfinalproject.trash;

public class BaseVO {

    String memberID = null;
    String memberPW = null;
    String memberName = null;
    String memberTel = null;
    String memberinfo = null;
    String registerDate = null;

    public BaseVO() {
    }

    public BaseVO(String memberID, String memberPW, String memberName, String memberTel, String memberinfo, String registerDate) {
        this.memberID = memberID;
        this.memberPW = memberPW;
        this.memberName = memberName;
        this.memberTel = memberTel;
        this.memberinfo = memberinfo;
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "BaseVO{" +
                "memberID='" + memberID + '\'' +
                ", memberPW='" + memberPW + '\'' +
                ", memberNum='" + memberName + '\'' +
                ", memberTel='" + memberTel + '\'' +
                ", memberinfo='" + memberinfo + '\'' +
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