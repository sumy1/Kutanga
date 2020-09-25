package com.vibescom.kutanga.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.vibescom.kutanga.Activities.Restorants.Activity.HomeActivity;
import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansManageAddressAdapterr;
import com.vibescom.kutanga.Blocks.Block;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.DispatchQueue.DispatchQueue;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.AddUpdateAddressModel.AddUpdateAdddressModelClass;
import com.vibescom.kutanga.Models.CurrentUser;
import com.vibescom.kutanga.Models.ManageAddressModel.AddressDataModel;
import com.vibescom.kutanga.Models.ManageAddressModel.AddressModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Services.GPSTracker;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.SpaceItemDecoration;
import com.vibescom.kutanga.Utils.Toaster;
import com.vibescom.kutanga.Utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.vibescom.kutanga.Constants.Constants.kAddress;
import static com.vibescom.kutanga.Constants.Constants.kAppPreferences;
import static com.vibescom.kutanga.Constants.Constants.kArea;
import static com.vibescom.kutanga.Constants.Constants.kCity;
import static com.vibescom.kutanga.Constants.Constants.kCountry;
import static com.vibescom.kutanga.Constants.Constants.kFormatedaddress;
import static com.vibescom.kutanga.Constants.Constants.kFullAddress;
import static com.vibescom.kutanga.Constants.Constants.kLandMark;
import static com.vibescom.kutanga.Constants.Constants.kLat;
import static com.vibescom.kutanga.Constants.Constants.kPincode;
import static com.vibescom.kutanga.Constants.Constants.kState;
import static com.vibescom.kutanga.Constants.Constants.kZip;
import static com.vibescom.kutanga.Constants.Constants.kaddressType;
import static com.vibescom.kutanga.Constants.Constants.klng;
import static com.vibescom.kutanga.Constants.Constants.kpage;
import static com.vibescom.kutanga.Constants.Constants.ksubArea;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener, View.OnClickListener {

    private GoogleMap googleMap;
    ImageView pin, img_find_location, img_back;
    double latitude, longitude;
    String address = "", city = "";
    TextView txt_address, txt_choosesaveAs, txt_map_city_name, txt_change, txt_home, txt_work, txt_hotel, txt_other, tv_cancel;
    private String state = "", country = "India", pincode = "", fullAddress = "", area = "", landmark, from;
    Context mContext;
    Button btn_confirm_location;
    SharedPreferences mPreferences;
    CustomLoaderView loaderView;
    TextInputEditText edit_houseNo, edit_landMark;
    EditText edit_other;
    RelativeLayout rv_other, main;
    LinearLayout ll_tag, ll_tag_location;
    String addressType = "", landMark;
    int userid = 0;
    int fromm = 0;
    int homeTypeStatus, workTypeSatus, otherTypestatus;
    String houseNo;
    CurrentUser currentUser;


    RelativeLayout rel_add_new_address;
    private LinearLayoutManager mLayoutManager;
    private boolean loading = true;
    private int page;
    private int pageSize;
    int addressid, pos;


    RestautansManageAddressAdapterr restautansManageAddressAdapter;
    private RecyclerView rv_myaddress;
    private CopyOnWriteArrayList<AddressDataModel> addressModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = ModelManager.modelManager().getCurrentUser();

        if (currentUser == null) {
            setContentView(R.layout.location_without_tag);
        } else {
            setContentView(R.layout.activity_location);
        }
        mContext = this;
        loaderView = CustomLoaderView.initialize(mContext);

        inItView();
        mPreferences = getSharedPreferences(kAppPreferences, Context.MODE_PRIVATE);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        pin = findViewById(R.id.imgLocationPinUp);

        btn_confirm_location = findViewById(R.id.btn_confirm_location);
        btn_confirm_location.setOnClickListener(this);


    }


    private void inItView() {

        ll_tag_location = findViewById(R.id.ll_tag_location);

        if (currentUser == null) {
            //ll_tag_location.setVisibility(View.GONE);
        } else {

            txt_home = findViewById(R.id.txt_home);
            txt_home.setOnClickListener(this);
            txt_work = findViewById(R.id.txt_work);
            txt_work.setOnClickListener(this);
            txt_hotel = findViewById(R.id.txt_hotel);
            txt_hotel.setOnClickListener(this);
            txt_other = findViewById(R.id.txt_other);
            txt_other.setOnClickListener(this);
            rv_other = findViewById(R.id.rv_other);
            ll_tag = findViewById(R.id.ll_tag);
            tv_cancel = findViewById(R.id.tv_cancel);
            tv_cancel.setOnClickListener(this);
            edit_houseNo = findViewById(R.id.edit_houseNo);
            edit_landMark = findViewById(R.id.edit_landMark);
            edit_other = findViewById(R.id.edit_other);
            rv_myaddress = findViewById(R.id.rv_myaddress);
            rel_add_new_address = findViewById(R.id.rel_add_new_address);
            rel_add_new_address.setOnClickListener(this);
            txt_choosesaveAs = findViewById(R.id.txt_choosesaveAs);
            txt_choosesaveAs.setOnClickListener(this);
            setRecyclerView();
            initData(1);
        }


    }

    private void setRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rv_myaddress.setLayoutManager(mLayoutManager);
        rv_myaddress.addItemDecoration(new SpaceItemDecoration(10));
        rv_myaddress.setHasFixedSize(true);
        restautansManageAddressAdapter = new RestautansManageAddressAdapterr(this, new CopyOnWriteArrayList<>(), new RestautansManageAddressAdapterr.onItemClickListener() {
            @Override
            public void onChceked(String fullAddress, String pincodee, String areaa, String landmark, String lat, String lng, String formattedAddress, String addressTypee) {
                rel_add_new_address.setVisibility(View.GONE);
                //btn_confirm_location.setVisibility(View.VISIBLE);
                address = fullAddress;
                pincode = pincodee;
                area = areaa;
                landMark = landmark;
                latitude = Double.parseDouble(lat);
                longitude = Double.parseDouble(lng);
                address = formattedAddress;
                addressType = addressTypee;
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putString("lat", String.valueOf(latitude));
                editor.putString("Lag", String.valueOf(longitude));
                editor.putString(kAddress, address);
                editor.putString(kCity, city);
                editor.putString(kPincode, pincode);
                editor.putString(ksubArea, area);
                editor.commit();

                Intent intentt = new Intent(mContext, HomeActivity.class);
                intentt.putExtra("lat", latitude);
                intentt.putExtra("Lag", longitude);
                intentt.putExtra("FROM", "2");
                intentt.putExtra(kAddress, address);
                intentt.putExtra(kCity, city);
                startActivity(intentt);
                finish();

            }


        });

                /*, new RestautansManageAddressAdapter.onItemClickListener() {
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
        });*/
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

                      /*  if(addressModelList.size()==0){
                            rv_myaddress.setVisibility(View.GONE);
                            rel_add_new_address.setVisibility(View.GONE);
                            btn_confirm_location.setVisibility(View.VISIBLE);
                            ll_tag_location.setVisibility(View.VISIBLE);
                        }else{
                            rv_myaddress.setVisibility(View.VISIBLE);
                            rel_add_new_address.setVisibility(View.VISIBLE);
                            btn_confirm_location.setVisibility(View.GONE);
                            ll_tag_location.setVisibility(View.GONE);
                        }*/


                        if (page != 1) {
                            restautansManageAddressAdapter.addData(addressModelList);
                            loading = !genericResponse.getObject().getAddressDataModelList().isEmpty();
                        } else {
                            //bookingAdapter=new BookingAdapter(context,bookings);
                            restautansManageAddressAdapter.newData(addressModelList);
                            pageSize = genericResponse.getObject().getAddressDataModelList().size();
                        }

                        if (pageSize == 0 || from.equalsIgnoreCase("1")) {
                            rv_myaddress.setVisibility(View.GONE);
                            rel_add_new_address.setVisibility(View.GONE);
                            btn_confirm_location.setVisibility(View.VISIBLE);
                            ll_tag_location.setVisibility(View.VISIBLE);
                        } else {
                            rv_myaddress.setVisibility(View.VISIBLE);
                            rel_add_new_address.setVisibility(View.VISIBLE);
                            btn_confirm_location.setVisibility(View.GONE);
                            ll_tag_location.setVisibility(View.GONE);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent in = getIntent();
        from = in.getStringExtra("From");

       /* latitude = Double.parseDouble(mPreferences.getString("lat", ""));
        longitude = Double.parseDouble(mPreferences.getString("Lag", ""));

        address = mPreferences.getString(kAddress, "");
        city = mPreferences.getString(Constants.kCity, "");
        pincode = mPreferences.getString(kPincode, "");
        area = mPreferences.getString(ksubArea, "");*/


        if (from.equalsIgnoreCase("2")) {
            latitude = Double.parseDouble(in.getStringExtra("lat"));
            longitude = Double.parseDouble(in.getStringExtra("Lag"));
            address = in.getStringExtra(Constants.kAddress);
            city = in.getStringExtra(Constants.kCity);
            pincode = in.getStringExtra(kPincode);
            area = in.getStringExtra(ksubArea);
            landmark = in.getStringExtra(kLandMark);
            addressType = in.getStringExtra(kaddressType);
            getAddress(latitude, longitude);

        } else if (from.equalsIgnoreCase("1")) {
            latitude = Double.parseDouble(in.getStringExtra("lat"));
            longitude = Double.parseDouble(in.getStringExtra("Lag"));
            address = in.getStringExtra(Constants.kAddress);
            city = in.getStringExtra(Constants.kCity);

            Log.d("City", city + "/" + address);
            pincode = in.getStringExtra(kPincode);
            area = in.getStringExtra(ksubArea);
            landmark = in.getStringExtra(kLandMark);
            addressType = in.getStringExtra(kaddressType);
            edit_houseNo.setText(area);
            edit_landMark.setText(landmark);
            if (addressType.equalsIgnoreCase("home")) {
                txt_home.setBackground(getResources().getDrawable(R.drawable.canvas_red_round_bg));
                txt_home.setTextColor(getResources().getColor(R.color.theme_color));

                txt_work.setBackground(getResources().getDrawable(R.drawable.canvas_gray_round_bg));
                txt_work.setTextColor(getResources().getColor(R.color.dim_grey));


                txt_other.setBackground(getResources().getDrawable(R.drawable.canvas_gray_round_bg));
                txt_other.setTextColor(getResources().getColor(R.color.dim_grey));
            } else if (addressType.equalsIgnoreCase("work")) {
                txt_home.setBackground(getResources().getDrawable(R.drawable.canvas_gray_round_bg));
                txt_home.setTextColor(getResources().getColor(R.color.dim_grey));

                txt_work.setBackground(getResources().getDrawable(R.drawable.canvas_red_round_bg));
                txt_work.setTextColor(getResources().getColor(R.color.theme_color));


                txt_other.setBackground(getResources().getDrawable(R.drawable.canvas_gray_round_bg));
                txt_other.setTextColor(getResources().getColor(R.color.dim_grey));
            } else {
                txt_home.setBackground(getResources().getDrawable(R.drawable.canvas_gray_round_bg));
                txt_home.setTextColor(getResources().getColor(R.color.dim_grey));

                txt_work.setBackground(getResources().getDrawable(R.drawable.canvas_gray_round_bg));
                txt_work.setTextColor(getResources().getColor(R.color.dim_grey));


                txt_other.setBackground(getResources().getDrawable(R.drawable.canvas_red_round_bg));
                txt_other.setTextColor(getResources().getColor(R.color.theme_color));
            }
            getAddress(latitude, longitude);
        } else {
            getLocation(2);
        }
        txt_address = findViewById(R.id.txt_address);
        txt_address.setText(address);
        txt_map_city_name = findViewById(R.id.txt_map_city_name);
        txt_map_city_name.setText(city);
        txt_change = findViewById(R.id.txt_change);
        txt_change.setOnClickListener(this);
        img_find_location = findViewById(R.id.img_find_location);
        img_find_location.setOnClickListener(this);
        Log.d("Lat", latitude + "/" + longitude + "/" + address + "/" + from);

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(V -> {
            finish();
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        Log.d("lat", latitude + "/" + longitude + "");
        LatLng sydney = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney")
                .draggable(false));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f));

        // maps events we need to respond to
        googleMap.setOnCameraMoveListener(LocationActivity.this);
        googleMap.setOnCameraIdleListener(LocationActivity.this);


    }


    private boolean validate() {
        boolean isOk = true;

        if (Utils.getProperText(edit_houseNo).isEmpty()) {
            edit_houseNo.setError(getString(R.string.error_house));
            edit_houseNo.requestFocus();
            isOk = false;

        } else if (Utils.getProperText(edit_landMark).isEmpty()) {
            edit_landMark.setError(getString(R.string.error_landmark));
            edit_landMark.requestFocus();
            isOk = false;
        } else if (addressType.isEmpty()) {
            Toaster.customToast("Please add Location Tag");
            isOk = false;
        }

        return isOk;
    }

    @Override
    public void onCameraMove() {
        googleMap.clear();
        pin.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCameraIdle() {
        pin.setVisibility(View.GONE);
        googleMap.addMarker(new MarkerOptions().position(googleMap.getCameraPosition().target).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txt_change:
                Intent intent = new Intent(mContext, LocationSearchActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.img_find_location:
                getLocation(1);
                break;
            case R.id.btn_confirm_location:

                Log.d("Click", "1");

                try {
                    if (currentUser == null) {

                        SharedPreferences.Editor editor = mPreferences.edit();
                        editor.putString("lat", String.valueOf(latitude));
                        editor.putString("Lag", String.valueOf(longitude));
                        editor.putString(kAddress, address);
                        editor.putString(kCity, city);
                        editor.putString(kPincode, pincode);
                        editor.putString(ksubArea, area);
                        editor.commit();

                        Intent intentt = new Intent(mContext, HomeActivity.class);
                        intentt.putExtra("lat", latitude);
                        intentt.putExtra("Lag", longitude);
                        intentt.putExtra("FROM", "2");
                        intentt.putExtra(kAddress, address);
                        intentt.putExtra(kCity, city);
                        startActivity(intentt);
                        finish();

                    } else {
                        Log.d("All", latitude + "/" + longitude + "/" + fullAddress + "/" + city + edit_houseNo.getText().toString() + "/" + edit_landMark.getText().toString() + "/" + edit_other.getText().toString() + "");

                        houseNo = edit_houseNo.getText().toString().trim();
                        if (validate()) {
                            getAddAddress();

                        }

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;

            case R.id.rel_add_new_address:

                rv_myaddress.setVisibility(View.GONE);
                rel_add_new_address.setVisibility(View.GONE);
                txt_choosesaveAs.setVisibility(View.VISIBLE);
                btn_confirm_location.setVisibility(View.VISIBLE);
                ll_tag_location.setVisibility(View.VISIBLE);

                break;

            case R.id.tv_cancel:
                rv_other.setVisibility(View.GONE);
                ll_tag.setVisibility(View.VISIBLE);
                edit_other.setText("");
                break;
            case R.id.txt_choosesaveAs:
                rv_myaddress.setVisibility(View.VISIBLE);
                btn_confirm_location.setVisibility(View.GONE);
                ll_tag_location.setVisibility(View.GONE);
                rel_add_new_address.setVisibility(View.VISIBLE);
                break;
            case R.id.txt_home:
                homeTypeStatus = 1;
                workTypeSatus = 0;
                otherTypestatus = 0;

                if (homeTypeStatus == 1) {
                    addressType = "home";
                } else {
                    addressType = "";
                }

                txt_home.setBackground(getResources().getDrawable(R.drawable.canvas_red_round_bg));
                txt_home.setTextColor(getResources().getColor(R.color.theme_color));

                txt_work.setBackground(getResources().getDrawable(R.drawable.canvas_gray_round_bg));
                txt_work.setTextColor(getResources().getColor(R.color.dim_grey));

                txt_hotel.setBackground(getResources().getDrawable(R.drawable.canvas_gray_round_bg));
                txt_hotel.setTextColor(getResources().getColor(R.color.dim_grey));

                txt_other.setBackground(getResources().getDrawable(R.drawable.canvas_gray_round_bg));
                txt_other.setTextColor(getResources().getColor(R.color.dim_grey));


                break;
            case R.id.txt_work:

                homeTypeStatus = 0;
                workTypeSatus = 1;
                otherTypestatus = 0;

                if (workTypeSatus == 1) {
                    addressType = "work";
                } else {
                    addressType = "";
                }
                txt_work.setBackground(getResources().getDrawable(R.drawable.canvas_red_round_bg));
                txt_work.setTextColor(getResources().getColor(R.color.theme_color));

                txt_home.setBackground(getResources().getDrawable(R.drawable.canvas_gray_round_bg));
                txt_home.setTextColor(getResources().getColor(R.color.dim_grey));

                txt_hotel.setBackground(getResources().getDrawable(R.drawable.canvas_gray_round_bg));
                txt_hotel.setTextColor(getResources().getColor(R.color.dim_grey));

                txt_other.setBackground(getResources().getDrawable(R.drawable.canvas_gray_round_bg));
                txt_other.setTextColor(getResources().getColor(R.color.dim_grey));
                break;
            case R.id.txt_hotel:


                txt_hotel.setBackground(getResources().getDrawable(R.drawable.canvas_red_round_bg));
                txt_hotel.setTextColor(getResources().getColor(R.color.theme_color));

                txt_home.setBackground(getResources().getDrawable(R.drawable.canvas_gray_round_bg));
                txt_home.setTextColor(getResources().getColor(R.color.dim_grey));

                txt_work.setBackground(getResources().getDrawable(R.drawable.canvas_gray_round_bg));
                txt_work.setTextColor(getResources().getColor(R.color.dim_grey));

                txt_other.setBackground(getResources().getDrawable(R.drawable.canvas_gray_round_bg));
                txt_other.setTextColor(getResources().getColor(R.color.dim_grey));
                break;
            case R.id.txt_other:
                // rv_other.setVisibility(View.VISIBLE);


                txt_other.setBackground(getResources().getDrawable(R.drawable.canvas_red_round_bg));
                txt_other.setTextColor(getResources().getColor(R.color.theme_color));

                txt_home.setBackground(getResources().getDrawable(R.drawable.canvas_gray_round_bg));
                txt_home.setTextColor(getResources().getColor(R.color.dim_grey));

                txt_work.setBackground(getResources().getDrawable(R.drawable.canvas_gray_round_bg));
                txt_work.setTextColor(getResources().getColor(R.color.dim_grey));

                txt_hotel.setBackground(getResources().getDrawable(R.drawable.canvas_gray_round_bg));
                txt_hotel.setTextColor(getResources().getColor(R.color.dim_grey));


                ll_tag.setVisibility(View.VISIBLE);
                homeTypeStatus = 0;
                workTypeSatus = 0;
                otherTypestatus = 1;
                if (otherTypestatus == 1) {
                    addressType = "other";
                } else {
                    addressType = "";
                }
                break;
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation(int from) {
        GPSTracker gps = new GPSTracker(this);
        // check if GPS enabled
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            if (gps.getLatitude() == 0.0 & gps.getLongitude() == 0.0) {
                latitude = 28.5970537;
                longitude = 77.3257818;
                getAddress(latitude, longitude);

                if (from == 1) {
                    LatLng sydney = new LatLng(latitude, longitude);
                    googleMap.addMarker(new MarkerOptions()
                            .position(sydney)
                            .title("Marker in Sydney")
                            .draggable(false));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f));

                    // maps events we need to respond to
                    googleMap.setOnCameraMoveListener(LocationActivity.this);
                    googleMap.setOnCameraIdleListener(LocationActivity.this);
                } else {

                }


            } else {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                getAddress(latitude, longitude);
                if (from == 1) {
                    LatLng sydney = new LatLng(latitude, longitude);
                    googleMap.addMarker(new MarkerOptions()
                            .position(sydney)
                            .title("Marker in Sydney")
                            .draggable(false));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f));

                    // maps events we need to respond to
                    googleMap.setOnCameraMoveListener(LocationActivity.this);
                    googleMap.setOnCameraIdleListener(LocationActivity.this);
                } else {

                }
            }
        } else {

            Utils.showAlertDialog(this, "Error!",
                    "Sorry unable to get current location. Make sure your GPS is ON!",
                    (dialogInterface, i) -> {
                       /* Intent intent=new Intent("android.location.GPS_ENABLED_CHANGE");
                        intent.putExtra("enabled", true);
                        sendBroadcast(intent);*/
                        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                        if (!provider.contains("gps")) { //if gps is disabled
                            final Intent poke = new Intent();
                            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                            poke.setData(Uri.parse("3"));
                            sendBroadcast(poke);
                        }
                    });
        }
    }

    public void getAddress(double latitude, double longitude) {
        getFullAddress(latitude, longitude, (iStatus, response) -> {
            HashMap<String, String> map = response.getObject();
            city = map.get(kCity);
            state = map.get(kState);
            country = map.get(kCountry);
            pincode = map.get(kPincode);
            address = map.get(kAddress);


            if (from.equalsIgnoreCase("3")) {
                txt_address.setText(address);
                txt_map_city_name.setText(city);
            } else {

            }

           /* Intent intent = new Intent(mContext, LocationActivity.class);
            intent.putExtra("lat", latitude);
            intent.putExtra("Lag", longitude);
            intent.putExtra(kAddress, address);
            intent.putExtra(kCity, city);
            startActivity(intent);
            finish();*/

            Log.d("add", city + "/" + pincode);

            //tvAddress.setText(map.get(kAddress));
        }, (iStatus, error) -> {

            Toaster.kalaToast("Unable to Get Location. Check Connection");
        });

    }

    public void getFullAddress(double latitude, double longitude, Block.Success<HashMap<String, String>> success, Block.Failure failure) {
        ModelManager.modelManager().getDispatchQueue().async(() -> {
            try {
                String address;
                HashMap<String, String> map = new HashMap<>();
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);

                Log.d("jsonmap", listAddresses.toString() + "" + listAddresses.get(0).getPostalCode());
                if (listAddresses != null && listAddresses.size() > 0) {
                    address = listAddresses.get(0).getLocality() + ", " + listAddresses.get(0).getAdminArea() + ", " + listAddresses.get(0).getCountryName();
                    map.put(kCity, listAddresses.get(0).getSubLocality());
                    map.put(kState, listAddresses.get(0).getAdminArea());
                    map.put(kCountry, listAddresses.get(0).getCountryName());
                    map.put(kPincode, listAddresses.get(0).getPostalCode());
                    map.put(kAddress, address);
                }
                GenericResponse<HashMap<String, String>> genericResponse = new GenericResponse<>(map);
                DispatchQueue.main(() -> success.iSuccess(Constants.Status.success, genericResponse));
            } catch (Exception e) {
                DispatchQueue.main(() -> failure.iFailure(Constants.Status.fail, e.getMessage()));
            }
        });
    }

    private void getAddAddress() {
        area = edit_houseNo.getText().toString().trim();
        landMark = edit_landMark.getText().toString().trim();
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kFullAddress, address);
        map.put(kZip, pincode);
        map.put(kArea, area);
        map.put(kLandMark, landMark);
        map.put(kLat, latitude);
        map.put(klng, longitude);
        map.put(kFormatedaddress, address);
        map.put(kaddressType, addressType);
        Log.e("Send Request", map.toString());
        ModelManager.modelManager().getAddAddress(map,
                (Constants.Status iStatus, GenericResponse<JSONObject> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        JSONObject jsonObject = genericResponse.getObject();

                        AddUpdateAdddressModelClass addUpdateAdddressModelClass = new AddUpdateAdddressModelClass(jsonObject);

                        String msg = addUpdateAdddressModelClass.getMessage();

                        Toaster.customToast(msg);

                        Intent intentt = new Intent(mContext, HomeActivity.class);
                        intentt.putExtra("lat", latitude);
                        intentt.putExtra("Lag", longitude);
                        intentt.putExtra("FROM", "2");
                        intentt.putExtra(kAddress, address);
                        intentt.putExtra(kCity, city);
                        startActivity(intentt);
                        finish();


                        SharedPreferences.Editor editor = mPreferences.edit();
                        editor.putString("lat", String.valueOf(latitude));
                        editor.putString("Lag", String.valueOf(longitude));
                        editor.putString(kAddress, address);
                        editor.putString(kCity, city);
                        editor.putString(kPincode, pincode);
                        editor.putString(kArea, houseNo);
                        editor.putString(kLandMark, edit_landMark.getText().toString().trim());
                        editor.putString(kFormatedaddress, address);
                        editor.putString(kaddressType, addressType);
                        editor.putString(ksubArea, area);
                        editor.commit();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }


    private void menuDialogg() {
        // dialog

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert_deletee);
        dialog.setCancelable(false);

        Button btn_yes = dialog.findViewById(R.id.btn_yes);

        btn_yes.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(mContext, SignUpActivity.class);
            startActivity(intent);
            finish();
        });
        Button btn_no = dialog.findViewById(R.id.btn_no);

        btn_no.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(mContext, SignInActivity.class);
            startActivity(intent);
            finish();
        });


        dialog.show();
    }
}




