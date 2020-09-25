package com.vibescom.kutanga.Activities.Restorants.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Activities.Restorants.Activity.FavroutesActivity;
import com.vibescom.kutanga.Activities.Restorants.Activity.HomeActivity;
import com.vibescom.kutanga.Activities.Restorants.Activity.ManageaddressActivity;
import com.vibescom.kutanga.Activities.Restorants.Activity.NotificationActivity;
import com.vibescom.kutanga.Activities.Restorants.Activity.PastOrderActivity;
import com.vibescom.kutanga.Activities.SignInActivity;
import com.vibescom.kutanga.Activities.SignUpActivity;
import com.vibescom.kutanga.ApplicationManager;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.AdervisementModel;
import com.vibescom.kutanga.Models.BaseModel;
import com.vibescom.kutanga.Models.CurrentUser;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.Toaster;
import com.vibescom.kutanga.Utils.Utils;
import com.vibescom.kutanga.Utils.Validations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import static com.vibescom.kutanga.Constants.Constants.kAppPreferencesLanguage;
import static com.vibescom.kutanga.Constants.Constants.kAuthToken;
import static com.vibescom.kutanga.Constants.Constants.kConfirmPassword;
import static com.vibescom.kutanga.Constants.Constants.kLangPref;
import static com.vibescom.kutanga.Constants.Constants.kLoginType;
import static com.vibescom.kutanga.Constants.Constants.kPassword;
import static com.vibescom.kutanga.Constants.Constants.kPhone;
import static com.vibescom.kutanga.Constants.Constants.kUserEmail;
import static com.vibescom.kutanga.Constants.Constants.kUserMobile;
import static com.vibescom.kutanga.Constants.Constants.kUserName;


public class ProfileFragments extends Fragment implements View.OnClickListener {
    Context mContext;
    private final static String TAG = ProfileFragments.class.getSimpleName();
    TextView txt_user_name, btn_sign_in,btn_sign_up, tv_past_fav, txt_my_account, tv_check_profile, tv_check_fav, tv_past_order, tv_check_past_order, txt_login, txt_edit, txt_mobile_no, txt_email_id, txt_manage_address, txt_resetPassword, txt_favorites;
    RelativeLayout lay_midd, ly_fav, ly_past_order , ly_notification,ly_logout, ly_logouttt, ly_change_language;
    LinearLayout  ll_notLogin,layout_manage_addres, ll_visible_fav, ll_login, ll_visible_order, layout_food_delivery, layout_marketplace_delivery, layout_food_delivery_fav, layout_marketplace_delivery_fav;
    ImageView img_down_arrow, img_done, img_past, img_fav;
    Dialog dialog;
    EditText et_username, et_phone;
    CustomLoaderView loaderView;
    private EventClickListener listener;
    View v2;
    private boolean isClick = false;
    LinearLayout ll_name;
    View v9, v13;
    String fromOne;
    int userid;
    SharedPreferences sharedPreferences;
    String selectedlanguage;
    ViewPager pager;
    ImageView img_close;
    RelativeLayout ll_main;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 200;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000;
    CurrentUser currentUser;

    public ProfileFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_profile_fragments, container, false);
        mContext = getActivity();
        currentUser=ModelManager.modelManager().getCurrentUser();
        pager = rootView.findViewById(R.id.pager);
        setAdvertisementAdapter();
        ll_main = rootView.findViewById(R.id.ll_main);
        img_close = rootView.findViewById(R.id.img_close);

        img_close.setOnClickListener(v -> {
            ll_main.setVisibility(View.GONE);
        });
        sharedPreferences = mContext.getSharedPreferences(kAppPreferencesLanguage, Context.MODE_PRIVATE);
        ApplicationManager.setLanguageForApp(mContext, sharedPreferences.getString(kLangPref, ""));
        selectedlanguage = sharedPreferences.getString(kLangPref, "");
        loaderView = CustomLoaderView.initialize(mContext);
        inItView(rootView);

        return rootView;
    }

    private void inItView(View rootView) {
        btn_sign_in=rootView.findViewById(R.id.btn_sign_in);
        btn_sign_in.setOnClickListener(this);
        btn_sign_up=rootView.findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(this);


        ly_logout = rootView.findViewById(R.id.ly_logout);
        ll_notLogin = rootView.findViewById(R.id.ll_notLogin);
        ll_login = rootView.findViewById(R.id.ll_login);
        if(currentUser==null){
            ll_notLogin.setVisibility(View.VISIBLE);
            ll_login.setVisibility(View.GONE);
        }else{
            ll_notLogin.setVisibility(View.GONE);
            ll_login.setVisibility(View.VISIBLE);
        }


        v9 = rootView.findViewById(R.id.v9);
        v13 = rootView.findViewById(R.id.v13);
        v2 = rootView.findViewById(R.id.v2);
        ll_name = rootView.findViewById(R.id.ll_name);

        txt_user_name = rootView.findViewById(R.id.txt_user_name);
        txt_edit = rootView.findViewById(R.id.txt_edit);
        txt_edit.setOnClickListener(this);
        txt_mobile_no = rootView.findViewById(R.id.txt_mobile_no);

        txt_email_id = rootView.findViewById(R.id.txt_email_id);

        try {

            userid = ModelManager.modelManager().getCurrentUser().getUserId();
            if (ModelManager.modelManager().getCurrentUser().getUserName().isEmpty()) {

            } else {
                txt_user_name.setText(ModelManager.modelManager().getCurrentUser().getUserName());
            }

            if (ModelManager.modelManager().getCurrentUser().getUserMobile().isEmpty()) {

            } else {
                txt_mobile_no.setText(ModelManager.modelManager().getCurrentUser().getUserMobile());
            }
            if (ModelManager.modelManager().getCurrentUser().getUserEmail().isEmpty()) {

            } else {
                txt_email_id.setText(ModelManager.modelManager().getCurrentUser().getUserEmail());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        lay_midd = rootView.findViewById(R.id.lay_midd);
        lay_midd.setOnClickListener(this);

        layout_manage_addres = rootView.findViewById(R.id.layout_manage_addres);
        img_down_arrow = rootView.findViewById(R.id.img_down_arrow);

        ly_past_order = rootView.findViewById(R.id.ly_past_order);
        ly_past_order.setOnClickListener(this);
        ll_visible_order = rootView.findViewById(R.id.ll_visible_order);
        ll_visible_order.setOnClickListener(this);
        layout_food_delivery = rootView.findViewById(R.id.layout_food_delivery);
        layout_food_delivery.setOnClickListener(this);
        layout_marketplace_delivery = rootView.findViewById(R.id.layout_marketplace_delivery);
        layout_marketplace_delivery.setOnClickListener(this);
        img_past = rootView.findViewById(R.id.img_past);
        tv_past_order = rootView.findViewById(R.id.tv_past_order);
        tv_check_past_order = rootView.findViewById(R.id.tv_check_past_order);
        ly_fav = rootView.findViewById(R.id.ly_fav);
        ly_fav.setOnClickListener(this);
        img_fav = rootView.findViewById(R.id.img_fav);
        ll_visible_fav = rootView.findViewById(R.id.ll_visible_fav);
        layout_food_delivery_fav = rootView.findViewById(R.id.layout_food_delivery_fav);
        layout_food_delivery_fav.setOnClickListener(this);
        layout_marketplace_delivery_fav = rootView.findViewById(R.id.layout_marketplace_delivery_fav);
        layout_marketplace_delivery_fav.setOnClickListener(this);
        tv_past_fav = rootView.findViewById(R.id.tv_past_fav);
        tv_check_fav = rootView.findViewById(R.id.tv_check_fav);

        txt_manage_address = rootView.findViewById(R.id.txt_manage_address);
        txt_manage_address.setOnClickListener(this);

        txt_my_account = rootView.findViewById(R.id.txt_my_account);
        tv_check_profile = rootView.findViewById(R.id.tv_check_profile);

        txt_favorites = rootView.findViewById(R.id.txt_favorites);
        txt_favorites.setOnClickListener(this);

        txt_resetPassword = rootView.findViewById(R.id.txt_resetPassword);
        txt_resetPassword.setOnClickListener(this);



        ly_notification = rootView.findViewById(R.id.ly_notification);
        ly_notification.setOnClickListener(this);


        ly_logouttt = rootView.findViewById(R.id.ly_logout);


        if (userid == 0) {
            //v3.setVisibility(View.GONE);
            ll_name.setVisibility(View.GONE);
            //txt_login.setVisibility(View.VISIBLE);
            txt_user_name.setVisibility(View.GONE);
            txt_mobile_no.setVisibility(View.GONE);
            txt_email_id.setVisibility(View.GONE);
            ly_logouttt.setVisibility(View.GONE);
            txt_edit.setVisibility(View.GONE);
        } else {
            //v3.setVisibility(View.VISIBLE);
            ll_name.setVisibility(View.VISIBLE);
           // txt_login.setVisibility(View.GONE);
            txt_user_name.setVisibility(View.VISIBLE);
            txt_mobile_no.setVisibility(View.VISIBLE);
            txt_email_id.setVisibility(View.VISIBLE);
            ly_logouttt.setVisibility(View.VISIBLE);
            txt_edit.setVisibility(View.VISIBLE);
            ly_logouttt.setOnClickListener(v -> {
                listener.logout();
            });
        }

        ly_change_language = rootView.findViewById(R.id.ly_change_language);
        ly_change_language.setOnClickListener(v -> {
            selectLanguageDialog();

        });

    }

    public void setAdvertisementAdapter() {
        AdvertismentAdapter adapter = new AdvertismentAdapter(getActivity(), Utils.getAdvertisementData());
        pager.setAdapter(adapter);

        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == Utils.getAdvertisementData().size()) {
                    currentPage = 0;
                }
                pager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void setEventClickListener(EventClickListener listener) {
        this.listener = listener;
    }


    public interface EventClickListener {
        void logout();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_midd:
                if (isClick == false) {
                    layout_manage_addres.setVisibility(View.VISIBLE);
                    //v2.setVisibility(View.GONE);
                    lay_midd.setBackgroundColor(getResources().getColor(R.color.user_theme_color));
                    img_down_arrow.setColorFilter(getResources().getColor(R.color.dim_grey));
                    img_down_arrow.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_white_24px));
                    txt_my_account.setTextColor(getResources().getColor(R.color.white));
                    tv_check_profile.setTextColor(getResources().getColor(R.color.white));
                    isClick = true;
                } else if (isClick == true) {
                    layout_manage_addres.setVisibility(View.GONE);
                    //v2.setVisibility(View.VISIBLE);
                    lay_midd.setBackgroundColor(getResources().getColor(R.color.white));
                    img_down_arrow.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_24px));
                    img_down_arrow.setColorFilter(getResources().getColor(R.color.dim_grey));
                    txt_my_account.setTextColor(getResources().getColor(R.color.dim_grey));
                    tv_check_profile.setTextColor(getResources().getColor(R.color.dim_grey));
                    isClick = false;
                }
                break;

            case R.id.txt_edit:
                editAccount();
                break;

            case R.id.txt_manage_address:
                /*fromOne = "1";
                if (userid == 0) {
                    menuDialog(fromOne);
                } else {
                    Intent intent1 = new Intent(mContext, ManageaddressActivity.class);
                    startActivity(intent1);
                }*/
                Intent intent1 = new Intent(mContext, ManageaddressActivity.class);
                startActivity(intent1);

                break;

            case R.id.txt_resetPassword:
              /*  fromOne = "3";
                if (userid == 0) {
                    menuDialog(fromOne);
                } else {
                    resetPasswordDialog(ModelManager.modelManager().getCurrentUser().getUserEmail());
                }*/
                resetPasswordDialog(ModelManager.modelManager().getCurrentUser().getUserEmail());
                break;


            case R.id.ly_notification:
                /*if (userid == 0) {
                    menuDialog(fromOne);
                } else {
                    Intent intent2 = new Intent(mContext, NotificationActivity.class);
                    startActivity(intent2);
                }  fromOne = "4";*/


                Intent intent2 = new Intent(mContext, NotificationActivity.class);
                startActivity(intent2);

                break;

            case R.id.ly_past_order:

                if (ll_visible_order.getVisibility() == View.VISIBLE) {
                    ll_visible_order.setVisibility(View.GONE);
                    v9.setVisibility(View.VISIBLE);
                    img_past.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_24px));
                    ly_past_order.setBackgroundColor(getResources().getColor(R.color.white));
                    img_past.setColorFilter(getResources().getColor(R.color.dim_grey));
                    tv_check_past_order.setTextColor(getResources().getColor(R.color.dim_grey));
                    tv_past_order.setTextColor(getResources().getColor(R.color.dim_grey));
                } else {
                    ly_past_order.setBackgroundColor(getResources().getColor(R.color.user_theme_color));
                    img_past.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_white_24px));
                    img_past.setColorFilter(getResources().getColor(R.color.white));
                    ll_visible_order.setVisibility(View.VISIBLE);
                    v9.setVisibility(View.GONE);
                    tv_check_past_order.setTextColor(getResources().getColor(R.color.white));
                    tv_past_order.setTextColor(getResources().getColor(R.color.white));
                }

                break;

            case R.id.ly_fav:
                if (ll_visible_fav.getVisibility() == View.VISIBLE) {
                    ll_visible_fav.setVisibility(View.GONE);
                    v13.setVisibility(View.VISIBLE);
                    img_fav.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_24px));
                    ly_fav.setBackgroundColor(getResources().getColor(R.color.white));
                    img_fav.setColorFilter(getResources().getColor(R.color.dim_grey));
                    tv_past_fav.setTextColor(getResources().getColor(R.color.dim_grey));
                    tv_check_fav.setTextColor(getResources().getColor(R.color.dim_grey));
                } else {
                    ly_fav.setBackgroundColor(getResources().getColor(R.color.user_theme_color));
                    img_fav.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_white_24px));
                    img_fav.setColorFilter(getResources().getColor(R.color.white));
                    ll_visible_fav.setVisibility(View.VISIBLE);
                    v13.setVisibility(View.GONE);
                    tv_check_fav.setTextColor(getResources().getColor(R.color.white));
                    tv_past_fav.setTextColor(getResources().getColor(R.color.white));
                }

                break;

            case R.id.layout_food_delivery_fav:
                /*fromOne = "2";
                if (userid == 0) {
                    menuDialog(fromOne);
                } else {
                    Intent intent2 = new Intent(mContext, FavroutesActivity.class);
                    startActivity(intent2);
                }*/

                Intent intent3 = new Intent(mContext, FavroutesActivity.class);
                startActivity(intent3);
                break;
            case R.id.layout_marketplace_delivery_fav:
                Toaster.customToast("Work is in process..");
                break;

            case R.id.layout_food_delivery:
               /* fromOne = "0";
                if (userid == 0) {
                    menuDialog(fromOne);
                } else {
                    Intent intent = new Intent(mContext, PastOrderActivity.class);
                    startActivity(intent);
                }*/
                Intent intent = new Intent(mContext, PastOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_marketplace_delivery:
                Toaster.customToast("Work is in process..");
                break;

            case R.id.btn_sign_in:
                setIntentLogin();
                break;
            case R.id.btn_sign_up:
                setIntentSignUp();
                break;

        }
    }

    private void setIntent() {
        Intent in = new Intent(mContext, SignInActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);
        getActivity().finish();
    }

    private void setResetPassword(String email, String password, String ConfirmPassword) {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kPassword, password);
        map.put(kConfirmPassword, ConfirmPassword);
        map.put(kUserEmail, email);
        ModelManager.modelManager().userResetPassword(map,
                (Constants.Status iStatus, GenericResponse<String> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        String msg = genericResponse.getObject();
                        dialog.dismiss();
                        congratsDialog(msg, email);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }


    private void resetPasswordDialog(String email) {
        // dialog
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_reset_password);

        dialog.findViewById(R.id.btn_close).setOnClickListener(v -> dialog.dismiss());
        EditText et_password = dialog.findViewById(R.id.et_password);
        EditText et_Conpassword = dialog.findViewById(R.id.et_Conpassword);
        ImageButton ib_visible_p = dialog.findViewById(R.id.ib_visible_p);
        ImageButton ib_visible_c = dialog.findViewById(R.id.ib_visible_c);
        TextView tv_resend = dialog.findViewById(R.id.tv_resend);
        ///tv_resend.setOnClickListener(v->{resendOTP(email);});

        ib_visible_p.setTag("InVisible");
        ib_visible_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ib_visible_p.getTag().equals("InVisible")) {
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
                if (ib_visible_c.getTag().equals("InVisible")) {
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

            if (validate(et_password, et_Conpassword)) {
                setResetPassword(email, et_password.getText().toString().trim(), et_Conpassword.getText().toString().trim());
            }


        });
        dialog.show();
    }

    private void congratsDialog(String msg, String email) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_password_link);
        TextView tv_otp_text = dialog.findViewById(R.id.tv_otp_text);
        tv_otp_text.setText(msg);
        dialog.setCancelable(false);
        Button btContinue = dialog.findViewById(R.id.dialog_btn_continue);
        btContinue.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void editAccount() {
        // dialog
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.acount_edit);
        dialog.setCanceledOnTouchOutside(true);

        et_username = dialog.findViewById(R.id.et_username);
        if (ModelManager.modelManager().getCurrentUser().getUserName().isEmpty()) {

        } else {
            et_username.setText(ModelManager.modelManager().getCurrentUser().getUserName());
        }
        et_phone = dialog.findViewById(R.id.et_phone);
        if (ModelManager.modelManager().getCurrentUser().getUserMobile().isEmpty()) {

        } else {
            et_phone.setText(ModelManager.modelManager().getCurrentUser().getUserMobile());
        }
        img_done = dialog.findViewById(R.id.img_done);
        dialog.findViewById(R.id.btn_update).setOnClickListener(view -> {


            if (validate(et_username, et_phone)) {
                dialog.dismiss();
                getprofileUpdate(et_username.getText().toString(), et_phone.getText().toString());
            }


        });

        dialog.findViewById(R.id.btn_close).setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }


    private boolean validate(EditText et_username, EditText et_phone) {
        boolean isOk = true;

        if (!(Validations.isValidPassword(Utils.getProperText(et_username)))) {
            et_username.setError(getString(R.string.error_invalid_password));
            et_username.requestFocus();
            isOk = false;
        } else if (!(Validations.isValidPassword(Utils.getProperText(et_phone)))) {
            et_phone.setError(getString(R.string.error_invalid_password));
            et_phone.requestFocus();
            isOk = false;
        }

        return isOk;
    }


    private void getprofileUpdate(String name, String phone) {
        loaderView.showLoader();
        //Log.d("Token", ModelManager.modelManager().getCurrentUser().getToken());
        HashMap<String, Object> map = new HashMap<>();
        map.put(kUserName, name);
        map.put(kUserMobile, phone);
        Log.e(TAG, map.toString());
        ModelManager.modelManager().userProfileUpdate(map,
                (Constants.Status iStatus, GenericResponse<CurrentUser> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        CurrentUser user = genericResponse.getObject();

                        txt_user_name.setText(user.getUserName());
                        txt_mobile_no.setText(user.getUserMobile());

                        SharedPreferences sharedPreferences = ApplicationManager.getContext().getSharedPreferences(BaseModel.kAppPreferences, Context.MODE_PRIVATE);

                        ModelManager.modelManager().getCurrentUser().setUserName(user.getUserName());
                        ModelManager.modelManager().getCurrentUser().setUserMobile(user.getUserMobile());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }

    private void setIntentLogin() {
        Intent in = new Intent(mContext, SignInActivity.class);
        in.putExtra("FROM", "2");
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);
        //getActivity().finish();
    }

    private void setIntentSignUp() {
        Intent intent = new Intent(mContext, SignUpActivity.class);
        intent.putExtra("FROM", "2");
        intent.putExtra(kLoginType, "N");
        intent.putExtra(kAuthToken, "");
        intent.putExtra(kUserName, "");
        intent.putExtra(kUserEmail, "");
        intent.putExtra(kPhone, "");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        //getActivity().finish();

    }

    private void menuDialog(String from) {
        // dialog
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert_place_order);
        TextView tv_login = dialog.findViewById(R.id.tv_login);
        TextView tv_text = dialog.findViewById(R.id.tv_text);


        if (from.equalsIgnoreCase("0")) {
            tv_text.setText(getString(R.string.loginSignup_alert));
        } else if (from.equalsIgnoreCase("1")) {
            tv_text.setText(getString(R.string.mnagedAddares_alertt));
        } else if (from.equalsIgnoreCase("2")) {
            tv_text.setText(getString(R.string.fav_alertt));
        } else if (from.equalsIgnoreCase("3")) {
            tv_text.setText(getString(R.string.resetPass_alertt));
        } else if (from.equalsIgnoreCase("4")) {
            tv_text.setText(getString(R.string.notification_alertt));
        }

        tv_login.setOnClickListener(v -> {
            setIntentLogin();
        });
        TextView tv_signup = dialog.findViewById(R.id.tv_signup);

        tv_signup.setOnClickListener(v -> {
            setIntentSignUp();
        });

        ImageButton btn_close = dialog.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(v -> {
            dialog.dismiss();
        });


        dialog.show();
    }


    private void menuDialogg() {
        // dialog
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert_place_orderr);
        TextView tv_login = dialog.findViewById(R.id.tv_login);
        tv_login.setOnClickListener(v -> {
            setIntentLogin();
        });
        TextView tv_signup = dialog.findViewById(R.id.tv_signup);

        tv_signup.setOnClickListener(v -> {
            setIntentSignUp();
        });

        ImageButton btn_close = dialog.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(v -> {
            dialog.dismiss();
        });


        dialog.show();
    }

    private void selectLanguageDialog() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        // dialog
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_select_language);
        RadioButton rb_french = dialog.findViewById(R.id.rb_french);
        RadioButton rb_english = dialog.findViewById(R.id.rb_english);
        RadioButton rb_portgal = dialog.findViewById(R.id.rb_portgal);
        TextView tv_confirm = dialog.findViewById(R.id.tv_confirm);


        if (selectedlanguage.equalsIgnoreCase("en")) {
            rb_english.setChecked(true);
            rb_portgal.setChecked(false);
            rb_french.setChecked(false);
        } else if (selectedlanguage.equalsIgnoreCase("pt")) {
            rb_english.setChecked(false);
            rb_portgal.setChecked(true);
            rb_french.setChecked(false);

        } else if (selectedlanguage.equalsIgnoreCase("fr")) {
            rb_english.setChecked(false);
            rb_portgal.setChecked(false);
            rb_french.setChecked(true);
        } else {

        }

        rb_french.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rb_french.setChecked(true);
                    rb_english.setChecked(false);
                    rb_portgal.setChecked(false);

                    editor.putString(kLangPref, "fr");


                }
            }
        });
        rb_english.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rb_english.setChecked(true);
                    rb_french.setChecked(false);
                    rb_portgal.setChecked(false);
                    editor.putString(kLangPref, "en");

                }
            }
        });

        rb_portgal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rb_portgal.setChecked(true);
                    rb_french.setChecked(false);
                    rb_english.setChecked(false);
                    editor.putString(kLangPref, "pt");

                }
            }
        });


        RelativeLayout rl_english = dialog.findViewById(R.id.rl_english);
        rl_english.setOnClickListener(v -> {
            rb_english.setChecked(true);
            rb_french.setChecked(false);
            rb_portgal.setChecked(false);
            editor.putString(kLangPref, "en");

        });
        RelativeLayout rl_french = dialog.findViewById(R.id.rl_french);
        rl_french.setOnClickListener(v -> {
            rb_english.setChecked(false);
            rb_french.setChecked(true);
            rb_portgal.setChecked(false);
            editor.putString(kLangPref, "fr");

        });
        RelativeLayout rl_purtgal = dialog.findViewById(R.id.rl_purtgal);
        rl_purtgal.setOnClickListener(v -> {
            rb_english.setChecked(false);
            rb_french.setChecked(false);
            rb_portgal.setChecked(true);
            editor.putString(kLangPref, "pt");

        });


        tv_confirm.setOnClickListener(v -> {
            editor.commit();
            ApplicationManager.setLanguageForApp(mContext, sharedPreferences.getString(kLangPref, ""));
            dialog.dismiss();

            Intent mainIntent = new Intent(mContext, HomeActivity.class);
            mainIntent.putExtra("FROM", "4");
            startActivity(mainIntent);
            getActivity().finish();

        });

        dialog.show();
    }

    public class AdvertismentAdapter extends PagerAdapter {

        private Context activity;
        private ArrayList<AdervisementModel> imagesArray;
        String imagepath;

        public AdvertismentAdapter(Context activity, ArrayList<AdervisementModel> imagesArray) {

            this.activity = activity;
            this.imagesArray = imagesArray;
            this.imagepath = imagepath;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            LayoutInflater inflater = ((Activity) activity).getLayoutInflater();

            View viewItem = inflater.inflate(R.layout.image_item_advertsement, container, false);
            ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView);

            Picasso.with(activity).load(imagesArray.get(position).getAdvertiseImage()).placeholder(R.drawable.ic_image_black_24dp).fit().into(imageView);

           /* if (!imagesArray.get(position).isEmpty()) {
                String imgPath = imagepath + "/" + imagesArray.get(position);
                Picasso.with(activity).load(imgPath).placeholder(R.drawable.ic_image_black_24dp).fit().into(imageView);
            } else {
                Picasso.with(activity).load(R.drawable.ic_image_black_24dp).placeholder(R.drawable.ic_image_black_24dp).fit().into(imageView);
            }*/

            ((ViewPager) container).addView(viewItem);

            return viewItem;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imagesArray.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            // TODO Auto-generated method stub
            return view == ((View) object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            ((ViewPager) container).removeView((View) object);
        }
    }
}
