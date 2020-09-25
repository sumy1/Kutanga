package com.vibescom.kutanga.Models.PastOrderModle;

import com.vibescom.kutanga.Models.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class OrderDetailsModel extends BaseModel implements Serializable {


    public int getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(int orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getVendorid() {
        return vendorid;
    }

    public void setVendorid(String vendorid) {
        this.vendorid = vendorid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantityType() {
        return itemQuantityType;
    }

    public void setItemQuantityType(String itemQuantityType) {
        this.itemQuantityType = itemQuantityType;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getIsDispached() {
        return isDispached;
    }

    public void setIsDispached(String isDispached) {
        this.isDispached = isDispached;
    }

    public String getCredtedOn() {
        return credtedOn;
    }

    public void setCredtedOn(String credtedOn) {
        this.credtedOn = credtedOn;
    }

    public String getUserItemcancelStatus() {
        return userItemcancelStatus;
    }

    public void setUserItemcancelStatus(String userItemcancelStatus) {
        this.userItemcancelStatus = userItemcancelStatus;
    }

    public String getUserItemcancenReason() {
        return userItemcancenReason;
    }

    public void setUserItemcancenReason(String userItemcancenReason) {
        this.userItemcancenReason = userItemcancenReason;
    }

    public String getUserItemCnacelReqOn() {
        return userItemCnacelReqOn;
    }

    public void setUserItemCnacelReqOn(String userItemCnacelReqOn) {
        this.userItemCnacelReqOn = userItemCnacelReqOn;
    }

    private int orderDetailsId;
    private String orderId;
    private String vendorid;
    private String productid;
    private String productColor;
    private String vendorName;
    private String vendorAddress;
    private String itemName;
    private String itemQuantityType;
    private String itemQuantity;
    private String itemPrice;
    private String itemImage;
    private String isDispached;
    private String credtedOn;
    private String userItemcancelStatus;
    private String userItemcancenReason;
    private String userItemCnacelReqOn;


    public JSONObject getInTheSport() {
        return inTheSport;
    }

    public void setInTheSport(JSONObject inTheSport) {
        this.inTheSport = inTheSport;
    }

    private JSONObject inTheSport;




    public OrderDetailsModel(JSONObject  jsonObject){
        this.orderId=getValue(jsonObject,kOrderId,String.class);

        if(jsonObject.has(kOrderDetailsId)){
            try {
                this.orderDetailsId=jsonObject.getInt(kOrderDetailsId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.productid=getValue(jsonObject,kproductId,String.class);
        this.credtedOn=getValue(jsonObject,kCreatedOn,String.class);
        this.isDispached=getValue(jsonObject,kIsDispached,String.class);
        this.itemImage=getValue(jsonObject,kItemImage,String.class);
        this.itemName=getValue(jsonObject,kItemName,String.class);
        this.itemPrice=getValue(jsonObject,kItemPrice,String.class);
        this.itemQuantity=getValue(jsonObject,kItemQuantity,String.class);
        this.itemQuantityType=getValue(jsonObject,kItemQuantityType,String.class);
        this.productColor=getValue(jsonObject,kProductColor,String.class);
        this.userItemcancelStatus=getValue(jsonObject,kuserItemCancelStatus,String.class);
        this.userItemcancenReason=getValue(jsonObject,kuserCancelReason,String.class);
        this.userItemCnacelReqOn=getValue(jsonObject,kuserCancelReqOn,String.class);
        this.vendorid=getValue(jsonObject,kVendorId,String.class);
        this.vendorAddress=getValue(jsonObject,kvendorAddress,String.class);
        this.vendorName=getValue(jsonObject,kVendorName,String.class);


        if(jsonObject.has(kVendor)){
            try {
                this.inTheSport = jsonObject.getJSONObject(kVendor);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

}
