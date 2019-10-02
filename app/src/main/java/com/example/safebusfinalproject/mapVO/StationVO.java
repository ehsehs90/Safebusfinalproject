package com.example.safebusfinalproject.mapVO;

public class StationVO {
    String index = null;
    String viaPointId = null;
    String viaPointName = null;
    String viaDetailAddress = null; //경유지
    String groupKey = null; //경유지
    String arriveTime = null;
    String completeTime = null;
    String distance = null;
    String deliveryTime = null;
    String waitTime = null;
    String pointType = null;



    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getViaPointId() {
        return viaPointId;
    }

    public void setViaPointId(String viaPointId) {
        this.viaPointId = viaPointId;
    }

    public String getViaPointName() {
        return viaPointName;
    }

    public void setViaPointName(String viaPointName) {
        this.viaPointName = viaPointName;
    }

    public String getViaDetailAddress() {
        return viaDetailAddress;
    }

    public void setViaDetailAddress(String viaDetailAddress) {
        this.viaDetailAddress = viaDetailAddress;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }
}
