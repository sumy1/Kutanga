package com.vibescom.kutanga.Activities.Restorants.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Activities.LocationActivity;
import com.vibescom.kutanga.Activities.Restorants.Activity.ApplyCoupanActivity;
import com.vibescom.kutanga.Activities.Restorants.Activity.HomeActivity;
import com.vibescom.kutanga.Activities.Restorants.Activity.chooseAddressActivity;
import com.vibescom.kutanga.Activities.Restorants.Adapter.CategoryPriceAdapterr;
import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansCartAdapter;
import com.vibescom.kutanga.Activities.SignInActivity;
import com.vibescom.kutanga.Activities.SignUpActivity;
import com.vibescom.kutanga.ApplicationManager;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.AdervisementModel;
import com.vibescom.kutanga.Models.BaseModel;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ProductPrice;
import com.vibescom.kutanga.Models.UpdatedcartModel.Updatedcartdata;
import com.vibescom.kutanga.Models.ViewCart.CartItemModel;
import com.vibescom.kutanga.Models.ViewCart.ViewcartModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.Toaster;
import com.vibescom.kutanga.Utils.Utils;
import com.vibescom.kutanga.Utils.VerticalSpaceItemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import static com.vibescom.kutanga.Constants.Constants.kAddress;
import static com.vibescom.kutanga.Constants.Constants.kAuthToken;
import static com.vibescom.kutanga.Constants.Constants.kCity;
import static com.vibescom.kutanga.Constants.Constants.kCompanyName;
import static com.vibescom.kutanga.Constants.Constants.kLoginType;
import static com.vibescom.kutanga.Constants.Constants.kMessage;
import static com.vibescom.kutanga.Constants.Constants.kMinimumOrder;
import static com.vibescom.kutanga.Constants.Constants.kPhone;
import static com.vibescom.kutanga.Constants.Constants.kResponse;
import static com.vibescom.kutanga.Constants.Constants.kRowId;
import static com.vibescom.kutanga.Constants.Constants.kUserEmail;
import static com.vibescom.kutanga.Constants.Constants.kUserName;
import static com.vibescom.kutanga.Constants.Constants.kproductId;
import static com.vibescom.kutanga.Constants.Constants.kproductPrice;
import static com.vibescom.kutanga.Constants.Constants.kqty;


public class RestorantsCartFragment extends Fragment {

    private static final String TAG=RestorantsCartFragment.class.getSimpleName();
    private Context mContext;
    private RecyclerView rv_cart;
    private TextView txt_item_total, txt_restorants_charges,txt_deliver_address, txt_delivery_fee, txt_cart_total_fee;
    private RestautansCartAdapter restautansCartAdapter;
    private RelativeLayout rel_proceed_checkout, rel_apllay, rv_no_cart, rl_main;
    TextView txt_clearAll, txt_cart_freshery;
    CustomLoaderView loaderView;
    int itemrowId;
    int productPricee,totalpayablePrice;
    int productId;
    int quy;
    int userId;
    String productImagePath,subTotal,priceQuantity;
    CategoryPriceAdapterr categoryPriceAdapter;
    SharedPreferences sharedPreferences;
    CopyOnWriteArrayList<CartItemModel> cartItemModels;
    TextView txt_min_order;
    int minOrdervalue;

    String address = "", city = "",finalPrice;
    double latitude, longitude;
    SharedPreferences mPreferences;
    RelativeLayout search_layout_home;
    cartItemkListener listener;
    Fragment currentFragment;
    ViewPager pager;
    ImageView img_close;
    RelativeLayout ll_main;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 200;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000;

    public RestorantsCartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_restorants_cart, container, false);
        mContext = getActivity();
        sharedPreferences = ApplicationManager.getContext().getSharedPreferences(BaseModel.kAppPreferences, Context.MODE_PRIVATE);
        loaderView = CustomLoaderView.initialize(mContext);
        pager = rootView.findViewById(R.id.pager);
        setAdvertisementAdapter();
        ll_main=rootView.findViewById(R.id.ll_main);
        img_close=rootView.findViewById(R.id.img_close);

        img_close.setOnClickListener(v -> {
            ll_main.setVisibility(View.GONE);
        });
        getViewCart();
        inItView(rootView);

        return rootView;
    }


    private void inItView(View rootView) {
        search_layout_home=rootView.findViewById(R.id.search_layout_home);
        txt_deliver_address = rootView.findViewById(R.id.txt_deliver_address);
        rv_no_cart = rootView.findViewById(R.id.rv_no_cart);
        txt_min_order=rootView.findViewById(R.id.txt_min_order);
        rl_main = rootView.findViewById(R.id.rl_main);
        txt_cart_freshery = rootView.findViewById(R.id.txt_cart_freshery);
        txt_item_total = rootView.findViewById(R.id.txt_item_total);
        txt_restorants_charges = rootView.findViewById(R.id.txt_restorants_charges);
        txt_delivery_fee = rootView.findViewById(R.id.txt_delivery_fee);
        txt_cart_total_fee = rootView.findViewById(R.id.txt_cart_total_fee);
        rv_cart = rootView.findViewById(R.id.rv_cart);
        rv_cart.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL));
        rv_cart.addItemDecoration(new VerticalSpaceItemDecoration(2));

        rel_apllay = rootView.findViewById(R.id.rel_apllay);

        rel_apllay.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ApplyCoupanActivity.class);
            startActivity(intent);
        });

        try {
            latitude = Double.parseDouble(sharedPreferences.getString("lat", ""));
            longitude = Double.parseDouble(sharedPreferences.getString("Lag", ""));
            address = sharedPreferences.getString(Constants.kAddress,"");
            city = sharedPreferences.getString(Constants.kCity,"");
            txt_deliver_address.setText(address);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        txt_item_total = rootView.findViewById(R.id.txt_item_total);
        txt_restorants_charges = rootView.findViewById(R.id.txt_restorants_charges);
        txt_delivery_fee = rootView.findViewById(R.id.txt_delivery_fee);
        txt_cart_total_fee = rootView.findViewById(R.id.txt_cart_total_fee);

        rel_proceed_checkout = rootView.findViewById(R.id.rel_proceed_checkout);
        rel_proceed_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    if(ModelManager.modelManager().getCurrentUser().getUserId()==0){
                        userId=0;
                    }else{
                        userId=ModelManager.modelManager().getCurrentUser().getUserId();

                    }

                }catch(Exception e){
                    e.printStackTrace();
                }


                if(userId==0){
                    menuDialog();
                }else{

                    if(totalpayablePrice<minOrdervalue){
                        Toaster.customToast("Your order is too short! Please achieve minimum order limit");
                    }else{
                        Intent intent = new Intent(mContext, chooseAddressActivity.class);
                        intent.putExtra("AMOUNT",totalpayablePrice);
                        mContext.startActivity(intent);
                    }

                }
            }
        });

        txt_clearAll = rootView.findViewById(R.id.txt_clearAll);
        txt_clearAll.setOnClickListener(v -> {
            getClearAllCart();
        });

        search_layout_home.setOnClickListener(V -> {
            Intent intent = new Intent(mContext, LocationActivity.class);
            intent.putExtra("From","2");
            intent.putExtra("lat", String.valueOf(latitude));
            intent.putExtra("Lag", String.valueOf(longitude));
            intent.putExtra(kAddress, address);
            intent.putExtra(kCity, city);
            startActivity(intent);
            //getActivity().finish();
        });
    }

    public void setAdvertisementAdapter(){
        AdvertismentAdapter adapter = new AdvertismentAdapter(getActivity(), Utils.getAdvertisementData());
        pager.setAdapter(adapter);

        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == Utils.getAdvertisementData().size()) {
                    currentPage = 0;
                }
                pager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    private void getViewCart() {
        loaderView.showLoader();
        ModelManager.modelManager().getViewcart(
                (Constants.Status iStatus, GenericResponse<ViewcartModel> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        ViewcartModel viewcartModel = genericResponse.getObject();
                        productImagePath = viewcartModel.getProductImgPath();
                        txt_item_total.setText(viewcartModel.getSubTotal() + " " + "Kz");
                        txt_restorants_charges.setText("+"+viewcartModel.getRestorantsCharge() + " " + "Kz");
                        txt_delivery_fee.setText("+"+viewcartModel.getDeliveryCharge() + " " + "Kz");
                        txt_cart_total_fee.setText(viewcartModel.getTotalPayablePrice() + " " + "Kz");
                        totalpayablePrice=viewcartModel.getTotalPayablePrice();

                        Log.d("TotalpayablePrice",totalpayablePrice+"");
                        cartItemModels = viewcartModel.getCartItemModels();
                        if(cartItemModels.size()==0){
                            rv_no_cart.setVisibility(View.VISIBLE);
                            rl_main.setVisibility(View.GONE);
                        }else{
                            rv_no_cart.setVisibility(View.GONE);
                            rl_main.setVisibility(View.VISIBLE);

                            JSONObject jsonObject = null;

                            for (int i = 0; i < cartItemModels.size(); i++) {

                                jsonObject = cartItemModels.get(i).getAssocialteModel();
                                itemrowId=cartItemModels.get(i).getCartid();
                                productPricee=cartItemModels.get(i).getCartPrice();
                                quy=cartItemModels.get(i).getCartQuantity();

                            }
                            JSONObject jsonObject1 = null;
                            if (jsonObject.has("vendor")) {
                                jsonObject1 = jsonObject.getJSONObject("vendor");
                            }

                            if(jsonObject1.getString(kMinimumOrder).equalsIgnoreCase("")){

                            }else{
                                minOrdervalue=Integer.parseInt(jsonObject1.getString(kMinimumOrder));
                                txt_min_order.setText(minOrdervalue+" "+"Kz");
                            }
                            txt_cart_freshery.setText(jsonObject1.getString(kCompanyName));
                            productId = jsonObject.getInt(kproductId);
                            restautansCartAdapter = new RestautansCartAdapter(mContext, cartItemModels, productImagePath, new RestautansCartAdapter.onItemClickListener() {
                                @Override
                                public void onRemovecartItem(int pos,int rowId) {
                                    itemrowId = rowId;
                                    getRemoveitem(pos);
                                }

                                @Override
                                public void updatecrat(int rowId, int productPrice, int quantity) {
                                    itemrowId = rowId;
                                    productPricee = productPrice;
                                    quy = quantity;
                                    getUpdatecart(1,quy);
                                }

                                @Override
                                public void customizeCart(CopyOnWriteArrayList<ProductPrice> productPrices,String priceQuantity,String selectedcartprice, String productName, String mainAttribute, int productId) {
                                    customizeDialog(2,productPrices, productName, mainAttribute, productId,selectedcartprice,priceQuantity);
                                }

                                /*@Override
                                public void customizeCart(int productid, int productprice) {
                                    customizeDialog();
                                }*/
                            });
                            rv_cart.setAdapter(restautansCartAdapter);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }


    private void getRemoveitem(int pos) {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kRowId, itemrowId);
        Log.e("Send param", map.toString());
        ModelManager.modelManager().getRemovecartItem(map,
                (Constants.Status iStatus, GenericResponse<JSONObject> genericResponse) -> {
                    loaderView.hideLoader();
                    try {

                        String msg = genericResponse.getObject().getString(kMessage);

                        JSONArray jsonArray=genericResponse.getObject().getJSONArray(kResponse);

                        JSONObject jsonObject=jsonArray.getJSONObject(0);
                        listener.onRemoveCartbadge();

                        if(cartItemModels.size()>0){
                            restautansCartAdapter.removeItem(pos);

                            txt_item_total.setText(jsonObject.getString("sub_total") + " " + "Kz");
                            txt_restorants_charges.setText(jsonObject.getString("restaurant_charges") + " " + "Kz");

                            txt_delivery_fee.setText(jsonObject.getString("delivery_charges") + " " + "Kz");

                            txt_cart_total_fee.setText(jsonObject.getString("total_payable_price") + " " + "Kz");

                            //totalpayablePrice= Integer.parseInt(jsonObject.getString("total_payable_price"));

                            Log.d("SizeItem",cartItemModels.size()+"");
                            if(cartItemModels.size()==0){
                                rv_no_cart.setVisibility(View.VISIBLE);
                                rl_main.setVisibility(View.GONE);
                            }else {
                                rv_no_cart.setVisibility(View.GONE);
                                rl_main.setVisibility(View.VISIBLE);
                            }

                            restautansCartAdapter.notifyDataSetChanged();
                        }else{

                        }

                        Toaster.customToast(msg);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });

    }


    private void getUpdatecart(int from,int quy) {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kRowId, itemrowId);
        map.put(kproductPrice, productPricee);

        if(from==1){
            map.put(kqty, quy);
        }else{
            map.put(kqty, "");
        }

        Log.e("pamram", map.toString());
        ModelManager.modelManager().getUpdateCart(map,
                (Constants.Status iStatus, GenericResponse<Updatedcartdata> genericResponse) -> {
                    loaderView.hideLoader();
                    try {

                        Updatedcartdata updatedcartdata = genericResponse.getObject();
                        listener.onRemoveCartbadge();

                        Intent intent = new Intent(mContext, HomeActivity.class);
                        intent.putExtra("FROM","1");
                        startActivity(intent);
                        getActivity().finish();


                       /* JSONObject jsonObject = updatedcartdata.getCartdataObject();
                        txt_item_total.setText(jsonObject.getString("sub_total") + " " + "Kz");
                        txt_restorants_charges.setText(jsonObject.getString("restaurant_charges") + " " + "Kz");

                        txt_delivery_fee.setText(jsonObject.getString("delivery_charges") + " " + "Kz");

                        txt_cart_total_fee.setText(jsonObject.getString("total_payable_price") + " " + "Kz");
                        totalpayablePrice= Integer.parseInt(jsonObject.getString("total_payable_price"));*/


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });

    }

    private void getClearAllCart() {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kproductId, productId);
        map.put(kproductPrice, totalpayablePrice);
        Log.e("pamram", map.toString());
        ModelManager.modelManager().getClearAllcart(map,
                (Constants.Status iStatus, GenericResponse<ViewcartModel> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        ViewcartModel viewcartModel = genericResponse.getObject();

                        CopyOnWriteArrayList<CartItemModel> cartItemModels = viewcartModel.getCartItemModels();
                        listener.onRemoveCartbadge();

                        if(cartItemModels.size()==0){
                            rv_no_cart.setVisibility(View.VISIBLE);
                            rl_main.setVisibility(View.GONE);
                        }else {
                            rv_no_cart.setVisibility(View.GONE);
                            rl_main.setVisibility(View.VISIBLE);
                        }
                        /*String msg = genericResponse.getObject().getString(kMessage);
                        Toaster.customToast(msg);*/


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });

    }

    private void menuDialog() {
        // dialog
        final Dialog  dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert_place_order);
        TextView tv_login=dialog.findViewById(R.id.tv_login);

        tv_login.setOnClickListener(v -> {
            setIntentLogin();
        });
        TextView tv_signup=dialog.findViewById(R.id.tv_signup);

        tv_signup.setOnClickListener(v -> {
            setIntentSignUp();
        });

        ImageButton btn_close=dialog.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(v -> {
            dialog.dismiss();
        });


        dialog.show();
    }

    private void setIntentLogin() {
        Intent in = new Intent(mContext, SignInActivity.class);
        in.putExtra("FROM","2");
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);
        getActivity().finish();
    }

    private void setIntentSignUp() {
        Intent intent=new Intent(mContext, SignUpActivity.class);
        intent.putExtra(kLoginType, "N");
        intent.putExtra(kAuthToken,"");
        intent.putExtra(kUserName,"");
        intent.putExtra(kUserEmail,"");
        intent.putExtra(kPhone,"");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();

    }

    private boolean validatead() {
        boolean isOk = true;

        Log.d("itemcount", categoryPriceAdapter.getItemCount() + "");
        if (categoryPriceAdapter.getQuantitysSelectionStatus()) {
            Toaster.customToast("Please select quantity with price!");
            isOk = false;
        }

        return isOk;
    }
    private void getAddtocartt(int from) {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kproductId, productId);
        map.put(kproductPrice, subTotal);
        Log.e(TAG, map.toString());
        ModelManager.modelManager().getAddtocart(map,
                (Constants.Status iStatus, GenericResponse<JSONObject> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        //genericResponse.headers("Set-Cookie");
                        String msg = genericResponse.getObject().getString(kMessage);
                        Toaster.customToast(msg);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });


    }

    private void customizeDialog(int from,CopyOnWriteArrayList<ProductPrice> productPrices, String productName, String mainAttribute, int productId,String selcetedcartPrice,String priceQuantity ) {
        // dialog

        this.productId = productId;

        finalPrice = selcetedcartPrice;

         Dialog  dialog= new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_recommended_menuu);
        RecyclerView rv_myorder = dialog.findViewById(R.id.rv_category_price);
        TextView tv_totalItem = dialog.findViewById(R.id.tv_totalItem);
        TextView tv_updateItem = dialog.findViewById(R.id.tv_updateItem);
        tv_updateItem.setText(getString(R.string.update_item));
        TextView txt_customize_item = dialog.findViewById(R.id.txt_customize_item);
        txt_customize_item.setText(productName);
        TextView tv_item_quantity = dialog.findViewById(R.id.tv_item_quantity);
        tv_item_quantity.setText(finalPrice + " " + "Kz" + " " + "(" + priceQuantity + ")");

        RadioButton radia_id1=dialog.findViewById(R.id.radia_id1);


        ImageButton btn_close=dialog.findViewById(R.id.btn_close);
        btn_close.setVisibility(View.VISIBLE);

        ImageView img_veg = dialog.findViewById(R.id.img_veg);

        if (mainAttribute.equalsIgnoreCase("Veg")) {
            img_veg.setColorFilter(getActivity().getResources().getColor(R.color.green));
        } else {
            img_veg.setColorFilter(getActivity().getResources().getColor(R.color.red));
        }


        rv_myorder.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL));
        rv_myorder.setHasFixedSize(true);
        radia_id1.setVisibility(View.VISIBLE);
        radia_id1.setChecked(true);
        radia_id1.setTextColor(mContext.getResources().getColor(R.color.user_theme_color));
        radia_id1.setText(finalPrice + " " + "Kz" + " " + priceQuantity);
        tv_totalItem.setText("Total" + " kz" + " " + finalPrice);


        /*if(productPrices.size()==1){
            radia_id1.setVisibility(View.GONE);
        }else{
            radia_id1.setVisibility(View.VISIBLE);
            for(int i=0;i<productPrices.size();i++){
                if(productPrices.get(i).getProductprice().equalsIgnoreCase(selcetedcartPrice)){
                    productPrices.remove(i);

                }else{
                    //productPrices.add(i);
                }
            }
        }*/

        for(int i=0;i<productPrices.size();i++){
            if(productPrices.get(i).getProductprice().equalsIgnoreCase(selcetedcartPrice)){
                productPrices.remove(i);
            }else{
                //productPrices.add(i);
            }
        }

        categoryPriceAdapter = new CategoryPriceAdapterr(getActivity(),selcetedcartPrice, productPrices, new CategoryPriceAdapterr.selectItemInterface() {
            @Override
            public void selectItem(String total, String quantity) {
                productPricee = Integer.parseInt(total);
                radia_id1.setChecked(false);

                finalPrice =total;
                radia_id1.setTextColor(mContext.getResources().getColor(R.color.dim_grey));
                tv_totalItem.setText("Total" + " kz" + " " + finalPrice);
                tv_item_quantity.setText(finalPrice + " " + "Kz" + " " + "(" + quantity + ")");

            }


        });


        rv_myorder.setAdapter(categoryPriceAdapter);


        radia_id1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    radia_id1.setChecked(true);
                    radia_id1.setText(selcetedcartPrice + " " + "Kz" + " " + priceQuantity);
                    tv_totalItem.setText("Total" + " kz" + " " + selcetedcartPrice);
                    radia_id1.setTextColor(mContext.getResources().getColor(R.color.user_theme_color));
                    categoryPriceAdapter.itemChcekedStatus();
                    categoryPriceAdapter.notifyDataSetChanged();
                }else{
                    radia_id1.setTextColor(mContext.getResources().getColor(R.color.dim_grey));
                    radia_id1.setChecked(false);
                    categoryPriceAdapter.notifyDataSetChanged();
                }
            }
        });

        tv_updateItem.setOnClickListener(v -> {

            if (validatead()) {
                dialog.dismiss();

                getUpdatecart(from,0);

                // vollyRequestAddtoCart();
            }


        });
        dialog.findViewById(R.id.btn_close).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }


    public void setcartItemkListener(cartItemkListener listener){
        this.listener=listener;
    }

    public interface cartItemkListener{
        void onRemoveCartbadge();

    }

    public void changeFragment(Fragment fragment, boolean addToBackStack, boolean animate, String tag) {
        currentFragment = fragment;
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (animate) {
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        }
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.content_main, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    public class AdvertismentAdapter extends PagerAdapter {

        private Context activity;
        private ArrayList<AdervisementModel> imagesArray;
        String imagepath;

        public AdvertismentAdapter(Context activity, ArrayList<AdervisementModel> imagesArray) {

            this.activity = activity;
            this.imagesArray = imagesArray;
            this.imagepath = imagepath;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            LayoutInflater inflater = ((Activity) activity).getLayoutInflater();

            View viewItem = inflater.inflate(R.layout.image_item_advertsement, container, false);
            ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView);

            Picasso.with(activity).load(imagesArray.get(position).getAdvertiseImage()).placeholder(R.drawable.ic_image_black_24dp).fit().into(imageView);

           /* if (!imagesArray.get(position).isEmpty()) {
                String imgPath = imagepath + "/" + imagesArray.get(position);
                Picasso.with(activity).load(imgPath).placeholder(R.drawable.ic_image_black_24dp).fit().into(imageView);
            } else {
                Picasso.with(activity).load(R.drawable.ic_image_black_24dp).placeholder(R.drawable.ic_image_black_24dp).fit().into(imageView);
            }*/

            ((ViewPager) container).addView(viewItem);

            return viewItem;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imagesArray.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            // TODO Auto-generated method stub
            return view == ((View) object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            ((ViewPager) container).removeView((View) object);
        }
    }
}
