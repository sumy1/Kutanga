package com.vibescom.kutanga.Models.ViewCart;

import com.vibescom.kutanga.Models.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CartAssociateModel extends BaseModel implements Serializable {
    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
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

    public String getProductslug() {
        return productslug;
    }

    public void setProductslug(String productslug) {
        this.productslug = productslug;
    }

    public String getIndustriesid() {
        return industriesid;
    }

    public void setIndustriesid(String industriesid) {
        this.industriesid = industriesid;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getPopularitytag() {
        return popularitytag;
    }

    public void setPopularitytag(String popularitytag) {
        this.popularitytag = popularitytag;
    }

    public String getMetatitle() {
        return metatitle;
    }

    public void setMetatitle(String metatitle) {
        this.metatitle = metatitle;
    }

    public String getMetakeyword() {
        return metakeyword;
    }

    public void setMetakeyword(String metakeyword) {
        this.metakeyword = metakeyword;
    }

    public String getMetadescription() {
        return metadescription;
    }

    public void setMetadescription(String metadescription) {
        this.metadescription = metadescription;
    }

    public String getProductstatus() {
        return productstatus;
    }

    public void setProductstatus(String productstatus) {
        this.productstatus = productstatus;
    }

    public String getProducttag() {
        return producttag;
    }

    public void setProducttag(String producttag) {
        this.producttag = producttag;
    }

    public String getProductshow() {
        return productshow;
    }

    public void setProductshow(String productshow) {
        this.productshow = productshow;
    }

    public String getProductAvailable() {
        return productAvailable;
    }

    public void setProductAvailable(String productAvailable) {
        this.productAvailable = productAvailable;
    }

    public String getProductdelstatus() {
        return productdelstatus;
    }

    public void setProductdelstatus(String productdelstatus) {
        this.productdelstatus = productdelstatus;
    }

    public String getApporovalmsg() {
        return apporovalmsg;
    }

    public void setApporovalmsg(String apporovalmsg) {
        this.apporovalmsg = apporovalmsg;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getRequestdate() {
        return requestdate;
    }

    public void setRequestdate(String requestdate) {
        this.requestdate = requestdate;
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

    public String getUpdatedon() {
        return updatedon;
    }

    public void setUpdatedon(String updatedon) {
        this.updatedon = updatedon;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    private int productid;
    private String cateId;
    private String subcateid;
    private String productName;
    private String productslug;
    private String industriesid;
    private String preparationTime;
    private String popularitytag;
    private String metatitle;
    private String metakeyword;
    private String metadescription;
    private String productstatus;
    private String producttag;
    private String productshow;
    private String productAvailable;
    private String productdelstatus;
    private String apporovalmsg;
    private String approvedBy;
    private String requestdate;
    private String createdOn;
    private String createdBy;
    private String updatedon;
    private String updatedBy;

    public JSONObject getVendor() {
        return vendor;
    }

    public void setVendor(JSONObject vendor) {
        this.vendor = vendor;
    }

    private JSONObject vendor;



    public CartAssociateModel(JSONObject jsonObject){

        if(jsonObject.has(kproductId)){
            try {
                this.productid=jsonObject.getInt(kproductId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        this.apporovalmsg=getValue(jsonObject,kapprovalMessage,String.class);
        this.approvedBy=getValue(jsonObject,kapprovedBy,String.class);
        this.cateId=getValue(jsonObject,kcateid,String.class);
        this.createdBy=getValue(jsonObject,kCreatedBy,String.class);
        this.createdOn=getValue(jsonObject,kCreatedOn,String.class);
        this.metadescription=getValue(jsonObject,kMetaDes,String.class);
        this.metakeyword=getValue(jsonObject,kMetaKeyword,String.class);
        this.metatitle=getValue(jsonObject,kMetaTitle,String.class);
        this.industriesid=getValue(jsonObject,kIndustriesId,String.class);
        this.popularitytag=getValue(jsonObject,kPopularityTag,String.class);
        this.preparationTime=getValue(jsonObject,kpreparationTime,String.class);
        this.productAvailable=getValue(jsonObject,kProductAvailale,String.class);
        this.productdelstatus=getValue(jsonObject,kproductdelStatus,String.class);
        this.productName=getValue(jsonObject,kproductName,String.class);
        this.productshow=getValue(jsonObject,kProductShow,String.class);
        this.productslug=getValue(jsonObject,kproductSlug,String.class);
        this.requestdate=getValue(jsonObject,krequestdate,String.class);
        this.producttag=getValue(jsonObject,kproducttag,String.class);
        this.updatedBy=getValue(jsonObject,kUpdatedBy,String.class);
        this.updatedon=getValue(jsonObject,kUpdatedOn,String.class);

        if(jsonObject.has(kVendor)){
            try {
                this.vendor=jsonObject.getJSONObject(kVendor);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
