package com.vibescom.kutanga.Activities.Restorants.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Activities.LocationActivity;
import com.vibescom.kutanga.Activities.Restorants.Activity.TakeYourPicDetailsActivity;
import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansHomeBestOfferAdapter;
import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansHomeFeaturedBrandAdapter;
import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansHomeInTheSportLightAdapter;
import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansHomeTopPicForYouAdapter;
import com.vibescom.kutanga.Activities.Restorants.Adapter.ResturantsTypeAdapter;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.AdervisementModel;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.FeaturedBranddataModel;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.FoodLandingPageModel;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.OfferData;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.RestaurantsTypeData;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.TopPicData;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.inTheSportLightData;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.HorizontalSpaceItemDecoration;
import com.vibescom.kutanga.Utils.Toaster;
import com.vibescom.kutanga.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import static com.vibescom.kutanga.Constants.Constants.kAddress;
import static com.vibescom.kutanga.Constants.Constants.kAppPreferences;
import static com.vibescom.kutanga.Constants.Constants.kCity;
import static com.vibescom.kutanga.Constants.Constants.kOfferdata;
import static com.vibescom.kutanga.Constants.Constants.kRestuarantsTypeId;
import static com.vibescom.kutanga.Constants.Constants.kTitle;
import static com.vibescom.kutanga.Constants.Constants.kimagepath;
import static com.vibescom.kutanga.Constants.Constants.ksubTitle;


public class RestorantsHomeFragment extends Fragment {
    private Context context;
    private static String TAG=RestorantsHomeFragment.class.getSimpleName();
    TextView txt_inTheSportLightSeeAll,txt_deliver_address, txt_topPicSeeAll, tv_bestOffer, tv_takeYourPic, tv_inTheSportLight, tv_inTheSpotExplore, tv_topPicForYou, tv_feturedBrand;
    private RecyclerView rv_best_offer, rv_tack_your_pic, rv_sport_light, rv_top_pic_for_you, rv_featured_brands;
    private RestautansHomeBestOfferAdapter restautansHomeBestOfferAdapter;
    private ResturantsTypeAdapter restautansHomeTakeYourPicAdapter;
    private RestautansHomeInTheSportLightAdapter restautansHomeInTheSportLightAdapter;
    private RestautansHomeTopPicForYouAdapter restautansHomeTopPicForYouAdapter;
    private RestautansHomeFeaturedBrandAdapter restautansHomeFeaturedBrandAdapter;

    CopyOnWriteArrayList<OfferData> offerData;
    CopyOnWriteArrayList<RestaurantsTypeData> restaurantsTypedata;
    CopyOnWriteArrayList<inTheSportLightData> inTheSportLightdate;
    CopyOnWriteArrayList<TopPicData> topPicdata;
    CopyOnWriteArrayList<FeaturedBranddataModel> featuredBranddataModels;


    OfferData offersModel;
    RestaurantsTypeData restaurantsTypeModel;
    inTheSportLightData inTheSportLightModel;
    TopPicData topPicModel;
    FeaturedBranddataModel feturedBrandModel;


    JSONObject bestOfferjsonObject;
    JSONObject resturantsTypeJsonObj;
    JSONObject inTheSportLightJsonObj;
    JSONObject topPicJsonObj;
    JSONObject feturedJsonObj;

    CustomLoaderView loaderView;
    String address = "", city = "";
    double latitude, longitude;

    HomeItemkListener listener;
    SharedPreferences mPreferences;
    int businessid,resturantsTypeId;
    RelativeLayout search_layout_home;
    ViewPager pager;
    ImageView img_close;
    RelativeLayout ll_main;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 200;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000;

    public RestorantsHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_restorants_home, container, false);
        context = getActivity();
        mPreferences=context.getSharedPreferences(kAppPreferences, Context.MODE_PRIVATE);
        loaderView = CustomLoaderView.initialize(context);
        pager = rootView.findViewById(R.id.pager);
        setAdvertisementAdapter();
        ll_main=rootView.findViewById(R.id.ll_main);
        img_close=rootView.findViewById(R.id.img_close);

        img_close.setOnClickListener(v -> {
            ll_main.setVisibility(View.GONE);
        });


        inItView(rootView);
        getFoodLandingPagedata();

        return rootView;

    }

    private void inItView(View rootView) {
        search_layout_home=rootView.findViewById(R.id.search_layout_home);
        txt_deliver_address = rootView.findViewById(R.id.txt_deliver_address);
        tv_bestOffer = rootView.findViewById(R.id.tv_bestOffer);
        tv_takeYourPic = rootView.findViewById(R.id.tv_takeYourPic);
        tv_inTheSportLight = rootView.findViewById(R.id.tv_inTheSportLight);
        tv_inTheSpotExplore = rootView.findViewById(R.id.tv_inTheSpotExplore);
        tv_topPicForYou = rootView.findViewById(R.id.tv_topPicForYou);
        tv_feturedBrand = rootView.findViewById(R.id.tv_feturedBrand);
        rv_best_offer = rootView.findViewById(R.id.rv_best_offer);
        rv_best_offer.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.HORIZONTAL));
        rv_best_offer.addItemDecoration(new HorizontalSpaceItemDecoration(10));


        try {
            latitude = Double.parseDouble(mPreferences.getString("lat", ""));
            longitude = Double.parseDouble(mPreferences.getString("Lag", ""));
            address = mPreferences.getString(Constants.kAddress,"");
            city = mPreferences.getString(Constants.kCity,"");
            txt_deliver_address.setText(address);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        //setBestOfferData(Utils.getEventData());

        rv_tack_your_pic = rootView.findViewById(R.id.rv_tack_your_pic);
        rv_tack_your_pic.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.HORIZONTAL));
        rv_tack_your_pic.addItemDecoration(new HorizontalSpaceItemDecoration(10));

        //setTakeYourPicData(Utils.getEventData());

        rv_sport_light = rootView.findViewById(R.id.rv_sport_light);
        rv_sport_light.setHasFixedSize(true);
        rv_sport_light.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.HORIZONTAL));
        rv_sport_light.addItemDecoration(new HorizontalSpaceItemDecoration(10));


        //setTakeSportsLightData(Utils.getEventData());


        rv_top_pic_for_you = rootView.findViewById(R.id.rv_top_pic_for_you);
        rv_top_pic_for_you.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.HORIZONTAL));
        rv_top_pic_for_you.addItemDecoration(new HorizontalSpaceItemDecoration(10));

        //setTopPicForYouData(Utils.getAcaData());

        rv_featured_brands = rootView.findViewById(R.id.rv_featured_brands);
        rv_featured_brands.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.HORIZONTAL));
        rv_featured_brands.addItemDecoration(new HorizontalSpaceItemDecoration(10));

        //setFeaturedBrandsData(Utils.getAcaData());

        txt_inTheSportLightSeeAll = rootView.findViewById(R.id.txt_inTheSportLightSeeAll);
        txt_inTheSportLightSeeAll.setOnClickListener(v -> {
            Intent intent = new Intent(context, TakeYourPicDetailsActivity.class);
            intent.putExtra(kRestuarantsTypeId, 0);
            startActivity(intent);
        });

        txt_topPicSeeAll = rootView.findViewById(R.id.txt_topPicSeeAll);
        txt_topPicSeeAll.setOnClickListener(v -> {
            Intent intent = new Intent(context, TakeYourPicDetailsActivity.class);
            intent.putExtra(kRestuarantsTypeId, 0);
            startActivity(intent);
        });


        search_layout_home.setOnClickListener(V -> {
            Intent intent = new Intent(context, LocationActivity.class);
            intent.putExtra("From","2");
            intent.putExtra("lat", String.valueOf(latitude));
            intent.putExtra("Lag", String.valueOf(longitude));
            intent.putExtra(kAddress, address);
            intent.putExtra(kCity, city);
            startActivity(intent);
            //getActivity().finish();
        });
    }

    public void setAdvertisementAdapter(){
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

    private void getFoodLandingPagedata() {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put("lat",latitude);
        map.put("lon",longitude);

        Log.e(TAG,map.toString());
        ModelManager.modelManager().getFoodlandingpage(map,
                (Constants.Status iStatus, GenericResponse<FoodLandingPageModel> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        FoodLandingPageModel foodLandingPageModel = genericResponse.getObject();

                        bestOfferjsonObject = foodLandingPageModel.getOffersModels();
                        JSONArray array = bestOfferjsonObject.getJSONArray(kOfferdata);
                        offerData=new CopyOnWriteArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                             offersModel = new OfferData(array.getJSONObject(i));
                            offerData.add(offersModel) ;
                        }

                        if(offerData.isEmpty()){
                            rv_best_offer.setVisibility(View.GONE);
                            tv_bestOffer.setVisibility(View.GONE);
                        }else{
                            rv_best_offer.setVisibility(View.VISIBLE);
                            tv_bestOffer.setVisibility(View.VISIBLE);
                            restautansHomeBestOfferAdapter=new RestautansHomeBestOfferAdapter(context, offerData,bestOfferjsonObject.getString(kimagepath), offersModel -> {
                                listener.bestOfferClick(offersModel);
                            });
                            rv_best_offer.setAdapter(restautansHomeBestOfferAdapter);
                        }

                        resturantsTypeJsonObj = foodLandingPageModel.getRestaurantsTypeModels();
                        JSONArray array1 = resturantsTypeJsonObj.getJSONArray(kOfferdata);
                        restaurantsTypedata=new CopyOnWriteArrayList<>();
                        for (int i = 0; i < array1.length(); i++) {
                            restaurantsTypeModel = new RestaurantsTypeData(array1.getJSONObject(i));
                            restaurantsTypedata.add(restaurantsTypeModel);
                        }
                        if(!restaurantsTypedata.isEmpty()){
                            restautansHomeTakeYourPicAdapter = new ResturantsTypeAdapter(context, restaurantsTypedata,resturantsTypeId->{listener.takeYourPicItemClick(resturantsTypeId);});
                            rv_tack_your_pic.setAdapter(restautansHomeTakeYourPicAdapter);
                        }

                        inTheSportLightJsonObj = foodLandingPageModel.getInTheSportLightModels();
                        JSONArray array2 = inTheSportLightJsonObj.getJSONArray(kOfferdata);
                        inTheSportLightdate=new CopyOnWriteArrayList<>();
                        for (int i = 0; i < array2.length(); i++) {
                            inTheSportLightModel = new inTheSportLightData(array2.getJSONObject(i));
                            inTheSportLightdate.add(inTheSportLightModel);
                        }

                        if(!inTheSportLightdate.isEmpty()){
                            restautansHomeInTheSportLightAdapter = new RestautansHomeInTheSportLightAdapter(context, inTheSportLightdate,inTheSportLightJsonObj.getString(kimagepath),businessid -> {listener.inTheSportLightItemClick(businessid);});
                            rv_sport_light.setAdapter(restautansHomeInTheSportLightAdapter);
                        }


                        topPicJsonObj = foodLandingPageModel.getTopPicModels();
                        JSONArray array3 = topPicJsonObj.getJSONArray(kOfferdata);
                        topPicdata=new CopyOnWriteArrayList<>();
                        for (int i = 0; i < array3.length(); i++) {
                            topPicModel = new TopPicData(array3.getJSONObject(i));
                            topPicdata.add(topPicModel);
                        }
                        if(!topPicdata.isEmpty()){
                            restautansHomeTopPicForYouAdapter = new RestautansHomeTopPicForYouAdapter(context, topPicdata,topPicJsonObj.getString(kimagepath), topPicdata->{listener.topPicItemClick(topPicModel.getVendorBusinessId());});
                            rv_top_pic_for_you.setAdapter(restautansHomeTopPicForYouAdapter);
                        }



                        feturedJsonObj = foodLandingPageModel.getFeturedBrandModels();
                        JSONArray array4 = feturedJsonObj.getJSONArray(kOfferdata);
                        featuredBranddataModels=new CopyOnWriteArrayList<>();
                        for (int i = 0; i < array4.length(); i++) {
                            feturedBrandModel = new FeaturedBranddataModel(array4.getJSONObject(i));
                            featuredBranddataModels.add(feturedBrandModel);
                        }
                        if(!featuredBranddataModels.isEmpty()){
                            restautansHomeFeaturedBrandAdapter = new RestautansHomeFeaturedBrandAdapter(context, featuredBranddataModels,feturedJsonObj.getString(kimagepath),businessid->{listener.feturedBrandItemClick(businessid);});
                            rv_featured_brands.setAdapter(restautansHomeFeaturedBrandAdapter);
                        }


                        try {
                            tv_bestOffer.setText(bestOfferjsonObject.getString(kTitle));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        try {
                            tv_takeYourPic.setText(resturantsTypeJsonObj.getString(kTitle).toLowerCase());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        try {
                            String upperString = inTheSportLightJsonObj.getString(kTitle).substring(0, 1).toUpperCase() + inTheSportLightJsonObj.getString(kTitle).substring(1).toLowerCase();
                            tv_inTheSportLight.setText(upperString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {

                            tv_inTheSpotExplore.setText(inTheSportLightJsonObj.getString(ksubTitle));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            tv_topPicForYou.setText(topPicJsonObj.getString(kTitle));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        try {
                            tv_feturedBrand.setText(feturedJsonObj.getString(kTitle));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setHomeItemkListener(HomeItemkListener listener){
        this.listener=listener;
    }

    public interface HomeItemkListener{
        void bestOfferClick(OfferData offersModel);
        void topPicItemClick(int businessId);
        void takeYourPicItemClick(int resturantsTypeId);
        void inTheSportLightItemClick(int businessId);
        void feturedBrandItemClick(int businessId);
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
