package com.vibescom.kutanga.Activities.Restorants.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vibescom.kutanga.Activities.Restorants.Fragment.ProfileFragments;
import com.vibescom.kutanga.Activities.Restorants.Fragment.RestorantsCartFragment;
import com.vibescom.kutanga.Activities.Restorants.Fragment.RestorantsHomeFragment;
import com.vibescom.kutanga.Activities.Restorants.Fragment.RestorantsOfferFrag;
import com.vibescom.kutanga.Activities.Restorants.Fragment.RestorantsSearchFragment;
import com.vibescom.kutanga.ApplicationManager;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.BaseModel;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.OfferData;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.OfferListModel;
import com.vibescom.kutanga.Models.ViewCart.ViewcartModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.SocialManager.FacebookManager;
import com.vibescom.kutanga.SocialManager.GoogleManager;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.Toaster;

import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import static com.vibescom.kutanga.Constants.Constants.kAppPreferences;
import static com.vibescom.kutanga.Constants.Constants.kBusinessId;
import static com.vibescom.kutanga.Constants.Constants.kCoupon;
import static com.vibescom.kutanga.Constants.Constants.kCurrentUser;
import static com.vibescom.kutanga.Constants.Constants.kRestuarantsTypeId;

public class HomeActivity extends AppCompatActivity implements ProfileFragments.EventClickListener, RestorantsHomeFragment.HomeItemkListener,RestorantsCartFragment.cartItemkListener,RestorantsSearchFragment.cartNotificationListener {

    private RelativeLayout toolbar_hide,search_layout_home;
    private Fragment currentFragment;
    private TextView tvTitle, txt_deliver_address,gallery;
    private RelativeLayout toolbar;
    private GoogleManager googleManager;
    private FacebookManager facebookManager;
    private CustomLoaderView loaderView;
    Context mContext;
    private String state = "", country = "India", pincode = "", fullAddress = "";
    String address = "", city = "";
    double latitude, longitude;
    String from = "";
    int total_count;
    BottomNavigationView navigation;
    SharedPreferences  mPreferences;
    BadgeDrawable badgeDrawable;
    ViewPager pager;
    ImageView img_close;
    RelativeLayout ll_main;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 200;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000;

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof ProfileFragments) {
            ProfileFragments profileFragment = (ProfileFragments) fragment;
            profileFragment.setEventClickListener(this);
        } else if (fragment instanceof RestorantsHomeFragment) {
            RestorantsHomeFragment userDashboardFragment = (RestorantsHomeFragment) fragment;
            userDashboardFragment.setHomeItemkListener(this);
        }else if(fragment instanceof RestorantsCartFragment){
            RestorantsCartFragment restorantsCartFragment = (RestorantsCartFragment) fragment;
            restorantsCartFragment.setcartItemkListener(this);
        }else if(fragment instanceof  RestorantsSearchFragment){
            RestorantsSearchFragment restorantsSearchFragment=(RestorantsSearchFragment)fragment;
            restorantsSearchFragment.setcartItemkListener(this);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = this;
        loaderView = CustomLoaderView.initialize(mContext);
        mPreferences=getSharedPreferences(kAppPreferences, Context.MODE_PRIVATE);

        //search_layout_home=findViewById(R.id.search_layout_home);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        googleManager = new GoogleManager(HomeActivity.this);
        facebookManager = new FacebookManager(HomeActivity.this);

        /*if(ModelManager.modelManager().getCurrentUser()!=null){
            getCouponAvailable();
        }*/

        getViewCart();


        Intent in = getIntent();


        try {
            latitude = Double.parseDouble(mPreferences.getString("lat", ""));
            longitude = Double.parseDouble(mPreferences.getString("Lag", ""));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        //txt_deliver_address = findViewById(R.id.txt_deliver_address);

        from = in.getStringExtra("FROM");

        if(from.equalsIgnoreCase("1")){
            navigation.setSelectedItemId(R.id.action_cart);
            //toolbar.setElevation(20f);
            changeFragment(new RestorantsCartFragment(), false, false, getString(R.string.cart));
            //search_layout_home.setVisibility(View.VISIBLE);
        }else if(from.equalsIgnoreCase("4")){

            navigation.setSelectedItemId(R.id.action_profile);
            //toolbar.setElevation(20f);
            changeFragment(new ProfileFragments(), false, false, getString(R.string.cart));




        }else{
            if (savedInstanceState == null) {
                // on first time display view for first nav row_banner_item
                changeFragment(new RestorantsHomeFragment(), false, false, getString(R.string.app_name));
            } else {
                //Restore the fragment's instance
                currentFragment = getSupportFragmentManager().getFragment(savedInstanceState, "myFragmentName");
            }
        }

        //do here


    }




    private void getViewCart() {
        //loaderView.showLoader();
        ModelManager.modelManager().getViewcart(
                (Constants.Status iStatus, GenericResponse<ViewcartModel> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        ViewcartModel viewcartModel = genericResponse.getObject();
                        badgeDrawable=navigation.getOrCreateBadge(R.id.action_cart);
                        total_count=viewcartModel.getTotalItems();
                        if(total_count>0){
                            badgeDrawable.setNumber(total_count);
                            badgeDrawable.setVisible(true);
                        }else{
                            badgeDrawable.setVisible(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    //loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }


    private void getCouponAvailable() {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kCoupon, "32fdsf");
        ModelManager.modelManager().getOfferList(map,
                (Constants.Status iStatus, GenericResponse<OfferListModel> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        OfferListModel offerListModel = genericResponse.getObject();
                        CopyOnWriteArrayList<OfferData> offerData = offerListModel.getOfferData();
                        badgeDrawable=navigation.getOrCreateBadge(R.id.action_offer);
                        if(!offerData.isEmpty()){
                            total_count=offerData.size();
                            if(total_count>0){
                                badgeDrawable.setNumber(total_count);
                                badgeDrawable.setVisible(true);
                            }else{
                                badgeDrawable.setVisible(false);
                            }
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            item -> {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        //toolbar.setElevation(0f);
                        changeFragment(new RestorantsHomeFragment(), false, false, getString(R.string.app_name));
                        //search_layout_home.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.action_offer:
                        changeFragment(new RestorantsOfferFrag(), false, false, getString(R.string.offer));
                        //search_layout_home.setVisibility(View.GONE);
                        return true;
                    case R.id.action_search:
                        changeFragment(new RestorantsSearchFragment(), false, false, getString(R.string.search));
                        //search_layout_home.setVisibility(View.GONE);
                        return true;
                    case R.id.action_cart:
                        //toolbar.setElevation(20f);
                        changeFragment(new RestorantsCartFragment(), false, false, getString(R.string.cart));
                        //search_layout_home.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.action_profile:
                        changeFragment(new ProfileFragments(), false, false, getString(R.string.profile));
                        //search_layout_home.setVisibility(View.GONE);
                        return true;
                }
                return false;
            };

    public void changeFragment(Fragment fragment, boolean addToBackStack, boolean animate, String tag) {
        currentFragment = fragment;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (animate) {
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        }
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.content_main, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    public void logoutData() {
        loaderView.showLoader();
        ModelManager.modelManager().getLogout(iStatus -> {
            loaderView.hideLoader();
            clearContent();
        }, (iStatus, error) -> {
            loaderView.hideLoader();
            Toaster.customToast(error);
        });
    }

    private void clearContent() {
        SharedPreferences preferences = ApplicationManager.getContext()
                .getSharedPreferences(BaseModel.kAppPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.remove(kCurrentUser);
        editor.apply();
        {
            ModelManager.modelManager().setCurrentUser(null);
        }

        if (googleManager.getAlreadyLogin() != null)
            googleManager.signOut();
        if (AccessToken.getCurrentAccessToken() != null)
            facebookManager.onLogout();
        setIntent();
    }

    private void setIntent() {
        /*Intent in = new Intent(mContext, SplashActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);
        finish();*/

        navigation.setSelectedItemId(R.id.action_profile);
        //toolbar.setElevation(20f);
        changeFragment(new ProfileFragments(), false, false, getString(R.string.cart));
    }

    @Override
    public void logout() {
        logoutData();
    }


    @Override
    public void bestOfferClick(OfferData offersModel) {
        navigation.setSelectedItemId(R.id.action_offer);
        changeFragment(new RestorantsOfferFrag(), false, false, getString(R.string.offer));
    }

    @Override
    public void topPicItemClick(int businessId) {
        Intent intent = new Intent(mContext, InTheSportLightDetailsActivity.class);
        intent.putExtra(kBusinessId, businessId);
        startActivity(intent);
    }

    @Override
    public void takeYourPicItemClick(int resturantsTypeId) {

        Log.d("id",resturantsTypeId+"");
        Intent intent = new Intent(mContext, TakeYourPicDetailsActivity.class);
        intent.putExtra(kRestuarantsTypeId, resturantsTypeId);
        startActivity(intent);
    }

    @Override
    public void inTheSportLightItemClick(int businessId) {

        Intent intent = new Intent(mContext, InTheSportLightDetailsActivity.class);
        intent.putExtra(kBusinessId, businessId);
        startActivity(intent);
    }

    @Override
    public void feturedBrandItemClick(int businessId) {

        Log.d("BusinessId", businessId + "");
        Intent intent = new Intent(mContext, InTheSportLightDetailsActivity.class);
        intent.putExtra(kBusinessId, businessId);
        startActivity(intent);
    }


    @Override
    public void onRemoveCartbadge() {
        getViewCart();
    }

    public  void getUpdatebadge(){
        getViewCart();
    }

    @Override
    public void onUpdateCartbadge() {
        getViewCart();
    }


}
