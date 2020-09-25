package com.vibescom.kutanga.Models.ViewCart;

import com.vibescom.kutanga.Models.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CartVendorModule extends BaseModel implements Serializable {


    public int getVendorBusinessid() {
        return vendorBusinessid;
    }

    public void setVendorBusinessid(int vendorBusinessid) {
        this.vendorBusinessid = vendorBusinessid;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public String getFoodIndustrytype() {
        return foodIndustrytype;
    }

    public void setFoodIndustrytype(String foodIndustrytype) {
        this.foodIndustrytype = foodIndustrytype;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getPaymentaccepMode() {
        return paymentaccepMode;
    }

    public void setPaymentaccepMode(String paymentaccepMode) {
        this.paymentaccepMode = paymentaccepMode;
    }

    public String getEstimatedeliveryTime() {
        return estimatedeliveryTime;
    }

    public void setEstimatedeliveryTime(String estimatedeliveryTime) {
        this.estimatedeliveryTime = estimatedeliveryTime;
    }

    public String getPackagingtime() {
        return packagingtime;
    }

    public void setPackagingtime(String packagingtime) {
        this.packagingtime = packagingtime;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getMiniumOrder() {
        return miniumOrder;
    }

    public void setMiniumOrder(String miniumOrder) {
        this.miniumOrder = miniumOrder;
    }

    public String getContctEmailId() {
        return contctEmailId;
    }

    public void setContctEmailId(String contctEmailId) {
        this.contctEmailId = contctEmailId;
    }

    public String getRestoranstCharge() {
        return restoranstCharge;
    }

    public void setRestoranstCharge(String restoranstCharge) {
        this.restoranstCharge = restoranstCharge;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getDistanceCovered() {
        return distanceCovered;
    }

    public void setDistanceCovered(String distanceCovered) {
        this.distanceCovered = distanceCovered;
    }

    public String getVendorBusinessstatus() {
        return vendorBusinessstatus;
    }

    public void setVendorBusinessstatus(String vendorBusinessstatus) {
        this.vendorBusinessstatus = vendorBusinessstatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
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

    private int vendorBusinessid;
    private String vendorId;
    private String designation;
    private String companyName;
    private String coverImage;
    private String logoImage;
    private String foodIndustrytype;
    private String businessType;
    private String city;
    private String address;
    private String address1;
    private String lat;
    private String longi;
    private String paymentaccepMode;
    private String estimatedeliveryTime;
    private String packagingtime;
    private String registrationNumber;
    private String miniumOrder;
    private String contctEmailId;
    private String restoranstCharge;
    private String deliveryCharge;
    private String distanceCovered;
    private String vendorBusinessstatus;
    private String createdBy;
    private String updatedBy;
    private String createdat;
    private String updatedAt;


    public CartVendorModule(JSONObject jsonObject){

        if(jsonObject.has(kvVendorBusinessId)){
            try {
                this.vendorBusinessid=jsonObject.getInt(kvVendorBusinessId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.address=getValue(jsonObject,kAddress,String.class);
        this.address1=getValue(jsonObject,kAddress1,String.class);
        this.businessType=getValue(jsonObject,kBusinessType,String.class);
        this.city=getValue(jsonObject,kCity,String.class);
        this.companyName=getValue(jsonObject,kCompanyName,String.class);
        this.contctEmailId=getValue(jsonObject,kcontactEmailId,String.class);
        this.coverImage=getValue(jsonObject,kCoverImage,String.class);
        this.createdat=getValue(jsonObject,kCreatedAt,String.class);
        this.createdBy=getValue(jsonObject,kCreatedBy,String.class);
        this.deliveryCharge=getValue(jsonObject,kdeliveryCharges,String.class);
        this.designation=getValue(jsonObject,kDesignation,String.class);
        this.distanceCovered=getValue(jsonObject,kDisToBeCovered,String.class);
        this.estimatedeliveryTime=getValue(jsonObject,kEstimateDeliveryTime,String.class);
        this.foodIndustrytype=getValue(jsonObject,kFoodIndustriesType,String.class);
        this.lat=getValue(jsonObject,klatitude,String.class);
        this.longi=getValue(jsonObject,kLongitude,String.class);
        this.logoImage=getValue(jsonObject,kLogoImage,String.class);
        this.packagingtime=getValue(jsonObject,kPackagingTime,String.class);
        this.paymentaccepMode=getValue(jsonObject,kPaymentAcceptMode,String.class);
        this.miniumOrder=getValue(jsonObject,kMinimumOrder,String.class);
        this.registrationNumber=getValue(jsonObject,kRegistrationNumber,String.class);
        this.restoranstCharge=getValue(jsonObject,kResturantsCharge,String.class);
        this.updatedBy=getValue(jsonObject,kUpdatedBy,String.class);
        this.vendorBusinessstatus=getValue(jsonObject,kvendorBusinessStatus,String.class);
        this.vendorId=getValue(jsonObject,kVendorId,String.class);


    }

}
