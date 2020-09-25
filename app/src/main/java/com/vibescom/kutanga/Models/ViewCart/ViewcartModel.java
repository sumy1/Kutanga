package com.vibescom.kutanga.Models.ViewCart;

import com.vibescom.kutanga.Models.BaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class ViewcartModel extends BaseModel implements Serializable {

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public int getTotalPayablePrice() {
        return totalPayablePrice;
    }

    public void setTotalPayablePrice(int totalPayablePrice) {
        this.totalPayablePrice = totalPayablePrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getRestorantsCharge() {
        return restorantsCharge;
    }

    public void setRestorantsCharge(int restorantsCharge) {
        this.restorantsCharge = restorantsCharge;
    }

    public int getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(int deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public int getCouponDis() {
        return couponDis;
    }

    public void setCouponDis(int couponDis) {
        this.couponDis = couponDis;
    }

    public String getProductImgPath() {
        return productImgPath;
    }

    public void setProductImgPath(String productImgPath) {
        this.productImgPath = productImgPath;
    }

    public String getResturantsImgpath() {
        return resturantsImgpath;
    }

    public void setResturantsImgpath(String resturantsImgpath) {
        this.resturantsImgpath = resturantsImgpath;
    }

    public CopyOnWriteArrayList<CartItemModel> getCartItemModels() {
        return cartItemModels;
    }

    public void setCartItemModels(CopyOnWriteArrayList<CartItemModel> cartItemModels) {
        this.cartItemModels = cartItemModels;
    }

    private int totalItems;
  private int subTotal;
  private int totalPayablePrice;
  private int totalPrice;
  private int restorantsCharge;
  private int deliveryCharge;
  private int couponDis;
  private String productImgPath;
  private String resturantsImgpath;

  private CopyOnWriteArrayList<CartItemModel>cartItemModels;



   public ViewcartModel(JSONObject jsonObject){

       if(jsonObject.has(kTotalItems)){
           try {
               this.totalItems=jsonObject.getInt(kTotalItems);
           } catch (JSONException e) {
               e.printStackTrace();
           }

       }

       if(jsonObject.has(kSubTotal)){
           try {
               this.subTotal=jsonObject.getInt(kSubTotal);
           } catch (JSONException e) {
               e.printStackTrace();
           }

       }

       if(jsonObject.has(kTotalPayablePrice)){
           try {
               this.totalPayablePrice=jsonObject.getInt(kTotalPayablePrice);
           } catch (JSONException e) {
               e.printStackTrace();
           }

       }

       if(jsonObject.has(kTotalPrice)){
           try {
               this.totalPrice=jsonObject.getInt(kTotalPrice);
           } catch (JSONException e) {
               e.printStackTrace();
           }
       }

       if(jsonObject.has(kResturantsCharge)){
           try {
               this.restorantsCharge=jsonObject.getInt(kResturantsCharge);
           } catch (JSONException e) {
               e.printStackTrace();
           }
       }

       if(jsonObject.has(kdeliveryCharges)){
           try {
               this.deliveryCharge=jsonObject.getInt(kdeliveryCharges);
           } catch (JSONException e) {
               e.printStackTrace();
           }
       }

       if(jsonObject.has(kCouponDis)){
           try {
               this.couponDis=jsonObject.getInt(kCouponDis);
           } catch (JSONException e) {
               e.printStackTrace();
           }
       }

       this.resturantsImgpath=getValue(jsonObject,kresturantsImagePath,String.class);
       this.productImgPath=getValue(jsonObject,kproductImagePath,String.class);

       if(jsonObject.has(kCart)){
           try {
               this.cartItemModels = handleMarketPlace(jsonObject.getJSONArray(kCart));
           } catch (JSONException e) {
               e.printStackTrace();
           }
       }

    }




    private CopyOnWriteArrayList<CartItemModel> handleMarketPlace(JSONArray jsonArray) throws JSONException {
        CopyOnWriteArrayList<CartItemModel> marketPlaces = new CopyOnWriteArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            marketPlaces.add(new CartItemModel(jsonArray.getJSONObject(i)));
        }
        return marketPlaces;
    }
}
