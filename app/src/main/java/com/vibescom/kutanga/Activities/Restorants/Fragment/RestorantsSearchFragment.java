package com.vibescom.kutanga.Activities.Restorants.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.AdervisementModel;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ResturantsDishesSearchModel;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.SearchDishesModel;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.SearchResturantsModel;
import com.vibescom.kutanga.Models.ViewCart.CartItemModel;
import com.vibescom.kutanga.Models.ViewCart.ViewcartModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.Toaster;
import com.vibescom.kutanga.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import static com.vibescom.kutanga.Constants.Constants.kAppPreferences;
import static com.vibescom.kutanga.Constants.Constants.ksearchFoodRes;


public class RestorantsSearchFragment extends Fragment implements View.OnClickListener {
     Context context;
    EditText search_et;
    ImageView img_search, img_clear;
    CustomLoaderView loaderView;
    String resturansImagePath, productImagepath;
    String searchValue;
    private RestorantsSearchFrag restorantsSearchFrag;
    private DishesSearchCoupan dishesSearchCoupan;
    private CopyOnWriteArrayList<SearchDishesModel> searchDishesModel = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<SearchResturantsModel> searchResturantsModel = new CopyOnWriteArrayList<>();

    RelativeLayout rel_resturants, rel_dishes,rv_cart,food_search;
    LinearLayout layout_t;
    TextView txt_dishes, txt_restaurant;
    CopyOnWriteArrayList<CartItemModel> cartItemModels=new CopyOnWriteArrayList<>();
    cartNotificationListener  listener;
    ViewPager pager;
    ImageView img_close;
    RelativeLayout ll_main;
    int currentPage = 0;
    Timer timer;
    double latitude, longitude;
    final long DELAY_MS = 200;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000;
    SharedPreferences mPreferences;
    Fragment currentFragment;
    public RestorantsSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_restorants_search, container, false);

        context = getActivity();
        loaderView = CustomLoaderView.initialize(context);
        mPreferences=context.getSharedPreferences(kAppPreferences, Context.MODE_PRIVATE);

        try {
            latitude = Double.parseDouble(mPreferences.getString("lat", ""));
            longitude = Double.parseDouble(mPreferences.getString("Lag", ""));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        pager = rootView.findViewById(R.id.pager);
        setAdvertisementAdapter();
        ll_main=rootView.findViewById(R.id.ll_main);
        img_close=rootView.findViewById(R.id.img_close);

        img_close.setOnClickListener(v -> {
            ll_main.setVisibility(View.GONE);
        });
        food_search=rootView.findViewById(R.id.food_search);
        rel_resturants = rootView.findViewById(R.id.rel_resturants);
        rel_resturants.setOnClickListener(this);
        rel_dishes = rootView.findViewById(R.id.rel_dishes);
        rel_dishes.setOnClickListener(this);
        layout_t = rootView.findViewById(R.id.layout_t);
        txt_restaurant = rootView.findViewById(R.id.txt_restaurant);
        txt_dishes = rootView.findViewById(R.id.txt_dishes);
        rv_cart = rootView.findViewById(R.id.rv_cart);


        img_search = rootView.findViewById(R.id.img_search);

        img_clear = rootView.findViewById(R.id.img_clear);

        search_et = rootView.findViewById(R.id.search_et);
        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                img_search.setVisibility(View.GONE);
                img_clear.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {
                img_search.setVisibility(View.VISIBLE);
                img_clear.setVisibility(View.GONE);
                //searchValue = s.toString();

            }
        });

        //getViewCart();
        img_search.setOnClickListener(v -> {
            searchResturantsModel.clear();
            searchDishesModel.clear();

            searchValue=search_et.getText().toString().trim();
            if(searchValue.isEmpty()){
                ll_main.setVisibility(View.GONE);
                Toaster.customToast(getResources().getString(R.string.search_validation));
            }else{
                getResturantsOfferList(searchValue);
            }



        });
        return rootView;
    }


    public void loadFragment(Fragment fragment) {
        FragmentManager manager = getFragmentManager();//create an instance of fragment manager
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frameLayout, fragment, "HELLO");
        transaction.detach(fragment);
        transaction.attach(fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void changeFragment(Fragment fragment, boolean addToBackStack, boolean animate, String tag) {
        currentFragment = fragment;
        FragmentManager manager =  getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (animate) {
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        }
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.frameLayout, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    public void rePlaceFragment(Fragment fragment) {
        FragmentManager manager = getFragmentManager();//create an instance of fragment manager
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment, "HELLO");
        transaction.commit();
    }


    private void getResturantsOfferList(String searchValue) {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(ksearchFoodRes, searchValue);
        map.put("lat",latitude);
        map.put("lon",longitude);
        Log.e("Send Serach",map.toString());
        ModelManager.modelManager().getSearchList(map,
                (Constants.Status iStatus, GenericResponse<ResturantsDishesSearchModel> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        ResturantsDishesSearchModel resturantsDishesSearchModel = genericResponse.getObject();
                        searchDishesModel = new CopyOnWriteArrayList<>();
                        searchResturantsModel = new CopyOnWriteArrayList<>();
                        searchResturantsModel = resturantsDishesSearchModel.getSearchResturantsModels();
                        searchDishesModel = resturantsDishesSearchModel.getSearchDishesModels();
                        resturansImagePath = resturantsDishesSearchModel.getResturantsImagePath();
                        productImagepath = resturantsDishesSearchModel.getProductImagePath();


                        if(searchResturantsModel.isEmpty() && searchDishesModel.isEmpty()){
                            layout_t.setVisibility(View.GONE);
                            rv_cart.setVisibility(View.VISIBLE);
                            food_search.setVisibility(View.GONE);
                            changeFragment(new RestorantsSearchFrag(searchResturantsModel, resturansImagePath, productImagepath), true, true, "Hello");
                         /*   restorantsSearchFrag = new RestorantsSearchFrag(searchResturantsModel, resturansImagePath, productImagepath);
                            loadFragment(restorantsSearchFrag);*/

                        }else{
                            rv_cart.setVisibility(View.GONE);
                            layout_t.setVisibility(View.VISIBLE);
                            food_search.setVisibility(View.GONE);


                            if(searchResturantsModel.isEmpty()&& searchDishesModel.size()>0){
                                txt_dishes.setTextColor(getResources().getColor(R.color.white));
                                rel_dishes.setBackground(getResources().getDrawable(R.drawable.oval_pink_shadow));
                                txt_restaurant.setTextColor(getResources().getColor(R.color.user_theme_color));
                                rel_resturants.setBackground(getResources().getDrawable(R.drawable.gback_light_shadow));
                                dishesSearchCoupan = new DishesSearchCoupan(listener,searchDishesModel, resturansImagePath, productImagepath);
                                rePlaceFragment(dishesSearchCoupan);
                            }else if(searchResturantsModel.size()>0&& searchDishesModel.isEmpty()){
                                rel_resturants.setBackground(getResources().getDrawable(R.drawable.oval_pink_shadow));
                                txt_restaurant.setTextColor(getResources().getColor(R.color.white));
                                if (!searchResturantsModel.isEmpty()) {
                                    restorantsSearchFrag = new RestorantsSearchFrag(searchResturantsModel, resturansImagePath, productImagepath);
                                    rePlaceFragment(restorantsSearchFrag);
                                }
                                rel_dishes.setBackground(getResources().getDrawable(R.drawable.gback_light_shadow));
                                txt_dishes.setTextColor(getResources().getColor(R.color.light_pink));
                            }else{
                                rel_resturants.setBackground(getResources().getDrawable(R.drawable.oval_pink_shadow));
                                txt_restaurant.setTextColor(getResources().getColor(R.color.white));
                                rel_dishes.setBackground(getResources().getDrawable(R.drawable.gback_light_shadow));
                                txt_dishes.setTextColor(getResources().getColor(R.color.light_pink));
                                if (!searchResturantsModel.isEmpty()) {
                                    restorantsSearchFrag = new RestorantsSearchFrag(searchResturantsModel, resturansImagePath, productImagepath);
                                    rePlaceFragment(restorantsSearchFrag);
                                }
                        if (!searchDishesModel.isEmpty()) {
                            dishesSearchCoupan = new DishesSearchCoupan(listener,searchDishesModel, resturansImagePath, productImagepath);
                            rePlaceFragment(dishesSearchCoupan);
                        }
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

    private void getViewCart() {
        //loaderView.showLoader();
        ModelManager.modelManager().getViewcart(
                (Constants.Status iStatus, GenericResponse<ViewcartModel> genericResponse) -> {
                    try {
                        ViewcartModel viewcartModel = genericResponse.getObject();

                        cartItemModels = viewcartModel.getCartItemModels();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    //loaderView.hideLoader();
                    Toaster.customToast(message);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_resturants:
                rel_resturants.setBackground(getResources().getDrawable(R.drawable.oval_pink_shadow));
                txt_restaurant.setTextColor(getResources().getColor(R.color.white));
                changeFragment(new RestorantsSearchFrag(searchResturantsModel, resturansImagePath, productImagepath), true, true, "Hello");
                rel_dishes.setBackground(getResources().getDrawable(R.drawable.gback_light_shadow));
                txt_dishes.setTextColor(getResources().getColor(R.color.light_pink));
                break;
            case R.id.rel_dishes:
                txt_dishes.setTextColor(getResources().getColor(R.color.white));
                rel_dishes.setBackground(getResources().getDrawable(R.drawable.oval_pink_shadow));
                changeFragment(new DishesSearchCoupan(listener,searchDishesModel, resturansImagePath, productImagepath), true, true, "Helloo");
                txt_restaurant.setTextColor(getResources().getColor(R.color.user_theme_color));
                rel_resturants.setBackground(getResources().getDrawable(R.drawable.gback_light_shadow));
                break;

        }
    }

    public void setcartItemkListener(cartNotificationListener listener){
        this.listener=listener;
    }

    public interface cartNotificationListener{
        void onUpdateCartbadge();

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
