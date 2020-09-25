package com.vibescom.kutanga.Models.RestaurantsFoodModel;

import org.json.JSONObject;

import static com.vibescom.kutanga.Constants.Constants.kproductImage;
import static com.vibescom.kutanga.Models.BaseModel.getValue;

public class ProductImage {


    private String product_image;

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public ProductImage(JSONObject jsonObject) {
        this.product_image=getValue(jsonObject,kproductImage,String.class);

    }
}
