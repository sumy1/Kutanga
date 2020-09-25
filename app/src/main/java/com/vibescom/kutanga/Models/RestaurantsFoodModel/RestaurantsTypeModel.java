package com.vibescom.kutanga.Models.RestaurantsFoodModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CopyOnWriteArrayList;

import static com.vibescom.kutanga.Constants.Constants.kOfferdata;
import static com.vibescom.kutanga.Constants.Constants.kOrder;
import static com.vibescom.kutanga.Constants.Constants.kViewAlllabel;
import static com.vibescom.kutanga.Constants.Constants.kimagepath;
import static com.vibescom.kutanga.Constants.Constants.ksubTitle;
import static com.vibescom.kutanga.Constants.Constants.ktitle;
import static com.vibescom.kutanga.Constants.Constants.kviewAllValue;
import static com.vibescom.kutanga.Models.BaseModel.getValue;

public class RestaurantsTypeModel {



    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getViewAlllabel() {
        return viewAlllabel;
    }

    public void setViewAlllabel(String viewAlllabel) {
        this.viewAlllabel = viewAlllabel;
    }

    public String getViewAllValue() {
        return viewAllValue;
    }

    public void setViewAllValue(String viewAllValue) {
        this.viewAllValue = viewAllValue;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String subTitle;
 private String viewAlllabel;
 private String viewAllValue;
 private int order;
 private String ImagePath;



    private CopyOnWriteArrayList<RestaurantsTypeData> resturantsType;

    public CopyOnWriteArrayList<RestaurantsTypeData> getResturantsType() {
        return resturantsType;
    }

    public void setResturantsType(CopyOnWriteArrayList<RestaurantsTypeData> resturantsType) {
        this.resturantsType = resturantsType;
    }

    public RestaurantsTypeModel(JSONObject jsonObject){
        this.title = getValue(jsonObject,ktitle,String.class);
        this.ImagePath=getValue(jsonObject,kimagepath,String.class);
        this.subTitle=getValue(jsonObject,ksubTitle,String.class);
        this.viewAlllabel=getValue(jsonObject,kViewAlllabel,String.class);
        this.viewAllValue=getValue(jsonObject,kviewAllValue,String.class);
        if(jsonObject.has(kOrder)){
            try {
                this.order=jsonObject.getInt(kOrder);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //this.order=Integer.valueOf(getValue(jsonObject,kOrder,Integer.class));

        if(jsonObject.has(kOfferdata)){
            try {
                this.resturantsType = handleMarketPlace(jsonObject.getJSONArray(kOfferdata));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private CopyOnWriteArrayList<RestaurantsTypeData> handleMarketPlace(JSONArray jsonArray) throws JSONException {
        CopyOnWriteArrayList<RestaurantsTypeData> marketPlaces = new CopyOnWriteArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            marketPlaces.add(new RestaurantsTypeData(jsonArray.getJSONObject(i)));
        }
        return marketPlaces;
    }
}
