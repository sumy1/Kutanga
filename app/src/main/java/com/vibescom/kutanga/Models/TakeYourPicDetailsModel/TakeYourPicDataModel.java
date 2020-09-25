package com.vibescom.kutanga.Models.TakeYourPicDetailsModel;

import org.json.JSONObject;

import java.io.Serializable;

import static com.vibescom.kutanga.Constants.Constants.kAddress;
import static com.vibescom.kutanga.Constants.Constants.kAddress1;
import static com.vibescom.kutanga.Constants.Constants.kBusinessType;
import static com.vibescom.kutanga.Constants.Constants.kCity;
import static com.vibescom.kutanga.Constants.Constants.kCompanyName;
import static com.vibescom.kutanga.Constants.Constants.kCoverImage;
import static com.vibescom.kutanga.Constants.Constants.kCreatedAt;
import static com.vibescom.kutanga.Constants.Constants.kCreatedBy;
import static com.vibescom.kutanga.Constants.Constants.kDesignation;
import static com.vibescom.kutanga.Constants.Constants.kDisToBeCovered;
import static com.vibescom.kutanga.Constants.Constants.kDistance;
import static com.vibescom.kutanga.Constants.Constants.kEstimateDeliveryTime;
import static com.vibescom.kutanga.Constants.Constants.kFoodIndustriesType;
import static com.vibescom.kutanga.Constants.Constants.kLogoImage;
import static com.vibescom.kutanga.Constants.Constants.kLongitude;
import static com.vibescom.kutanga.Constants.Constants.kMinimumOrder;
import static com.vibescom.kutanga.Constants.Constants.kOrderBookingStatus;
import static com.vibescom.kutanga.Constants.Constants.kPackagingTime;
import static com.vibescom.kutanga.Constants.Constants.kPaymentAcceptMode;
import static com.vibescom.kutanga.Constants.Constants.kRegistrationNumber;
import static com.vibescom.kutanga.Constants.Constants.kRestuarantsTypeId;
import static com.vibescom.kutanga.Constants.Constants.kReviewCount;
import static com.vibescom.kutanga.Constants.Constants.kTypeName;
import static com.vibescom.kutanga.Constants.Constants.kUpdatedAt;
import static com.vibescom.kutanga.Constants.Constants.kUpdatedBy;
import static com.vibescom.kutanga.Constants.Constants.kVendorId;
import static com.vibescom.kutanga.Constants.Constants.kcontactEmailId;
import static com.vibescom.kutanga.Constants.Constants.kdeliveryCharges;
import static com.vibescom.kutanga.Constants.Constants.klatitude;
import static com.vibescom.kutanga.Constants.Constants.krating;
import static com.vibescom.kutanga.Constants.Constants.kvVendorBusinessId;
import static com.vibescom.kutanga.Constants.Constants.kvendorBusinessStatus;
import static com.vibescom.kutanga.Models.BaseModel.getValue;

public class TakeYourPicDataModel implements Serializable {

    public int getVendorBusinessId() {
        return vendorBusinessId;
    }

    public void setVendorBusinessId(int vendorBusinessId) {
        this.vendorBusinessId = vendorBusinessId;
    }

    public String getVendorid() {
        return vendorid;
    }

    public void setVendorid(String vendorid) {
        this.vendorid = vendorid;
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

    public String getFoodIndustriesType() {
        return foodIndustriesType;
    }

    public void setFoodIndustriesType(String foodIndustriesType) {
        this.foodIndustriesType = foodIndustriesType;
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

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getPaymentAcceptMode() {
        return paymentAcceptMode;
    }

    public void setPaymentAcceptMode(String paymentAcceptMode) {
        this.paymentAcceptMode = paymentAcceptMode;
    }

    public String getEstimateDeliveryTime() {
        return estimateDeliveryTime;
    }

    public void setEstimateDeliveryTime(String estimateDeliveryTime) {
        this.estimateDeliveryTime = estimateDeliveryTime;
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

    public String getContactEmailid() {
        return contactEmailid;
    }

    public void setContactEmailid(String contactEmailid) {
        this.contactEmailid = contactEmailid;
    }

    public String getResturantsCharges() {
        return resturantsCharges;
    }

    public void setResturantsCharges(String resturantsCharges) {
        this.resturantsCharges = resturantsCharges;
    }

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getPackageName() {
        return packagingTime;
    }

    public void setPackageName(String packagingTime) {
        this.packagingTime = packagingTime;
    }

    public String getDistanceTobeCovered() {
        return distanceTobeCovered;
    }

    public void setDistanceTobeCovered(String distanceTobeCovered) {
        this.distanceTobeCovered = distanceTobeCovered;
    }

    public String getVendorBusinessStatus() {
        return vendorBusinessStatus;
    }

    public void setVendorBusinessStatus(String vendorBusinessStatus) {
        this.vendorBusinessStatus = vendorBusinessStatus;
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

    public String getResturantsTypeId() {
        return resturantsTypeId;
    }

    public void setResturantsTypeId(String resturantsTypeId) {
        this.resturantsTypeId = resturantsTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }

    private int vendorBusinessId;
    private String vendorid;
    private String designation;
    private String companyName;
    private String coverImage;
    private String logoImage;
    private String foodIndustriesType;
    private String businessType;
    private String city;
    private String address;
    private String address1;
    private String lat;
    private String lon;
    private String paymentAcceptMode;
    private String estimateDeliveryTime;
    private String registrationNumber;
    private String minOrder;
    private String contactEmailid;
    private String resturantsCharges;
    private String deliveryCharges;
    private String packagingTime;
    private String distanceTobeCovered;
    private String vendorBusinessStatus;
    private String createdBy;
    private String updatedBy;
    private String createdAt;
    private String updatedat;
    private String resturantsTypeId;
    private String typeName;
    private String rating;
    private String reviewCount;
    private String distance;

    public String getOrderBookingStatus() {
        return orderBookingStatus;
    }

    public void setOrderBookingStatus(String orderBookingStatus) {
        this.orderBookingStatus = orderBookingStatus;
    }

    private String orderBookingStatus;



    public TakeYourPicDataModel(JSONObject jsonObject){
        this.vendorBusinessId = getValue(jsonObject,kvVendorBusinessId,Integer.class);
        this.address=getValue(jsonObject,kAddress,String.class);
        this.address1=getValue(jsonObject,kAddress1,String.class);
        this.businessType=getValue(jsonObject,kBusinessType,String.class);
        this.city=getValue(jsonObject,kCity,String.class);
        this.companyName=getValue(jsonObject,kCompanyName,String.class);
        this.contactEmailid=getValue(jsonObject,kcontactEmailId,String.class);
        this.coverImage=getValue(jsonObject,kCoverImage,String.class);
        this.createdAt=getValue(jsonObject,kCreatedAt,String.class);
        this.createdBy=getValue(jsonObject,kCreatedBy,String.class);
        this.deliveryCharges=getValue(jsonObject,kdeliveryCharges,String.class);
        this.designation=getValue(jsonObject,kDesignation,String.class);
        this.distanceTobeCovered=getValue(jsonObject,kDisToBeCovered,String.class);
        this.estimateDeliveryTime=getValue(jsonObject,kEstimateDeliveryTime,String.class);
        this.foodIndustriesType=getValue(jsonObject,kFoodIndustriesType,String.class);
        this.lat=getValue(jsonObject,klatitude,String.class);
        this.lon=getValue(jsonObject,kLongitude,String.class);
        this.minOrder=getValue(jsonObject,kMinimumOrder,String.class);
        this.logoImage=getValue(jsonObject,kLogoImage,String.class);
        this.packagingTime=getValue(jsonObject,kPackagingTime,String.class);
        this.paymentAcceptMode=getValue(jsonObject,kPaymentAcceptMode,String.class);
        this.rating=getValue(jsonObject,krating,String.class);
        this.registrationNumber=getValue(jsonObject,kRegistrationNumber,String.class);
        this.resturantsTypeId=getValue(jsonObject,kRestuarantsTypeId,String.class);
        this.reviewCount=getValue(jsonObject,kReviewCount,String.class);
        this.typeName=getValue(jsonObject,kTypeName,String.class);
        this.updatedat=getValue(jsonObject,kUpdatedAt,String.class);
        this.updatedBy=getValue(jsonObject,kUpdatedBy,String.class);
        this.vendorBusinessStatus=getValue(jsonObject,kvendorBusinessStatus,String.class);
        this.vendorid=getValue(jsonObject,kVendorId,String.class);
        this.distance=getValue(jsonObject,kDistance,String.class);
        this.orderBookingStatus=getValue(jsonObject,kOrderBookingStatus,String.class);


    }
}
