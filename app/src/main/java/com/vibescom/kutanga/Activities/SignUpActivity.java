package com.vibescom.kutanga.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mukesh.OtpView;
import com.vibescom.kutanga.Activities.Restorants.Activity.HomeActivity;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.CurrentUser;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.Toaster;
import com.vibescom.kutanga.Utils.Utils;
import com.vibescom.kutanga.Utils.Validations;

import java.util.HashMap;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

import static com.vibescom.kutanga.Constants.Constants.kOtp;
import static com.vibescom.kutanga.Constants.Constants.kUserConfirmPassword;
import static com.vibescom.kutanga.Constants.Constants.kUserEmail;
import static com.vibescom.kutanga.Constants.Constants.kUserMobile;
import static com.vibescom.kutanga.Constants.Constants.kUserName;
import static com.vibescom.kutanga.Constants.Constants.kUserPassword;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    Context mContext;
    private final static String TAG = SignUpActivity.class.getSimpleName();
    CustomLoaderView loaderView;
    EditText et_username, et_email, et_pass, et_phone;
    TextView btn_signUp, txt_signIn,tv_skip;
    ImageView img_back;
    private OtpView otpView;
    String from="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mContext = this;
        loaderView = CustomLoaderView.initialize(this);
        initView();
    }

    private void initView() {
        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        et_phone = findViewById(R.id.et_phone);
        btn_signUp = findViewById(R.id.btn_signUp);
        btn_signUp.setOnClickListener(this);
        txt_signIn = findViewById(R.id.txt_signIn);
        txt_signIn.setOnClickListener(this);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        tv_skip=findViewById(R.id.tv_skip);
        tv_skip.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent in = getIntent();
        from = in.getStringExtra("FROM");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_back:


                if(from.equalsIgnoreCase("2")){
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    intent.putExtra("FROM","4");
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(mContext, IntroScreenActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;

            case R.id.tv_skip:

                if(from.equalsIgnoreCase("2")){
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    intent.putExtra("FROM","4");
                    startActivity(intent);
                    finish();
                }else{
                  Intent intent = new Intent(mContext, IntroScreenActivity.class);
                startActivity(intent);
                finish();
                }

                break;
            case R.id.btn_signUp:

                if (validate()) {
                    setSignUp();
                }

                break;

            case R.id.txt_signIn:
                Intent intenttt = new Intent(mContext, SignInActivity.class);
                intenttt.putExtra("FROM","3");
                startActivity(intenttt);
                break;
        }
    }

    private boolean validate() {
        boolean isOk = true;

        if (Utils.getProperText(et_username).isEmpty()) {
            et_username.setError(getString(R.string.user_error));
            et_username.requestFocus();
            isOk = false;
        }  if (Utils.getProperText(et_email).isEmpty()) {
            et_email.setError(getString(R.string.email_error));
            et_email.requestFocus();
            isOk = false;
        }
        else if (!(Validations.isValidEmail(Utils.getProperText(et_email)))) {
            et_email.setError(getString(R.string.error_invalid_email));
            et_email.requestFocus();
            isOk = false;
        }  else if(Utils.getProperText(et_pass).isEmpty()){
            et_pass.setError(getString(R.string.pass_error));
            et_pass.requestFocus();
            isOk = false;
        }
        else if (!Validations.isValidPassword(et_pass.getText().toString())) {
            et_pass.setError(getString(R.string.error_invalid_password));
            et_pass.requestFocus();
            isOk = false;
        } else if(Utils.getProperText(et_phone).isEmpty()){
            et_phone.setError(getString(R.string.phone_error));
            et_phone.requestFocus();
            isOk = false;
        }
        else if (!Validations.isValidPhoneNumber(et_phone.getText().toString())) {
            et_phone.setError(getString(R.string.error_invalid_phone));
            et_phone.requestFocus();
            isOk = false;
        }

        return isOk;
    }


    //String email, String userName, String userMobileNo, String password,String userConfirmPassword
    private void setSignUp() {
        loaderView.showLoader();

        HashMap<String, Object> map = new HashMap<>();
        map.put(kUserEmail, Utils.getProperText(et_email));
        map.put(kUserName, Utils.getProperText(et_username));
        map.put(kUserMobile, Utils.getProperText(et_phone));
        map.put(kUserPassword, Utils.getProperText(et_pass));
        map.put(kUserConfirmPassword, Utils.getProperText(et_pass));
        Log.e(TAG, map.toString());
        ModelManager.modelManager().userRegisterRequest(map,
                (Constants.Status iStatus, GenericResponse<CurrentUser > genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        CurrentUser user = genericResponse.getObject();
                        EmailOtpDialog();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }



    private void EmailOtpDialog(){
        final Dialog dialog = new Dialog(SignUpActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_email_verification);
        dialog.setCancelable(false);

        TextView tvOTPTime = dialog.findViewById(R.id.tv_otp_time);
        TextView btResend = dialog.findViewById(R.id.btn_resend);
        Button btContinue = dialog.findViewById(R.id.btn_continue);
        ImageView img_close=dialog.findViewById(R.id.img_close);
        //startTimer(tvOTPTime,btResend);

        btResend.setOnClickListener(view -> {
            resendOTP(ModelManager.modelManager().getCurrentUser().getUserEmail());
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
                //dialog.dismiss();
                setOTP(getOTP(),dialog);
            }
        });


        img_close.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    public HashMap<String, Object> getOTP() {
        HashMap<String, Object> loginMap = new HashMap<>();
        loginMap.put(kUserEmail,ModelManager.modelManager().getCurrentUser().getUserEmail());
        loginMap.put(kOtp, Objects.requireNonNull(otpView.getText()).toString());

        return loginMap;
    }

    private void setOTP(HashMap<String, Object> map,Dialog dialog) {
        loaderView.showLoader();
        Log.e(TAG,"send value to server..."+ map.toString());
        ModelManager.modelManager().userEmailVerification(map,
                (Constants.Status iStatus, GenericResponse<CurrentUser> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        CurrentUser OTP = genericResponse.getObject();
                        dialog.dismiss();
                        Log.e(TAG,"final signup response.. "+OTP);
                        congratsDialog();
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
        final Dialog dialog = new Dialog(SignUpActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_congratulation);
        dialog.setCancelable(false);

        Button btContinue = dialog.findViewById(R.id.btn_continue);

        btContinue.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent=new Intent(SignUpActivity.this,MainScreen.class);
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
        ModelManager.modelManager().userResendOTP(map,
                (Constants.Status iStatus, GenericResponse<String> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        String OTP = genericResponse.getObject();
                        Log.e(TAG,String.valueOf(OTP));
                        Toaster.customToast(OTP);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }
}
