package com.vibescom.kutanga.Activities.Restorants.Activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansCoupanAdapter;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.OfferData;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.OfferListModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.Toaster;
import com.vibescom.kutanga.Utils.VerticalSpaceItemDecoration;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import static com.vibescom.kutanga.Constants.Constants.kCoupon;
import static com.vibescom.kutanga.Constants.Constants.kMessage;

public class ApplyCoupanActivity extends AppCompatActivity {

    private static final String TAG = ApplyCoupanActivity.class.getSimpleName();
    Context mContext;
    RecyclerView rv_apply_coupan;
    RestautansCoupanAdapter restautansMyOrderdapter;
    ImageView img_back;
    TextView txt_apply;
    EditText et_coupon;
    CopyOnWriteArrayList<OfferData> offerData;
    String ImagePath;
    CustomLoaderView loaderView;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_coupan);
        mContext=this;
        loaderView = CustomLoaderView.initialize(mContext);

        getCouponAvailable();
        inItView();
    }

    private void inItView() {
        rv_apply_coupan=findViewById(R.id.rv_apply_coupan);
        rv_apply_coupan.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL ));
        rv_apply_coupan.addItemDecoration(new VerticalSpaceItemDecoration(10));
        rv_apply_coupan.setHasFixedSize(true);


        et_coupon=findViewById(R.id.et_coupon);
        txt_apply=findViewById(R.id.txt_apply);

        txt_apply.setOnClickListener(v -> {

            if(validatead()){
                getApplayCoupon();
            }
        });


        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(v->{
            finish();
        });


    }

    private boolean validatead() {
        boolean isOk = true;

        if (et_coupon.getText().toString().isEmpty()) {
            Toaster.customToast("Please enter coupon code!");
            isOk = false;
        }

        return isOk;
    }


    private void getApplayCoupon(){
        loaderView.showLoader();
        HashMap<String,Object> map=new HashMap<>();
        map.put(kCoupon,et_coupon.getText().toString());
        Log.e(TAG, map.toString());
        ModelManager.modelManager().getApplayCoupon(map,
                (Constants.Status iStatus, GenericResponse<JSONObject> genericResponse) -> {
                    loaderView.hideLoader();
                    try {

                        String msg=genericResponse.getObject().getString(kMessage);
                        Log.d("Message",msg);
                        Toaster.customToast(msg);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });

    }
    private void getCouponAvailable() {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kCoupon, "32fdsf");
        ModelManager.modelManager().getOfferList(map,
                (Constants.Status iStatus, GenericResponse<OfferListModel> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        OfferListModel offerListModel = genericResponse.getObject();
                        ImagePath=offerListModel.getImagePath();
                        offerData = offerListModel.getOfferData();


                        if(!offerData.isEmpty()){
                            restautansMyOrderdapter=new RestautansCoupanAdapter(mContext,offerData,ImagePath);
                            rv_apply_coupan.setAdapter(restautansMyOrderdapter);
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
