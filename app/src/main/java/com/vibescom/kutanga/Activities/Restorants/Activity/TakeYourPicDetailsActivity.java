package com.vibescom.kutanga.Activities.Restorants.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mancj.slideup.SlideUp;
import com.mancj.slideup.SlideUpBuilder;
import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansHomeTakeYourPicDetailsAdapter;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.TakeYourPicDetailsModel.TakeYourPicDataModel;
import com.vibescom.kutanga.Models.TakeYourPicDetailsModel.TakeYourPicModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.Toaster;
import com.vibescom.kutanga.Utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import static com.vibescom.kutanga.Constants.Constants.kAppPreferences;
import static com.vibescom.kutanga.Constants.Constants.kBusinessId;
import static com.vibescom.kutanga.Constants.Constants.kLat;
import static com.vibescom.kutanga.Constants.Constants.kLon;
import static com.vibescom.kutanga.Constants.Constants.kMinimumOrder;
import static com.vibescom.kutanga.Constants.Constants.kPrice;
import static com.vibescom.kutanga.Constants.Constants.kRestuarantsTypeId;
import static com.vibescom.kutanga.Constants.Constants.kdeliveryTime;
import static com.vibescom.kutanga.Constants.Constants.kfoodTypeId;
import static com.vibescom.kutanga.Constants.Constants.krating;

public class TakeYourPicDetailsActivity extends AppCompatActivity {

    private RecyclerView rv_take_your_pic_details;
    private RestautansHomeTakeYourPicDetailsAdapter restaurantHomeTakeYourPicDetails;
    Context context;
    ImageView img_back;
    CustomLoaderView loaderView;
    private CopyOnWriteArrayList<TakeYourPicDataModel> mData;
    int resturantsTypeId;
    String resturantsImagepath;
    TextView tv_trending, tv_filter;
    SharedPreferences mPreferences;
    double latitude, longitude;
    private ArrayList<String> imageList = new ArrayList<>();
    ViewPager pager;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 200;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000;
    private SlideUp slideUp;
    private View dim, rootView;
    private View slideView;
    RadioButton radia_relivence, radio_fourStart, radio_rating_threestar, radio_rating_twoStr, radio_oneStart, radio_price, radio_delivery_time, radio_min_order, radio_rating_fiveStr;
    LinearLayout ll_price, ll_delivery_time, ll_min_order;
    SeekBar seekBar_price, seekBar_deliveryTime, seekBar_minOrder;
    TextView txt_min_kz, txt_min_time, txt_min_order;
    private static final int MIN = 10;
    private static final int MAX = 1000;
    private static final int STEP = 5;
    int step = 5;
    int max = 60;
    int min = 10;
    private int currentValue = 0;
    boolean isChcekRelevemce,isCheckprice,isCheckTime,isChcekOrder,iscahcekfive,isChcekfour,isChcekthree,isChcektwo,isChcekone;
    Button btn_apply;
    int ratingvalue,minOrdervalue,timeValue,pricevalue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takeyourpicdetailst);
        context = this;
        resturantsTypeId = getIntent().getIntExtra(kRestuarantsTypeId, 0);
        mPreferences = getSharedPreferences(kAppPreferences, Context.MODE_PRIVATE);
        latitude = Double.parseDouble(mPreferences.getString("lat", ""));
        longitude = Double.parseDouble(mPreferences.getString("Lag", ""));
        loaderView = CustomLoaderView.initialize(context);
        inItView();
        geTakeyourPicDetails();
    }

    private void inItView() {

       /* rv_take_your_pic_details.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
        rv_take_your_pic_details.addItemDecoration(new SpaceItemDecoration(25));*/
        tv_trending = findViewById(R.id.tv_trending);
        rv_take_your_pic_details = findViewById(R.id.rv_take_your_pic_details);
        rv_take_your_pic_details.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL));
        rv_take_your_pic_details.addItemDecoration(new VerticalSpaceItemDecoration(5));
        rv_take_your_pic_details.setHasFixedSize(true);

        txt_min_kz = findViewById(R.id.txt_min_kz);
        txt_min_time = findViewById(R.id.txt_min_time);
        txt_min_order = findViewById(R.id.txt_min_order);

        radia_relivence = findViewById(R.id.radia_relivence);

        radia_relivence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isChcekRelevemce=true;
                    iscahcekfive=false;
                    isChcekfour=false;
                    isChcekOrder=false;
                    isChcekthree=false;
                    isCheckprice=false;
                    isChcekone=false;
                    isCheckTime=false;
                    isChcektwo=false;
                    minOrdervalue=0;
                    pricevalue=0;
                    ratingvalue=0;
                    timeValue=0;
                    radia_relivence.setChecked(true);
                    radio_price.setChecked(false);

                    radio_rating_fiveStr.setChecked(false);

                    radio_fourStart.setChecked(false);

                    radio_rating_threestar.setChecked(false);
                    radio_rating_twoStr.setChecked(false);
                    radio_oneStart.setChecked(false);
                    radio_delivery_time.setChecked(false);
                    radio_min_order.setChecked(false);
                    radio_rating_fiveStr.setChecked(false);
                } else {
                    isChcekRelevemce=false;
                    radia_relivence.setChecked(false);
                }
            }
        });


        radio_price = findViewById(R.id.radio_price);

        radio_price.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isChcekRelevemce=false;
                    iscahcekfive=false;
                    isChcekfour=false;
                    isChcekOrder=false;
                    isChcekthree=false;
                    isCheckprice=true;
                    isChcekone=false;
                    isCheckTime=false;
                    isChcektwo=false;
                    ll_price.setVisibility(View.VISIBLE);
                    radio_price.setChecked(true);

                    radio_rating_fiveStr.setChecked(false);

                    radio_fourStart.setChecked(false);

                    radio_rating_threestar.setChecked(false);
                    radio_rating_twoStr.setChecked(false);
                    radio_oneStart.setChecked(false);
                    radia_relivence.setChecked(false);
                    radio_delivery_time.setChecked(false);
                    radio_min_order.setChecked(false);
                    radio_rating_fiveStr.setChecked(false);
                } else {
                    isCheckprice=false;
                    ll_price.setVisibility(View.GONE);
                    radio_price.setChecked(false);
                }
            }
        });

        radio_delivery_time = findViewById(R.id.radio_delivery_time);

        radio_delivery_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isChcekRelevemce=false;
                    iscahcekfive=false;
                    isChcekfour=false;
                    isChcekOrder=false;
                    isChcekthree=false;
                    isCheckprice=false;
                    isChcekone=false;
                    isCheckTime=true;
                    isChcektwo=false;
                    ll_delivery_time.setVisibility(View.VISIBLE);
                    radio_delivery_time.setChecked(true);


                    radio_rating_fiveStr.setChecked(false);

                    radio_fourStart.setChecked(false);

                    radio_rating_threestar.setChecked(false);
                    radio_rating_twoStr.setChecked(false);
                    radio_oneStart.setChecked(false);
                    radia_relivence.setChecked(false);
                    radio_price.setChecked(false);
                    radio_min_order.setChecked(false);
                    radio_rating_fiveStr.setChecked(false);
                } else {
                    isCheckTime=false;
                    ll_delivery_time.setVisibility(View.GONE);
                    radio_delivery_time.setChecked(false);
                }
            }
        });

        radio_min_order = findViewById(R.id.radio_min_order);

        radio_min_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isChcekRelevemce=false;
                    iscahcekfive=false;
                    isChcekfour=false;
                    isChcekOrder=true;
                    isChcekthree=false;
                    isCheckprice=false;
                    isChcekone=false;
                    isCheckTime=false;
                    isChcektwo=false;
                    ll_min_order.setVisibility(View.VISIBLE);
                    radio_min_order.setChecked(true);

                    radio_rating_fiveStr.setChecked(false);

                    radio_fourStart.setChecked(false);

                    radio_rating_threestar.setChecked(false);
                    radio_rating_twoStr.setChecked(false);
                    radio_oneStart.setChecked(false);

                    radia_relivence.setChecked(false);
                    radio_price.setChecked(false);
                    radio_delivery_time.setChecked(false);
                    radio_rating_fiveStr.setChecked(false);
                } else {
                    isChcekOrder=false;
                    ll_min_order.setVisibility(View.GONE);
                    radio_min_order.setChecked(false);
                }
            }
        });

        radio_rating_fiveStr = findViewById(R.id.radio_rating_fiveStr);


        radio_rating_fiveStr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isChcekRelevemce=false;
                    iscahcekfive=true;
                    isChcekfour=false;
                    isChcekOrder=false;
                    isChcekthree=false;
                    isCheckprice=false;
                    isChcekone=false;
                    isCheckTime=false;
                    isChcektwo=false;
                    ratingvalue=5;
                    radio_rating_fiveStr.setChecked(true);

                    radio_fourStart.setChecked(false);

                    radio_rating_threestar.setChecked(false);
                    radio_rating_twoStr.setChecked(false);
                    radio_oneStart.setChecked(false);
                    radia_relivence.setChecked(false);
                    radio_price.setChecked(false);
                    radio_delivery_time.setChecked(false);
                    radio_min_order.setChecked(false);
                } else {
                    iscahcekfive=false;
                    ratingvalue=0;
                    radio_rating_fiveStr.setChecked(false);
                }
            }
        });


        radio_fourStart = findViewById(R.id.radio_fourStart);



        radio_fourStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isChcekRelevemce=false;
                    iscahcekfive=false;
                    isChcekfour=true;
                    isChcekOrder=false;
                    isChcekthree=false;
                    isCheckprice=false;
                    isChcekone=false;
                    isCheckTime=false;
                    isChcektwo=false;
                    ratingvalue=5;
                    radio_fourStart.setChecked(true);

                    radio_rating_threestar.setChecked(false);
                    radio_rating_twoStr.setChecked(false);
                    radio_oneStart.setChecked(false);
                    radio_rating_fiveStr.setChecked(false);

                    radia_relivence.setChecked(false);
                    radio_price.setChecked(false);
                    radio_delivery_time.setChecked(false);
                    radio_min_order.setChecked(false);
                } else {
                    ratingvalue=0;
                    isChcekfour=false;
                    radio_fourStart.setChecked(false);
                }
            }
        });

        radio_rating_threestar = findViewById(R.id.radio_rating_threestar);


        radio_rating_threestar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isChcekRelevemce=false;
                    iscahcekfive=false;
                    isChcekfour=false;
                    isChcekOrder=false;
                    isChcekthree=true;
                    isCheckprice=false;
                    isChcekone=false;
                    isCheckTime=false;
                    isChcektwo=false;
                    ratingvalue=3;
                    radio_rating_threestar.setChecked(true);

                    radio_rating_twoStr.setChecked(false);

                    radio_oneStart.setChecked(false);
                    radio_fourStart.setChecked(false);
                    radio_rating_fiveStr.setChecked(false);

                    radia_relivence.setChecked(false);
                    radio_price.setChecked(false);
                    radio_delivery_time.setChecked(false);
                    radio_min_order.setChecked(false);
                } else {
                    ratingvalue=0;
                    isChcekthree=false;
                    radio_rating_threestar.setChecked(false);
                }
            }
        });


        radio_rating_twoStr = findViewById(R.id.radio_rating_twoStr);

        radio_rating_twoStr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isChcekRelevemce=false;
                    iscahcekfive=false;
                    isChcekfour=false;
                    isChcekOrder=false;
                    isChcekthree=false;
                    isCheckprice=false;
                    isChcekone=false;
                    isCheckTime=false;
                    isChcektwo=true;
                    ratingvalue=2;
                    radio_rating_twoStr.setChecked(true);

                    radio_oneStart.setChecked(false);
                    radio_rating_threestar.setChecked(false);
                    radio_fourStart.setChecked(false);
                    radio_rating_fiveStr.setChecked(false);

                    radia_relivence.setChecked(false);
                    radio_price.setChecked(false);
                    radio_delivery_time.setChecked(false);
                    radio_min_order.setChecked(false);
                } else {
                    ratingvalue=0;
                    isChcektwo=false;
                    radio_rating_twoStr.setChecked(false);
                }
            }
        });

        radio_oneStart = findViewById(R.id.radio_oneStart);


        radio_oneStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isChcekRelevemce=false;
                    iscahcekfive=false;
                    isChcekfour=false;
                    isChcekOrder=false;
                    isChcekthree=false;
                    isCheckprice=false;
                    isChcekone=true;
                    isCheckTime=false;
                    isChcektwo=false;
                    ratingvalue=1;
                    radio_oneStart.setChecked(true);

                    radio_rating_twoStr.setChecked(false);

                    radio_rating_threestar.setChecked(false);
                    radio_fourStart.setChecked(false);
                    radio_rating_fiveStr.setChecked(false);

                    radia_relivence.setChecked(false);
                    radio_price.setChecked(false);
                    radio_delivery_time.setChecked(false);
                    radio_min_order.setChecked(false);
                } else {
                    ratingvalue=1;
                    isChcekone=false;
                    radio_oneStart.setChecked(false);
                }
            }
        });


        ll_price = findViewById(R.id.ll_price);
        ll_delivery_time = findViewById(R.id.ll_delivery_time);
        ll_min_order = findViewById(R.id.ll_min_order);


        seekBar_price = findViewById(R.id.seekBar_price);
        seekBar_price.setMax(1000);

        seekBar_price.setProgress(calculateProgress(currentValue, MIN, MAX, STEP));

        seekBar_price.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double value = Math.round((progress * (MAX - MIN)) / 1000);
                int displayValue = (((int) value + MIN) / STEP) * STEP;

                pricevalue=displayValue;
                txt_min_kz.setText("Rs"+" "+String.valueOf(displayValue));
            }
        });


        seekBar_deliveryTime = findViewById(R.id.seekBar_deliveryTime);

        seekBar_deliveryTime.setMax( (max - min) / step );


        seekBar_deliveryTime.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser)
                    {
                        // Ex :
                        // And finally when you want to retrieve the value in the range you
                        // wanted in the first place -> [3-5]
                        //
                        // if progress = 13 -> value = 3 + (13 * 0.1) = 4.3
                        double value = min + (progress * step);


                        timeValue = (int)value;
                        txt_min_time.setText("Min"+" "+String.valueOf(timeValue));

                    }
                }
        );



        seekBar_minOrder = findViewById(R.id.seekBar_minOrder);

        seekBar_minOrder.setMax(1000);

        seekBar_minOrder.setProgress(calculateProgress(currentValue, MIN, MAX, STEP));

        seekBar_minOrder.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double value = Math.round((progress * (MAX - MIN)) / 1000);
                int displayValue = (((int) value + MIN) / STEP) * STEP;
                minOrdervalue=displayValue;
                txt_min_order.setText("Kz"+" "+String.valueOf(displayValue));
            }
        });


        tv_filter = findViewById(R.id.tv_filter);
        tv_filter.setOnClickListener(v -> {
            slideUp.show();

            //startActivity(new Intent(this,FilterActivity.class));
        });

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(v -> {
            finish();
        });

        pager = findViewById(R.id.pager);
        rootView = findViewById(R.id.root_view);
        slideView = findViewById(R.id.slideView);
        dim = findViewById(R.id.dim);

        //slideUp.hideImmediately();
        slideUp = new SlideUpBuilder(slideView)
                .withListeners(new SlideUp.Listener.Events() {
                    @Override
                    public void onSlide(float percent) {
                        dim.setAlpha(1 - (percent / 100));
                        if (percent < 100) {
                            // slideUp started showing

                        }
                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {
                        if (visibility == View.GONE) {
                        }
                    }
                })
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withStartState(SlideUp.State.HIDDEN)
                .withSlideFromOtherView(rootView)
                .build();


        btn_apply=findViewById(R.id.btn_apply);
        btn_apply.setOnClickListener(v -> {
            Log.d("value",minOrdervalue+"/"+pricevalue+"/"+timeValue+"/"+ratingvalue+"/");

            if (iscahcekfive==false && isChcekfour==false&&isChcekthree==false&&isChcektwo==false&&isChcekone==false&&isChcekOrder==false&&isChcekRelevemce==false&&isCheckprice==false&&isCheckTime==false) {
                Toaster.customToast("Please select at least one item!");
            }else{
                geTakeyourPicDetails();
            }


        });


    }



    private int calculateProgress(int value, int MIN, int MAX, int STEP) {
        return (100 * (value - MIN)) / (MAX - MIN);
    }


    private void geTakeyourPicDetails() {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kfoodTypeId, resturantsTypeId);
        map.put(kLat, latitude);
        map.put(kLon, longitude);
        map.put(krating, ratingvalue);
        map.put(kdeliveryTime, timeValue);
        map.put(kPrice, pricevalue);
        map.put(kMinimumOrder, minOrdervalue);
        Log.e("Send Request", map.toString());
        ModelManager.modelManager().getTakeYourPicDetails(map,
                (Constants.Status iStatus, GenericResponse<TakeYourPicModel> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        TakeYourPicModel object = genericResponse.getObject();
                        resturantsImagepath = object.getRestaurantspathImage();
                        mData = object.getPastDataModels();


                        if (mData.isEmpty()) {

                        } else {


                            for (int i = 0; i < mData.size(); i++) {
                                imageList.add(mData.get(i).getLogoImage());
                            }


                            tv_trending.setText(String.valueOf(mData.size() + " " + "Restaurants"));
                            restaurantHomeTakeYourPicDetails = new RestautansHomeTakeYourPicDetailsAdapter(context, mData, resturantsImagepath, new RestautansHomeTakeYourPicDetailsAdapter.onItemClickListener() {
                                @Override
                                public void onrestaurantsClick(int businessid) {

                                    Intent intent = new Intent(context, InTheSportLightDetailsActivity.class);
                                    intent.putExtra(kBusinessId, businessid);
                                    startActivity(intent);
                                    //finish();

                                }
                            });
                            rv_take_your_pic_details.setAdapter(restaurantHomeTakeYourPicDetails);


                            CustomAdapter adapter = new CustomAdapter(context, resturantsImagepath, imageList);

                            pager.setAdapter(adapter);



                            /*After setting the adapter use the timer */
                            final Handler handler = new Handler();
                            final Runnable Update = new Runnable() {
                                public void run() {
                                    if (currentPage == imageList.size()) {
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

                        slideUp.hide();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }


    public class CustomAdapter extends PagerAdapter {

        private Context activity;
        private ArrayList<String> imagesArray;
        String imagepath;

        public CustomAdapter(Context activity, String imagepath, ArrayList<String> imagesArray) {

            this.activity = activity;
            this.imagesArray = imagesArray;
            this.imagepath = imagepath;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            LayoutInflater inflater = ((Activity) activity).getLayoutInflater();

            View viewItem = inflater.inflate(R.layout.image_item, container, false);
            ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView);

            if (!imagesArray.get(position).isEmpty()) {
                String imgPath = imagepath + "/" + imagesArray.get(position);
                Picasso.with(context).load(imgPath).placeholder(R.drawable.ic_image_black_24dp).fit().into(imageView);
            } else {
                Picasso.with(context).load(R.drawable.ic_image_black_24dp).placeholder(R.drawable.ic_image_black_24dp).fit().into(imageView);
            }

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
