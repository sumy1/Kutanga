package com.vibescom.kutanga.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.vibescom.kutanga.Activities.Restorants.Activity.HomeActivity;
import com.vibescom.kutanga.Activities.SetDeliveryLocationActivity;
import com.vibescom.kutanga.Adapter.RestaurantAdapter;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.CurrentUser;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.LandingFood;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.EqualSpacingItemDecoration;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.vibescom.kutanga.Constants.Constants.kAddress;
import static com.vibescom.kutanga.Constants.Constants.kAppPreferences;
import static com.vibescom.kutanga.Constants.Constants.kCity;

public class RestaurantFragment extends Fragment {
    CustomLoaderView loaderView;
    private Context context;
    private RecyclerView rec_rest;
    private RestaurantAdapter restaurantAdapter;
    RelativeLayout rl_explore;
    private CopyOnWriteArrayList<LandingFood>res_data;
    private Integer[] IMAGES= {R.drawable.res_o,R.drawable.drinks_o,R.drawable.coffe_ofering,R.drawable.sea_f_o};
    CopyOnWriteArrayList<LandingFood>landingFoods;
    SharedPreferences mPreferences;
    String latitude,longitude,address,city;
    public RestaurantFragment(CopyOnWriteArrayList<LandingFood>landingFoods) {
        this.landingFoods=landingFoods;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurant, container, false);
        context = getActivity();
        // Inflate the layout for this fragment
        loaderView = CustomLoaderView.initialize(context);
        mPreferences=context.getSharedPreferences(kAppPreferences, Context.MODE_PRIVATE);
        latitude = mPreferences.getString("lat", "");
        longitude = mPreferences.getString("Lag", "");
        address = mPreferences.getString(kAddress,"");
        city = mPreferences.getString(Constants.kCity,"");
        rec_rest=rootView.findViewById(R.id.rec_rest);

        rl_explore=rootView.findViewById(R.id.rl_explore);
        rl_explore.setOnClickListener(v -> {
            CurrentUser user=ModelManager.modelManager().getCurrentUser();
            if(ModelManager.modelManager().getCurrentUser()==null && latitude.equalsIgnoreCase("") && longitude.equalsIgnoreCase("")){
                Intent intent=new Intent(context, SetDeliveryLocationActivity.class);
                context.startActivity(intent);
                ((Activity)context).finish();
            }else if((user==null || user.getUserId()>0) &&latitude.equalsIgnoreCase("") && longitude.equalsIgnoreCase("")){
                Intent intent=new Intent(context, SetDeliveryLocationActivity.class);
                context.startActivity(intent);
                ((Activity)context).finish();
            }

            else{
                Intent intent=new Intent(context, HomeActivity.class);
                intent.putExtra("lat", latitude);
                intent.putExtra("Lag", longitude);
                intent.putExtra("FROM", "2");
                intent.putExtra(kAddress, address);
                intent.putExtra(kCity, city);
                context.startActivity(intent);
                //((Activity)context).finish();
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
        rec_rest.setLayoutManager(mLayoutManager);
        rec_rest.addItemDecoration(new EqualSpacingItemDecoration(10, EqualSpacingItemDecoration.HORIZONTAL));
        getRestaurantData();
        return rootView;
    }

    private void getRestaurantData() {
        loaderView.showLoader();
        CopyOnWriteArrayList<LandingFood>res_data=new CopyOnWriteArrayList<>();
        res_data.add(new LandingFood(1,"Restaurants",IMAGES[0]));
        res_data.add(new LandingFood(1,"Drinks",IMAGES[1]));
        res_data.add(new LandingFood(1,"Coffee",IMAGES[2]));
        res_data.add(new LandingFood(1,"Sea Food",IMAGES[3]));

        //landingFoods.add(new LandingFood(1,"Explore",IMAGES[0]));

        setAdapter(landingFoods);
        loaderView.hideLoader();

    }

    public void setAdapter(CopyOnWriteArrayList<LandingFood>res_data){
        restaurantAdapter=new RestaurantAdapter(context,res_data);
        rec_rest.setAdapter(restaurantAdapter);

    }


}
