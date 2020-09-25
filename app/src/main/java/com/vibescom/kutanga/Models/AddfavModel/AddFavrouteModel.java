package com.vibescom.kutanga.Models.AddfavModel;

import com.vibescom.kutanga.Models.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class AddFavrouteModel extends BaseModel implements Serializable {

    private int userfavId;
    private String businessid;
    private String userid;
    private String isfav;
    private String industriesId;
    private String createdAt;
    private String updatedat;

    public AddFavrouteModel(JSONObject jsonObject){

        if(jsonObject.has(kuserfavId)){
            try {
                this.userfavId=jsonObject.getInt(kuserfavId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        this.businessid=getValue(jsonObject,kBusinessId,String.class);
        this.createdAt=getValue(jsonObject,kCreatedAt,String.class);
        this.industriesId=getValue(jsonObject,kIndustriesId,String.class);
        this.isfav=getValue(jsonObject,kIsfav,String.class);
        this.userid=getValue(jsonObject,kUserId,String.class);
        this.updatedat=getValue(jsonObject,kUpdatedAt,String.class);

    }
}
