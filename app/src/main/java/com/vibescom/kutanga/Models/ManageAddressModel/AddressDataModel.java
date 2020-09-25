package com.vibescom.kutanga.Models.ManageAddressModel;

import com.vibescom.kutanga.Models.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class AddressDataModel extends BaseModel implements Serializable {


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }




    private String userId;
    private String createdat;
    private String updatedAt;

    public boolean getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    private boolean isSelect;

    public int getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(int userAddressId) {
        this.userAddressId = userAddressId;
    }

    public String getShipname() {
        return shipname;
    }

    public void setShipname(String shipname) {
        this.shipname = shipname;
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

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getUserAddressStatus() {
        return userAddressStatus;
    }

    public void setUserAddressStatus(String userAddressStatus) {
        this.userAddressStatus = userAddressStatus;
    }

    private int userAddressId;
    private String shipname;
    private String shipMobile;
    private String city;
    private String state;
    private String country;
    private String zip;
    private String area;
    private String landmark;
    private String lat;
    private String lng;
    private String fullAddress;
    private String addressType;
    private String isDefault;
    private String userAddressStatus;

    public String getFormattedaddress() {
        return formattedaddress;
    }

    public void setFormattedaddress(String formattedaddress) {
        this.formattedaddress = formattedaddress;
    }

    private String formattedaddress;










    public AddressDataModel(JSONObject jsonObject){

        if(jsonObject.has(kuserAddressid)){
            try {
                this.userAddressId=jsonObject.getInt(kuserAddressid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        this.createdat=getValue(jsonObject,kCreatedAt,String.class);
        this.updatedAt=getValue(jsonObject,kUpdatedAt,String.class);
        this.userId=getValue(jsonObject,kUserId,String.class);
        this.addressType=getValue(jsonObject,kaddressType,String.class);
        this.area=getValue(jsonObject,kArea,String.class);
        this.city=getValue(jsonObject,kCity,String.class);
        this.country=getValue(jsonObject,kCountry,String.class);
        this.fullAddress=getValue(jsonObject,kFullAddress,String.class);
        this.lat=getValue(jsonObject,kLat,String.class);
        this.lng=getValue(jsonObject,klng,String.class);
        this.isDefault=getValue(jsonObject,kisDefault,String.class);
        this.landmark=getValue(jsonObject,kLandMark,String.class);
        this.state=getValue(jsonObject,kState,String.class);
        this.userAddressStatus=getValue(jsonObject,kUserAddressStatus,String.class);
        this.zip=getValue(jsonObject,kZip,String.class);
        this.formattedaddress=getValue(jsonObject,kFormatedaddress,String.class);



    }
}
