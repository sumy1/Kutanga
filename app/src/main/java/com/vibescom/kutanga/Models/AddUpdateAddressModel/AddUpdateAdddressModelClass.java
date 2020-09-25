package com.vibescom.kutanga.Models.AddUpdateAddressModel;

import com.vibescom.kutanga.Models.BaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class AddUpdateAdddressModelClass extends BaseModel implements Serializable {


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CopyOnWriteArrayList<ChildaddressModel> getChildAddressList() {
        return childAddressList;
    }

    public void setChildAddressList(CopyOnWriteArrayList<ChildaddressModel> childAddressList) {
        this.childAddressList = childAddressList;
    }

    private String Status;
    private String cmd;
    private String message;

    private CopyOnWriteArrayList<ChildaddressModel> childAddressList;

    public AddUpdateAdddressModelClass(JSONObject jsonObject){

        this.message=getValue(jsonObject,kMessage,String.class);

        if(jsonObject.has(kResponse)){
            try {
                this.childAddressList = handleMarketPlace(jsonObject.getJSONArray(kResponse));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    private CopyOnWriteArrayList<ChildaddressModel> handleMarketPlace(JSONArray jsonArray) throws JSONException {
        CopyOnWriteArrayList<ChildaddressModel> marketPlaces = new CopyOnWriteArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            marketPlaces.add(new ChildaddressModel(jsonArray.getJSONObject(i)));
        }
        return marketPlaces;
    }

}
