package com.vibescom.kutanga.Models.SearchDishesModel;

import com.vibescom.kutanga.Models.BaseModel;

import org.json.JSONObject;

import java.io.Serializable;

public class ProductImageModel extends BaseModel implements Serializable {

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    private String productImage;

    public ProductImageModel(JSONObject jsonObject){
        this.productImage=getValue(jsonObject,kproductImage,String.class);
    }
}
