package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.vibescom.kutanga.Activities.Restorants.Activity.HomeActivity;
import com.vibescom.kutanga.Activities.Restorants.Fragment.RestorantsSearchFragment;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ProductPrice;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.SearchDishesModel;
import com.vibescom.kutanga.Models.SearchDishesModel.ResturantsDishesproductModel;
import com.vibescom.kutanga.Models.ViewCart.CartItemModel;
import com.vibescom.kutanga.Models.ViewCart.ViewcartModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.SpaceItemDecoration;
import com.vibescom.kutanga.Utils.Toaster;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import static com.vibescom.kutanga.Constants.Constants.kMessage;
import static com.vibescom.kutanga.Constants.Constants.kResponse;
import static com.vibescom.kutanga.Constants.Constants.kRowId;
import static com.vibescom.kutanga.Constants.Constants.kStatus;
import static com.vibescom.kutanga.Constants.Constants.kproductId;
import static com.vibescom.kutanga.Constants.Constants.kproductPrice;
import static com.vibescom.kutanga.Constants.Constants.kqty;

public class DishesSearcAdapter extends RecyclerView.Adapter<DishesSearcAdapter.myViewHolder> {
    private Context context;
    private CopyOnWriteArrayList<SearchDishesModel> searchDishesModel;
    private String imagepath,booking_order_status;
    CopyOnWriteArrayList<CartItemModel> cartItemModels;
    DishesSearcchildAdapter dishesSearcAdapter;
    CopyOnWriteArrayList<ResturantsDishesproductModel>data;
    CustomLoaderView loaderView;
    int userid,vendorid,quantity,cartPrice,from, productId,productPricee;
    int businessId,viewcartproductid,rawId;
    CategoryPriceAdapter categoryPriceAdapter;
    String subTotal,product_img_path;
    Dialog dialog;
    String selcetedcartPricee,priceQuantityy,finalPrice;

    private CopyOnWriteArrayList<CartItemModel>cartItemModelslist;
    RestorantsSearchFragment.cartNotificationListener listener;
    public DishesSearcAdapter(RestorantsSearchFragment.cartNotificationListener listener,Context context, String imagepath, CopyOnWriteArrayList<SearchDishesModel> searchDishesModel, CopyOnWriteArrayList<CartItemModel> cartItemModels) {
        this.context = context;
        this.listener=listener;
        this.searchDishesModel=searchDishesModel;
        this.cartItemModels=cartItemModels;
        this.imagepath=imagepath;
        loaderView=CustomLoaderView.initialize(context);
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.row_expandable_list_group_dishes, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {
        SearchDishesModel parent = searchDishesModel.get(position);
        data= parent.getResturantsproductModels();
        dishesSearcAdapter=new DishesSearcchildAdapter(context,cartPrice, imagepath, data, cartItemModels, new DishesSearcchildAdapter.AddItemClickInterfase() {
            @Override
            public void itemcuatomize(int rawid,int selectedPrice,String priceQuantity,CopyOnWriteArrayList<ProductPrice> productPrices, String productName, String mainAttribute, int productId) {
                rawId = rawid;
                menuDialogCustomize(rawid,selectedPrice,priceQuantity,productPrices, productName, mainAttribute, productId);
            }

            @Override
            public void onRemovecartItem(int position, int rowId) {
                rawId = rowId;
                getRemoveitem(position);
            }

            @Override
            public void updatecrat(int rowId, int productPrice, int quantity) {
                rawId = rowId;
                finalPrice =String.valueOf(productPrice);
                getUpdatecart(1,quantity);
            }

            @Override
            public void addItemclick(CopyOnWriteArrayList<ProductPrice> productPrices, String productName, String mainAttribute, int productId) {
                getAddChcekItem(productPrices, productName, mainAttribute, productId);
            }
        });
        myViewHolder.child_reclerView.setAdapter(dishesSearcAdapter);
        myViewHolder.tvMainCategoryName.setText(parent.getCompanyName());
        myViewHolder.tv_delivery_time.setText(parent.getDistanceTobeCovered()+" "+context.getResources().getString(R.string.min));
        myViewHolder.tv_address.setText(parent.getAddress());
        myViewHolder.tv_rating.setText(parent.getRating());
        myViewHolder.tv_review.setText(parent.getReviewCount()+" "+context.getResources().getString(R.string.reviews));

    }

    @Override
    public int getItemCount() {
        return searchDishesModel.size();
    }

    public void addData(CopyOnWriteArrayList<SearchDishesModel> searchDishesModel,String imagepath) {
        searchDishesModel.addAll(searchDishesModel);
        this.imagepath=imagepath;
        notifyDataSetChanged();
    }


    private void setRecyclerView(){
        //main_reclerView.addOnScrollListener(onScrollListener);
    }
    public void removeItem(int position) {
        searchDishesModel.remove(position);
        notifyItemRemoved(position);
    }
    public void newData( CopyOnWriteArrayList<SearchDishesModel> searchDishesModel, String imagepath) {
        searchDishesModel.clear();
        this.imagepath=imagepath;
        searchDishesModel.addAll(searchDishesModel);
        notifyDataSetChanged();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView tvMainCategoryName,tv_delivery_time,tv_address,tv_rating,tv_review;
        RecyclerView child_reclerView;
        private LinearLayoutManager mLayoutManager;
        private myViewHolder(View itemView) {
            super(itemView);

            child_reclerView=itemView.findViewById(R.id.child_reclerView);
            mLayoutManager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
            child_reclerView.setLayoutManager(mLayoutManager);
            child_reclerView.addItemDecoration(new SpaceItemDecoration(5));
            child_reclerView.setHasFixedSize(true);
            tvMainCategoryName = itemView.findViewById(R.id.tv_category_name);
            tv_delivery_time = itemView.findViewById(R.id.tv_delivery_time);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_rating = itemView.findViewById(R.id.tv_rating);
            tv_review = itemView.findViewById(R.id.tv_review);


        }
    }

    private void menuDialogCustomize(int rawid,int selectedPrice,String priceQuantity,CopyOnWriteArrayList<ProductPrice> productPrices, String productName, String mainAttribute, int productId) {
        // dialog
        this.productId = productId;
        finalPrice = String.valueOf(selectedPrice);
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_recommended_menuu);
        RecyclerView rv_myorder = dialog.findViewById(R.id.rv_category_price);
        TextView tv_totalItem = dialog.findViewById(R.id.tv_totalItem);
        TextView tv_updateItem = dialog.findViewById(R.id.tv_updateItem);
        ImageButton btn_close=dialog.findViewById(R.id.btn_close);
        btn_close.setVisibility(View.VISIBLE);
        RadioButton radia_id1=dialog.findViewById(R.id.radia_id1);
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


        for(int i=0;i<productPrices.size();i++){
            if(productPrices.get(i).getProductprice().equalsIgnoreCase(finalPrice)){
                productPrices.remove(i);
                //break;
            }else{
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

                finalPrice =total;
                radia_id1.setTextColor(context.getResources().getColor(R.color.dim_grey));
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
                    radia_id1.setText(finalPrice + " " + "Kz" + " " + priceQuantity);
                    tv_totalItem.setText("Total" + " kz" + " " + finalPrice);
                    radia_id1.setTextColor(context.getResources().getColor(R.color.user_theme_color));
                    categoryPriceAdapter.itemChcekedStatus();
                    categoryPriceAdapter.notifyDataSetChanged();
                }else{
                    radia_id1.setTextColor(context.getResources().getColor(R.color.dim_grey));
                    radia_id1.setChecked(false);
                    categoryPriceAdapter.notifyDataSetChanged();
                }
            }
        });

        tv_updateItem.setOnClickListener(v -> {
            dialog.dismiss();
            getUpdatecart(2,rawid);
        });
        btn_close.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void menuDialog(CopyOnWriteArrayList<ProductPrice> productPrices, String productName, String mainAttribute, int productId) {
        // dialog

        this.productId = productId;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_recommended_menu);
        RecyclerView rv_myorder = dialog.findViewById(R.id.rv_category_price);
        TextView tv_totalItem = dialog.findViewById(R.id.tv_totalItem);
        TextView tv_updateItem = dialog.findViewById(R.id.tv_updateItem);

        RadioButton radia_id1=dialog.findViewById(R.id.radia_id1);



        if(from==1){
            tv_updateItem.setText("Add Item");
        }else{
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



        if(productPrices.size()==1){
            radia_id1.setVisibility(View.GONE);
        }else{
            radia_id1.setVisibility(View.VISIBLE);
            List<Integer> list=new ArrayList<>();
            for(int i=0;i<productPrices.size();i++){
                list.add(Integer.parseInt(productPrices.get(i).getProductprice()));
            }


            int min= Collections.min(list);

            Log.d("Min",min+"/"+productPrices.size());

            for(int i=0;i<productPrices.size();i++){
                if(Integer.parseInt(productPrices.get(i).getProductprice())==min){
                    radia_id1.setChecked(true);
                    selcetedcartPricee=String.valueOf(min);
                    subTotal=selcetedcartPricee;
                    priceQuantityy=productPrices.get(i).getQuantity();
                    tv_totalItem.setText("Total" + " kz" + " " + selcetedcartPricee);
                    radia_id1.setText(min + " " + "Kz" + " " + "(" + productPrices.get(i).getQuantity() + ")");
                    radia_id1.setTextColor(context.getResources().getColor(R.color.user_theme_color));
                    tv_item_quantity.setText(min + " " + "Kz" + " " + "(" + productPrices.get(i).getQuantity() + ")");
                    productPrices.remove(i);
                    //break;
                }else{

                }
            }
        }




        rv_myorder.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL));
        rv_myorder.setHasFixedSize(true);

        categoryPriceAdapter = new CategoryPriceAdapter(context, productPrices, new CategoryPriceAdapter.selectItemInterface() {
            @Override
            public void selectItem(String total, String quantity) {
                subTotal = total;
                priceQuantityy=quantity;
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
                if(isChecked){
                    radia_id1.setChecked(true);
                    radia_id1.setTextColor(context.getResources().getColor(R.color.user_theme_color));
                    tv_item_quantity.setText(selcetedcartPricee + " " + "Kz" + " " + "(" + priceQuantityy + ")");
                    radia_id1.setText(selcetedcartPricee + " " + "Kz" + " " +"(" + priceQuantityy+ ")");
                    tv_totalItem.setText("Total" + " kz" + " " + selcetedcartPricee);
                    categoryPriceAdapter.itemChcekedStatus();
                    categoryPriceAdapter.notifyDataSetChanged();
                }else{
                    radia_id1.setTextColor(context.getResources().getColor(R.color.dim_grey));
                    radia_id1.setChecked(false);
                    categoryPriceAdapter.notifyDataSetChanged();
                }
            }
        });

        tv_updateItem.setOnClickListener(v -> {
            dialog.dismiss();

            getAddtocartt();
            /*if (validatead()) {
                // vollyRequestAddtoCart();
            }*/


        });
        //dialog.findViewById(R.id.btn_close).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
    private void menuDialogg(CopyOnWriteArrayList<ProductPrice> productPrices, String productName, String mainAttribute, int productId) {
        // dialog

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert_delete);
        dialog.setCancelable(false);

        Button btn_yes=dialog.findViewById(R.id.btn_yes);

        btn_yes.setOnClickListener(v -> {
            dialog.dismiss();
            from=1;
            menuDialog(productPrices, productName, mainAttribute, productId);
        });
        Button btn_no=dialog.findViewById(R.id.btn_no);

        btn_no.setOnClickListener(v -> {
            dialog.dismiss();

        });



        dialog.show();
    }

    private void getAddtocartt() {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kproductId, productId);
        map.put(kproductPrice, subTotal);
        Log.e("Send", map.toString());
        ModelManager.modelManager().getAddtocart(map,
                (Constants.Status iStatus, GenericResponse<JSONObject> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        //genericResponse.headers("Set-Cookie");
                      /*  String msg = genericResponse.getObject().getString(kMessage);


                        JSONObject jsonObject=genericResponse.getObject().getJSONObject(kResponse);


                        JSONObject cartObject=jsonObject.getJSONObject(kcartData);
                        JSONArray array=cartObject.getJSONArray(kCart);*/
                        listener.onUpdateCartbadge();
                        Intent intent=new Intent(context, HomeActivity.class);
                        intent.putExtra("FROM","1");
                        context.startActivity(intent);
                        ((Activity)context).finish();


                       // new RestorantsSearchFragment();
                        /*CartItemModel  cartItemModel=null;

                        cartItemModelslist=new CopyOnWriteArrayList<>();
                        for(int i=0;i<array.length();i++){
                            cartItemModel=new CartItemModel(array.getJSONObject(i));
                        }
                        cartItemModelslist.add(cartItemModel);

                        JSONObject jsonObjectt = null;

                        for (int i = 0; i < cartItemModelslist.size(); i++) {
                            jsonObjectt = cartItemModelslist.get(i).getAssocialteModel();
                            rawId=cartItemModelslist.get(i).getCartid();
                            cartPrice=cartItemModelslist.get(i).getCartPrice();
                            quantity=cartItemModelslist.get(i).getCartQuantity();
                        }
                        viewcartproductid=jsonObjectt.getInt(kproductId);*/

                        //getViewCart();

                     /*   bottom_rel.setVisibility(View.VISIBLE);
                        tv_total_item_weight.setText(subTotal + " kz");*/

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
        Log.e("Send", map.toString());
        ModelManager.modelManager().getCheckedItem(map,
                (Constants.Status iStatus, GenericResponse<JSONObject> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        //genericResponse.headers("Set-Cookie");
                        String msg = genericResponse.getObject().getString(kMessage);
                        String status=genericResponse.getObject().getString(kStatus);
                        if(msg.equalsIgnoreCase("success") && status.equalsIgnoreCase("1")){
                            from=1;
                            menuDialog(productPrices, productname, mainattribute, productId);
                        }else{
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

    private void getRemoveitem(int pos) {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kRowId, rawId);
        Log.e("Send param", map.toString());
        ModelManager.modelManager().getRemovecartItem(map,
                (Constants.Status iStatus, GenericResponse<JSONObject> genericResponse) -> {
                    loaderView.hideLoader();
                    try {

                        String msg = genericResponse.getObject().getString(kMessage);


                        JSONArray jsonArray=genericResponse.getObject().getJSONArray(kResponse);

                       listener.onUpdateCartbadge();



                        /*bottom_rel.setVisibility(View.VISIBLE);
                        tv_total_item_weight.setText(subTotal + " kz");
                        tv_total_item_weight.setText(jsonObject.getString("sub_total") + " " + "Kz");*/



                        /*txt_restorants_charges.setText(jsonObject.getString("restaurant_charges") + " " + "Kz");

                        txt_delivery_fee.setText(jsonObject.getString("delivery_charges") + " " + "Kz");

                        txt_cart_total_fee.setText(jsonObject.getString("total_payable_price") + " " + "Kz");

                        totalpayablePrice= Integer.parseInt(jsonObject.getString("total_payable_price"));*/


                        Toaster.customToast(msg);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });

    }

    private void getUpdatecart(int from,int quantity) {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kRowId, rawId);
        map.put(kproductPrice, finalPrice);

        if(from==1){
            map.put(kqty, quantity);
        }else{
            map.put(kqty, "");
        }


        Log.e("pamram", map.toString());
        ModelManager.modelManager().getUpdateCartt(map,
                (Constants.Status iStatus, GenericResponse<JSONObject> genericResponse) -> {
                    loaderView.hideLoader();
                    try {

                        String msg = genericResponse.getObject().getString(kMessage);
                        listener.onUpdateCartbadge();
                        //JSONObject jsonObject = updatedcartdata.getCartdataObject();

                        /*bottom_rel.setVisibility(View.VISIBLE);
                        tv_total_item_weight.setText(subTotal + " kz");
                        tv_total_item_weight.setText(jsonObject.getString("sub_total") + " " + "Kz");*/
                     /*   txt_item_total.setText(jsonObject.getString("sub_total") + " " + "Kz");
                        txt_restorants_charges.setText(jsonObject.getString("restaurant_charges") + " " + "Kz");

                        txt_delivery_fee.setText(jsonObject.getString("delivery_charges") + " " + "Kz");

                        txt_cart_total_fee.setText(jsonObject.getString("total_payable_price") + " " + "Kz");
                        totalpayablePrice= Integer.parseInt(jsonObject.getString("total_payable_price"));*/

                        //String msg = genericResponse.getObject().getString(kMessage);
                        Toaster.customToast(msg);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });

    }

    private void getViewCart() {
        //loaderView.showLoader();
        ModelManager.modelManager().getViewcart(
                (Constants.Status iStatus, GenericResponse<ViewcartModel> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        ViewcartModel viewcartModel = genericResponse.getObject();
                        cartItemModels = viewcartModel.getCartItemModels();
                        //dishesSearcAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    //loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }
}
