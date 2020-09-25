package com.vibescom.kutanga.SocialManager;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.vibescom.kutanga.Utils.Toaster;

import androidx.appcompat.app.AppCompatActivity;


public class GoogleManager  {

    private final static String TAG = GoogleManager.class.getSimpleName();
    public static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleApiClient;
    private AppCompatActivity activity;
    private GoogleManagerInterface googleManagerListener;

    public GoogleManager(AppCompatActivity context, GoogleManagerInterface googleManagerListener) {
        this.activity = context;
        this.googleManagerListener=googleManagerListener;
        googleLoginPermission();
    }

    public GoogleManager(AppCompatActivity context){
        this.activity = context;
        googleLoginPermission();
    }

    private void googleLoginPermission(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleApiClient = GoogleSignIn.getClient(activity, gso);
    }

    /*public void getLastLogin(){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(activity);
        googleManagerListener.success(account);
    }*/

    public GoogleSignInAccount getAlreadyLogin(){
        return GoogleSignIn.getLastSignedInAccount(activity);
    }

    public void signIn() {
        Intent signInIntent = mGoogleApiClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            googleManagerListener.success(account);
        } else if(result.getStatus().isCanceled()){
            Log.w(TAG, "signInResult:failed code=" + result.getStatus().getStatusCode());
            Toaster.customToast("Login Cancelled");
            signOut();
        } else {
            Log.w(TAG, "signInResult:failed msg=" + result.getStatus().getStatusCode());
            signOut();
            googleManagerListener.failure();
        }
    }

    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            googleManagerListener.success(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            signOut();
        }
    }

    public void signOut(){
        mGoogleApiClient.revokeAccess().addOnCompleteListener(task -> Log.e("Google Login","Sign Out Successful"));
    }

    public interface GoogleManagerInterface{
        void success(GoogleSignInAccount googleUser);
        void failure();
    }

}
