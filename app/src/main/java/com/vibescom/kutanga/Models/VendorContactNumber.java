package com.vibescom.kutanga.Models;

import org.json.JSONObject;

import java.io.Serializable;

public class VendorContactNumber extends BaseModel implements Serializable {

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    String mobileNo;
    public VendorContactNumber(JSONObject  jsonObject){
        this.mobileNo=getValue(jsonObject,kVendorMobileNo,String.class);
    }
}
