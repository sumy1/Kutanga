package com.vibescom.kutanga.Models.RestaurantsFoodModel;

import org.json.JSONObject;

import static com.vibescom.kutanga.Constants.Constants.kRestuarantsTypeId;
import static com.vibescom.kutanga.Constants.Constants.kTypeName;
import static com.vibescom.kutanga.Models.BaseModel.getValue;

public class LandingFood {

    private int resId;
    private String resName;
    private int resIcon;

    public int getRestaurantTypeId() {
        return restaurantTypeId;
    }

    public void setRestaurantTypeId(int restaurantTypeId) {
        this.restaurantTypeId = restaurantTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    private int restaurantTypeId;
    private String typeName;




    public LandingFood(JSONObject jsonResponse){
        this.restaurantTypeId = getValue(jsonResponse,kRestuarantsTypeId,Integer.class);
        this.typeName=getValue(jsonResponse,kTypeName,String.class);

    }

    public LandingFood(int resId, String resName, int resIcon){
        this.resId = resId;
        this.resName = resName;
        this.resIcon = resIcon;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }




}
