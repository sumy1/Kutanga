package com.vibescom.kutanga.Activities.Restorants.Activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vibescom.kutanga.Activities.Restorants.Adapter.TimeLineAdapter;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.Toaster;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import static com.vibescom.kutanga.Constants.Constants.kCoupon;
import static com.vibescom.kutanga.Constants.Constants.kMessage;

public class PaymentActivity extends AppCompatActivity {
    Context mContext;
    ImageView img_back;
    LinearLayout pay_layout;
    Dialog dialog;
    TimeLineAdapter timeLineAdapter;
    RecyclerView recyclerView;
    RelativeLayout rel_proceed_checkout;
    TextView tv_pay;
    CustomLoaderView loaderView;
    int totalPrice;
    RadioButton radio_multicasia, radio_paypal, radio_credit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mContext = this;
        loaderView = CustomLoaderView.initialize(mContext);
        totalPrice = getIntent().getIntExtra("AMOUNT", 0);
        inItView();
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        rel_proceed_checkout = findViewById(R.id.rel_proceed_checkout);
        rel_proceed_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPlaceOrder();
            }
        });

        tv_pay = findViewById(R.id.tv_pay);
        tv_pay.setText("PAY " + totalPrice + " " + "Kz");
    }


    private void inItView() {
        radio_multicasia = findViewById(R.id.radio_multicasia);

        radio_paypal = findViewById(R.id.radio_paypal);
        radio_credit = findViewById(R.id.radio_credit);

        radio_multicasia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    radio_multicasia.setChecked(true);
                    radio_paypal.setChecked(false);
                    radio_credit.setChecked(false);
                }else{
                    radio_multicasia.setChecked(false);
                }
            }
        });


        radio_paypal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    radio_paypal.setChecked(true);
                    radio_multicasia.setChecked(false);
                    radio_credit.setChecked(false);
                }else{
                    radio_paypal.setChecked(false);
                }
            }
        });


        radio_credit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    radio_credit.setChecked(true);
                    radio_paypal.setChecked(false);
                    radio_multicasia.setChecked(false);
                }else{
                    radio_credit.setChecked(false);
                }
            }
        });




    }

    private void showOderDeails() {
        // dialog
        dialog = new Dialog(this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.order_deails);
        timeLineAdapter = new TimeLineAdapter(mContext);
        recyclerView = dialog.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(timeLineAdapter);
        dialog.findViewById(R.id.img_back).setOnClickListener(v -> dialog.dismiss());
        dialog.findViewById(R.id.img_cancel).setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }


    private void getPlaceOrder() {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kCoupon, "");
        ModelManager.modelManager().getPlaceOrder(map,
                (Constants.Status iStatus, GenericResponse<JSONObject> genericResponse) -> {
                    loaderView.hideLoader();
                    try {

                        String msg = genericResponse.getObject().getString(kMessage);
                        Log.d("Message", msg);
                        Toaster.customToast(msg);
                        //showOderDeails();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });

    }
}
