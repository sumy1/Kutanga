package com.vibescom.kutanga.Models.FavrouteModel;

import com.vibescom.kutanga.Models.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class favrouteDataModel extends BaseModel implements Serializable {
    public int getUserfavId() {
        return userfavId;
    }

    public void setUserfavId(int userfavId) {
        this.userfavId = userfavId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsFav() {
        return isFav;
    }

    public void setIsFav(String isFav) {
        this.isFav = isFav;
    }

    public String getIndustriesid() {
        return industriesid;
    }

    public void setIndustriesid(String industriesid) {
        this.industriesid = industriesid;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public JSONObject getJsonObjectBusiness() {
        return jsonObjectBusiness;
    }

    public void setJsonObjectBusiness(JSONObject jsonObjectBusiness) {
        this.jsonObjectBusiness = jsonObjectBusiness;
    }

    private int userfavId;
    private String businessId;
    private String userId;
    private String isFav;
    private String industriesid;
    private String createdat;
    private String updatedAt;

   private JSONObject jsonObjectBusiness;

    public favrouteDataModel(JSONObject jsonObject){

        if(jsonObject.has(kuserfavId)){
            try {
                this.userfavId=jsonObject.getInt(kuserfavId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        this.businessId=getValue(jsonObject,kBusinessId,String.class);
        this.createdat=getValue(jsonObject,kCreatedAt,String.class);
        this.industriesid=getValue(jsonObject,kIndustriesId,String.class);
        this.isFav=getValue(jsonObject,kIsfav,String.class);
        this.updatedAt=getValue(jsonObject,kUpdatedAt,String.class);
        this.userId=getValue(jsonObject,kUserId,String.class);

       if(jsonObject.has(kBusiness)){
           try {
               this.jsonObjectBusiness=jsonObject.getJSONObject(kBusiness);
           } catch (JSONException e) {
               e.printStackTrace();
           }

       }
    }
}
