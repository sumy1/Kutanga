package com.vibescom.kutanga.Models;

import com.vibescom.kutanga.Models.MarketPlaceModel.MarketPlace;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.LandingFood;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CopyOnWriteArrayList;

import static com.vibescom.kutanga.Constants.Constants.kFood;
import static com.vibescom.kutanga.Constants.Constants.kFoodImagePath;
import static com.vibescom.kutanga.Constants.Constants.kmarketPlace;
import static com.vibescom.kutanga.Constants.Constants.kmarketplaceImagePath;
import static com.vibescom.kutanga.Models.BaseModel.getValue;

public class RestaurantsFoodCommonLanding {


    public String getMarketplaceImagepath() {
        return marketplaceImagepath;
    }

    public void setMarketplaceImagepath(String marketplaceImagepath) {
        this.marketplaceImagepath = marketplaceImagepath;
    }

    public String getFoodImagePath() {
        return foodImagePath;
    }

    public void setFoodImagePath(String foodImagePath) {
        this.foodImagePath = foodImagePath;
    }

    public CopyOnWriteArrayList<MarketPlace> getMarketPlaces() {
        return marketPlaces;
    }

    public void setMarketPlaces(CopyOnWriteArrayList<MarketPlace> marketPlaces) {
        this.marketPlaces = marketPlaces;
    }

    public CopyOnWriteArrayList<LandingFood> getLandingFoods() {
        return landingFoods;
    }

    public void setLandingFoods(CopyOnWriteArrayList<LandingFood> landingFoods) {
        this.landingFoods = landingFoods;
    }

    private String marketplaceImagepath;
    private String foodImagePath;

    CopyOnWriteArrayList<MarketPlace>marketPlaces;
    CopyOnWriteArrayList<LandingFood>landingFoods;





    public RestaurantsFoodCommonLanding(JSONObject jsonObject){
        this.foodImagePath=getValue(jsonObject,kFoodImagePath,String.class);
        this.marketplaceImagepath=getValue(jsonObject,kmarketplaceImagePath,String.class);

        try {
            this.marketPlaces = handleMarketPlace(getValue(jsonObject,kmarketPlace, JSONArray.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.landingFoods = handleLandingFood(getValue(jsonObject,kFood, JSONArray.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    private CopyOnWriteArrayList<MarketPlace> handleMarketPlace(JSONArray jsonArray) throws JSONException {
        CopyOnWriteArrayList<MarketPlace> marketPlaces = new CopyOnWriteArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            marketPlaces.add(new MarketPlace(jsonArray.getJSONObject(i)));
        }
        return marketPlaces;
    }

    private CopyOnWriteArrayList<LandingFood> handleLandingFood(JSONArray jsonArray) throws JSONException {
        CopyOnWriteArrayList<LandingFood> landingFood = new CopyOnWriteArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            landingFood.add(new LandingFood(jsonArray.getJSONObject(i)));
        }
        return landingFood;
    }

}
