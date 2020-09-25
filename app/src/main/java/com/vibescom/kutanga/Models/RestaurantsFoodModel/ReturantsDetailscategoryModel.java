package com.vibescom.kutanga.Models.RestaurantsFoodModel;

import com.vibescom.kutanga.Models.BaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class ReturantsDetailscategoryModel extends BaseModel implements Serializable {

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }



    public String getIndustriesId() {
        return industriesId;
    }

    public void setIndustriesId(String industriesId) {
        this.industriesId = industriesId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCateSlug() {
        return cateSlug;
    }

    public void setCateSlug(String cateSlug) {
        this.cateSlug = cateSlug;
    }

    public String getCateImage() {
        return cateImage;
    }

    public void setCateImage(String cateImage) {
        this.cateImage = cateImage;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaKeyword() {
        return metaKeyword;
    }

    public void setMetaKeyword(String metaKeyword) {
        this.metaKeyword = metaKeyword;
    }

    public String getMetades() {
        return metades;
    }

    public void setMetades(String metades) {
        this.metades = metades;
    }

    public String getCateStatus() {
        return cateStatus;
    }

    public void setCateStatus(String cateStatus) {
        this.cateStatus = cateStatus;
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

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public CopyOnWriteArrayList<ResturantsproductModel> getResturantsproductModels() {
        return resturantsproductModels;
    }

    public void setResturantsproductModels(CopyOnWriteArrayList<ResturantsproductModel> resturantsproductModels) {
        this.resturantsproductModels = resturantsproductModels;
    }

    private int cateId;

    public String getParentcateid() {
        return parentcateid;
    }

    public void setParentcateid(String parentcateid) {
        this.parentcateid = parentcateid;
    }

    private String parentcateid;
    private String industriesId;
    private String cateName;
    private String cateSlug;
    private String cateImage;
    private String metaTitle;
    private String metaKeyword;
    private String metades;
    private String cateStatus;
    private String createdOn;
    private String createdBy;
    private String updatedOn;
    private String updatedBy;

    private CopyOnWriteArrayList<ResturantsproductModel>resturantsproductModels;



    public ReturantsDetailscategoryModel(JSONObject jsonObject){
        this.parentcateid=getValue(jsonObject,kParentcateId,String.class);
        this.industriesId=getValue(jsonObject,kIndustriesId,String.class);
        this.cateName=getValue(jsonObject,kcateName,String.class);
        this.cateSlug=getValue(jsonObject,kcateSlug,String.class);
        this.cateImage=getValue(jsonObject,kcateImage,String.class);
        this.metaTitle=getValue(jsonObject,kMetaTitle,String.class);
        this.metaKeyword=getValue(jsonObject,kMetaKeyword,String.class);
        this.metades=getValue(jsonObject,kMetaDes,String.class);
        this.cateStatus=getValue(jsonObject,kCateStatus,String.class);
        this.createdOn=getValue(jsonObject,createdOn,String.class);
        this.createdBy=getValue(jsonObject,kCreatedBy,String.class);
        this.updatedOn=getValue(jsonObject,kUpdatedOn,String.class);
        this.updatedBy=getValue(jsonObject,kUpdatedBy,String.class);

        if(jsonObject.has(kcateid)){
            try {
                this.cateId=jsonObject.getInt(kcateid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has(kproduct)){
            try {
                this.resturantsproductModels = handleMarketPlace(jsonObject.getJSONArray(kproduct));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    private CopyOnWriteArrayList<ResturantsproductModel> handleMarketPlace(JSONArray jsonArray) throws JSONException {
        CopyOnWriteArrayList<ResturantsproductModel> marketPlaces = new CopyOnWriteArrayList<>();
        for(int i=0;i<jsonArray.length();i++){
            marketPlaces.add(new ResturantsproductModel(jsonArray.getJSONObject(i)));
        }
        return marketPlaces;
    }

}
