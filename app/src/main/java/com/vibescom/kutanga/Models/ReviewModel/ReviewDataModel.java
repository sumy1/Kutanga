package com.vibescom.kutanga.Models.ReviewModel;

import com.vibescom.kutanga.Models.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ReviewDataModel extends BaseModel implements Serializable {

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public String getIndustriesId() {
        return industriesId;
    }

    public void setIndustriesId(String industriesId) {
        this.industriesId = industriesId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVendorid() {
        return vendorid;
    }

    public void setVendorid(String vendorid) {
        this.vendorid = vendorid;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getHasObjection() {
        return hasObjection;
    }

    public void setHasObjection(String hasObjection) {
        this.hasObjection = hasObjection;
    }

    public String getRatingStatus() {
        return ratingStatus;
    }

    public void setRatingStatus(String ratingStatus) {
        this.ratingStatus = ratingStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    private int ratingId;
    private String industriesId;
    private String userId;
    private String vendorid;
    private String productId;
    private String orderid;
    private String review;
    private String hasObjection;
    private String ratingStatus;
    private String createdBy;
    private String updatedAt;
    private String createdAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    private String rating;

    public ReviewDataModel(JSONObject jsonObject){

        if(jsonObject.has(kratingId)){
            try {
                this.ratingId=jsonObject.getInt(kratingId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.createdBy=getValue(jsonObject,kCreatedBy,String.class);
        this.hasObjection=getValue(jsonObject,khasObjection,String.class);
        this.industriesId=getValue(jsonObject,kIndustriesId,String.class);
        this.orderid=getValue(jsonObject,kOrderId,String.class);
        this.ratingStatus=getValue(jsonObject,kratingStatus,String.class);
        this.productId=getValue(jsonObject,kproductId,String.class);
        this.review=getValue(jsonObject,kReview,String.class);
        this.userId=getValue(jsonObject,kUserId,String.class);
        this.vendorid=getValue(jsonObject,kVendorId,String.class);
        this.updatedAt=getValue(jsonObject,kUpdatedAt,String.class);
        this.createdAt=getValue(jsonObject,kCreatedAt,String.class);
        this.rating=getValue(jsonObject,krating,String.class);

    }



}
