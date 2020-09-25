package com.vibescom.kutanga.Activities.Restorants.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansSearchAdapter;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.SearchResturantsModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.VerticalSpaceItemDecoration;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class RestorantsSearchFrag extends Fragment  {
    private Context context;
    RelativeLayout rv_cart;
    private RecyclerView rv_offer;
    RestautansSearchAdapter restautansOfferAdapter;
    private CopyOnWriteArrayList<SearchResturantsModel> searchResturantsModel;
    String resturansImagePath, productImagepath;

    public RestorantsSearchFrag(CopyOnWriteArrayList<SearchResturantsModel> searchResturantsModell, String resturansImagePathh, String productImagepathh) {
        searchResturantsModel = searchResturantsModell;
        productImagepath = productImagepathh;
        resturansImagePath = resturansImagePathh;

        Log.d("SizeRes",searchResturantsModel.size()+"");
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_restorants_offer2, container, false);
        context = getActivity();
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        rv_offer = rootView.findViewById(R.id.rv_offerr);
        rv_cart=rootView.findViewById(R.id.rv_cart);
        rv_offer.setLayoutManager(new GridLayoutManager(context, RecyclerView.VERTICAL));
        rv_offer.addItemDecoration(new VerticalSpaceItemDecoration(8));
        rv_offer.setHasFixedSize(true);
        restautansOfferAdapter = new RestautansSearchAdapter(context, new CopyOnWriteArrayList<>(), resturansImagePath, productImagepath);
        rv_offer.setAdapter(restautansOfferAdapter);
        if(searchResturantsModel.size()==0){
            rv_cart.setVisibility(View.VISIBLE);
            rv_offer.setVisibility(View.GONE);
        }else{
            rv_cart.setVisibility(View.GONE);
            rv_offer.setVisibility(View.VISIBLE);
            restautansOfferAdapter.newData(searchResturantsModel);
        }
    }
}
