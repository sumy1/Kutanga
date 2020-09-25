package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.inTheSportLightData;
import com.vibescom.kutanga.R;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestautansHomeInTheSportLightAdapter extends RecyclerView.Adapter<RestautansHomeInTheSportLightAdapter.myViewHolder> {
    private Context context;
    CopyOnWriteArrayList<inTheSportLightData> inTheSportLightdate;
    private HomeItemkListener listener;
    String imagepath;

    public RestautansHomeInTheSportLightAdapter(Context context, CopyOnWriteArrayList<inTheSportLightData> inTheSportLightdate, String imagepath,HomeItemkListener listener) {
        this.context = context;
        this.inTheSportLightdate = inTheSportLightdate;
        this.imagepath = imagepath;
        this.listener=listener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.row_view_restorants_home_sport_light, parent, false);
        myViewHolder mViewHold = new myViewHolder(layoutView);
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {
        inTheSportLightData inTheSportLightData = inTheSportLightdate.get(position);
        if (!inTheSportLightData.getCoverImage().isEmpty()) {
            String imgPath = imagepath + "/" + inTheSportLightData.getCoverImage();
            Picasso.with(context).load(imgPath).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.ivBanner);
        } else {
            Picasso.with(context).load(R.drawable.ic_image_black_24dp).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.ivBanner);
        }
        myViewHolder.tv_rest_name.setText(inTheSportLightData.getCompanyName());
        myViewHolder.tv_resturantsType.setText(inTheSportLightData.getTypeName());
        myViewHolder.tv_venue_address.setText(inTheSportLightData.getAddress());
        myViewHolder.txt_min_distance.setText(inTheSportLightData.getEstimateDeliveryTime()+" "+"min");
        myViewHolder.txt_min_order.setText(inTheSportLightData.getMinOrder()+" "+"kg");

        if(inTheSportLightData.getRating().isEmpty()){
            myViewHolder.ll_rating.setVisibility(View.GONE);
        }else{
            myViewHolder.ll_rating.setVisibility(View.VISIBLE);
            myViewHolder.ratingBar.setText(String.valueOf(Math.floor(Double.parseDouble(inTheSportLightData.getRating()))));
        }

        if(inTheSportLightData.getReviewCount().isEmpty()||inTheSportLightData.getReviewCount().equalsIgnoreCase("0")){
            myViewHolder.ll_review.setVisibility(View.GONE);
        }else{
            myViewHolder.ll_review.setVisibility(View.VISIBLE);
            myViewHolder.tv_review_count.setText(inTheSportLightData.getReviewCount());
        }

        myViewHolder.itemView.setOnClickListener(view -> listener.inTheSportLightItemClick(inTheSportLightData.getVendorBusinessId()));


    }

    @Override
    public int getItemCount() {
        return (inTheSportLightdate.size() > 0) ? inTheSportLightdate.size() : 0;
    }

    /*public void newData(CopyOnWriteArrayList<UserFacAca> list){
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }*/

    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBanner;
        TextView tv_rest_name, tv_venue_address, tv_resturantsType, txt_min_order, txt_min_distance, ratingBar, tv_review_count;
        LinearLayout ll_rating,ll_review;
        private myViewHolder(View itemView) {
            super(itemView);
            ivBanner = itemView.findViewById(R.id.iv_banner);

            tv_rest_name = itemView.findViewById(R.id.tv_rest_name);
            tv_venue_address = itemView.findViewById(R.id.tv_venue_address);
            tv_resturantsType = itemView.findViewById(R.id.tv_resturantsType);
            txt_min_order = itemView.findViewById(R.id.txt_min_order);
            txt_min_distance = itemView.findViewById(R.id.txt_min_distance);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            tv_review_count = itemView.findViewById(R.id.tv_review_count);
            ll_rating=itemView.findViewById(R.id.ll_rating);
            ll_review=itemView.findViewById(R.id.ll_review);
        }
    }

    public interface HomeItemkListener {
        void inTheSportLightItemClick(int businessId);
    }
}
