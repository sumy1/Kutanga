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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.vibescom.kutanga.Activities.LocationActivity;
import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansManageAddressAdapter;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.ManageAddressModel.AddressDataModel;
import com.vibescom.kutanga.Models.ManageAddressModel.AddressModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.SpaceItemDecoration;
import com.vibescom.kutanga.Utils.Toaster;
import com.vibescom.kutanga.Utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.vibescom.kutanga.Constants.Constants.kAddressId;
import static com.vibescom.kutanga.Constants.Constants.kMessage;
import static com.vibescom.kutanga.Constants.Constants.kpage;

public class ManageaddressActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    RestautansManageAddressAdapter restautansManageAddressAdapter;
    private RecyclerView rv_myaddress;
    private CopyOnWriteArrayList<AddressDataModel> addressModelList;
    ImageView img_back;
    CustomLoaderView loaderView;
    RelativeLayout rel_add_new_address;
    private LinearLayoutManager mLayoutManager;
    private boolean loading = true;
    private int page;
    private int pageSize;
    Dialog dialog;
    int addressid,pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageaddress);
        context = this;
        loaderView = CustomLoaderView.initialize(context);
        inItView();
        setRecyclerView();
        initData(1);
    }

    private void inItView() {
        rv_myaddress = findViewById(R.id.rv_myaddress);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
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
        rv_myaddress.setLayoutManager(mLayoutManager);
        rv_myaddress.addItemDecoration(new SpaceItemDecoration(10));
        rv_myaddress.setHasFixedSize(true);
        restautansManageAddressAdapter = new RestautansManageAddressAdapter(context, new CopyOnWriteArrayList<>(), new RestautansManageAddressAdapter.onItemClickListener() {
            @Override
            public void onEdit(int addressId) {
                editAccount();
            }

            @Override
            public void ondelete(int addressId,int position) {
                addressid=addressId;
                pos=position;
                getDeleteAddress();
            }
        });
        rv_myaddress.setAdapter(restautansManageAddressAdapter);
        rv_myaddress.addOnScrollListener(onScrollListener);
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
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }


    private void editAccount() {
        // dialog
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.address_edit);
        dialog.setCanceledOnTouchOutside(true);

        EditText et_tag = dialog.findViewById(R.id.et_tag);
        EditText et_address = dialog.findViewById(R.id.et_address);
        dialog.findViewById(R.id.btn_update).setOnClickListener(view -> {


            if (Utils.getProperText(et_tag).isEmpty()) {
                et_tag.setError(getString(R.string.error_landmark));
                et_tag.requestFocus();

            } else if (Utils.getProperText(et_address).isEmpty()) {
                et_address.setError(getString(R.string.error_address));
                et_address.requestFocus();

            } else {

            }
            dialog.dismiss();
            //updateAccount(et_phone,et_email);
        });

        dialog.findViewById(R.id.btn_close).setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }


    private void getDeleteAddress() {
            loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kAddressId, addressid);
        Log.e("Request", map.toString());
        ModelManager.modelManager().getDeleteAddress(map,
                (Constants.Status iStatus, GenericResponse<JSONObject> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        JSONObject favrouteModel = genericResponse.getObject();


                        String msg = favrouteModel.getString(kMessage);
                        Toaster.customToast(msg);
                        if(addressModelList.size()>0){
                            addressModelList.remove(pos);
                            restautansManageAddressAdapter.newData(addressModelList);
                            restautansManageAddressAdapter.notifyDataSetChanged();
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
