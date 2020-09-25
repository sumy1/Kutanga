package com.vibescom.kutanga.Activities.Restorants.Activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mancj.slideup.SlideUp;
import com.mancj.slideup.SlideUpBuilder;
import com.vibescom.kutanga.Activities.Restorants.Adapter.ItemCountAdapter;
import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansMyOrderdapter;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.ItemCountModel;
import com.vibescom.kutanga.Models.PastOrderModle.PastOrderData;
import com.vibescom.kutanga.Models.PastOrderModle.PastOrderModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.SpaceItemDecoration;
import com.vibescom.kutanga.Utils.Toaster;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.vibescom.kutanga.Constants.Constants.kMessage;
import static com.vibescom.kutanga.Constants.Constants.kOrderCode;
import static com.vibescom.kutanga.Constants.Constants.kpage;

public class PastOrderActivity extends AppCompatActivity {
    Context mContext;
    RecyclerView rv_myorder,rv_itemcount;
    RelativeLayout rv_cart;
    RestautansMyOrderdapter restautansMyOrderdapter;
    ItemCountAdapter itemCountAdapter;
    ImageView img_back;
    private ShimmerFrameLayout mShimmerViewContainer;
    private int page;
    private int pageSize;
    CustomLoaderView loaderView;
    private boolean loading = true;
    private SlideUp slideUp;
    private View dim, rootView;
    private View slideView;
    CopyOnWriteArrayList<PastOrderData> pastOrderData;
    private LinearLayoutManager mLayoutManager,mLayoutManagerr;
    PastOrderData  pastOrderDataa;
    CopyOnWriteArrayList<ItemCountModel> productPriceslist;
    String productImgPath;
    TextView txt_restaurants_name,tv_orderNo, txt_restaurants_address, txt_deliver_name, txt_deliver_address, txt_order_status, txt_order_date, txt_item_count, txt_item_name, txt_item_name_count, txt_item_weight, txt_sub_total_weight, txt_food_tax_amt, txt_restaurants_charges, txt_delivery_charges, txt_order_total_amt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_order);
        mContext = this;
        loaderView = CustomLoaderView.initialize(mContext);

        inItView();
        setRecyclerView();
        initData(1);

    }

    private void inItView() {
        rv_myorder = findViewById(R.id.rv_myorder);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        rv_cart = findViewById(R.id.rv_cart);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(v -> {
            finish();
        });


        rootView = findViewById(R.id.root_view);
        slideView = findViewById(R.id.slideView);
        dim = findViewById(R.id.dim);


        txt_restaurants_name = findViewById(R.id.txt_restaurants_name);
        txt_restaurants_address = findViewById(R.id.txt_restaurants_address);
        txt_deliver_name = findViewById(R.id.txt_deliver_name);

        txt_deliver_address = findViewById(R.id.txt_deliver_address);

        txt_order_status = findViewById(R.id.txt_order_statuss);

        txt_order_date = findViewById(R.id.txt_order_date);

        txt_item_count = findViewById(R.id.txt_item_count);

        txt_item_name = findViewById(R.id.txt_item_name);

        txt_item_name_count = findViewById(R.id.txt_item_name_count);

        txt_item_weight = findViewById(R.id.txt_item_weight);
        txt_sub_total_weight = findViewById(R.id.txt_sub_total_weight);
        tv_orderNo=findViewById(R.id.tv_orderNo);
        txt_food_tax_amt = findViewById(R.id.txt_food_tax_amt);
        mLayoutManagerr = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        rv_itemcount=findViewById(R.id.rv_itemcount);
        rv_itemcount.setLayoutManager(mLayoutManagerr);
        rv_itemcount.addItemDecoration(new SpaceItemDecoration(5));
        rv_itemcount.setHasFixedSize(true);

        txt_restaurants_charges = findViewById(R.id.txt_restaurants_charges);

        txt_delivery_charges = findViewById(R.id.txt_delivery_charges);

        txt_order_total_amt = findViewById(R.id.txt_order_total_amt);



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

    }


    private void setRecyclerView() {
        mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        rv_myorder.setLayoutManager(mLayoutManager);
        rv_myorder.addItemDecoration(new SpaceItemDecoration(25));
        rv_myorder.setHasFixedSize(true);
        restautansMyOrderdapter = new RestautansMyOrderdapter(mContext ,new CopyOnWriteArrayList<>(), new RestautansMyOrderdapter.onItemClickListener() {
            @Override
            public void onViewDetails(String orderno,int subtotal,PastOrderData pastOrderData1,String vendorname,String vendorAddress,String itemQuantityType,String itemquantity,String itemPrice,String itemName,CopyOnWriteArrayList<ItemCountModel> productPriceslist) {
                pastOrderDataa=pastOrderData1;

                Log.d("total",subtotal+"");
                txt_deliver_name.setText(pastOrderDataa.getUserName());
                txt_deliver_address.setText(pastOrderDataa.getUserAddress());
                txt_restaurants_name.setText(vendorname);
                txt_restaurants_address.setText(vendorAddress);
                txt_order_status.setText(pastOrderDataa.getOrderStatus()+" "+"on");
                txt_order_date.setText(convertUTCDateToLocalDate(pastOrderDataa.getTransitionOn()));
                txt_sub_total_weight.setText(subtotal+" "+getResources().getString(R.string.kg));
                itemCountAdapter=new ItemCountAdapter(mContext,productPriceslist);
                rv_itemcount.setAdapter(itemCountAdapter);

                if(productPriceslist.size()==1){
                    txt_item_count.setText(productPriceslist.size()+" "+mContext.getResources().getString(R.string.item));
                }else if(productPriceslist.size()>1){
                    txt_item_count.setText(productPriceslist.size()+" "+mContext.getResources().getString(R.string.itemm));
                }else{
                    txt_item_count.setText(productPriceslist.size()+" "+mContext.getResources().getString(R.string.item));
                }


                tv_orderNo.setText(getResources().getString(R.string.order)+" "+orderno);

                txt_food_tax_amt.setText(pastOrderDataa.getFoodItemtax()+" "+"%");
                txt_restaurants_charges.setText("+"+""+pastOrderDataa.getRetaurantsCharge()+" "+getResources().getString(R.string.kg));
                txt_delivery_charges.setText("+"+""+pastOrderDataa.getDeliverYCharge()+" "+getResources().getString(R.string.kg));
                txt_order_total_amt.setText(pastOrderDataa.getOrderPrice()+" "+getResources().getString(R.string.kg));
                slideUp.show();
            }

            @Override
            public void onCnacelOrder(String orderId) {
                getCancelOrdre(orderId);
            }

        });
        rv_myorder.setAdapter(restautansMyOrderdapter);
        rv_myorder.addOnScrollListener(onScrollListener);
    }

    public String convertUTCDateToLocalDate(String date_string) {
        if (date_string.isEmpty()){
            return "";
        }

        SimpleDateFormat oldFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //oldFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        String dueDateAsNormal ="";
        try {
            value = oldFormatter.parse(date_string);
            SimpleDateFormat newFormatter = new SimpleDateFormat("dd MMM yyyy (hh:mm a)");

           /* Date date = new SimpleDateFormat("yyyy-M-d").parse(date_string);
            String dayOfWeek = new SimpleDateFormat("EEE", Locale.ENGLISH).format(newFormatter);

            Log.d("day",dayOfWeek);*/
            newFormatter.setTimeZone(TimeZone.getDefault());
            dueDateAsNormal = newFormatter.format(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("date",dueDateAsNormal);

        return dueDateAsNormal;
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            if (dy > 0) //check for scroll down
            {
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= pageSize) {
                        loading = false;
                        ++page;
                        initData(page);
                    }
                }
            }
        }
    };

    private void showShimmerView() {
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        rv_cart.setVisibility(View.GONE);
        mShimmerViewContainer.startShimmerAnimation();
        rv_myorder.setVisibility(View.GONE);
    }

    private void hideShimmerView() {
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
        rv_myorder.setVisibility(View.VISIBLE);
    }

    private void checkEmptyView() {
        if (restautansMyOrderdapter.getItemCount() == 0)
            rv_cart.setVisibility(View.VISIBLE);
        else
            rv_cart.setVisibility(View.GONE);
    }

    public void initData(int pg) {
        try {
            page = pg;
            getfavrouteList(page);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getfavrouteList(int page) {
        if (page == 1)
            showShimmerView();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kpage, page);

        Log.d("Request", map.toString());
        ModelManager.modelManager().getpastOrder(map,
                (Constants.Status iStatus, GenericResponse<PastOrderModel> genericResponse) -> {
                    hideShimmerView();
                    try {
                        PastOrderModel favrouteModel = genericResponse.getObject();

                        pastOrderData = favrouteModel.getPastDataModels();
                        productImgPath=favrouteModel.getRes_img_path();
                        Log.d("data",productImgPath+"");

                        if (page != 1) {
                            restautansMyOrderdapter.addData(pastOrderData,productImgPath);
                            loading = !genericResponse.getObject().getPastDataModels().isEmpty();
                        } else {
                            //bookingAdapter=new BookingAdapter(context,bookings);
                            restautansMyOrderdapter.newData(pastOrderData,productImgPath);
                            pageSize = genericResponse.getObject().getPastDataModels().size();
                        }


                        /*if(pastOrderData.size()==0){
                            rv_cart.setVisibility(View.VISIBLE);
                            rv_myorder.setVisibility(View.GONE);
                        }else {
                            rv_cart.setVisibility(View.GONE);
                            rv_myorder.setVisibility(View.VISIBLE);
                            if(page!=1){
                                restautansMyOrderdapter.addData(pastOrderData);
                                loading = !genericResponse.getObject().getPastDataModels().isEmpty();
                            }
                            else{
                                //bookingAdapter=new BookingAdapter(context,bookings);
                                restautansMyOrderdapter.newData(pastOrderData);
                                pageSize = genericResponse.getObject().getPastDataModels().size();
                            }
                        }*/


                        checkEmptyView();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    hideShimmerView();
                    Toaster.customToast(message);
                });
    }

    private void getCancelOrdre(String  orderId) {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kOrderCode, orderId);
        Log.e("Send param", map.toString());
        ModelManager.modelManager().getCancelOrder(map,
                (Constants.Status iStatus, GenericResponse<JSONObject> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        String msg = genericResponse.getObject().getString(kMessage);
                        Toaster.customToast(msg);
                        startActivity(getIntent());
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });

    }
}
