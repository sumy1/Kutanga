package com.vibescom.kutanga.Activities.Restorants.Activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansfavroutesAdapter;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.FavrouteModel.FavrouteModel;
import com.vibescom.kutanga.Models.FavrouteModel.favrouteDataModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.SpaceItemDecoration;
import com.vibescom.kutanga.Utils.Toaster;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.vibescom.kutanga.Constants.Constants.kMessage;
import static com.vibescom.kutanga.Constants.Constants.kpage;
import static com.vibescom.kutanga.Constants.Constants.kvVendorBusinessId;

public class FavroutesActivity extends AppCompatActivity {
    Context context;
    RecyclerView rv_my_fav;
    ImageView img_back;
    RestautansfavroutesAdapter restautansfavroutesAdapter;
    CustomLoaderView loaderView;
    CopyOnWriteArrayList<favrouteDataModel>favrouteDataModels;
    private LinearLayoutManager mLayoutManager;
    private boolean loading=true;
    private int page;
    private int pageSize;
    private String imagpath,productImgPath;
    private ShimmerFrameLayout mShimmerViewContainer;
    RelativeLayout rv_cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favroutes);
        context=this;
        loaderView = CustomLoaderView.initialize(context);
        inItView();
        setRecyclerView();
        initData(1);

    }

    private void inItView() {
        rv_my_fav=findViewById(R.id.rv_my_fav);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        rv_cart = findViewById(R.id.rv_cart);
        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(v->{
            finish();
        });

        /*txt_menu=findViewById(R.id.txt_menu);
        txt_menu.setOnClickListener(v->{
            menuDialog();
        });*/
    }


    private void setRecyclerView(){
        mLayoutManager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        rv_my_fav.setLayoutManager(mLayoutManager);
        rv_my_fav.addItemDecoration(new SpaceItemDecoration(25));
        rv_my_fav.setHasFixedSize(true);
        restautansfavroutesAdapter=new RestautansfavroutesAdapter(context, new CopyOnWriteArrayList<>(), new RestautansfavroutesAdapter.OnitemClick() {
            @Override
            public void onRemove(int vendorBusinessid, int pos) {
                getAddfavroute(vendorBusinessid,pos);
            }
        });

        rv_my_fav.setAdapter(restautansfavroutesAdapter);
        rv_my_fav.addOnScrollListener(onScrollListener);
    }


    private void showShimmerView() {
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        rv_cart.setVisibility(View.GONE);
        mShimmerViewContainer.startShimmerAnimation();
        rv_my_fav.setVisibility(View.GONE);
    }

    private void hideShimmerView() {
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
        rv_my_fav.setVisibility(View.VISIBLE);
    }

    private void checkEmptyView() {
        if (restautansfavroutesAdapter.getItemCount() == 0)
            rv_cart.setVisibility(View.VISIBLE);
        else
            rv_cart.setVisibility(View.GONE);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            if(dy > 0) //check for scroll down
            {
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

                if (loading)
                {
                    if ( (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
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

    public void initData(int pg){
        try{
            page = pg;
            getfavrouteList(page);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getfavrouteList(int page) {
        if(page==1)
            showShimmerView();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kpage, page);

        Log.d("Request",map.toString());
        ModelManager.modelManager().getFavroute(map,
                (Constants.Status iStatus, GenericResponse<FavrouteModel> genericResponse) -> {
                    hideShimmerView();
                    try {
                        FavrouteModel favrouteModel = genericResponse.getObject();
                        imagpath=favrouteModel.getPath();

                        favrouteDataModels=favrouteModel.getFavrouteDataModels();
                        productImgPath=favrouteModel.getRes_img_path();
                        Log.d("favSize",favrouteDataModels.size()+"");

                        if(page!=1){
                            restautansfavroutesAdapter.addData(favrouteDataModels,productImgPath);
                            loading = !genericResponse.getObject().getFavrouteDataModels().isEmpty();
                        }
                        else{
                            //bookingAdapter=new BookingAdapter(context,bookings);
                            restautansfavroutesAdapter.newData(favrouteDataModels,productImgPath);
                            pageSize = genericResponse.getObject().getFavrouteDataModels().size();
                        }
                        checkEmptyView();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    hideShimmerView();
                    Toaster.customToast(message);
                });
    }


    private void getAddfavroute(int vendorBusinessId,int pos) {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kvVendorBusinessId, vendorBusinessId);

        Log.d("Request", map.toString());
        ModelManager.modelManager().getAddFavroute(map,
                (Constants.Status iStatus, GenericResponse<JSONObject> genericResponse) -> {
                    loaderView.hideLoader();
                    try {

                        JSONObject obj = genericResponse.getObject();

                        String msg = obj.getString(kMessage);

                        if(favrouteDataModels.size()>0){
                            restautansfavroutesAdapter.removeItem(pos);
                            checkEmptyView();

                            /*if(favrouteDataModels.size()==0){
                                rv_no_cart.setVisibility(View.VISIBLE);
                                rl_main.setVisibility(View.GONE);
                            }else {
                                rv_no_cart.setVisibility(View.GONE);
                                rl_main.setVisibility(View.VISIBLE);
                            }*/

                            restautansfavroutesAdapter.notifyDataSetChanged();
                        }else{

                        }

                        //Toaster.customToast(msg);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }
}
