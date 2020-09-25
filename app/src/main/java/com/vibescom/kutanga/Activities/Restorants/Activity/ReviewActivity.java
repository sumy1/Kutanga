package com.vibescom.kutanga.Activities.Restorants.Activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansReviewsAdapter;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.ReviewModel.ReviewDataModel;
import com.vibescom.kutanga.Models.ReviewModel.ReviewModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.CustomLoaderView;
import com.vibescom.kutanga.Utils.Toaster;
import com.vibescom.kutanga.Utils.VerticalSpaceItemDecoration;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import static com.vibescom.kutanga.Constants.Constants.kMessage;
import static com.vibescom.kutanga.Constants.Constants.kReview;
import static com.vibescom.kutanga.Constants.Constants.kVendorId;
import static com.vibescom.kutanga.Constants.Constants.kpage;
import static com.vibescom.kutanga.Constants.Constants.krating;

public class ReviewActivity extends AppCompatActivity {
    Context mContext;
    RecyclerView rv_reviews;
    RestautansReviewsAdapter restautansMyOrderdapter;
    private CopyOnWriteArrayList<ReviewDataModel> addressModelList;
    ImageView img_back;
    CustomLoaderView loaderView;
    private boolean loading = true;
    private int page;
    private int pageSize,vendorId;
    Dialog dialog;
    String rating_size,review;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        mContext=this;
        loaderView=CustomLoaderView.initialize(mContext);

        if(getIntent()!=null){
            vendorId= getIntent().getIntExtra(kVendorId, 0);
        }
        inItView();
        setRecyclerView();
        initData(1);
    }

    private void inItView() {
        rv_reviews=findViewById(R.id.rv_reviews);

        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(v->{
            finish();
        });

        findViewById(R.id.fab).setOnClickListener(v -> {

            reviewDialog();
        });
    }

    private void setRecyclerView() {
        mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        rv_reviews.setLayoutManager(mLayoutManager);
        rv_reviews.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL ));
        rv_reviews.addItemDecoration(new VerticalSpaceItemDecoration(9));
        rv_reviews.setHasFixedSize(true);
        restautansMyOrderdapter=new RestautansReviewsAdapter(mContext,new CopyOnWriteArrayList<>());
        rv_reviews.setAdapter(restautansMyOrderdapter);
        rv_reviews.addOnScrollListener(onScrollListener);
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
            getReviewList(page);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getReviewList(int page) {
        if (page == 1)
            loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kpage, page);
        map.put(kVendorId,141);
        Log.d("Request", map.toString());
        ModelManager.modelManager().getReview(map,
                (Constants.Status iStatus, GenericResponse<ReviewModel> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        ReviewModel reviewModel = genericResponse.getObject();


                        addressModelList = reviewModel.getReviewDataModelList();

                        Log.d("favSize", addressModelList.size() + "");

                        if (page != 1) {
                            restautansMyOrderdapter.addData(addressModelList);
                            loading = !genericResponse.getObject().getReviewDataModelList().isEmpty();
                        } else {
                            //bookingAdapter=new BookingAdapter(context,bookings);
                            restautansMyOrderdapter.newData(addressModelList);
                            pageSize = genericResponse.getObject().getReviewDataModelList().size();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }


    private void reviewDialog() {
        // dialog
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_review);

        dialog.findViewById(R.id.btn_close).setOnClickListener(v -> dialog.dismiss());
        RatingBar rating_bar_review=dialog.findViewById(R.id.rating_bar_review);

        EditText editText1 = dialog.findViewById(R.id.et_message);


        dialog.findViewById(R.id.btn_submit).setOnClickListener(view -> {
            review=editText1.getText().toString().trim();
            rating_size= String.valueOf(rating_bar_review.getRating());
            if(validate()){
                dialog.dismiss();
                getDeleteAddress();
            }
        });
        dialog.show();
    }
    private void getDeleteAddress() {
        loaderView.showLoader();
        HashMap<String, Object> map = new HashMap<>();
        map.put(kVendorId, vendorId);
        map.put(krating, rating_size);
        map.put(kReview, review);
        Log.e("Request", map.toString());
        ModelManager.modelManager().getreviewPost(map,
                (Constants.Status iStatus, GenericResponse<JSONObject> genericResponse) -> {
                    loaderView.hideLoader();
                    try {
                        JSONObject favrouteModel = genericResponse.getObject();


                        String msg = favrouteModel.getString(kMessage);
                        Toaster.customToast(msg);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, (Constants.Status iStatus, String message) -> {
                    loaderView.hideLoader();
                    Toaster.customToast(message);
                });
    }

    private boolean validate() {
        boolean isOk = true;
        if(Float.parseFloat(rating_size)==0){
            Toaster.customToast("Please add rating!");
            isOk = false;
        }
		 /*if (editText1.getText().toString().isEmpty()) {
			Toaster.customToast("Please type message!");
			isOk = false;
		}*/

        return isOk;
    }

}
