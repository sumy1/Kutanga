package com.vibescom.kutanga.Models;

import org.json.JSONObject;

import java.io.Serializable;

public class AdervisementModel extends BaseModel implements Serializable {


    public int getAdvertiseImage() {
        return advertiseImage;
    }

    public void setAdvertiseImage(int advertiseImage) {
        this.advertiseImage = advertiseImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int advertiseImage;
    private int id;





    public AdervisementModel(JSONObject jsonResponse){
        this.advertiseImage=getValue(jsonResponse,kImage,Integer.class);
        this.id=getValue(jsonResponse,kId,String.class);


    }

    public AdervisementModel(int id, int image) {
        this.id = id;
        this.advertiseImage = image;

    }





}
