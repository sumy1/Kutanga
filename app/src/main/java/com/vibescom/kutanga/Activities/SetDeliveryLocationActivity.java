package com.vibescom.kutanga.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.libraries.places.api.Places;
import com.vibescom.kutanga.Blocks.Block;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.DispatchQueue.DispatchQueue;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Services.GPSTracker;
import com.vibescom.kutanga.Utils.Toaster;
import com.vibescom.kutanga.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.vibescom.kutanga.Constants.Constants.PERMISSIONS_REQUEST;
import static com.vibescom.kutanga.Constants.Constants.kAddress;
import static com.vibescom.kutanga.Constants.Constants.kAppPreferences;
import static com.vibescom.kutanga.Constants.Constants.kCity;
import static com.vibescom.kutanga.Constants.Constants.kCountry;
import static com.vibescom.kutanga.Constants.Constants.kLocationPermissionMsg;
import static com.vibescom.kutanga.Constants.Constants.kPincode;
import static com.vibescom.kutanga.Constants.Constants.kState;
import static com.vibescom.kutanga.Constants.Constants.ksubArea;

public class SetDeliveryLocationActivity extends AppCompatActivity implements  View.OnClickListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = SetDeliveryLocationActivity.class.getSimpleName();
    Context mContext;
    Button btn_set_delivery_location;
    private ProgressBar progressGPS;
    private String city="",state="",country = "India",pincode = "",fullAddress="",subArea;
    private double latitude = 0.0 ,longitude = 0.0;
    private TextView tvPhoneCode;
    private int phoneCode=0;
    private Dialog dialog;
    SharedPreferences  mPreferences;

    private Location mylocation;
    private GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS=0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0x2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_delivery_location);
        mContext=this;
        setUpGClient();
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.Api_key));
        }

        mPreferences=getSharedPreferences(kAppPreferences, Context.MODE_PRIVATE);
        inItView();
    }

    private void inItView() {
        progressGPS = findViewById(R.id.progressBar);
        btn_set_delivery_location=findViewById(R.id.btn_set_delivery_location);
        btn_set_delivery_location.setOnClickListener(this);

    }

    private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_set_delivery_location:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    checkPermissions();
                else
                    getMyLocation();
                break;
            case R.id.btn_grant_access:

                break;
        }

    }


    private void getMyLocation(){
        if(googleApiClient!=null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(SetDeliveryLocationActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation =                     LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, new com.google.android.gms.location.LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                    mylocation = location;
                                    if (mylocation != null) {
                                        Double latitude=mylocation.getLatitude();
                                        Double longitude=mylocation.getLongitude();
                                        getAddress(latitude,longitude);

                                    }
                                }
                            });
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(SetDeliveryLocationActivity.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(SetDeliveryLocationActivity.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getMyLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
        }
    }

    private void checkPermissions(){
        int permissionLocation = ContextCompat.checkSelfPermission(SetDeliveryLocationActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        }else{
            getMyLocation();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(SetDeliveryLocationActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOCATION) {
            *//*if(resultCode == RESULT_OK){
                // Get the user's selected place from the Intent.
                String id = data.getStringExtra("id");
                String address = data.getStringExtra("address");
                tvAddress.setText(address);
                tvAddress.setTag(id);
            }*//*
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

                            Log.d("City",city);

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
    }*/

 /*   private boolean checkSelfPermission(){
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
    }*/

   /* @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission(){
        // We've never asked. Just do it.
        requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_LOCATION);
    }*/

   /* @RequiresApi(api = Build.VERSION_CODES.M)
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
    }*/




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


                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);

                        //String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                        /* if(!provider.contains("gps")){ //if gps is disabled
                         *//* final Intent poke = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                    poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                    poke.setData(Uri.parse("3"));
                    sendBroadcast(poke);*//*

                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }*/
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
