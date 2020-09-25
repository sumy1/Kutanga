package com.vibescom.kutanga.Activities.Restorants.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.vibescom.kutanga.Activities.Restorants.Adapter.ViewPagerAdapter;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.OfferData;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


public class RestorantsOfferFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Context context;
    CopyOnWriteArrayList<OfferData> offerData;
    String ImagePath;
    CustomLoaderView loaderView;
    public RestorantsOfferFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_restorants_offer, container, false);
        context = getActivity();
        loaderView=CustomLoaderView.initialize(context);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        addTabs(viewPager);
        return rootView;
    }




    private void addTabs(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new RestorantsOfferFrag(), "RESTORANTS OFFER");
        adapter.addFrag(new PaymentsOfferCoupan(), "PAYMENTS OFFER/COUPANS");

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
