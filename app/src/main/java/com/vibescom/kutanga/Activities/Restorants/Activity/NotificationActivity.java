package com.vibescom.kutanga.Activities.Restorants.Activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vibescom.kutanga.Activities.Restorants.Adapter.NotificationsAdapter;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.NotificationModel.NotificationData;
import com.vibescom.kutanga.Models.NotificationModel.NotificationModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.Toaster;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.vibescom.kutanga.Constants.Constants.kNotifications;
import static com.vibescom.kutanga.Constants.Constants.kReadCount;
import static com.vibescom.kutanga.Constants.Constants.kUnReadCount;
import static com.vibescom.kutanga.Constants.Constants.kpage;

public class NotificationActivity extends AppCompatActivity {
    Context mContext;
    RecyclerView rv_reviews;
    NotificationsAdapter restautansMyOrderdapter;
    RelativeLayout rv_cart;
    ImageView img_back;
    private int page;
    private int pageSize;
    CustomLoaderView loaderView;
    private boolean loading = true;
    int readCount, unReadCount, total;
    CopyOnWriteArrayList<NotificationData> notifactiondata;
    private LinearLayoutManager mLayoutManager;
    TextView tv_total, tv_active, tv_Inactive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        mContext = this;
        loaderView = CustomLoaderView.initialize(mContext);
        inItView();
        setRecyclerView();
        initData(1);
    }

    private void inItView() {
        rv_reviews = findViewById(R.id.rv_reviews);
        rv_cart = findViewById(R.id.rv_cart);
        img_back = findViewById(R.id.img_back);

        tv_total = findViewById(R.id.tv_total);
        tv_active = findViewById(R.id.tv_active);
        tv_Inactive = findViewById(R.id.tv_Inactive);
        img_back.setOnClickListener(v -> {
            finish();
        });
    }


    private void setRecyclerView() {
        mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        rv_reviews.setLayoutManager(mLayoutManager);
        rv_reviews.setHasFixedSize(true);
        restautansMyOrderdapter = new NotificationsAdapter(mContext, new CopyOnWriteArrayList<>());
        rv_reviews.setAdapter(restautansMyOrderdapter);
        rv_reviews.addOnScrollListener(onScrollListener);
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
            loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kpage, page);

        Log.d("Request", map.toString());
        ModelManager.modelManager().getNotification(map,
                (Constants.Status iStatus, GenericResponse<JSONObject> genericResponse) -> {
                    loaderView.hideLoader();
                    try {


                        JSONObject jsonObject = genericResponse.getObject().getJSONObject(kNotifications);
                        readCount = genericResponse.getObject().getInt(kReadCount);

                        tv_active.setText(String.valueOf(readCount));
                        unReadCount = genericResponse.getObject().getInt(kUnReadCount);
                        tv_Inactive.setText(String.valueOf(unReadCount));
                        NotificationModel notificationModel = new NotificationModel(jsonObject);
                        total=notificationModel.getTotal();
                        tv_total.setText(String.valueOf(total));
                        notifactiondata = notificationModel.getPastDataModels();

                        if (notifactiondata.size() == 0) {
                            rv_cart.setVisibility(View.VISIBLE);
                            rv_reviews.setVisibility(View.GONE);
                        } else {
                            rv_cart.setVisibility(View.GONE);
                            rv_reviews.setVisibility(View.VISIBLE);
                            if (page != 1) {
                                restautansMyOrderdapter.addData(notifactiondata);
                                loading = !notificationModel.getPastDataModels().isEmpty();
                            } else {
                                //bookingAdapter=new BookingAdapter(context,bookings);
                                restautansMyOrderdapter.newData(notifactiondata);
                                pageSize = notificationModel.getPastDataModels().size();
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
}
