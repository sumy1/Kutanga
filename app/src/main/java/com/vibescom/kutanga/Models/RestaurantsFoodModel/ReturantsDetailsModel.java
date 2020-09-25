package com.vibescom.kutanga.Models.RestaurantsFoodModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CopyOnWriteArrayList;

import static com.vibescom.kutanga.Constants.Constants.kResturant;
import static com.vibescom.kutanga.Constants.Constants.kcategories;
import static com.vibescom.kutanga.Constants.Constants.kproductImagePath;
import static com.vibescom.kutanga.Constants.Constants.kresturantsImagePath;
import static com.vibescom.kutanga.Models.BaseModel.getValue;

public class ReturantsDetailsModel {

    public String getProductImgPath() {
        return productImgPath;
    }

    public void setProductImgPath(String productImgPath) {
        this.productImgPath = productImgPath;
    }

    public String getResturantsImagepath() {
        return resturantsImagepath;
    }

    public void setResturantsImagepath(String resturantsImagepath) {
        this.resturantsImagepath = resturantsImagepath;
    }



    private String productImgPath;
  private String resturantsImagepath;

    public JSONObject getResturantsDetailsTypeModels() {
        return resturantsDetailsTypeModels;
    }

    public void setResturantsDetailsTypeModels(JSONObject resturantsDetailsTypeModels) {
        this.resturantsDetailsTypeModels = resturantsDetailsTypeModels;
    }

    private JSONObject resturantsDetailsTypeModels;

    public CopyOnWriteArrayList<ReturantsDetailscategoryModel> getReturantsDetailscategoryModels() {
        return returantsDetailscategoryModels;
    }

    public void setReturantsDetailscategoryModels(CopyOnWriteArrayList<ReturantsDetailscategoryModel> returantsDetailscategoryModels) {
        this.returantsDetailscategoryModels = returantsDetailscategoryModels;
    }

    private CopyOnWriteArrayList<ReturantsDetailscategoryModel> returantsDetailscategoryModels;


  public ReturantsDetailsModel(JSONObject jsonObject){

      this.productImgPath=getValue(jsonObject,kproductImagePath,String.class);
      this.resturantsImagepath=getValue(jsonObject,kresturantsImagePath,String.class);

      if(jsonObject.has(kResturant)){
          try {
              this.resturantsDetailsTypeModels =jsonObject.getJSONObject(kResturant);
          } catch (JSONException e) {
              e.printStackTrace();
          }
      }

      if(jsonObject.has(kcategories)){
          try {
              this.returantsDetailscategoryModels = handleMarketPlace(jsonObject.getJSONArray(kcategories));
          } catch (JSONException e) {
              e.printStackTrace();
          }
      }

  }


    private CopyOnWriteArrayList<ReturantsDetailscategoryModel> handleMarketPlace(JSONArray jsonArray) throws JSONException {
        CopyOnWriteArrayList<ReturantsDetailscategoryModel> marketPlaces = new CopyOnWriteArrayList<>();
        for(int i=0;i<jsonArray.length();i++){
            marketPlaces.add(new ReturantsDetailscategoryModel(jsonArray.getJSONObject(i)));
        }
        return marketPlaces;
    }

}
