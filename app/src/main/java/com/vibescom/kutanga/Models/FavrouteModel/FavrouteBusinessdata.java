package com.vibescom.kutanga.Models.FavrouteModel;

import com.vibescom.kutanga.Models.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class FavrouteBusinessdata extends BaseModel implements Serializable {


    public int getVendorBusinessId() {
        return vendorBusinessId;
    }

    public void setVendorBusinessId(int vendorBusinessId) {
        this.vendorBusinessId = vendorBusinessId;
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

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }

    public String getFoodIndustryType() {
        return foodIndustryType;
    }

    public void setFoodIndustryType(String foodIndustryType) {
        this.foodIndustryType = foodIndustryType;
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

    public String getPaymentAcceptMode() {
        return paymentAcceptMode;
    }

    public void setPaymentAcceptMode(String paymentAcceptMode) {
        this.paymentAcceptMode = paymentAcceptMode;
    }

    public String getEstimatedeliveryTime() {
        return estimatedeliveryTime;
    }

    public void setEstimatedeliveryTime(String estimatedeliveryTime) {
        this.estimatedeliveryTime = estimatedeliveryTime;
    }

    public String getPackagingTime() {
        return packagingTime;
    }

    public void setPackagingTime(String packagingTime) {
        this.packagingTime = packagingTime;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(String minOrder) {
        this.minOrder = minOrder;
    }

    public String getContactEmailId() {
        return contactEmailId;
    }

    public void setContactEmailId(String contactEmailId) {
        this.contactEmailId = contactEmailId;
    }

    public String getRestorantsCharge() {
        return restorantsCharge;
    }

    public void setRestorantsCharge(String restorantsCharge) {
        this.restorantsCharge = restorantsCharge;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getDistanceTobeCovered() {
        return distanceTobeCovered;
    }

    public void setDistanceTobeCovered(String distanceTobeCovered) {
        this.distanceTobeCovered = distanceTobeCovered;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(String updatedat) {
        this.updatedat = updatedat;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTypenmae() {
        return typenmae;
    }

    public void setTypenmae(String typenmae) {
        this.typenmae = typenmae;
    }

    public String getReviewCunt() {
        return reviewCunt;
    }

    public void setReviewCunt(String reviewCunt) {
        this.reviewCunt = reviewCunt;
    }

    private int vendorBusinessId;
    private String vendorId;
    private String designation;
    private String companyName;
    private String coverImg;
    private String logoImg;
    private String foodIndustryType;
    private String businessType;
    private String city;
    private String address;
    private String address1;
    private String lat;
    private String longi;
    private String paymentAcceptMode;
    private String estimatedeliveryTime;
    private String packagingTime;
    private String registrationNumber;
    private String minOrder;
    private String contactEmailId;
    private String restorantsCharge;
    private String deliveryCharge;
    private String distanceTobeCovered;
    private String vendorBusinessstatus;
    private String createdBy;
    private String updatedBy;
    private String createdAt;
    private String updatedat;
    private String rating;
    private String typenmae;
    private String reviewCunt;

    public JSONObject getInTheSport() {
        return inTheSport;
    }

    public void setInTheSport(JSONObject inTheSport) {
        this.inTheSport = inTheSport;
    }

    private JSONObject inTheSport;


    public FavrouteBusinessdata(JSONObject jsonObject){

        if(jsonObject.has(kvVendorBusinessId)){
            try {
                this.vendorBusinessId=jsonObject.getInt(kvVendorBusinessId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        this.vendorId=getValue(jsonObject,kVendorId,String.class);
        this.address=getValue(jsonObject,kAddress,String.class);
        this.address1=getValue(jsonObject,kAddress1,String.class);
        this.businessType=getValue(jsonObject,kBusinessType,String.class);
        this.city=getValue(jsonObject,kCity,String.class);
        this.companyName=getValue(jsonObject,kCompanyName,String.class);
        this.contactEmailId=getValue(jsonObject,kcontactEmailId,String.class);
        this.coverImg=getValue(jsonObject,kCoverImage,String.class);
        this.createdAt=getValue(jsonObject,kCreatedAt,String.class);
        this.createdBy=getValue(jsonObject,kCreatedBy,String.class);
        this.deliveryCharge=getValue(jsonObject,kdeliveryCharges,String.class);
        this.designation=getValue(jsonObject,kDesignation,String.class);
        this.distanceTobeCovered=getValue(jsonObject,kDisToBeCovered,String.class);
        this.estimatedeliveryTime=getValue(jsonObject,kEstimateDeliveryTime,String.class);
        this.foodIndustryType=getValue(jsonObject,kFoodIndustriesType,String.class);
        this.lat=getValue(jsonObject,klatitude,String.class);
        this.logoImg=getValue(jsonObject,kLogoImage,String.class);
        this.longi=getValue(jsonObject,kLongitude,String.class);
        this.minOrder=getValue(jsonObject,kMinimumOrder,String.class);
        this.packagingTime=getValue(jsonObject,kPackagingTime,String.class);
        this.rating=getValue(jsonObject,krating,String.class);
        this.paymentAcceptMode=getValue(jsonObject,kPaymentAcceptMode,String.class);
        this.typenmae=getValue(jsonObject,kTypeName,String.class);
        this.restorantsCharge=getValue(jsonObject,kResturantsCharge,String.class);
        this.reviewCunt=getValue(jsonObject,kReviewCount,String.class);

        if(jsonObject.has(kVendor)){
            try {
                this.inTheSport = jsonObject.getJSONObject(kVendor);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
