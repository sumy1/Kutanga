package com.vibescom.kutanga.Models.RestaurantsFoodModel;

import org.json.JSONObject;

import static com.vibescom.kutanga.Constants.Constants.kRestuarantsTypeId;
import static com.vibescom.kutanga.Constants.Constants.kTypeName;
import static com.vibescom.kutanga.Models.BaseModel.getValue;

public class RestaurantsTypeData {


    public int getResturantTypeId() {
        return resturantTypeId;
    }

    public void setResturantTypeId(int resturantTypeId) {
        this.resturantTypeId = resturantTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    private int resturantTypeId;
    private String typeName;


    public RestaurantsTypeData(JSONObject jsonObject){
        this.resturantTypeId = getValue(jsonObject,kRestuarantsTypeId,Integer.class);
        this.typeName = getValue(jsonObject,kTypeName,Integer.class);

    }
}
