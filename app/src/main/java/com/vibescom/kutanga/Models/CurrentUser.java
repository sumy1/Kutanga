package com.vibescom.kutanga.Models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CurrentUser extends BaseModel implements Serializable {

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }



    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }



    private String userName;
    private String userEmail="";
    private String userMobile;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;


    public String getIsMobileVerified() {
        return isMobileVerified;
    }

    public void setIsMobileVerified(String isMobileVerified) {
        this.isMobileVerified = isMobileVerified;
    }

    public String getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(String isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }



    private String isMobileVerified;
    private String isEmailVerified;
    private String userStatus;
    private String createdAt;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;



    public CurrentUser(JSONObject jsonResponse) {
        this.userId=getValue(jsonResponse, kUserId, Integer.class);
        this.userName = getValue(jsonResponse,kUserName,String.class);
        this.userEmail = getValue(jsonResponse,kUserEmail,String.class);


        /*if(jsonResponse.has(kToken)){
            this.token=getValue(jsonResponse,kToken,String.class);
        }else{
            this.token= ModelManager.modelManager().getCurrentUser().getToken();
        }*/

        if(jsonResponse.has(kToken)){


            try {
                if(jsonResponse.getString(kToken).startsWith("Bearer ")){
                   this.token= jsonResponse.getString(kToken).replaceFirst("Bearer ","");
                }else{
                    this.token = getValue(jsonResponse,kToken,String.class);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            this.token = getValue(jsonResponse,kToken,String.class);
        }

        if(jsonResponse.has(kPassword))
            this.password = getValue(jsonResponse,kPassword,String.class);
        if(jsonResponse.has(kUserStatus))
            this.userStatus = getValue(jsonResponse,kUserStatus,String.class);
        if(jsonResponse.has(kUserMobile))
            this.userMobile= getValue(jsonResponse,kUserMobile,String.class);

        if(jsonResponse.has(kisEmailVerified))
            this.isEmailVerified =getValue(jsonResponse,kisEmailVerified,String.class);
        else
            this.isEmailVerified = "0";
        if(jsonResponse.has(kisMobileVerified))
            this.isMobileVerified =getValue(jsonResponse,kisMobileVerified,String.class);
        else
            this.isMobileVerified = "0";

    }

    }
