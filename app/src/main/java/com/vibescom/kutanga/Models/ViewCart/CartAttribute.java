package com.vibescom.kutanga.Models.ViewCart;

import com.vibescom.kutanga.Models.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CartAttribute extends BaseModel implements Serializable {

    public String getPriceQuantity() {
        return priceQuantity;
    }

    public void setPriceQuantity(String priceQuantity) {
        this.priceQuantity = priceQuantity;
    }

    public int getItemPriceSum() {
        return itemPriceSum;
    }

    public void setItemPriceSum(int itemPriceSum) {
        this.itemPriceSum = itemPriceSum;
    }

    private String priceQuantity;
    private int itemPriceSum;



    public CartAttribute(JSONObject jsonObject){

        this.priceQuantity=getValue(jsonObject,kpriceQuantity,String.class);
        if(jsonObject.has(kItempriceSum)){
            try {
                this.itemPriceSum=jsonObject.getInt(kItempriceSum);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}
