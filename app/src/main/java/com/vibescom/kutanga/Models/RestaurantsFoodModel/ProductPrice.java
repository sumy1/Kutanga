package com.vibescom.kutanga.Models.RestaurantsFoodModel;

import org.json.JSONException;
import org.json.JSONObject;

import static com.vibescom.kutanga.Constants.Constants.kCreatedBy;
import static com.vibescom.kutanga.Constants.Constants.kCreatedOn;
import static com.vibescom.kutanga.Constants.Constants.kDiscount;
import static com.vibescom.kutanga.Constants.Constants.kPriceId;
import static com.vibescom.kutanga.Constants.Constants.kProductDetaildId;
import static com.vibescom.kutanga.Constants.Constants.kUpdatedBy;
import static com.vibescom.kutanga.Constants.Constants.kUpdatedOn;
import static com.vibescom.kutanga.Constants.Constants.kdiscountprice;
import static com.vibescom.kutanga.Constants.Constants.kproductPrice;
import static com.vibescom.kutanga.Constants.Constants.kquantity;
import static com.vibescom.kutanga.Models.BaseModel.getValue;

public class ProductPrice {
    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public String getProductDetailsId() {
        return productDetailsId;
    }

    public void setProductDetailsId(String productDetailsId) {
        this.productDetailsId = productDetailsId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCretaedBy() {
        return cretaedBy;
    }

    public void setCretaedBy(String cretaedBy) {
        this.cretaedBy = cretaedBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    private int priceId;
    private String productDetailsId;
    private String quantity;
    private String productprice;
    private String discount;
    private String discountPrice;
    private String createdOn;
    private String cretaedBy;
    private String updatedOn;
    private String updatedBy;



    public ProductPrice(JSONObject jsonObject){
        this.createdOn=getValue(jsonObject,kCreatedOn,String.class);
        this.cretaedBy=getValue(jsonObject,kCreatedBy,String.class);
        this.updatedOn=getValue(jsonObject,kUpdatedOn,String.class);
        this.updatedBy=getValue(jsonObject,kUpdatedBy,String.class);
        this.productDetailsId=getValue(jsonObject,kProductDetaildId,String.class);
        this.quantity=getValue(jsonObject,kquantity,String.class);
        this.productprice=getValue(jsonObject,kproductPrice,String.class);
        this.discount=getValue(jsonObject,kDiscount,String.class);
        this.discountPrice=getValue(jsonObject,kdiscountprice,String.class);
        if(jsonObject.has(kPriceId)){
            try {
                priceId=jsonObject.getInt(kPriceId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



    }
}
