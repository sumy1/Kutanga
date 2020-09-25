package com.vibescom.kutanga.Models.NotificationModel;

import com.vibescom.kutanga.Models.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class NotificationData extends BaseModel implements Serializable {

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getIndustriesId() {
        return industriesId;
    }

    public void setIndustriesId(String industriesId) {
        this.industriesId = industriesId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponCodeprice() {
        return couponCodeprice;
    }

    public void setCouponCodeprice(String couponCodeprice) {
        this.couponCodeprice = couponCodeprice;
    }

    public String getFoodItemtax() {
        return foodItemtax;
    }

    public void setFoodItemtax(String foodItemtax) {
        this.foodItemtax = foodItemtax;
    }

    public String getDeliverYCharge() {
        return deliverYCharge;
    }

    public void setDeliverYCharge(String deliverYCharge) {
        this.deliverYCharge = deliverYCharge;
    }

    public String getFoodDeliveryTax() {
        return foodDeliveryTax;
    }

    public void setFoodDeliveryTax(String foodDeliveryTax) {
        this.foodDeliveryTax = foodDeliveryTax;
    }

    public String getRetaurantsCharge() {
        return retaurantsCharge;
    }

    public void setRetaurantsCharge(String retaurantsCharge) {
        this.retaurantsCharge = retaurantsCharge;
    }

    public String getRestaurantsChargetax() {
        return restaurantsChargetax;
    }

    public void setRestaurantsChargetax(String restaurantsChargetax) {
        this.restaurantsChargetax = restaurantsChargetax;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCancelreason() {
        return cancelreason;
    }

    public void setCancelreason(String cancelreason) {
        this.cancelreason = cancelreason;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUserCancelStatus() {
        return userCancelStatus;
    }

    public void setUserCancelStatus(String userCancelStatus) {
        this.userCancelStatus = userCancelStatus;
    }

    public String getUsercancelreson() {
        return usercancelreson;
    }

    public void setUsercancelreson(String usercancelreson) {
        this.usercancelreson = usercancelreson;
    }

    public String getUsercancelregOn() {
        return usercancelregOn;
    }

    public void setUsercancelregOn(String usercancelregOn) {
        this.usercancelregOn = usercancelregOn;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getTransictionId() {
        return transictionId;
    }

    public void setTransictionId(String transictionId) {
        this.transictionId = transictionId;
    }

    public String getTransitionStatus() {
        return transitionStatus;
    }

    public void setTransitionStatus(String transitionStatus) {
        this.transitionStatus = transitionStatus;
    }

    public String getTransitionOtherInfo() {
        return transitionOtherInfo;
    }

    public void setTransitionOtherInfo(String transitionOtherInfo) {
        this.transitionOtherInfo = transitionOtherInfo;
    }

    public String getTransitionOn() {
        return transitionOn;
    }

    public void setTransitionOn(String transitionOn) {
        this.transitionOn = transitionOn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPincode() {
        return userPincode;
    }

    public void setUserPincode(String userPincode) {
        this.userPincode = userPincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingMobile() {
        return shippingMobile;
    }

    public void setShippingMobile(String shippingMobile) {
        this.shippingMobile = shippingMobile;
    }

    public String getShippingEmail() {
        return shippingEmail;
    }

    public void setShippingEmail(String shippingEmail) {
        this.shippingEmail = shippingEmail;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public void setShippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    public String getShippingPincode() {
        return shippingPincode;
    }

    public void setShippingPincode(String shippingPincode) {
        this.shippingPincode = shippingPincode;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public JSONObject getJsonObject() {
        return jsonObjectNotice;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObjectNotice = jsonObject;
    }

    private JSONObject jsonObjectNotice;

    private int orderId;
    private String industriesId;
    private String orderCode;
    private String userId;
    private String orderPrice;
    private String couponCode;
    private String couponCodeprice;
    private String foodItemtax;
    private String deliverYCharge;
    private String foodDeliveryTax;
    private String retaurantsCharge;
    private String restaurantsChargetax;
    private String discountPrice;
    private String orderStatus;
    private String cancelreason;
    private String createdOn;
    private String userCancelStatus;
    private String usercancelreson;
    private String usercancelregOn;
    private String paymentMode;
    private String transictionId;
    private String transitionStatus;
    private String transitionOtherInfo;
    private String transitionOn;
    private String userName;
    private String userMobile;
    private String userEmail;
    private String userPincode;
    private String address;
    private String userCity;
    private String userState;
    private String userCountry;
    private String shippingName;
    private String shippingMobile;
    private String shippingEmail;
    private String shippingCity;
    private String shippingState;
    private String shippingCountry;
    private String shippingPincode;
    private String updatedOn;

    public NotificationData(JSONObject jsonObject){

        if(jsonObject.has(kOrderId)){
            try {
                this.orderId=jsonObject.getInt(kOrderId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        this.address=getValue(jsonObject,kAddress,String.class);
        this.cancelreason=getValue(jsonObject,kuserCancelReson,String.class);
        this.couponCode=getValue(jsonObject,kCouponCode,String.class);
        this.couponCodeprice=getValue(jsonObject,kCouponCodePrice,String.class);
        this.createdOn=getValue(jsonObject,kCreatedOn,String.class);
        this.deliverYCharge=getValue(jsonObject,kDeliveryCharge,String.class);
        this.discountPrice=getValue(jsonObject,kdiscaountPrice,String.class);
        this.foodDeliveryTax=getValue(jsonObject,kFoodDeliveryTax,String.class);
        this.foodItemtax=getValue(jsonObject,kFoodItemTax,String.class);
        this.industriesId=getValue(jsonObject,kIndustriesId,String.class);
        this.orderCode=getValue(jsonObject,kOrderCode,String.class);
        this.orderPrice=getValue(jsonObject,kOrderPrice,String.class);
        this.orderStatus=getValue(jsonObject,kOrderStatus,String.class);
        this.paymentMode=getValue(jsonObject,kPaymentMode,String.class);
        this.restaurantsChargetax=getValue(jsonObject,kRestaurantsChargetax,String.class);
        this.retaurantsCharge=getValue(jsonObject,kRestaurantsCharge,String.class);
        this.shippingCity=getValue(jsonObject,kShippingCity,String.class);
        this.shippingCountry=getValue(jsonObject,kShippingCountry,String.class);
        this.shippingEmail=getValue(jsonObject,kShippingEmail,String.class);
        this.shippingMobile=getValue(jsonObject,kShippingMobile,String.class);
        this.shippingName=getValue(jsonObject,kShippingname,String.class);
        this.shippingPincode=getValue(jsonObject,kShippingPincode,String.class);
        this.shippingState=getValue(jsonObject,kShippingState,String.class);
        this.transictionId=getValue(jsonObject,kTransitionId,String.class);
        this.transitionOn=getValue(jsonObject,kTransitionOn,String.class);
        this.transitionOtherInfo=getValue(jsonObject,kTransitionOtherInfo,String.class);
        this.transitionStatus=getValue(jsonObject,kTaransitionStatus,String.class);
        this.updatedOn=getValue(jsonObject,kUpdatedOn,String.class);
        this.usercancelregOn=getValue(jsonObject,kUserCancelReq,String.class);
        this.usercancelreson=getValue(jsonObject,kuserCancelReson,String.class);
        this.userCancelStatus=getValue(jsonObject,kuserCancelStatus,String.class);
        this.userCity=getValue(jsonObject,kuserCity,String.class);
        this.userCountry=getValue(jsonObject,kuserCountry,String.class);
        this.userEmail=getValue(jsonObject,kUserEmail,String.class);
        this.userId=getValue(jsonObject,kUserId,String.class);
        this.userMobile=getValue(jsonObject,kUserMobile,String.class);
        this.userName=getValue(jsonObject,kUserName,String.class);
        this.userPincode=getValue(jsonObject,kuserPincode,String.class);
        this.userState=getValue(jsonObject,kuserState,String.class);


        if(jsonObject.has(knoticetemaplate)){
            try {
                jsonObjectNotice=jsonObject.getJSONObject(knoticetemaplate);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


}
