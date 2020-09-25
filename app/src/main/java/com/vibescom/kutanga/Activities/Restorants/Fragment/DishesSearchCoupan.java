package com.vibescom.kutanga.Activities.Restorants.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vibescom.kutanga.Activities.Restorants.Activity.HomeActivity;
import com.vibescom.kutanga.Activities.Restorants.Adapter.DishesSearcAdapter;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.SearchDishesModel;
import com.vibescom.kutanga.Models.ViewCart.CartItemModel;
import com.vibescom.kutanga.Models.ViewCart.ViewcartModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.SpaceItemDecoration;
import com.vibescom.kutanga.Utils.Toaster;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class DishesSearchCoupan extends Fragment {
    private Context context;
    private RelativeLayout rv_cart;
    private TextView tv_total_item_weight, tv_viewcart;
    private CopyOnWriteArrayList<SearchDishesModel> searchDishesModel;
    private String resturansImagePath, productImagepath;
    private CustomLoaderView loaderView;
    CopyOnWriteArrayList<CartItemModel> cartItemModels;
    RelativeLayout bottom_rel;
    RecyclerView main_reclerView;
    private LinearLayoutManager mLayoutManager;
    DishesSearcAdapter dishesSearcAdapter;
    RestorantsSearchFragment.cartNotificationListener listener;

    public DishesSearchCoupan(RestorantsSearchFragment.cartNotificationListener listener, CopyOnWriteArrayList<SearchDishesModel> searchDishesModel, String resturansImagePath, String productImagepath) {
        this.listener=listener;
        this.searchDishesModel = searchDishesModel;
        this.productImagepath = productImagepath;
        this.resturansImagePath = resturansImagePath;

        Log.d("SizeDishes",searchDishesModel.size()+"");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dieseh, container, false);
        context = getActivity();
        loaderView = CustomLoaderView.initialize(getContext());

        initView(rootView);
        getViewCart();

        return rootView;
    }


    private void initView(View rootView) {

        main_reclerView = rootView.findViewById(R.id.main_reclerView);
        mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        main_reclerView.setLayoutManager(mLayoutManager);
        main_reclerView.addItemDecoration(new SpaceItemDecoration(7));
        main_reclerView.setHasFixedSize(true);
        rv_cart = rootView.findViewById(R.id.rv_cartt);

        tv_viewcart = rootView.findViewById(R.id.tv_viewcart);

        tv_viewcart.setOnClickListener(v -> {

            Intent intent = new Intent(context, HomeActivity.class);
            intent.putExtra("FROM", "1");
            startActivity(intent);
            getActivity().finish();

        });
        tv_total_item_weight = rootView.findViewById(R.id.tv_total_item_weight);
        bottom_rel = rootView.findViewById(R.id.bottom_rel);
    }
    private void getViewCart() {
        loaderView.showLoader();
        ModelManager.modelManager().getViewcart(
                (Constants.Status iStatus, GenericResponse<ViewcartModel> genericResponse) -> {
                    try {
                        loaderView.hideLoader();
                        ViewcartModel viewcartModel = genericResponse.getObject();

                        cartItemModels = viewcartModel.getCartItemModels();


                        //Log.d("SizeNew",searchDishesModel.size()+"");
                        if (searchDishesModel.size()==0) {
                            rv_cart.setVisibility(View.VISIBLE);
                            main_reclerView.setVisibility(View.GONE);
                        } else {
                            rv_cart.setVisibility(View.GONE);
                            main_reclerView.setVisibility(View.VISIBLE);
                            dishesSearcAdapter = new DishesSearcAdapter(listener,context, productImagepath, searchDishesModel, cartItemModels);
                            main_reclerView.setAdapter(dishesSearcAdapter);
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
