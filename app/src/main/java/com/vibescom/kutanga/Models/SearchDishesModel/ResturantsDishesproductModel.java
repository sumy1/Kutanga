package com.vibescom.kutanga.Models.SearchDishesModel;

import com.vibescom.kutanga.Models.RestaurantsFoodModel.ProductDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CopyOnWriteArrayList;

import static com.vibescom.kutanga.Constants.Constants.kCreatedBy;
import static com.vibescom.kutanga.Constants.Constants.kCreatedOn;
import static com.vibescom.kutanga.Constants.Constants.kIndustriesId;
import static com.vibescom.kutanga.Constants.Constants.kMetaDes;
import static com.vibescom.kutanga.Constants.Constants.kMetaKeyword;
import static com.vibescom.kutanga.Constants.Constants.kMetaTitle;
import static com.vibescom.kutanga.Constants.Constants.kPopularityTag;
import static com.vibescom.kutanga.Constants.Constants.kProductAvailale;
import static com.vibescom.kutanga.Constants.Constants.kProductImage;
import static com.vibescom.kutanga.Constants.Constants.kUpdatedBy;
import static com.vibescom.kutanga.Constants.Constants.kUpdatedOn;
import static com.vibescom.kutanga.Constants.Constants.kapprovalMessage;
import static com.vibescom.kutanga.Constants.Constants.kapprovedBy;
import static com.vibescom.kutanga.Constants.Constants.kcateid;
import static com.vibescom.kutanga.Constants.Constants.kpreparationTime;
import static com.vibescom.kutanga.Constants.Constants.kproductDetails;
import static com.vibescom.kutanga.Constants.Constants.kproductId;
import static com.vibescom.kutanga.Constants.Constants.kproductName;
import static com.vibescom.kutanga.Constants.Constants.kproductSlug;
import static com.vibescom.kutanga.Constants.Constants.kproductStatus;
import static com.vibescom.kutanga.Constants.Constants.kproducttag;
import static com.vibescom.kutanga.Constants.Constants.krequestdate;
import static com.vibescom.kutanga.Constants.Constants.ksubcateid;
import static com.vibescom.kutanga.Models.BaseModel.getValue;

public class ResturantsDishesproductModel {

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCateid() {
        return cateid;
    }

    public void setCateid(String cateid) {
        this.cateid = cateid;
    }

    public String getSubcateid() {
        return subcateid;
    }

    public void setSubcateid(String subcateid) {
        this.subcateid = subcateid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProdutSlug() {
        return produtSlug;
    }

    public void setProdutSlug(String produtSlug) {
        this.produtSlug = produtSlug;
    }

    public String getIndustriesId() {
        return IndustriesId;
    }

    public void setIndustriesId(String industriesId) {
        IndustriesId = industriesId;
    }

    public String getPreprationTime() {
        return preprationTime;
    }

    public void setPreprationTime(String preprationTime) {
        this.preprationTime = preprationTime;
    }

    public String getPopularityTag() {
        return popularityTag;
    }

    public void setPopularityTag(String popularityTag) {
        this.popularityTag = popularityTag;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetakeyword() {
        return metakeyword;
    }

    public void setMetakeyword(String metakeyword) {
        this.metakeyword = metakeyword;
    }

    public String getMetaDes() {
        return metaDes;
    }

    public void setMetaDes(String metaDes) {
        this.metaDes = metaDes;
    }

    public String getProductstatus() {
        return productstatus;
    }

    public void setProductstatus(String productstatus) {
        this.productstatus = productstatus;
    }

    public String getPrducttag() {
        return prducttag;
    }

    public void setPrducttag(String prducttag) {
        this.prducttag = prducttag;
    }

    public String getProductAvailable() {
        return productAvailable;
    }

    public void setProductAvailable(String productAvailable) {
        this.productAvailable = productAvailable;
    }

    public String getProductdelStatus() {
        return productdelStatus;
    }

    public void setProductdelStatus(String productdelStatus) {
        this.productdelStatus = productdelStatus;
    }

    public String getApprovalmessage() {
        return approvalmessage;
    }

    public void setApprovalmessage(String approvalmessage) {
        this.approvalmessage = approvalmessage;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getResquestdate() {
        return resquestdate;
    }

    public void setResquestdate(String resquestdate) {
        this.resquestdate = resquestdate;
    }

    public String getCraetedOn() {
        return craetedOn;
    }

    public void setCraetedOn(String craetedOn) {
        this.craetedOn = craetedOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    private int productId;
    private String cateid;
    private String subcateid;
    private String productName;
    private String produtSlug;
    private String IndustriesId;
    private String preprationTime;
    private String popularityTag;
    private String metaTitle;
    private String metakeyword;
    private String metaDes;
    private String productstatus;
    private String prducttag;
    private String productAvailable;
    private String productdelStatus;
    private String approvalmessage;
    private String approvedBy;
    private String resquestdate;
    private String craetedOn;
    private String createdBy;
    private String updatedOn;
    private String updateBy;


    public CopyOnWriteArrayList<ProductDetails> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(CopyOnWriteArrayList<ProductDetails> productDetails) {
        this.productDetails = productDetails;
    }

    private CopyOnWriteArrayList<ProductDetails>productDetails;

    public CopyOnWriteArrayList<ProductImageModel> getProductImage() {
        return productImage;
    }

    public void setProductImage(CopyOnWriteArrayList<ProductImageModel> productImage) {
        this.productImage = productImage;
    }

    private CopyOnWriteArrayList<ProductImageModel>productImage;



    public ResturantsDishesproductModel(JSONObject jsonObject){
        this.updateBy=getValue(jsonObject,kUpdatedBy,String.class);
        this.createdBy=getValue(jsonObject,kCreatedBy,String.class);
        this.updatedOn=getValue(jsonObject,kUpdatedOn,String.class);
        this.craetedOn=getValue(jsonObject,kCreatedOn,String.class);
        this.resquestdate=getValue(jsonObject,krequestdate,String.class);
        this.approvedBy=getValue(jsonObject,kapprovedBy,String.class);
        this.approvalmessage=getValue(jsonObject,kapprovalMessage,String.class);
        this.productdelStatus=getValue(jsonObject,kproductStatus,String.class);
        this.productAvailable=getValue(jsonObject,kProductAvailale,String.class);
        this.prducttag=getValue(jsonObject,kproducttag,String.class);
        this.productstatus=getValue(jsonObject,kproductStatus,String.class);
        this.metaDes=getValue(jsonObject,kMetaDes,String.class);
        this.metakeyword=getValue(jsonObject,kMetaKeyword,String.class);
        this.metaTitle=getValue(jsonObject,kMetaTitle,String.class);
        this.popularityTag=getValue(jsonObject,kPopularityTag,String.class);
        this.preprationTime=getValue(jsonObject,kpreparationTime,String.class);
        this.IndustriesId=getValue(jsonObject,kIndustriesId,String.class);
        this.produtSlug=getValue(jsonObject,kproductSlug,String.class);
        this.productName=getValue(jsonObject,kproductName,String.class);
        this.subcateid=getValue(jsonObject,ksubcateid,String.class);
        this.cateid=getValue(jsonObject,kcateid,String.class);

        if(jsonObject.has(kproductId)){
            try {
                this.productId=jsonObject.getInt(kproductId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has(kproductDetails)){
            try {
                this.productDetails = handleProductDetails(jsonObject.getJSONArray(kproductDetails));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has(kProductImage)){
            try {
                this.productImage = handleProductImage(jsonObject.getJSONArray(kProductImage));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private CopyOnWriteArrayList<ProductDetails> handleProductDetails(JSONArray jsonArray) throws JSONException {
        CopyOnWriteArrayList<ProductDetails> marketPlaces = new CopyOnWriteArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            marketPlaces.add(new ProductDetails(jsonArray.getJSONObject(i)));
        }
        return marketPlaces;
    }

    private CopyOnWriteArrayList<ProductImageModel> handleProductImage(JSONArray jsonArray) throws JSONException {
        CopyOnWriteArrayList<ProductImageModel> marketPlaces = new CopyOnWriteArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            marketPlaces.add(new ProductImageModel(jsonArray.getJSONObject(i)));
        }
        return marketPlaces;
    }
}
