package com.vibescom.kutanga.Activities.Restorants.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansOfferAdapter;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.AdervisementModel;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.OfferData;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.OfferListModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.Utils;
import com.vibescom.kutanga.Utils.VerticalSpaceItemDecoration;

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

import static com.vibescom.kutanga.Constants.Constants.kCoupon;


public class RestorantsOfferFrag extends Fragment {
    private Context context;
    private RecyclerView rv_offer;
    RestautansOfferAdapter restautansOfferAdapter;
    CopyOnWriteArrayList<OfferData> offerData;
    String ImagePath;
    private ShimmerFrameLayout mShimmerViewContainer;
    ViewPager pager;
    ImageView img_close;
    RelativeLayout ll_main;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 200;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000;
    RelativeLayout rv_cart;
    public RestorantsOfferFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_restorants_offer3, container, false);
        context = getActivity();
        mShimmerViewContainer = rootView.findViewById(R.id.shimmer_view_container);
        rv_cart=rootView.findViewById(R.id.rv_cart);
        pager = rootView.findViewById(R.id.pager);
        setAdvertisementAdapter();
        ll_main=rootView.findViewById(R.id.ll_main);
        img_close=rootView.findViewById(R.id.img_close);

        img_close.setOnClickListener(v -> {
            ll_main.setVisibility(View.GONE);
        });
        initView(rootView);
        getResturantsOfferList();
        // Inflate the layout for this fragment
        return rootView;
    }

    private void getResturantsOfferList() {
        showShimmerView();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kCoupon, "32fdsf");

        ModelManager.modelManager().getOfferList(map,
                (Constants.Status iStatus, GenericResponse<OfferListModel> genericResponse) -> {
                   hideShimmerView();
                    try {
                        OfferListModel offerListModel = genericResponse.getObject();
                        ImagePath=offerListModel.getImagePath();
                        offerData = offerListModel.getOfferData();

                        if(offerData.isEmpty()){
                            rv_cart.setVisibility(View.VISIBLE);
                            rv_offer.setVisibility(View.GONE);
                        }else{
                            rv_cart.setVisibility(View.GONE);
                            rv_offer.setVisibility(View.VISIBLE);
                            restautansOfferAdapter=new RestautansOfferAdapter(context,offerData,ImagePath);
                            rv_offer.setAdapter(restautansOfferAdapter);
                        }

                        checkEmptyView();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    hideShimmerView();
                    checkEmptyView();
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

    private void initView(View rootView) {
        rv_offer=rootView.findViewById(R.id.rv_offer);
        rv_offer.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL ));
        rv_offer.addItemDecoration(new VerticalSpaceItemDecoration(10));
        rv_offer.setHasFixedSize(true);


    }

    private void showShimmerView(){
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        rv_cart.setVisibility(View.GONE);
        mShimmerViewContainer.startShimmerAnimation();
        rv_offer.setVisibility(View.GONE);
    }

    private void hideShimmerView(){
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
        rv_offer.setVisibility(View.VISIBLE);
    }

    private void checkEmptyView(){
        try{
            if (restautansOfferAdapter.getItemCount() == 0)
                rv_cart.setVisibility(View.VISIBLE);
            else
                rv_cart.setVisibility(View.GONE);
        }catch (Exception e){
            e.printStackTrace();
        }
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
