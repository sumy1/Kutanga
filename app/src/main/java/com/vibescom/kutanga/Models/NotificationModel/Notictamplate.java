package com.vibescom.kutanga.Models.NotificationModel;

import com.vibescom.kutanga.Models.BaseModel;

import org.json.JSONObject;

import java.io.Serializable;

public class Notictamplate extends BaseModel implements Serializable {
    public String getTemplateSubject() {
        return templateSubject;
    }

    public void setTemplateSubject(String templateSubject) {
        this.templateSubject = templateSubject;
    }

    public String getTemplateDetails() {
        return templateDetails;
    }

    public void setTemplateDetails(String templateDetails) {
        this.templateDetails = templateDetails;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    private String templateSubject;
    private String templateDetails;
    private String createdDate;

    public Notictamplate(JSONObject jsonObject){
        this.createdDate=getValue(jsonObject,kCreatedate,String.class);
        this.templateDetails=getValue(jsonObject,ktemplateDetails,String.class);
        this.templateSubject=getValue(jsonObject,ktemplateSubject,String.class);

    }

}
