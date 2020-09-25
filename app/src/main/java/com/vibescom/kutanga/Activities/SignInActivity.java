package com.vibescom.kutanga.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.mukesh.OtpView;
import com.vibescom.kutanga.Activities.Restorants.Activity.HomeActivity;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Broadcast.ReachabilityManager;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.CurrentUser;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.SocialManager.FacebookManager;
import com.vibescom.kutanga.SocialManager.GoogleManager;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.Toaster;
import com.vibescom.kutanga.Utils.Utils;
import com.vibescom.kutanga.Utils.Validations;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

import static com.vibescom.kutanga.Constants.Constants.kAuthToken;
import static com.vibescom.kutanga.Constants.Constants.kConfirmPassword;
import static com.vibescom.kutanga.Constants.Constants.kDBEmail;
import static com.vibescom.kutanga.Constants.Constants.kFacebookEmail;
import static com.vibescom.kutanga.Constants.Constants.kFacebookFirstName;
import static com.vibescom.kutanga.Constants.Constants.kFacebookGender;
import static com.vibescom.kutanga.Constants.Constants.kFacebookId;
import static com.vibescom.kutanga.Constants.Constants.kFacebookLastName;
import static com.vibescom.kutanga.Constants.Constants.kLoginType;
import static com.vibescom.kutanga.Constants.Constants.kMobile;
import static com.vibescom.kutanga.Constants.Constants.kOtp;
import static com.vibescom.kutanga.Constants.Constants.kPassword;
import static com.vibescom.kutanga.Constants.Constants.kPhone;
import static com.vibescom.kutanga.Constants.Constants.kSignUp;
import static com.vibescom.kutanga.Constants.Constants.kUserEmail;
import static com.vibescom.kutanga.Constants.Constants.kUserName;
import static com.vibescom.kutanga.Constants.Constants.kUserPassword;
import static com.vibescom.kutanga.Constants.Constants.kprovider;
import static com.vibescom.kutanga.Constants.Constants.ksocialId;
import static com.vibescom.kutanga.Models.BaseModel.getValue;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = SignInActivity.class.getSimpleName();
    CustomLoaderView loaderView;
    EditText et_email, et_pass;
    RelativeLayout rel_facbook, rel_google;
    TextView btn_sign_in,tv_forgot;
    GoogleManager googleManager;
    FacebookManager facebookManager;
    Dialog dialog;
    Context mContext;
    String from="";
    private OtpView otpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mContext = this;

        loaderView = CustomLoaderView.initialize(this);
        initView();


        googleManager = new GoogleManager(SignInActivity.this, googleManagerListener);
        googleManager.signOut();
        //googleManager.getLastLogin();

        facebookManager = new FacebookManager(this, facebookManagerListener);
        LoginManager.getInstance().registerCallback(facebookManager.getCallbackManager(),
                facebookManager.getFacebookCallback());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent in = getIntent();
        from = in.getStringExtra("FROM");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(from.equalsIgnoreCase("2")){
            Intent inn = new Intent(SignInActivity.this, HomeActivity.class);
            inn.putExtra("FROM","4");
            inn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            inn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(inn);
            finish();
        }else{
           finish();
        }
    }

    private void initView() {
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);

        tv_forgot= findViewById(R.id.tv_forgot);
        tv_forgot.setOnClickListener(this);

        rel_facbook = findViewById(R.id.rel_facbook);
        rel_facbook.setOnClickListener(this);

        rel_google = findViewById(R.id.rel_google);
        rel_google.setOnClickListener(this);

        btn_sign_in=findViewById(R.id.btn_sign_in);
        btn_sign_in.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.btn_sign_in){
            if(validate()){
                setLogin(Utils.getProperText(et_email),Utils.getProperText(et_pass));
            }
        }else if(view.getId()==R.id.btn_sign_in){
            Intent intent=new Intent(SignInActivity.this, SignUpActivity.class);
            intent.putExtra(kLoginType, "N");
            intent.putExtra(kAuthToken,"");
            intent.putExtra(kUserName,"");
            intent.putExtra(kUserEmail,"");
            intent.putExtra(kPhone,"");
            startActivity(intent);
            finish();
        }else if(view.getId()==R.id.rel_facbook){
            if (!ReachabilityManager.getNetworkStatus()) {
                Utils.showAlertDialog(this, "Login Failed!",
                        "Sorry, Login is failed. Unable to reach Facebook servers. Please check your network or try again later.");
                return;
            }
            LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList("email"));
        }else if(view.getId()==R.id.rel_google){
            if (!ReachabilityManager.getNetworkStatus()) {
                Utils.showAlertDialog(this, "Login Failed!",
                        "Sorry, Login is failed. Unable to reach Google servers. Please check your network or try again later.");
                return;
            }
            googleManager.signIn();
        }else if(view.getId()==R.id.tv_forgot){
            forgotDialog();
        }
    }

    private boolean validate() {
        boolean isOk = true;

        if (Utils.getProperText(et_email).isEmpty()) {
            et_email.setError(getString(R.string.email_error));
            et_email.requestFocus();
            isOk = false;
        }
        else if (!(Validations.isValidEmail(Utils.getProperText(et_email)))) {
            et_email.setError(getString(R.string.error_invalid_email));
            et_email.requestFocus();
            isOk = false;
        }
        else if(Utils.getProperText(et_pass).isEmpty()){
            et_pass.setError(getString(R.string.pass_error));
            et_pass.requestFocus();
            isOk = false;
        }
        else if (!Validations.isValidPassword(et_pass.getText().toString())) {
            et_pass.setError(getString(R.string.error_invalid_password));
            et_pass.requestFocus();
            isOk = false;
        }

        return isOk;
    }

    private void setLogin(String email, String password){
        loaderView.showLoader();
        Log.e(TAG,"username: " + email + ",password: " +password);
        HashMap<String,Object> map = new HashMap<>();
        map.put(kUserEmail,email);
        map.put(kUserPassword,password);
        ModelManager.modelManager().userLoginRequest(map,
                (Constants.Status iStatus, GenericResponse<CurrentUser> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        CurrentUser user = genericResponse.getObject();

                        Log.e(TAG,user.toString()+"/Token"+user.getToken());

                        if(from.equalsIgnoreCase("1")){
                            if(user.getUserId()==0){
                                Intent in = new Intent(SignInActivity.this, IntroScreenActivity.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(in);
                                finish();
                            }else{
                                Intent in = new Intent(SignInActivity.this, MainScreen.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(in);
                                finish();
                            }
                        }else{
                            Intent in = new Intent(SignInActivity.this, HomeActivity.class);
                            in.putExtra("FROM","2");
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(in);
                            finish();
                        }


                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }

    GoogleManager.GoogleManagerInterface googleManagerListener = new GoogleManager.GoogleManagerInterface() {
        @Override
        public void success(GoogleSignInAccount acct) {
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                //Uri personPhoto = acct.getPhotoUrl();
                Log.e("Google Login",personId+","+personName+","+personEmail);

                setSocialLogin("gmail",personName,personEmail,personId);
            }
        }

        @Override
        public void failure() {
            Utils.showAlertDialog(SignInActivity.this, getString(R.string.error_msg),
                    getString(R.string.gl_msg));
        }
    };

    FacebookManager.FacebookManagerInterface facebookManagerListener = new FacebookManager.FacebookManagerInterface() {
        @Override
        public void success(JSONObject socialUser) {
            String id = getValue(socialUser,kFacebookId,String.class);
            String firstName = getValue(socialUser,kFacebookFirstName,String.class);
            String lastName = getValue(socialUser,kFacebookLastName,String.class);
            String gender = getValue(socialUser,kFacebookGender,String.class);
            String email = getValue(socialUser,kFacebookEmail,String.class);
            String phone=getValue(socialUser, kMobile,String.class);
            String token=getValue(socialUser,kAuthToken,String.class);
            String name = firstName + " " + lastName;
            Log.e("Facebook Login",id+","+name+","+gender+","+email+","+phone);
            if(email.isEmpty()){
                Utils.showAlertDialog(SignInActivity.this, getString(R.string.error_msg),
                        getString(R.string.fb_msg));
            }else
                setSocialLogin("facebook",name,email,id);
        }

        @Override
        public void failure(String s) {
            Utils.showAlertDialog(SignInActivity.this, getString(R.string.error_msg),
                    getString(R.string.fb_msg));
        }
    };

    private void forgotDialog(){
        // dialog
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_forgot_password);

        dialog.findViewById(R.id.btn_close).setOnClickListener(v -> dialog.dismiss());
        EditText editText = dialog.findViewById(R.id.et_email);
        dialog.findViewById(R.id.btn_submit).setOnClickListener(view -> {
            if(Utils.getProperText(editText).isEmpty())
                Toaster.customToast(getString(R.string.email_id_msg));
            else if(!Validations.isValidEmail(Utils.getProperText(editText)))
                Toaster.customToast(getString(R.string.email_id_invalid));
            else
                setForgotPassword(dialog,Utils.getProperText(editText));
            dialog.dismiss();
        });
        dialog.show();
    }


    private void resetPasswordDialog(String email){
        // dialog
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_reset_password);

        dialog.findViewById(R.id.btn_close).setOnClickListener(v -> dialog.dismiss());
        EditText et_password = dialog.findViewById(R.id.et_password);
        EditText et_Conpassword = dialog.findViewById(R.id.et_Conpassword);
        ImageButton ib_visible_p=dialog.findViewById(R.id.ib_visible_p);
        ImageButton ib_visible_c=dialog.findViewById(R.id.ib_visible_c);
        TextView tv_resend=dialog.findViewById(R.id.tv_resend);
        //tv_resend.setOnClickListener(v->{resendOTP(email);});

        ib_visible_p.setTag("InVisible");
        ib_visible_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ib_visible_p.getTag().equals("InVisible")){
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ib_visible_p.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility));
                    ib_visible_p.setTag("Visible");
                } else {
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ib_visible_p.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_off));
                    ib_visible_p.setTag("InVisible");
                }
            }
        });


        ib_visible_c.setTag("InVisible");

        ib_visible_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ib_visible_c.getTag().equals("InVisible")){
                    et_Conpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ib_visible_c.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility));
                    ib_visible_c.setTag("Visible");
                } else {
                    et_Conpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ib_visible_c.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_off));
                    ib_visible_c.setTag("InVisible");
                }
            }
        });


        dialog.findViewById(R.id.btn_submit).setOnClickListener(view -> {
                setResetPassword(email,et_password.getText().toString().trim(),et_Conpassword.getText().toString().trim());
        });
        dialog.show();
    }

    private void congratsDialog(String msg,String email){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_password_link);
        TextView tv_otp_text=dialog.findViewById(R.id.tv_otp_text);
        tv_otp_text.setText(msg);
        dialog.setCancelable(false);
        Log.e(TAG,"msg: " +msg);
        Button btContinue = dialog.findViewById(R.id.dialog_btn_continue);
        btContinue.setOnClickListener(v -> {dialog.dismiss();});
        dialog.show();
    }

    private void EmailOtpDialog(String email){
        final Dialog dialog = new Dialog(SignInActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_email_verification);
        dialog.setCancelable(false);

        TextView tvOTPTime = dialog.findViewById(R.id.tv_otp_time);
        TextView btResend = dialog.findViewById(R.id.btn_resend);
        Button btContinue = dialog.findViewById(R.id.btn_continue);
        //startTimer(tvOTPTime,btResend);

        btResend.setOnClickListener(view -> {
            resendOTP(email);
            //startTimer(tvOTPTime,btResend);
        });
        otpView = dialog.findViewById(R.id.otp_view);
        otpView.setOtpCompletionListener(s -> { });

        btContinue.setOnClickListener(v -> {
            if(Objects.requireNonNull(otpView.getText()).toString().isEmpty()){
                Toaster.customToast(getString(R.string.code_msg));
            }else if(otpView.getText().toString().length()!=4){
                Toaster.customToast(getString(R.string.code_invalid));
            } else{
                setOTP(getOTP(email),dialog,email);
            }
        });
        dialog.show();
    }

    public HashMap<String, Object> getOTP(String email) {
        HashMap<String, Object> loginMap = new HashMap<>();
        loginMap.put(kUserEmail,email);
        loginMap.put(kOtp, Objects.requireNonNull(otpView.getText()).toString());

        return loginMap;
    }

    private void setOTP(HashMap<String, Object> map,Dialog dialog,String email) {
        loaderView.showLoader();
        Log.e(TAG,"send value to server..."+ map.toString());
        ModelManager.modelManager().userForgotPasswordVerification(map,
                (Constants.Status iStatus, GenericResponse<String> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        String OTP = genericResponse.getObject();
                        Log.e(TAG,"final signup response.. "+OTP);


                        resetPasswordDialog(email);
                        //congratsDialog();
                        dialog.dismiss();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }


    private void congratsDialog(){
        final Dialog dialog = new Dialog(SignInActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_congratulation);
        dialog.setCancelable(false);

        Button btContinue = dialog.findViewById(R.id.btn_continue);

        btContinue.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent=new Intent(SignInActivity.this,MainScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        dialog.show();
    }

    private void resendOTP(String email) {
        loaderView.showLoader();
        HashMap<String,Object> map = new HashMap<>();
        map.put(kUserEmail,email);
        Log.e(TAG, map.toString());
        ModelManager.modelManager().userResetResendOTP(map,
                (Constants.Status iStatus, GenericResponse<String> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        String OTP = genericResponse.getObject();

                        Toaster.customToast(OTP);
                        Log.e(TAG,String.valueOf(OTP));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }

    private void setForgotPassword(Dialog dialog,String email){
        loaderView.showLoader();
        Log.e(TAG,"email_registered: " + email );
        HashMap<String,Object> map = new HashMap<>();
        map.put(kDBEmail,email);
        ModelManager.modelManager().userForgotPassword(map,
                (Constants.Status iStatus, GenericResponse<String> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        String msg = genericResponse.getObject();
                        dialog.dismiss();
                        EmailOtpDialog(email);
                        //congratsDialog(msg);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }

    private void setResetPassword(String email,String password,String ConfirmPassword){
        loaderView.showLoader();
        HashMap<String,Object> map = new HashMap<>();
        map.put(kPassword,password);
        map.put(kConfirmPassword,ConfirmPassword);
        map.put(kUserEmail,email);
        ModelManager.modelManager().userResetPassword(map,
                (Constants.Status iStatus, GenericResponse<String> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        String msg = genericResponse.getObject();
                        dialog.dismiss();
                        congratsDialog(msg,email);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }

    private void setSocialLogin(String provider,String name,String email,String socialId){
        loaderView.showLoader();
        Log.e("Social Login","username: " + email);
        HashMap<String,Object> map = new HashMap<>();
        map.put(kUserEmail,email);
        map.put(kprovider,provider);
        map.put(kUserName,name);
        map.put(ksocialId,socialId);
        ModelManager.modelManager().userSocialLoginRequest(map,
                (Constants.Status iStatus, GenericResponse<CurrentUser> genericResponse) -> {
                    loaderView.hideLoader();
                    CurrentUser user = genericResponse.getObject();
                    Log.e(TAG,user.toString());

                    if(from.equalsIgnoreCase("1")){
                        if(user.getUserId()==0){
                            Intent in = new Intent(SignInActivity.this, IntroScreenActivity.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(in);
                            finish();
                        }else{
                            Intent in = new Intent(SignInActivity.this, MainScreen.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(in);
                            finish();
                        }
                    }else{
                        Intent in = new Intent(SignInActivity.this, HomeActivity.class);
                        in.putExtra("FROM","2");
                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(in);
                        finish();
                    }


                    /*if(user.getOTPVerified().equals("0")){
                      *//*  Intent intent=new Intent(SignInActivity.this, SignUpActivity.class);
                        intent.putExtra(kLoginType, loginType);
                        intent.putExtra(kAuthToken,user.getHashKey());
                        intent.putExtra(kUserName,user.getUsername());
                        intent.putExtra(kEmail,user.getEmail());
                        intent.putExtra(kPhone,user.getPhone());
                        startActivity(intent);
                        finish();*//*
                    }
                    else{
                        if(user.getPublications().isEmpty()){
                           *//* Intent in = new Intent(SignInActivity.this, SubscriptionActivity.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(in);
                            finish();*//*
                        }else{
                           *//* Intent in = new Intent(SignInActivity.this, DashboardActivity.class);
                            in.putExtra(kData,"nothing");
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(in);
                            finish();*//*
                        }
                    }*/




                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    if(message.equals(kSignUp)){
                        setSignUpIntent(provider,socialId,name,email);
                    } else
                        Toaster.customToast(message);
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookManager.getCallbackManager().onActivityResult(requestCode, resultCode, data);
        if (requestCode == GoogleManager.RC_SIGN_IN) {
            /*Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            googleManager.handleSignInResult(task);*/
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            googleManager.handleSignInResult(result);
        }
    }

    private void setSignUpIntent(String loginType,String id,String name,String email){
       /* Intent in = new Intent(SignInActivity.this,SignUpActivity.class);
        in.putExtra(kLoginType, loginType);
        in.putExtra(kAuthToken,id);
        in.putExtra(kUserName,name);
        in.putExtra(kEmail,email);
        in.putExtra(kPhone,"");
        startActivity(in);*/
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
