package com.vibescom.kutanga.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vibescom.kutanga.Adapter.MarketPlaceAdapter;
import com.vibescom.kutanga.Models.MarketPlaceModel.MarketPlace;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.EqualSpacingItemDecoration;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MarketPlaceFragment extends Fragment {
    CustomLoaderView loaderView;
    private Context context;
    private RecyclerView rec_rest;
    private MarketPlaceAdapter restaurantAdapter;
    private CopyOnWriteArrayList<MarketPlace> res_data;
    private Integer[] IMAGES= {R.drawable.women_icon,R.drawable.men_icon,R.drawable.shoes_icon,R.drawable.mobile,R.drawable.home_app};
    CopyOnWriteArrayList<MarketPlace> marketPlaces;
    public MarketPlaceFragment(CopyOnWriteArrayList<MarketPlace> marketPlaces) {
        this.marketPlaces=marketPlaces;

        Log.d("SizeM",marketPlaces.size()+"");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_market_place, container, false);

        context = getActivity();
        // Inflate the layout for this fragment
        loaderView = CustomLoaderView.initialize(context);

        rec_rest=rootView.findViewById(R.id.recy_rest);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
        rec_rest.setLayoutManager(mLayoutManager);
        rec_rest.addItemDecoration(new EqualSpacingItemDecoration(10, EqualSpacingItemDecoration.HORIZONTAL));
        getMarketdata();
        return rootView;
    }

    private void getMarketdata() {
        loaderView.showLoader();
        CopyOnWriteArrayList<MarketPlace>res_data=new CopyOnWriteArrayList<>();
        res_data.add(new MarketPlace(1,"Women Wear",IMAGES[0]));
        res_data.add(new MarketPlace(1,"Men Wear",IMAGES[1]));
        res_data.add(new MarketPlace(1,"Shoes",IMAGES[2]));
        res_data.add(new MarketPlace(1,"Mobile",IMAGES[3]));
        res_data.add(new MarketPlace(1,"Home Applience",IMAGES[4]));

        setAdapter(marketPlaces);
        loaderView.hideLoader();

    }

    public void setAdapter(CopyOnWriteArrayList<MarketPlace>res_data){
        restaurantAdapter=new MarketPlaceAdapter(context,res_data);
        rec_rest.setAdapter(restaurantAdapter);

    }

}
