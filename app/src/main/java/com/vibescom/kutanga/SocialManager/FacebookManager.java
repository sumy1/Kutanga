package com.vibescom.kutanga.SocialManager;

import android.content.Context;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.vibescom.kutanga.Utils.Toaster;

import org.json.JSONObject;

import static com.vibescom.kutanga.Constants.Constants.kFacebookAllFields;
import static com.vibescom.kutanga.Constants.Constants.kFacebookFields;

public class FacebookManager {
    private CallbackManager callbackManager;
    private Context context;
    private FacebookManagerInterface facebookManagerListener;

    public FacebookManager(Context context, FacebookManagerInterface facebookManagerListener) {
        this.context =context;
        this.facebookManagerListener = facebookManagerListener;
        callbackManager= CallbackManager.Factory.create();
    }

    public FacebookManager(Context context){
        this.context =context;
        callbackManager= CallbackManager.Factory.create();
    }

    public CallbackManager getCallbackManager(){
        return callbackManager;
    }

    public FacebookCallback<LoginResult> getFacebookCallback(){
        return facebookCallback;
    }

    //this method deals with Facebook callbacks
    private FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            requestData();
        }

        @Override
        public void onCancel() {
            logout();
            Toaster.customToast("Login Cancelled");
        }

        @Override
        public void onError(FacebookException error) {
            logout();
            facebookManagerListener.failure(error.toString());
        }
    };

    //this method request for user specification and reflect changer to the activity through interfaces
    private void requestData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),(object, response) -> {
            JSONObject facebookUser = response.getJSONObject();
            facebookManagerListener.success(facebookUser);
        });
        Bundle parameters = new Bundle();
        parameters.putString(kFacebookFields,kFacebookAllFields );
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void onLogout(){
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, graphResponse -> logout()).executeAsync();
    }

    private void logout() {
        LoginManager.getInstance().logOut();
    }

    public interface FacebookManagerInterface{
        void success(JSONObject facebookUser);
        void failure(String s);
    }

}
