package com.vibescom.kutanga.Models.ViewCart;

import com.vibescom.kutanga.Models.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CartItemModel extends BaseModel implements Serializable {

    public int getCartid() {
        return cartid;
    }

    public void setCartid(int cartid) {
        this.cartid = cartid;
    }

    public String getCartName() {
        return cartName;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public int getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(int cartPrice) {
        this.cartPrice = cartPrice;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    private int cartid;
    private String cartName;
    private int cartPrice;
    private int cartQuantity;

    public JSONObject getCartattribute() {
        return cartattribute;
    }

    public void setCartattribute(JSONObject cartattribute) {
        this.cartattribute = cartattribute;
    }

    private JSONObject cartattribute;

    public JSONObject getAssocialteModel() {
        return associalteModel;
    }

    public void setAssocialteModel(JSONObject associalteModel) {
        this.associalteModel = associalteModel;
    }

    private JSONObject associalteModel;


    public CartItemModel(JSONObject jsonObject) {

        if(jsonObject.has(kcartid)){
            try {
                this.cartid=jsonObject.getInt(kcartid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        this.cartName=getValue(jsonObject,kCartName,String.class);
        if(jsonObject.has(kPrice)){
            try {
                this.cartPrice=jsonObject.getInt(kPrice);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        if(jsonObject.has(kQuantity)){
            try {
                this.cartQuantity=jsonObject.getInt(kQuantity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if(jsonObject.has(kattribute)){
            try {
                this.cartattribute=jsonObject.getJSONObject(kattribute);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if(jsonObject.has(kassociateModel)){
            try {
                this.associalteModel=jsonObject.getJSONObject(kassociateModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
