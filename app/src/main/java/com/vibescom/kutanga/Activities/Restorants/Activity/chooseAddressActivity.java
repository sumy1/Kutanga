package com.vibescom.kutanga.Activities.Restorants.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vibescom.kutanga.Activities.LocationActivity;
import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansManageAddresssetDefaultAdapter;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.ManageAddressModel.AddressDataModel;
import com.vibescom.kutanga.Models.ManageAddressModel.AddressModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.Toaster;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.vibescom.kutanga.Constants.Constants.kAddressId;
import static com.vibescom.kutanga.Constants.Constants.kCoupon;
import static com.vibescom.kutanga.Constants.Constants.kLandMark;
import static com.vibescom.kutanga.Constants.Constants.kMessage;
import static com.vibescom.kutanga.Constants.Constants.kaddressType;
import static com.vibescom.kutanga.Constants.Constants.kpage;
import static com.vibescom.kutanga.Constants.Constants.ksubArea;

public class chooseAddressActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv_address;
    private LinearLayoutManager mLayoutManager;
    RestautansManageAddresssetDefaultAdapter restautansManageAddressAdapter;
    private boolean loading = true;
    private int page;
    private int pageSize;
    CustomLoaderView loaderView;
    ImageView img_back;
    Context context;
    RelativeLayout rel_deliver_to_this_address, rel_edit_address,rl_radio, rel_add_new_address;;
    TextView tv_delivery,tv_address;
    RadioButton radio_select_recently;
    LinearLayout ll_default;
    public boolean checkedStatus;
    int addressId;
    private CopyOnWriteArrayList<AddressDataModel> addressModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);


        context = this;
        loaderView = CustomLoaderView.initialize(context);
        inItView();
        setRecyclerView();
        initData(1);
    }

    private void inItView() {
        rv_address = findViewById(R.id.rv_address);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        rl_radio=findViewById(R.id.rl_radio);
        rel_deliver_to_this_address = findViewById(R.id.rel_deliver_to_this_address);
        rel_deliver_to_this_address.setOnClickListener(this);
        rel_edit_address = findViewById(R.id.rel_edit_address);
        rel_edit_address.setOnClickListener(this);
        ll_default=findViewById(R.id.ll_default);
        tv_delivery=findViewById(R.id.tv_delivery);
        tv_address=findViewById(R.id.tv_address);

        radio_select_recently=findViewById(R.id.radio_select_recently);


        rl_radio.setOnClickListener(v -> {

            rel_deliver_to_this_address.setVisibility(View.VISIBLE);
            rel_edit_address.setVisibility(View.VISIBLE);
            rel_deliver_to_this_address.setBackgroundColor(getResources().getColor(R.color.orange));
            radio_select_recently.setChecked(true);
            restautansManageAddressAdapter.itemChcekedStatus();
            restautansManageAddressAdapter.notifyDataSetChanged();
        });

        radio_select_recently.setOnClickListener(v -> {
            rel_deliver_to_this_address.setVisibility(View.VISIBLE);
            rel_edit_address.setVisibility(View.VISIBLE);
            rel_deliver_to_this_address.setBackgroundColor(getResources().getColor(R.color.orange));
            radio_select_recently.setChecked(true);
            restautansManageAddressAdapter.itemChcekedStatus();
            restautansManageAddressAdapter.notifyDataSetChanged();
        });


        /*radio_select_recently.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkedStatus=isChecked;
                    rel_deliver_to_this_address.setVisibility(View.VISIBLE);
                    rel_edit_address.setVisibility(View.VISIBLE);
                    rel_deliver_to_this_address.setBackgroundColor(getResources().getColor(R.color.orange));
                    radio_select_recently.setChecked(true);
                    restautansManageAddressAdapter.itemChcekedStatus();
                    restautansManageAddressAdapter.notifyDataSetChanged();
                }else{
                    checkedStatus=isChecked;
                    rel_deliver_to_this_address.setVisibility(View.GONE);
                    rel_edit_address.setVisibility(View.GONE);
                    restautansManageAddressAdapter.statusChecked=checkedStatus;
                    rel_deliver_to_this_address.setBackgroundColor(getResources().getColor(R.color.grey));
                    radio_select_recently.setChecked(false);
                    restautansManageAddressAdapter.notifyDataSetChanged();
                }
            }
        });*/


        rel_add_new_address = findViewById(R.id.rel_add_new_address);
        rel_add_new_address.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.rel_add_new_address:
                Intent intent = new Intent(context, LocationActivity.class);
                intent.putExtra("From","3");
                startActivity(intent);
                finish();
                break;
        }
    }


    private void setRecyclerView() {
        mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        rv_address.setLayoutManager(mLayoutManager);
        rv_address.setHasFixedSize(true);
        restautansManageAddressAdapter = new RestautansManageAddresssetDefaultAdapter(context, checkedStatus,new CopyOnWriteArrayList<>(), new RestautansManageAddresssetDefaultAdapter.onItemClickListener() {
            @Override
            public void itemchceked(boolean statusChecked) {
                if(statusChecked){
                    rel_deliver_to_this_address.setVisibility(View.GONE);
                    rel_edit_address.setVisibility(View.GONE);
                    radio_select_recently.setChecked(false);
                    checkedStatus=false;
                }


            }

            @Override
            public void ondelete(int addressId, int pos) {

            }

            @Override
            public void OnDeliver(int addressId) {
                getAddAddressPlaceOrder(addressId);
            }
        }) ;

        rv_address.setAdapter(restautansManageAddressAdapter);
        rv_address.addOnScrollListener(onScrollListener);

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
            getManageAddress(page);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getManageAddress(int page) {
        if (page == 1)
            loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kpage, page);
        Log.d("Request", map.toString());
        ModelManager.modelManager().getAddressmanage(map,
                (Constants.Status iStatus, GenericResponse<AddressModel> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        AddressModel favrouteModel = genericResponse.getObject();

                        addressModelList = favrouteModel.getAddressDataModelList();

                        Log.d("favSize", addressModelList.size() + "");

                        if(addressModelList.size()==1){
                            rl_radio.setVisibility(View.GONE);
                        }else{
                            rl_radio.setVisibility(View.VISIBLE);
                            for(int i=0;i<addressModelList.size();i++){
                                if(addressModelList.get(i).getIsDefault().equalsIgnoreCase("1")){
                                    ll_default.setVisibility(View.VISIBLE);
                                    radio_select_recently.setChecked(true);
                                    tv_address.setText(addressModelList.get(i).getFormattedaddress());
                                    rel_deliver_to_this_address.setBackgroundColor(getResources().getColor(R.color.orange));
                                    int finalI = i;
                                    rel_edit_address.setOnClickListener(v -> {
                                        Intent intent=new Intent(context, LocationActivity.class);
                                        intent.putExtra("From","1");
                                        intent.putExtra("lat",addressModelList.get(finalI).getLat());
                                        intent.putExtra("Lag",addressModelList.get(finalI).getLng());
                                        intent.putExtra(Constants.kAddress,addressModelList.get(finalI).getFullAddress());
                                        intent.putExtra(Constants.kCity,addressModelList.get(finalI).getCity());
                                        intent.putExtra(ksubArea,addressModelList.get(finalI).getArea());
                                        intent.putExtra(kLandMark,addressModelList.get(finalI).getLandmark());
                                        intent.putExtra(kaddressType,addressModelList.get(finalI).getAddressType());
                                        context.startActivity(intent);
                                    });
                                    rel_deliver_to_this_address.setOnClickListener(v -> {
                                        try{
                                            getAddAddressPlaceOrder(addressModelList.get(finalI).getUserAddressId());
                                        }catch(Exception e){
                                            e.printStackTrace();
                                        }

                                    });
                                    addressModelList.remove(i);
                                    break;
                                }else{


                                    ll_default.setVisibility(View.GONE);
                                }

                            }
                        }


                        if (page != 1) {
                            restautansManageAddressAdapter.addData(addressModelList);
                            loading = !genericResponse.getObject().getAddressDataModelList().isEmpty();
                        } else {
                            //bookingAdapter=new BookingAdapter(context,bookings);
                            restautansManageAddressAdapter.newData(addressModelList);
                            pageSize = genericResponse.getObject().getAddressDataModelList().size();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    ll_default.setVisibility(View.GONE);
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }


    private void getAddAddressPlaceOrder(int AddressId) {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kAddressId, AddressId);

        Log.d("Request", map.toString());
        ModelManager.modelManager().addPlaceorderAddress(map,
                (Constants.Status iStatus, GenericResponse<JSONObject> genericResponse) -> {
                    loaderView.hideLoader();
                    try {

                        JSONObject obj = genericResponse.getObject();

                        String msg = obj.getString(kMessage);

                        //Toaster.customToast(msg);
                        getPlaceOrder();


                        //JSONObject jsonObject = obj.getJSONObject(kResponse);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
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
                        menuDialogg(msg);
                        //showOderDeails();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });

    }


    private void menuDialogg(String message) {
        // dialog

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_order_placed);
        dialog.setCancelable(false);

        TextView tv_order_placed=dialog.findViewById(R.id.tv_order_placed);
        tv_order_placed.setText(message);

        Button btn_gotoorder=dialog.findViewById(R.id.btn_gotoorder);

        btn_gotoorder.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent=new Intent(context,PastOrderActivity.class);
            startActivity(intent);

        });




        dialog.show();
    }


}
