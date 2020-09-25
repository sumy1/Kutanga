package com.vibescom.kutanga.Models.RestaurantsFoodModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CopyOnWriteArrayList;

import static com.vibescom.kutanga.Constants.Constants.kResturants;
import static com.vibescom.kutanga.Constants.Constants.kcurrentShorting;
import static com.vibescom.kutanga.Constants.Constants.kdishes;
import static com.vibescom.kutanga.Constants.Constants.kkeyword;
import static com.vibescom.kutanga.Constants.Constants.kproductImagePath;
import static com.vibescom.kutanga.Constants.Constants.kresturantsImagePath;
import static com.vibescom.kutanga.Constants.Constants.kshortOrder;
import static com.vibescom.kutanga.Constants.Constants.kshortkey;
import static com.vibescom.kutanga.Models.BaseModel.getValue;

public class ResturantsDishesSearchModel {

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }

    public String getResturantsImagePath() {
        return resturantsImagePath;
    }

    public void setResturantsImagePath(String resturantsImagePath) {
        this.resturantsImagePath = resturantsImagePath;
    }

    private String productImagePath;
    private String resturantsImagePath;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getShortkey() {
        return shortkey;
    }

    public void setShortkey(String shortkey) {
        this.shortkey = shortkey;
    }

    public String getShortOrder() {
        return shortOrder;
    }

    public void setShortOrder(String shortOrder) {
        this.shortOrder = shortOrder;
    }

    public String getCurrentShorting() {
        return currentShorting;
    }

    public void setCurrentShorting(String currentShorting) {
        this.currentShorting = currentShorting;
    }

    private String keyword;
    private String shortkey;
    private String shortOrder;
    private String currentShorting;

    public CopyOnWriteArrayList<SearchDishesModel> getSearchDishesModels() {
        return searchDishesModels;
    }

    public void setSearchDishesModels(CopyOnWriteArrayList<SearchDishesModel> searchDishesModels) {
        this.searchDishesModels = searchDishesModels;
    }

    private CopyOnWriteArrayList<SearchDishesModel> searchDishesModels;

    public CopyOnWriteArrayList<SearchResturantsModel> getSearchResturantsModels() {
        return searchResturantsModels;
    }

    public void setSearchResturantsModels(CopyOnWriteArrayList<SearchResturantsModel> searchResturantsModels) {
        this.searchResturantsModels = searchResturantsModels;
    }

    private CopyOnWriteArrayList<SearchResturantsModel> searchResturantsModels;

    public ResturantsDishesSearchModel(JSONObject jsonObject) {
        this.productImagePath = getValue(jsonObject, kproductImagePath, String.class);
        this.resturantsImagePath = getValue(jsonObject, kresturantsImagePath, String.class);
        this.currentShorting = getValue(jsonObject, kcurrentShorting, String.class);
        this.keyword = getValue(jsonObject, kkeyword, String.class);
        this.shortkey = getValue(jsonObject, kshortkey, String.class);
        this.shortOrder = getValue(jsonObject, kshortOrder, String.class);

        if (jsonObject.has(kResturants)) {
            try {
                this.searchResturantsModels = handleResturantsSearch(jsonObject.getJSONArray(kResturants));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (jsonObject.has(kdishes)) {
            try {
                this.searchDishesModels = handleDishesSearch(jsonObject.getJSONArray(kdishes));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private CopyOnWriteArrayList<SearchResturantsModel> handleResturantsSearch(JSONArray jsonArray) throws JSONException {
        CopyOnWriteArrayList<SearchResturantsModel> marketPlaces = new CopyOnWriteArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            marketPlaces.add(new SearchResturantsModel(jsonArray.getJSONObject(i)));
        }
        return marketPlaces;
    }

    private CopyOnWriteArrayList<SearchDishesModel> handleDishesSearch(JSONArray jsonArray) throws JSONException {
        CopyOnWriteArrayList<SearchDishesModel> marketPlaces = new CopyOnWriteArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            marketPlaces.add(new SearchDishesModel(jsonArray.getJSONObject(i)));
        }
        return marketPlaces;
    }
}
