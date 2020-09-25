package com.vibescom.kutanga.Models.RestaurantsFoodModel;

import org.json.JSONException;
import org.json.JSONObject;

import static com.vibescom.kutanga.Constants.Constants.kIntheSportLight;
import static com.vibescom.kutanga.Constants.Constants.kOffers;
import static com.vibescom.kutanga.Constants.Constants.kResturantsType;
import static com.vibescom.kutanga.Constants.Constants.kTopPics;
import static com.vibescom.kutanga.Constants.Constants.kfeturedBrand;
import static com.vibescom.kutanga.Models.BaseModel.getValue;

public class FoodLandingPageModel {


    public JSONObject getOffersModels() {
        return offersModels;
    }

    public void setOffersModels(JSONObject offersModels) {
        this.offersModels = offersModels;
    }

    public JSONObject getRestaurantsTypeModels() {
        return restaurantsTypeModels;
    }

    public void setRestaurantsTypeModels(JSONObject restaurantsTypeModels) {
        this.restaurantsTypeModels = restaurantsTypeModels;
    }

    public JSONObject getInTheSportLightModels() {
        return inTheSportLightModels;
    }

    public void setInTheSportLightModels(JSONObject inTheSportLightModels) {
        this.inTheSportLightModels = inTheSportLightModels;
    }

    public JSONObject getTopPicModels() {
        return topPicModels;
    }

    public void setTopPicModels(JSONObject topPicModels) {
        this.topPicModels = topPicModels;
    }

    public JSONObject getFeturedBrandModels() {
        return feturedBrandModels;
    }

    public void setFeturedBrandModels(JSONObject feturedBrandModels) {
        this.feturedBrandModels = feturedBrandModels;
    }

    JSONObject offersModels;
    JSONObject restaurantsTypeModels;
    JSONObject inTheSportLightModels;
    JSONObject topPicModels;
    JSONObject feturedBrandModels;






    public FoodLandingPageModel(JSONObject jsonObject){


        try {
            this.offersModels = handleOffers(getValue(jsonObject,kOffers, JSONObject.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.restaurantsTypeModels = handleresturants(getValue(jsonObject,kResturantsType, JSONObject.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.inTheSportLightModels = handleIntheSportLight(getValue(jsonObject,kIntheSportLight, JSONObject.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.topPicModels = handleTopPic(getValue(jsonObject,kTopPics, JSONObject.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.feturedBrandModels = handleFeturedBrand(getValue(jsonObject,kfeturedBrand, JSONObject.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    private JSONObject handleOffers(JSONObject jsonObject) throws JSONException {
        new OffersModel(jsonObject);
        return jsonObject;
    }

    private JSONObject handleresturants(JSONObject jsonObject) throws JSONException {
       new RestaurantsTypeModel(jsonObject);

        return jsonObject;
    }

    private JSONObject handleIntheSportLight(JSONObject jsonObject) throws JSONException {
        new InTheSportLightModel(jsonObject);

        return jsonObject;
    }

    private JSONObject handleTopPic(JSONObject jsonObject) throws JSONException {
       new TopPicModel(jsonObject);

        return jsonObject;
    }

    private JSONObject handleFeturedBrand(JSONObject jsonObject) throws JSONException {
        new FeturedBrandModel(jsonObject);

        return jsonObject;
    }

}
