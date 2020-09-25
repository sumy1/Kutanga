package com.vibescom.kutanga.Models.AddUpdateAddressModel;

import com.vibescom.kutanga.Models.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ChildaddressModel extends BaseModel implements Serializable {

    public int getUserAddressid() {
        return userAddressid;
    }

    public void setUserAddressid(int userAddressid) {
        this.userAddressid = userAddressid;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipMobile() {
        return shipMobile;
    }

    public void setShipMobile(String shipMobile) {
        this.shipMobile = shipMobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getFulladdress() {
        return fulladdress;
    }

    public void setFulladdress(String fulladdress) {
        this.fulladdress = fulladdress;
    }

    public String getFormatedaddress() {
        return formatedaddress;
    }

    public void setFormatedaddress(String formatedaddress) {
        this.formatedaddress = formatedaddress;
    }

    public String getAddessType() {
        return addessType;
    }

    public void setAddessType(String addessType) {
        this.addessType = addessType;
    }

    public String getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }

    public String getUseraddressStatus() {
        return useraddressStatus;
    }

    public void setUseraddressStatus(String useraddressStatus) {
        this.useraddressStatus = useraddressStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    private int userAddressid;
    private String UserId;
    private String shipName;
    private String shipMobile;
    private String city;
    private String state;
    private String country;
    private String zip;
    private String area;
    private String landmark;
    private String lat;
    private String lng;
    private String fulladdress;
    private String formatedaddress;
    private String addessType;
    private String isdefault;
    private String useraddressStatus;
    private String createdAt;
    private String updatedAt;

    public ChildaddressModel(JSONObject jsonObject){
        if(jsonObject.has(kuserAddressId)){
            try {
                this.userAddressid=jsonObject.getInt(kuserAddressId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        this.addessType=getValue(jsonObject,kaddressType,String.class);
        this.area=getValue(jsonObject,kArea,String.class);
        this.city=getValue(jsonObject,kCity,String.class);
        this.country=getValue(jsonObject,kCountry,String.class);
        this.createdAt=getValue(jsonObject,kCreatedAt,String.class);
        this.formatedaddress=getValue(jsonObject,kFormatedaddress,String.class);
        this.fulladdress=getValue(jsonObject,kFullAddress,String.class);
        this.isdefault=getValue(jsonObject,kisDefault,String.class);
        this.landmark=getValue(jsonObject,kLandMark,String.class);
        this.lat=getValue(jsonObject,kLat,String.class);
        this.shipMobile=getValue(jsonObject,kShipMobile,String.class);
        this.state=getValue(jsonObject,kState,String.class);
        this.updatedAt=getValue(jsonObject,kUpdatedAt,String.class);
        this.zip=getValue(jsonObject,kZip,String.class);
        this.lng=getValue(jsonObject,klng,String.class);
        this.useraddressStatus=getValue(jsonObject,kUserAddressStatus,String.class);
        this.shipName=getValue(jsonObject,kShipName,String.class);


    }



}
