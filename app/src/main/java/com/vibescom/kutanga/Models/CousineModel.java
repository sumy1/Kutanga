package com.vibescom.kutanga.Models;

import org.json.JSONObject;

public class CousineModel {

    private int resId;
    private String resName;
    private int resIcon;


    public CousineModel(JSONObject jsonResponse){
        /*this.sportId = Integer.valueOf(getValue(jsonResponse,kSportId,String.class));
        this.sportName = getValue(jsonResponse,kSportName,String.class);
        if(jsonResponse.has(kSportIcon))
            this.sportIcon = getValue(jsonResponse,kSportIcon,String.class);
        if(jsonResponse.has(kSportImage))
            this.sportImg = getValue(jsonResponse,kSportImage,String.class);
        this.folderName = getValue(jsonResponse,kFolderName,String.class);*/
    }

    public CousineModel(int resId, String resName){
        this.resId = resId;
        this.resName = resName;
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
