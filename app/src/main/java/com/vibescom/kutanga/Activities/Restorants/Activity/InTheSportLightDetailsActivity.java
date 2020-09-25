package com.vibescom.kutanga.Activities.Restorants.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vibescom.kutanga.Activities.Restorants.Adapter.CategoryPriceAdapter;
import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansHomeInTheSportLightDetailsAdapter;
import com.vibescom.kutanga.Activities.Restorants.Adapter.ResturantscategoryExpandableAdapter;
import com.vibescom.kutanga.ApplicationManager;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.BaseModel;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.OfferData;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.OfferListModel;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ProductPrice;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ResturantsDetailsTypeModel;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ResturantsproductModel;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ReturantsDetailsModel;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ReturantsDetailscategoryModel;
import com.vibescom.kutanga.Models.UpdatedcartModel.Updatedcartdata;
import com.vibescom.kutanga.Models.VendorContactNumber;
import com.vibescom.kutanga.Models.ViewCart.CartItemModel;
import com.vibescom.kutanga.Models.ViewCart.ViewcartModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.Toaster;
import com.vibescom.kutanga.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import static com.vibescom.kutanga.Constants.Constants.kBusinessId;
import static com.vibescom.kutanga.Constants.Constants.kCart;
import static com.vibescom.kutanga.Constants.Constants.kCoupon;
import static com.vibescom.kutanga.Constants.Constants.kData;
import static com.vibescom.kutanga.Constants.Constants.kIsfav;
import static com.vibescom.kutanga.Constants.Constants.kMessage;
import static com.vibescom.kutanga.Constants.Constants.kPrice;
import static com.vibescom.kutanga.Constants.Constants.kQuantity;
import static com.vibescom.kutanga.Constants.Constants.kResponse;
import static com.vibescom.kutanga.Constants.Constants.kRowId;
import static com.vibescom.kutanga.Constants.Constants.kStatus;
import static com.vibescom.kutanga.Constants.Constants.kVendor;
import static com.vibescom.kutanga.Constants.Constants.kVendorId;
import static com.vibescom.kutanga.Constants.Constants.kcartData;
import static com.vibescom.kutanga.Constants.Constants.kcartid;
import static com.vibescom.kutanga.Constants.Constants.kproductId;
import static com.vibescom.kutanga.Constants.Constants.kproductPrice;
import static com.vibescom.kutanga.Constants.Constants.kqty;
import static com.vibescom.kutanga.Constants.Constants.kvVendorBusinessId;

public class InTheSportLightDetailsActivity extends AppCompatActivity {

    public static String TAG = InTheSportLightDetailsActivity.class.getSimpleName();
    private RecyclerView rv_take_your_pic_details;
    private RestautansHomeInTheSportLightDetailsAdapter restautansHomeInTheSportLightDetailsAdapter;
    private ResturantsDetailsTypeModel resturantsDetailsTypeModel;
    private CopyOnWriteArrayList<ResturantsproductModel> childListproductDetails;
    Context context;
    ImageView img_back;
    CustomLoaderView loaderView;
    LinearLayout lv_reviews;
    Dialog dialog;
    TextView tv_trending, tv_not_available, tv_treding_description, tv_question, tv_rating, tv_callus, tv_review, tv_delivery_time, tv_estimate_weight, tv_coupon_code, tv_coupon_discount, tv_item_count, tv_total_item_weight, tv_viewcart;
    int businessId, productId, viewcartproductid, rawId, productPricee;
    private ExpandableListView expandableListView;
    String subTotal, finalPrice;
    RelativeLayout bottom_rel, rl_coupon_availble;
    CheckBox ch_favorite;
    String product_img_path;
    CategoryPriceAdapter categoryPriceAdapter;
    int userid, vendorid, quantity, cartPrice, from;
    String is_fav, vendorBusinessId;
    SharedPreferences sharedPreferences;
    String selcetedcartPricee, priceQuantityy;
    private CopyOnWriteArrayList<CartItemModel> cartItemModelslist;
    ResturantscategoryExpandableAdapter expandableAdapter;
    CopyOnWriteArrayList<CartItemModel> cartItemModels;
    HomeActivity homeActivity;
    VendorContactNumber vendorContactNumber;
    static boolean statusChecked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inthesportdetails);
        context = this;
        sharedPreferences = ApplicationManager.getContext().getSharedPreferences(BaseModel.kAppPreferences, Context.MODE_PRIVATE);
        is_fav = sharedPreferences.getString(kIsfav, "");
        vendorBusinessId = sharedPreferences.getString(kBusinessId, "");
        loaderView = CustomLoaderView.initialize(context);
        businessId = getIntent().getIntExtra(kBusinessId, 0);
        viewcartproductid = sharedPreferences.getInt(kproductId, 0);
        rawId = sharedPreferences.getInt(kcartid, 0);
        cartPrice = sharedPreferences.getInt(kPrice, 0);
        quantity = sharedPreferences.getInt(kQuantity, 0);
        homeActivity = new HomeActivity();
        Log.d("getpro", viewcartproductid + "");

        try {
            userid = ModelManager.modelManager().getCurrentUser().getUserId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        inItView();
        getCouponAvailable();
        getViewCart(1);
        getResturantsDetails();


    }

    @Override
    protected void onResume() {
        super.onResume();
        getViewCart(1);
    }

    private void inItView() {

        expandableListView = findViewById(R.id.expandableListView);
        DisplayMetrics metrics = new DisplayMetrics();
        Objects.requireNonNull(this).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        expandableListView.setIndicatorBounds(width - Utils.getPixelFromDips(60, Objects.requireNonNull(context)),
                width - Utils.getPixelFromDips(15, context));

        tv_trending = findViewById(R.id.tv_trending);
        tv_treding_description = findViewById(R.id.tv_treding_description);
        tv_rating = findViewById(R.id.tv_rating);
        tv_review = findViewById(R.id.tv_review);
        tv_delivery_time = findViewById(R.id.tv_delivery_time);
        tv_estimate_weight = findViewById(R.id.tv_estimate_weight);
        tv_coupon_code = findViewById(R.id.tv_coupon_code);
        tv_not_available = findViewById(R.id.tv_not_available);
        rl_coupon_availble = findViewById(R.id.rl_coupon_availble);

        tv_coupon_discount = findViewById(R.id.tv_coupon_discount);
        tv_item_count = findViewById(R.id.tv_item_count);
        tv_total_item_weight = findViewById(R.id.tv_total_item_weight);
        tv_viewcart = findViewById(R.id.tv_viewcart);
        tv_question = findViewById(R.id.tv_question);
        tv_callus = findViewById(R.id.tv_callus);

        ch_favorite = findViewById(R.id.ch_favorite);

        if (is_fav.equalsIgnoreCase("1") && vendorBusinessId.equalsIgnoreCase(String.valueOf(businessId))) {
            ch_favorite.setChecked(true);
        } else {
            ch_favorite.setChecked(false);
        }


        ch_favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    if (userid == 0) {
                        Toaster.customToast("Please Login First!");
                        ch_favorite.setChecked(false);
                    } else {
                        ch_favorite.setChecked(true);

                        if (ch_favorite.isChecked()) {
                            getAddfavroute();
                        } else {
                            getAddfavroute();
                        }


                    }


                } else {
                    getAddfavroute();
                }
            }
        });

        tv_viewcart.setOnClickListener(v -> {

            Intent intent = new Intent(context, HomeActivity.class);
            intent.putExtra("FROM", "1");
            startActivity(intent);
            finish();

        });
        bottom_rel = findViewById(R.id.bottom_rel);

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(v -> {
            finish();
        });

        lv_reviews = findViewById(R.id.lv_reviews);
        lv_reviews.setOnClickListener(v -> {
            Log.d("Vendorid1", vendorid + "");
            Intent intent = new Intent(context, ReviewActivity.class);
            intent.putExtra(kVendorId, vendorid);
            startActivity(intent);
        });


    }


    private void getResturantsDetails() {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put("business_id", businessId);
        Log.e(TAG, map.toString());
        ModelManager.modelManager().getResturantsDetails(map,
                (Constants.Status iStatus, GenericResponse<ReturantsDetailsModel> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        JSONObject jsonObject = genericResponse.getObject().getResturantsDetailsTypeModels();

                        CopyOnWriteArrayList<ReturantsDetailscategoryModel> returantsDetailscategoryModels = genericResponse.getObject().getReturantsDetailscategoryModels();

                        product_img_path = genericResponse.getObject().getProductImgPath();

                        resturantsDetailsTypeModel = new ResturantsDetailsTypeModel(jsonObject);
                        tv_question.setText(resturantsDetailsTypeModel.getContactEmailid());
                        vendorid = Integer.parseInt(resturantsDetailsTypeModel.getVendorid());


                        tv_trending.setText(resturantsDetailsTypeModel.getCompanyName());
                        tv_treding_description.setText(resturantsDetailsTypeModel.getAddress());

                        if (resturantsDetailsTypeModel.getRating().equalsIgnoreCase("")) {
                            tv_rating.setText("0");
                        } else {
                            tv_rating.setText(String.valueOf(Math.ceil(Double.parseDouble(resturantsDetailsTypeModel.getRating()))));
                        }

                        if (resturantsDetailsTypeModel.getReviewCount().equalsIgnoreCase("")) {
                            tv_review.setText("0");
                        } else {
                            tv_review.setText(resturantsDetailsTypeModel.getReviewCount() + " " + "Reviews");
                        }

                        if (resturantsDetailsTypeModel.getEstimateDeliveryTime().equalsIgnoreCase("")) {
                            tv_delivery_time.setText("30 Mins");
                        } else {
                            tv_delivery_time.setText(resturantsDetailsTypeModel.getEstimateDeliveryTime() + " " + "Mins");
                        }
                        if (resturantsDetailsTypeModel.getMinOrder().equalsIgnoreCase("")) {
                            tv_estimate_weight.setText("kz 10");
                        } else {
                            tv_estimate_weight.setText("kz" + " " + resturantsDetailsTypeModel.getMinOrder());
                        }

                        JSONObject jsonObject3 = jsonObject.getJSONObject(kVendor);

                        vendorContactNumber = new VendorContactNumber(jsonObject3);
                        tv_callus.setText(vendorContactNumber.getMobileNo());
                        //Log.d("Vendorid",vendorid+"/"+jsonObject1+""+jsonObject1.getString("vendor_mobile"));
                        InitSlotsBatchView(returantsDetailscategoryModels);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }

    private void menuDialog(int from, CopyOnWriteArrayList<ProductPrice> productPrices, String productName, String mainAttribute, int productId) {
        // dialog

        this.productId = productId;
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_recommended_menu);
        RecyclerView rv_myorder = dialog.findViewById(R.id.rv_category_price);
        TextView tv_totalItem = dialog.findViewById(R.id.tv_totalItem);
        TextView tv_updateItem = dialog.findViewById(R.id.tv_updateItem);

        RadioButton radia_id1 = dialog.findViewById(R.id.radia_id1);


        if (from == 1) {
            tv_updateItem.setText("Add Item");
        } else {
            tv_updateItem.setText("Update Item");
        }

        TextView txt_customize_item = dialog.findViewById(R.id.txt_customize_item);
        txt_customize_item.setText(productName);
        TextView tv_item_quantity = dialog.findViewById(R.id.tv_item_quantity);
        ImageView img_veg = dialog.findViewById(R.id.img_veg);

        if (mainAttribute.equalsIgnoreCase("Veg")) {
            img_veg.setColorFilter(context.getResources().getColor(R.color.green));
        } else {
            img_veg.setColorFilter(context.getResources().getColor(R.color.red));
        }


        if (productPrices.size() == 1) {
            radia_id1.setVisibility(View.GONE);
        } else {
            radia_id1.setVisibility(View.VISIBLE);
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < productPrices.size(); i++) {
                list.add(Integer.parseInt(productPrices.get(i).getProductprice()));
            }


            int min = Collections.min(list);

            Log.d("Min", min + "/" + productPrices.size());

            for (int i = 0; i < productPrices.size(); i++) {
                if (Integer.parseInt(productPrices.get(i).getProductprice()) == min) {
                    radia_id1.setChecked(true);
                    statusChecked = true;
                    selcetedcartPricee = String.valueOf(min);
                    subTotal = selcetedcartPricee;
                    priceQuantityy = productPrices.get(i).getQuantity();
                    tv_totalItem.setText("Total" + " kz" + " " + selcetedcartPricee);
                    radia_id1.setText(min + " " + "Kz" + " " + "(" + productPrices.get(i).getQuantity() + ")");
                    radia_id1.setTextColor(context.getResources().getColor(R.color.user_theme_color));
                    tv_item_quantity.setText(min + " " + "Kz" + " " + "(" + productPrices.get(i).getQuantity() + ")");
                    productPrices.remove(i);
                    //break;
                } else {

                }
            }
        }


        rv_myorder.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL));
        rv_myorder.setHasFixedSize(true);

        categoryPriceAdapter = new CategoryPriceAdapter(context, productPrices, new CategoryPriceAdapter.selectItemInterface() {
            @Override
            public void selectItem(String total, String quantity) {
                subTotal = total;
                priceQuantityy = quantity;
                radia_id1.setChecked(false);
                radia_id1.setTextColor(context.getResources().getColor(R.color.dim_grey));
                tv_totalItem.setText("Total" + " kz" + " " + total);
                tv_item_quantity.setText(total + " " + "Kz" + " " + "(" + quantity + ")");

            }
        });
        rv_myorder.setAdapter(categoryPriceAdapter);


        radia_id1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radia_id1.setChecked(true);
                    statusChecked = true;
                    radia_id1.setTextColor(context.getResources().getColor(R.color.user_theme_color));
                    tv_item_quantity.setText(selcetedcartPricee + " " + "Kz" + " " + "(" + priceQuantityy + ")");
                    radia_id1.setText(selcetedcartPricee + " " + "Kz" + " " + "(" + priceQuantityy + ")");
                    tv_totalItem.setText("Total" + " kz" + " " + selcetedcartPricee);
                    categoryPriceAdapter.itemChcekedStatus();
                    categoryPriceAdapter.notifyDataSetChanged();
                } else {
                    radia_id1.setTextColor(context.getResources().getColor(R.color.dim_grey));
                    radia_id1.setChecked(false);
                    statusChecked = false;
                    categoryPriceAdapter.notifyDataSetChanged();
                }
            }
        });

        tv_updateItem.setOnClickListener(v -> {
            if (validatead()) {
                dialog.dismiss();
                getAddtocartt();
            }


        });
        //dialog.findViewById(R.id.btn_close).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }


    private void menuDialogCustomize(int from, int rawid, int selectedPrice, String priceQuantity, CopyOnWriteArrayList<ProductPrice> productPrices, String productName, String mainAttribute, int productId) {
        // dialog

        this.productId = productId;
        finalPrice = String.valueOf(selectedPrice);
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_recommended_menu);
        RecyclerView rv_myorder = dialog.findViewById(R.id.rv_category_price);
        TextView tv_totalItem = dialog.findViewById(R.id.tv_totalItem);
        TextView tv_updateItem = dialog.findViewById(R.id.tv_updateItem);

        RadioButton radia_id1 = dialog.findViewById(R.id.radia_id1);
        radia_id1.setVisibility(View.VISIBLE);
        radia_id1.setChecked(true);
        radia_id1.setTextColor(context.getResources().getColor(R.color.user_theme_color));
        radia_id1.setText(finalPrice + " " + "Kz" + " " + priceQuantity);
        tv_totalItem.setText("Total" + " kz" + " " + finalPrice);


        tv_updateItem.setText("Update Item");

        TextView txt_customize_item = dialog.findViewById(R.id.txt_customize_item);
        txt_customize_item.setText(productName);
        TextView tv_item_quantity = dialog.findViewById(R.id.tv_item_quantity);
        tv_item_quantity.setText(finalPrice + " " + "Kz" + " " + "(" + priceQuantity + ")");
        ImageView img_veg = dialog.findViewById(R.id.img_veg);

        if (mainAttribute.equalsIgnoreCase("Veg")) {
            img_veg.setColorFilter(context.getResources().getColor(R.color.green));
        } else {
            img_veg.setColorFilter(context.getResources().getColor(R.color.red));
        }


        for (int i = 0; i < productPrices.size(); i++) {
            if (productPrices.get(i).getProductprice().equalsIgnoreCase(finalPrice)) {
                productPrices.remove(i);
                //break;
            } else {
                //productPrices.add(i);
            }
        }


        rv_myorder.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL));
        rv_myorder.setHasFixedSize(true);

        categoryPriceAdapter = new CategoryPriceAdapter(context, productPrices, new CategoryPriceAdapter.selectItemInterface() {
            @Override
            public void selectItem(String total, String quantity) {
                productPricee = Integer.parseInt(total);
                radia_id1.setChecked(false);

                finalPrice = total;
                radia_id1.setTextColor(context.getResources().getColor(R.color.dim_grey));
                tv_totalItem.setText("Total" + " kz" + " " + finalPrice);
                tv_item_quantity.setText(finalPrice + " " + "Kz" + " " + "(" + quantity + ")");

            }
        });
        rv_myorder.setAdapter(categoryPriceAdapter);


        radia_id1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radia_id1.setChecked(true);
                    radia_id1.setText(finalPrice + " " + "Kz" + " " + priceQuantity);
                    tv_totalItem.setText("Total" + " kz" + " " + finalPrice);
                    radia_id1.setTextColor(context.getResources().getColor(R.color.user_theme_color));
                    categoryPriceAdapter.itemChcekedStatus();
                    categoryPriceAdapter.notifyDataSetChanged();
                } else {
                    radia_id1.setTextColor(context.getResources().getColor(R.color.dim_grey));
                    radia_id1.setChecked(false);
                    categoryPriceAdapter.notifyDataSetChanged();
                }
            }
        });

        tv_updateItem.setOnClickListener(v -> {
            dialog.dismiss();

            getUpdatecart(from, rawid, quantity);
            //getAddtocartt();
            /*if (validatead()) {
                // vollyRequestAddtoCart();
            }*/


        });
        //dialog.findViewById(R.id.btn_close).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }


    private void menuDialogg(CopyOnWriteArrayList<ProductPrice> productPrices, String productName, String mainAttribute, int productId) {
        // dialog

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert_delete);
        dialog.setCancelable(false);

        Button btn_yes = dialog.findViewById(R.id.btn_yes);

        btn_yes.setOnClickListener(v -> {
            dialog.dismiss();
            from = 1;
            menuDialog(from, productPrices, productName, mainAttribute, productId);
        });
        Button btn_no = dialog.findViewById(R.id.btn_no);

        btn_no.setOnClickListener(v -> {
            dialog.dismiss();

        });


        dialog.show();
    }

    private boolean validatead() {
        boolean isOk = true;

        Log.d("itemcount", categoryPriceAdapter.getItemCount() + "" + productId + "?" + subTotal);
        if (productId == 0 || subTotal == null) {
            Toaster.customToast("Please select quantity with price!");
            isOk = false;
        }

        return isOk;
    }


    private void getAddtocartt() {
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

                        JSONObject jsonObject = genericResponse.getObject().getJSONObject(kResponse);

                        JSONObject cartObject = jsonObject.getJSONObject(kcartData);
                        JSONArray array = cartObject.getJSONArray(kCart);

                        CartItemModel cartItemModel = null;

                        cartItemModelslist = new CopyOnWriteArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            cartItemModel = new CartItemModel(array.getJSONObject(i));
                        }
                        cartItemModelslist.add(cartItemModel);

                        JSONObject jsonObjectt = null;

                        for (int i = 0; i < cartItemModelslist.size(); i++) {
                            jsonObjectt = cartItemModelslist.get(i).getAssocialteModel();
                            rawId = cartItemModelslist.get(i).getCartid();
                            cartPrice = cartItemModelslist.get(i).getCartPrice();
                            quantity = cartItemModelslist.get(i).getCartQuantity();

                        }

                        viewcartproductid = jsonObjectt.getInt(kproductId);
                        finish();
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        //getViewCart(2);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });


    }

    private void getAddChcekItem(CopyOnWriteArrayList<ProductPrice> productPrices, String productname, String mainattribute, int productId) {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kproductId, productId);
        Log.e(TAG, map.toString());
        ModelManager.modelManager().getCheckedItem(map,
                (Constants.Status iStatus, GenericResponse<JSONObject> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        //genericResponse.headers("Set-Cookie");
                        String msg = genericResponse.getObject().getString(kMessage);
                        String status = genericResponse.getObject().getString(kStatus);

                        Log.d("Status", status);

                        if (msg.equalsIgnoreCase("success") && status.equalsIgnoreCase("1")) {

                            from = 1;
                            menuDialog(from, productPrices, productname, mainattribute, productId);
                        } else {
                            menuDialogg(productPrices, productname, mainattribute, productId);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    menuDialogg(productPrices, productname, mainattribute, productId);
                    //Toaster.customToast(message);
                });


    }

    private void InitSlotsBatchView(CopyOnWriteArrayList<ReturantsDetailscategoryModel> list) {
        Utils.parentItems = new CopyOnWriteArrayList<>();
        Utils.parentItems = list;
        Utils.childItems = new CopyOnWriteArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put(kData, list.get(i).getResturantsproductModels());
            Utils.childItems.add(map);
        }


        CopyOnWriteArrayList<HashMap<String, Object>> childList = Utils.childItems;


        expandableAdapter = new ResturantscategoryExpandableAdapter(cartItemModels, cartPrice, product_img_path, context, list, childList, new ResturantscategoryExpandableAdapter.AddItemClickInterfase() {
            @Override
            public void itemcuatomize(int rawid, int selectedPrice, String priceQuantity, CopyOnWriteArrayList<ProductPrice> productPrices, String productName, String mainAttribute, int productId) {
                from = 2;
                menuDialogCustomize(2, rawid, selectedPrice, priceQuantity, productPrices, productName, mainAttribute, productId);

            }

            @Override
            public void onRemovecartItem(int position, int rowId) {
                rawId = rowId;
                getRemoveitem(position);
            }

            @Override
            public void updatecrat(int rowId, int productPrice, int quantity) {
                rawId = rowId;
                finalPrice = String.valueOf(productPrice);
                quantity = quantity;
                Log.d("Qay", quantity + "");
                getUpdatecart(1, rawId, quantity);
            }

            @Override
            public void addItemclick(CopyOnWriteArrayList<ProductPrice> productPrices, String productname, String mainattribute, int productId) {

                getAddChcekItem(productPrices, productname, mainattribute, productId);
            }
        });
        expandableListView.setAdapter(expandableAdapter);

        for (int i = 0; i < expandableAdapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i, true);
        }

    }

    private void getAddfavroute() {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kvVendorBusinessId, businessId);

        Log.d("Request", map.toString());
        ModelManager.modelManager().getAddFavroute(map,
                (Constants.Status iStatus, GenericResponse<JSONObject> genericResponse) -> {
                    loaderView.hideLoader();
                    try {

                        JSONObject obj = genericResponse.getObject();

                        String msg = obj.getString(kMessage);

                        Toaster.customToast(msg);

                        JSONObject jsonObject = obj.getJSONObject(kResponse);


                        if (jsonObject.has(kIsfav)) {
                            is_fav = jsonObject.getString(kIsfav);
                        }

                        if (jsonObject.has(kBusinessId)) {
                            vendorBusinessId = jsonObject.getString(kBusinessId);
                        }

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(kIsfav, is_fav);
                        editor.putString(kBusinessId, vendorBusinessId);
                        editor.apply();

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
        map.put(kRowId, rawId);
        Log.e("Send param", map.toString());
        ModelManager.modelManager().getRemovecartItem(map,
                (Constants.Status iStatus, GenericResponse<JSONObject> genericResponse) -> {
                    loaderView.hideLoader();
                    try {

                        //String msg = genericResponse.getObject().getString(kMessage);
                        finish();
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        JSONArray jsonArray = genericResponse.getObject().getJSONArray(kResponse);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                        bottom_rel.setVisibility(View.GONE);
                        /*tv_total_item_weight.setText(subTotal + " kz");*/
                        tv_total_item_weight.setText(jsonObject.getString("sub_total") + " " + "Kz");


                       /* txt_restorants_charges.setText(jsonObject.getString("restaurant_charges") + " " + "Kz");

                        txt_delivery_fee.setText(jsonObject.getString("delivery_charges") + " " + "Kz");

                        txt_cart_total_fee.setText(jsonObject.getString("total_payable_price") + " " + "Kz");

                        totalpayablePrice= Integer.parseInt(jsonObject.getString("total_payable_price"));*/


                        //Toaster.customToast(msg);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });

    }

    private void getUpdatecart(int form, int rawId, int quantity) {
        Log.d("Quantity", quantity + "");
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kRowId, rawId);
        map.put(kproductPrice, finalPrice);

        if (form == 1) {
            map.put(kqty, quantity);
        } else {
            map.put(kqty, "");
        }
        Log.e("pamram", map.toString());
        ModelManager.modelManager().getUpdateCart(map,
                (Constants.Status iStatus, GenericResponse<Updatedcartdata> genericResponse) -> {
                    loaderView.hideLoader();
                    try {

                        Updatedcartdata updatedcartdata = genericResponse.getObject();

                        JSONObject jsonObject = updatedcartdata.getCartdataObject();
                        //homeActivity.getUpdatebadge();
                        //listener.onRemoveCartbadgeDetails();
                        bottom_rel.setVisibility(View.VISIBLE);
                        tv_total_item_weight.setText(subTotal + " kz");
                        tv_total_item_weight.setText(jsonObject.getString("sub_total") + " " + "Kz");
                        /*finish();
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);*/
                        //getViewCart(2);


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
                        CopyOnWriteArrayList<OfferData> offerData = offerListModel.getOfferData();


                        if (offerData.size() > 0) {
                            tv_not_available.setVisibility(View.GONE);
                            rl_coupon_availble.setVisibility(View.VISIBLE);
                            for (int i = 0; i < offerData.size(); i++) {
                                tv_coupon_code.setText(offerData.get(0).getPromoCode());
                                tv_coupon_discount.setText(offerData.get(0).getOffferDiscount() + " " + context.getResources().getString(R.string.of) + " " + offerData.get(0).getMinOffer() + " " + context.getResources().getString(R.string.kg));
                            }
                        } else {
                            tv_not_available.setVisibility(View.VISIBLE);
                            rl_coupon_availble.setVisibility(View.GONE);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {

                    Toaster.customToast(message);
                });


    }


    private void getViewCart(int from) {
        //loaderView.showLoader();
        ModelManager.modelManager().getViewcart(
                (Constants.Status iStatus, GenericResponse<ViewcartModel> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        ViewcartModel viewcartModel = genericResponse.getObject();
                        cartItemModels = viewcartModel.getCartItemModels();

                        if (cartItemModels.isEmpty()) {
                            bottom_rel.setVisibility(View.GONE);
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    bottom_rel.setVisibility(View.VISIBLE);
                                    tv_total_item_weight.setText(subTotal + " kz");
                                    tv_total_item_weight.setText(viewcartModel.getSubTotal() + " " + "Kz");
                                }
                            }, 2000);


                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    //loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }

}
