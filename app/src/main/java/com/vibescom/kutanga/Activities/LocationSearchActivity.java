package com.vibescom.kutanga.Activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.vibescom.kutanga.Adapter.PlacesAutoCompleteAdapter;
import com.vibescom.kutanga.BaseActivity;
import com.vibescom.kutanga.Blocks.Block;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.DispatchQueue.DispatchQueue;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Services.GPSTracker;
import com.vibescom.kutanga.Utils.Toaster;
import com.vibescom.kutanga.Utils.Utils;
import com.vibescom.kutanga.Utils.VerticalSpaceItemDecoration;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.vibescom.kutanga.Constants.Constants.PERMISSIONS_REQUEST;
import static com.vibescom.kutanga.Constants.Constants.REQUEST_LOCATION;
import static com.vibescom.kutanga.Constants.Constants.REQUEST_PERMISSION_LOCATION;
import static com.vibescom.kutanga.Constants.Constants.kAddress;
import static com.vibescom.kutanga.Constants.Constants.kAdministrativeAreaLevel1;
import static com.vibescom.kutanga.Constants.Constants.kAppPreferences;
import static com.vibescom.kutanga.Constants.Constants.kCity;
import static com.vibescom.kutanga.Constants.Constants.kCountry;
import static com.vibescom.kutanga.Constants.Constants.kGCountry;
import static com.vibescom.kutanga.Constants.Constants.kLocality;
import static com.vibescom.kutanga.Constants.Constants.kLocationPermissionMsg;
import static com.vibescom.kutanga.Constants.Constants.kPincode;
import static com.vibescom.kutanga.Constants.Constants.kPostalCode;
import static com.vibescom.kutanga.Constants.Constants.kState;
import static com.vibescom.kutanga.Constants.Constants.ksubArea;

public class LocationSearchActivity extends BaseActivity implements PlacesAutoCompleteAdapter.ClickListener, View.OnClickListener
{
    private static final String TAG = LocationSearchActivity.class.getSimpleName();
    EditText search_et;
    Context mContext;
    private RecyclerView list_search;
    PlacesClient placesClient;
    FindAutocompletePredictionsRequest request;
    private PlacesAutoCompleteAdapter mAutoCompleteAdapte;
    RelativeLayout btn_grant_access;
    ImageView iv_auto_address,clear,img_back;
    private ProgressBar progressGPS;
    private String city="",state="",country = "India",pincode = "",fullAddress="",subArea;
    private double latitude = 0.0 ,longitude = 0.0;
    private TextView tvPhoneCode;
    private int phoneCode=0;
    private Dialog dialog;
    SharedPreferences  mPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_change_layout);
        mContext = this;
        Places.initialize(this, getResources().getString(R.string.Api_key));

        mPreferences=getSharedPreferences(kAppPreferences, Context.MODE_PRIVATE);
        inItView();


    }

    public void inItView(){
        search_et=findViewById(R.id.search_et);
        progressGPS = findViewById(R.id.progressBar);
        btn_grant_access=findViewById(R.id.btn_grant_access);
        btn_grant_access.setOnClickListener(this);
        iv_auto_address=findViewById(R.id.iv_auto_address);
        iv_auto_address.setOnClickListener(this);
        list_search = (RecyclerView) findViewById(R.id.list_search);

        clear=findViewById(R.id.clear);
        clear.setOnClickListener(this);
        search_et.addTextChangedListener(filterTextWatcher);
        mAutoCompleteAdapte = new PlacesAutoCompleteAdapter(this);
        list_search.addItemDecoration(new VerticalSpaceItemDecoration(5));
        list_search.setLayoutManager(new LinearLayoutManager(this));
        list_search.setHasFixedSize(true);
        mAutoCompleteAdapte.setClickListener(this);
        list_search.setAdapter(mAutoCompleteAdapte);
        mAutoCompleteAdapte.notifyDataSetChanged();

        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            clear.setVisibility(View.VISIBLE);
            if (!s.toString().equals("")) {
                mAutoCompleteAdapte.getFilter().filter(s.toString());
                if (list_search.getVisibility() == View.GONE) {
                    list_search.setVisibility(View.VISIBLE);

                }
            } else {
                if (list_search.getVisibility() == View.VISIBLE) {
                    iv_auto_address.setColorFilter(ContextCompat.getColor(mContext, R.color.black));
                    list_search.setVisibility(View.GONE);
                    clear.setVisibility(View.GONE);
                }
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            iv_auto_address.setColorFilter(ContextCompat.getColor(mContext, R.color.light_pink));
        }
    };

    @Override
    public void click(Place place) {

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("lat", String.valueOf(place.getLatLng().latitude));
        editor.putString("Lag", String.valueOf(place.getLatLng().longitude));
        editor.putString(kAddress, place.getAddress());
        editor.putString(kCity, place.getName());
        editor.commit();

        Intent intent=new Intent(mContext,LocationActivity.class);
        intent.putExtra("From","2");
        intent.putExtra("lat",String.valueOf(place.getLatLng().latitude));
        intent.putExtra("Lag",String.valueOf(place.getLatLng().longitude));
        intent.putExtra(kAddress,place.getAddress());
        intent.putExtra(kCity,place.getName());
        startActivity(intent);
        finish();


        //Toast.makeText(this, place.getAddress() + ", " + place.getLatLng().latitude + place.getLatLng().longitude, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_grant_access:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    checkGPSAccess();
                else
                    getLocation();
                break;
            case R.id.clear:
                search_et.setText("");
                mAutoCompleteAdapte.notifyDataSetChanged();

                break;

            case R.id.img_back:
                finish();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOCATION) {
            /*if(resultCode == RESULT_OK){
                // Get the user's selected place from the Intent.
                String id = data.getStringExtra("id");
                String address = data.getStringExtra("address");
                tvAddress.setText(address);
                tvAddress.setTag(id);
            }*/
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                LatLng ln = place.getLatLng();
                assert ln != null;
                latitude = ln.latitude;
                longitude = ln.longitude;

                List<AddressComponent> list = Objects.requireNonNull(place.getAddressComponents()).asList();
                for(int i=0;i<list.size();i++){
                    AddressComponent comp = list.get(i);
                    switch (comp.getTypes().get(0)) {
                        case kLocality:
                            city = comp.getName();
                            break;
                        case kAdministrativeAreaLevel1:
                            state = comp.getName();
                            break;
                        case kGCountry:
                            country = comp.getName();
                            break;
                        case kPostalCode:
                            pincode = comp.getName();
                            break;
                    }
                }

                //tvAddress.setText(place.getAddress());
                Log.i(TAG, "Place Id: " + place.getId()+ ", Place: " + place.getAddress() + ", LatLng:"
                        +latitude+":"+longitude + ", city:" + city + ", state:" + state + ", country:" + country);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                assert status.getStatusMessage() != null;
                Log.i(TAG, status.getStatusMessage());
            }
        } else if( requestCode == PERMISSIONS_REQUEST){
            if(checkSelfPermission()){
                dialog.dismiss();
            }
        }
    }

    private boolean checkSelfPermission(){
        return ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkGPSAccess(){
        if (checkSelfPermission()) {
            getLocation();
        }  else {
            requestPermission();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission(){
        // We've never asked. Just do it.
        requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_LOCATION);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_LOCATION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }else if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION)) {
                // We've been denied once before. Explain why we need the permission, then ask again.
                Utils.showDialog(this, getString(R.string.location_permission_required_text),
                        getString(R.string.ask_permission_text), getString(R.string.discard_text),
                        (dialog, which) -> {
                            if (which == -1)
                                requestPermission();
                            else
                                dialog.dismiss();
                        });
            } else if (!ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION)
                    && !ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION)) {
                // We've been denied for all. Explain why we need the permission, then ask again.
                showDialog();
            }
        }
    }

    // We were not granted permission this time, so don't try to show anything
    //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    private void showDialog(){
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_permission_setting);

        TextView text =  dialog.findViewById(R.id.tv_dialog);
        text.setText(kLocationPermissionMsg);

        Button dialogButton = dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, PERMISSIONS_REQUEST);
        });
        dialog.findViewById(R.id.btn_close).setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @SuppressLint("MissingPermission")
    private void getLocation(){
        showProgressBar(true);
        GPSTracker gps = new GPSTracker(this);
        // check if GPS enabled
        if (gps.canGetLocation()) {
            showProgressBar(false);

            if(gps.getLatitude()==0.0 & gps.getLongitude()==0.0){
                latitude=28.5970537;
                longitude=77.3257818;
                getAddress(latitude,longitude);
            }else{
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

                getAddress(latitude,longitude);
            }


        } else {
            showProgressBar(false);
            Utils.showAlertDialog(this, "Error!",
                    "Sorry unable to get current location. Make sure your GPS is ON!",
                    (dialogInterface, i) -> {
                        /*Intent intent=new Intent("android.location.GPS_ENABLED_CHANGE");
                        intent.putExtra("enabled", true);
                        sendBroadcast(intent);*/
                        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                        if(!provider.contains("gps")){ //if gps is disabled
                            final Intent poke = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                            poke.setData(Uri.parse("3"));
                            sendBroadcast(poke);
                        }
                    });
        }
    }

    private void showProgressBar(boolean b) {
        if (b) {
            progressGPS.setVisibility(View.VISIBLE);
        } else {
            progressGPS.setVisibility(View.GONE);
        }
    }

    public void getAddress(double latitude, double longitude){
        showProgressBar(true);
        getFullAddress(latitude,longitude,(iStatus, response) -> {
            showProgressBar(false);
            HashMap<String,String> map = response.getObject();
            city = map.get(kCity);
            state = map.get(kState);
            country = map.get(kCountry);
            pincode = map.get(kPincode);
            fullAddress=map.get(kAddress);
            subArea=map.get(ksubArea);

          /*  SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString("lat", String.valueOf(latitude));
            editor.putString("Lag", String.valueOf(longitude));
            editor.putString(kAddress, fullAddress);
            editor.putString(kCity, city);
            editor.putString(kPincode, pincode);
            editor.putString(ksubArea,subArea);
            editor.commit();*/

            Intent intent=new Intent(mContext,LocationActivity.class);
            intent.putExtra("From","2");
            intent.putExtra("lat", String.valueOf(latitude));
            intent.putExtra("Lag", String.valueOf(longitude));
            intent.putExtra(kAddress,fullAddress);
            intent.putExtra(kCity,city);
            intent.putExtra(kPincode,pincode);
            intent.putExtra(ksubArea,subArea);
            startActivity(intent);

            //tvAddress.setText(map.get(kAddress));
            Log.i(TAG,"getting address success"+city+state+country+pincode+fullAddress);
        },(iStatus, error) -> {
            showProgressBar(false);
            Toaster.kalaToast("Unable to Get Location. Check Connection");
        });

    }

    public void getFullAddress(double latitude, double longitude, Block.Success<HashMap<String,String>> success, Block.Failure failure) {
        ModelManager.modelManager().getDispatchQueue().async(() -> {
            try {
                String address ;
                HashMap<String,String> map=  new HashMap<>();
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
                if(listAddresses!=null && listAddresses.size()>0){
                    address = listAddresses.get(0).getLocality()+", "+listAddresses.get(0).getAdminArea()+", "+listAddresses.get(0).getCountryName();


                    map.put(kCity,listAddresses.get(0).getSubLocality());
                    map.put(kState,listAddresses.get(0).getAdminArea());
                    map.put(kCountry,listAddresses.get(0).getCountryName());
                    map.put(kPincode,listAddresses.get(0).getPostalCode());
                    map.put(ksubArea,listAddresses.get(0).getSubAdminArea());
                    map.put(kAddress,address);
                }
                GenericResponse<HashMap<String,String>> genericResponse = new GenericResponse<>(map);
                DispatchQueue.main(() -> success.iSuccess(Constants.Status.success,genericResponse));
            } catch (Exception e) {
                DispatchQueue.main(() -> failure.iFailure(Constants.Status.fail, e.getMessage()));
            }
        });
    }
}
