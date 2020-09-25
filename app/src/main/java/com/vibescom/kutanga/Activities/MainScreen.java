package com.vibescom.kutanga.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.Fragments.MarketPlaceFragment;
import com.vibescom.kutanga.Fragments.RestaurantFragment;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.MarketPlaceModel.MarketPlace;
import com.vibescom.kutanga.Models.RestaurantsFoodCommonLanding;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.LandingFood;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.Toaster;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.vibescom.kutanga.Constants.Constants.kUserEmail;

public class MainScreen extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    private MarketPlaceFragment marketPlaceFragment;
    private RestaurantFragment restaurantFragment;
    RelativeLayout rel_market_place, rel_restaurant;
    ImageView img_market, img_restaurant, img_back;
    TextView txt_market_place, txt_restaurant;
    CustomLoaderView loaderView;
    CopyOnWriteArrayList<MarketPlace>marketPlaces=new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<LandingFood>landingFoods=new CopyOnWriteArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        mContext = this;
        loaderView = CustomLoaderView.initialize(mContext);
        getCommonLandingpage();

        inItView();


    }


    /**
     * Load Fragment..
     *
     * @param fragment
     */
    public void loadFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();//create an instance of fragment manager
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frameLayout, fragment, "HELLO");
        transaction.commit();

    }

    public void rePlaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();//create an instance of fragment manager
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment, "HELLO");
        transaction.commit();
    }


    private void getCommonLandingpage() {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();

        if(ModelManager.modelManager().getCurrentUser()==null){
            map.put(kUserEmail, "socialsportz01@gmail.com");
        }else{
            map.put(kUserEmail, ModelManager.modelManager().getCurrentUser().getUserEmail());
        }
        ModelManager.modelManager().getLandingPageFood(map,
                (Constants.Status iStatus, GenericResponse<RestaurantsFoodCommonLanding> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        RestaurantsFoodCommonLanding restaurantsFoodCommonLanding = genericResponse.getObject();


                        marketPlaces = restaurantsFoodCommonLanding.getMarketPlaces();
                        landingFoods = restaurantsFoodCommonLanding.getLandingFoods();

                        if (!marketPlaces.isEmpty()) {
                            marketPlaceFragment = new MarketPlaceFragment(marketPlaces);
                            loadFragment(marketPlaceFragment);
                        }

                        if (!landingFoods.isEmpty()) {
                            restaurantFragment = new RestaurantFragment(landingFoods);
                        }

                        //Log.d("Size",landingFoods.size()+"/"+marketPlaces.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }




    private void inItView() {
        txt_restaurant = findViewById(R.id.txt_restaurant);
        img_restaurant = findViewById(R.id.img_restaurant);

        txt_market_place = findViewById(R.id.txt_market_place);
        img_market = findViewById(R.id.img_market);

        rel_market_place = findViewById(R.id.rel_market_place);
        rel_market_place.setOnClickListener(this);
        rel_restaurant = findViewById(R.id.rel_restaurant);
        rel_restaurant.setOnClickListener(this);

       /* img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_market_place:
                txt_restaurant.setTextColor(getResources().getColor(R.color.light_pink));
                rel_restaurant.setBackground(getResources().getDrawable(R.drawable.gback_light_shadow));
                rel_market_place.setBackground(getResources().getDrawable(R.drawable.oval_pink_shadow));
                txt_market_place.setTextColor(getResources().getColor(R.color.white));
                img_market.setColorFilter(getResources().getColor(R.color.white));
                img_restaurant.setColorFilter(getResources().getColor(R.color.market_color));

                if (!marketPlaces.isEmpty()) {
                    marketPlaceFragment = new MarketPlaceFragment(marketPlaces);
                    rePlaceFragment(marketPlaceFragment);
                }

                break;
            case R.id.rel_restaurant:
                //click.....

                txt_market_place.setTextColor(getResources().getColor(R.color.light_pink));
                rel_restaurant.setBackground(getResources().getDrawable(R.drawable.oval_pink_shadow));
                txt_restaurant.setTextColor(getResources().getColor(R.color.white));
                rel_market_place.setBackground(getResources().getDrawable(R.drawable.gback_light_shadow));
                img_market.setColorFilter(getResources().getColor(R.color.market_color));
                img_restaurant.setColorFilter(getResources().getColor(R.color.white));

                if (!landingFoods.isEmpty()) {
                    restaurantFragment = new RestaurantFragment(landingFoods);
                    rePlaceFragment(restaurantFragment);
                }


                break;
           /* case R.id.img_back:
                finish();
                break;*/
        }

    }
}
