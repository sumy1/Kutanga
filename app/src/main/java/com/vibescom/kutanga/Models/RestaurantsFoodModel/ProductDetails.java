package com.vibescom.kutanga.Models.RestaurantsFoodModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CopyOnWriteArrayList;

import static com.vibescom.kutanga.Constants.Constants.kCreatedBy;
import static com.vibescom.kutanga.Constants.Constants.kCreatedOn;
import static com.vibescom.kutanga.Constants.Constants.kProductDetaildId;
import static com.vibescom.kutanga.Constants.Constants.kmainattribute;
import static com.vibescom.kutanga.Constants.Constants.kmainattributeslug;
import static com.vibescom.kutanga.Constants.Constants.kproductAttribute;
import static com.vibescom.kutanga.Constants.Constants.kproductId;
import static com.vibescom.kutanga.Constants.Constants.kproductPrice;
import static com.vibescom.kutanga.Models.BaseModel.getValue;

public class ProductDetails {

    public int getProductdetailsid() {
        return productdetailsid;
    }

    public void setProductdetailsid(int productdetailsid) {
        this.productdetailsid = productdetailsid;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMinAttribute() {
        return minAttribute;
    }

    public void setMinAttribute(String minAttribute) {
        this.minAttribute = minAttribute;
    }

    public String getMainattributeslug() {
        return mainattributeslug;
    }

    public void setMainattributeslug(String mainattributeslug) {
        this.mainattributeslug = mainattributeslug;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public CopyOnWriteArrayList<ProductPrice> getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(CopyOnWriteArrayList<ProductPrice> productPrices) {
        this.productPrices = productPrices;
    }

    public CopyOnWriteArrayList<ProductAtribute> getProductAtributes() {
        return productAtributes;
    }

    public void setProductAtributes(CopyOnWriteArrayList<ProductAtribute> productAtributes) {
        this.productAtributes = productAtributes;
    }

    private int productdetailsid;
    private String productId;
    private String minAttribute;
    private String mainattributeslug;
    private String createdOn;
    private String createdBy;

    private CopyOnWriteArrayList<ProductPrice>productPrices;
    private CopyOnWriteArrayList<ProductAtribute>productAtributes;







    public ProductDetails(JSONObject jsonObject){
        this.createdOn=getValue(jsonObject,kCreatedOn,String.class);
        this.createdBy=getValue(jsonObject,kCreatedBy,String.class);
        this.mainattributeslug=getValue(jsonObject,kmainattributeslug,String.class);
        this.minAttribute=getValue(jsonObject,kmainattribute,String.class);
        this.productId=getValue(jsonObject,kproductId,String.class);

        if(jsonObject.has(kProductDetaildId)){
            try {
                this.productdetailsid=jsonObject.getInt(kProductDetaildId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has(kproductPrice)){
            try {
                this.productPrices = handleProductPrice(jsonObject.getJSONArray(kproductPrice));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has(kproductAttribute)){
            try {
                this.productAtributes = handleProductAttribute(jsonObject.getJSONArray(kproductAttribute));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    private CopyOnWriteArrayList<ProductPrice> handleProductPrice(JSONArray jsonArray) throws JSONException {
        CopyOnWriteArrayList<ProductPrice> marketPlaces = new CopyOnWriteArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            marketPlaces.add(new ProductPrice(jsonArray.getJSONObject(i)));
        }
        return marketPlaces;
    }

    private CopyOnWriteArrayList<ProductAtribute> handleProductAttribute(JSONArray jsonArray) throws JSONException {
        CopyOnWriteArrayList<ProductAtribute> marketPlaces = new CopyOnWriteArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            marketPlaces.add(new ProductAtribute(jsonArray.getJSONObject(i)));
        }
        return marketPlaces;
    }


    private CopyOnWriteArrayList<ProductImage> handleProductImage(JSONArray jsonArray) throws JSONException {
        CopyOnWriteArrayList<ProductImage> marketPlaces = new CopyOnWriteArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            marketPlaces.add(new ProductImage(jsonArray.getJSONObject(i)));
        }
        return marketPlaces;
    }
}
