package com.vibescom.kutanga.Models.MarketPlaceModel;

import org.json.JSONObject;

import static com.vibescom.kutanga.Constants.Constants.kCateId;
import static com.vibescom.kutanga.Constants.Constants.kCateStatus;
import static com.vibescom.kutanga.Constants.Constants.kCreatedBy;
import static com.vibescom.kutanga.Constants.Constants.kCreatedOn;
import static com.vibescom.kutanga.Constants.Constants.kIndustriesId;
import static com.vibescom.kutanga.Constants.Constants.kMetaDes;
import static com.vibescom.kutanga.Constants.Constants.kMetaKeyword;
import static com.vibescom.kutanga.Constants.Constants.kMetaTitle;
import static com.vibescom.kutanga.Constants.Constants.kParentcateId;
import static com.vibescom.kutanga.Constants.Constants.kUpdatedBy;
import static com.vibescom.kutanga.Constants.Constants.kUpdatedOn;
import static com.vibescom.kutanga.Constants.Constants.kcateImage;
import static com.vibescom.kutanga.Constants.Constants.kcateName;
import static com.vibescom.kutanga.Constants.Constants.kcateSlug;
import static com.vibescom.kutanga.Models.BaseModel.getValue;

public class MarketPlace {

    private int resId;
    private String resName;
    private int resIcon;

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getParentCateId() {
        return parentCateId;
    }

    public void setParentCateId(String parentCateId) {
        this.parentCateId = parentCateId;
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

    public String getMetaDes() {
        return metaDes;
    }

    public void setMetaDes(String metaDes) {
        this.metaDes = metaDes;
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

    private int cateId;
    private String parentCateId;
    private String industriesId;
    private String cateName;
    private String cateSlug;
    private String cateImage;
    private String metaTitle;
    private String metaKeyword;
    private String metaDes;
    private String cateStatus;
    private String createdOn;
    private String createdBy;
    private String updatedOn;
    private String updatedBy;



    public MarketPlace(JSONObject jsonResponse){
        this.cateId = getValue(jsonResponse,kCateId,Integer.class);
        this.cateImage=getValue(jsonResponse,kcateImage,String.class);
        this.cateName=getValue(jsonResponse,kcateName,String.class);
        this.cateSlug=getValue(jsonResponse,kcateSlug,String.class);
        this.cateStatus=getValue(jsonResponse,kCateStatus,String.class);
        this.createdBy=getValue(jsonResponse,kCreatedBy,String.class);
        this.createdOn=getValue(jsonResponse,kCreatedOn,String.class);
        this.industriesId=getValue(jsonResponse,kIndustriesId,String.class);
        this.metaDes=getValue(jsonResponse,kMetaDes,String.class);
        this.metaKeyword=getValue(jsonResponse,kMetaKeyword,String.class);
        this.metaTitle=getValue(jsonResponse,kMetaTitle,String.class);
        this.parentCateId=getValue(jsonResponse,kParentcateId,String.class);
        this.updatedBy=getValue(jsonResponse,kUpdatedBy,String.class);
        this.updatedOn=getValue(jsonResponse,kUpdatedOn,String.class);
    }

    public MarketPlace(int resId, String resName, int resIcon){
        this.resId = resId;
        this.resName = resName;
        this.resIcon = resIcon;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }




}
