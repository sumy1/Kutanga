package com.vibescom.kutanga.Models.RestaurantsFoodModel;

import org.json.JSONException;
import org.json.JSONObject;

import static com.vibescom.kutanga.Constants.Constants.kCreatedBy;
import static com.vibescom.kutanga.Constants.Constants.kCreatedOn;
import static com.vibescom.kutanga.Constants.Constants.kProductDetaildId;
import static com.vibescom.kutanga.Constants.Constants.kProductattibuteId;
import static com.vibescom.kutanga.Constants.Constants.kattibutes;
import static com.vibescom.kutanga.Models.BaseModel.getValue;

public class ProductAtribute {

    public int getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(int productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public String getProductDetailsId() {
        return productDetailsId;
    }

    public void setProductDetailsId(String productDetailsId) {
        this.productDetailsId = productDetailsId;
    }

    public String getAttibutes() {
        return attibutes;
    }

    public void setAttibutes(String attibutes) {
        this.attibutes = attibutes;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    private int productAttributeId;
    private String productDetailsId;
    private String attibutes;
    private String createdOn;
    private String createdBy;

    public ProductAtribute(JSONObject jsonObject) {
        this.createdOn=getValue(jsonObject,kCreatedOn,String.class);
        this.createdBy=getValue(jsonObject,kCreatedBy,String.class);
        this.attibutes=getValue(jsonObject,kattibutes,String.class);
        this.productDetailsId=getValue(jsonObject,kProductDetaildId,String.class);

        if(jsonObject.has(kProductattibuteId)){
            try {
                productAttributeId=jsonObject.getInt(kProductattibuteId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
