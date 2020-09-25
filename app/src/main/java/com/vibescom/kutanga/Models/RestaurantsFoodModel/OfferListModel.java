package com.vibescom.kutanga.Models.RestaurantsFoodModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CopyOnWriteArrayList;

import static com.vibescom.kutanga.Constants.Constants.kImageOfferPath;
import static com.vibescom.kutanga.Constants.Constants.kOffers;
import static com.vibescom.kutanga.Models.BaseModel.getValue;

public class OfferListModel {


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    private String imagePath;

    public CopyOnWriteArrayList<OfferData> getOfferData() {
        return offerData;
    }

    public void setOfferData(CopyOnWriteArrayList<OfferData> offerData) {
        this.offerData = offerData;
    }

    private CopyOnWriteArrayList<OfferData> offerData;

    public OfferListModel(JSONObject jsonObject ){

        this.imagePath = getValue(jsonObject,kImageOfferPath,String.class);
        if(jsonObject.has(kOffers)){
            try {
                this.offerData = handleMarketPlace(jsonObject.getJSONArray(kOffers));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    private CopyOnWriteArrayList<OfferData> handleMarketPlace(JSONArray jsonArray) throws JSONException {
        CopyOnWriteArrayList<OfferData> marketPlaces = new CopyOnWriteArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            marketPlaces.add(new OfferData(jsonArray.getJSONObject(i)));
        }
        return marketPlaces;
    }
}
