package com.vibescom.kutanga.Activities.Restorants.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import static com.vibescom.kutanga.Constants.Constants.kCoupon;
import static com.vibescom.kutanga.Constants.Constants.kMessage;


public class PaymentsOfferCoupan extends Fragment {

    private Context context;
    private RecyclerView rv_coupan;
    RestautansCoupanAdapter restautansOfferAdapter;
    CopyOnWriteArrayList<OfferData> offerData;
    String ImagePath;
    CustomLoaderView loaderView;
    TextView txt_apply;
    EditText et_coupon;
    public PaymentsOfferCoupan() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_payments_offer_coupan, container, false);
        context = getActivity();

        loaderView = CustomLoaderView.initialize(context);

        getCouponAvailable();
        initView(rootView);
        // Inflate the layout for this fragment
        return rootView;
    }

    private void initView(View rootView) {
        rv_coupan=rootView.findViewById(R.id.rv_coupan);

        rv_coupan.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL ));
        rv_coupan.addItemDecoration(new VerticalSpaceItemDecoration(10));
        rv_coupan.setHasFixedSize(true);

        et_coupon=rootView.findViewById(R.id.et_coupon);
        txt_apply=rootView.findViewById(R.id.txt_apply);

        txt_apply.setOnClickListener(v -> {

            if(validatead()){
                getApplayCoupon();
            }
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
        HashMap<String, Object> map = new HashMap<>();
        map.put(kCoupon, "32fdsf");

        ModelManager.modelManager().getOfferList(map,
                (Constants.Status iStatus, GenericResponse<OfferListModel> genericResponse) -> {

                    try {
                        OfferListModel offerListModel = genericResponse.getObject();
                        ImagePath=offerListModel.getImagePath();
                        offerData = offerListModel.getOfferData();


                        if(!offerData.isEmpty()){
                            restautansOfferAdapter=new RestautansCoupanAdapter(context,offerData,ImagePath);
                            rv_coupan.setAdapter(restautansOfferAdapter);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {

                    Toaster.customToast(message);
                });


    }


}
