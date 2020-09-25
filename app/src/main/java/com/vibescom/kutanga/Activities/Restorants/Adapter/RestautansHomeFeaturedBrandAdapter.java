package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.FeaturedBranddataModel;
import com.vibescom.kutanga.R;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestautansHomeFeaturedBrandAdapter extends RecyclerView.Adapter<RestautansHomeFeaturedBrandAdapter.myViewHolder> {
    private Context context;
    CopyOnWriteArrayList<FeaturedBranddataModel> featuredBranddataModels;
    private HomeItemkListener listener;
    String imagepath;

    public RestautansHomeFeaturedBrandAdapter(Context context, CopyOnWriteArrayList<FeaturedBranddataModel> featuredBranddataModels, String imagepath,HomeItemkListener listener) {
        this.context = context;
        this.featuredBranddataModels = featuredBranddataModels;
        this.imagepath = imagepath;
        this.listener=listener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.raw_res_home_featured_brand, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {

        FeaturedBranddataModel fetureBModel = featuredBranddataModels.get(position);
        if (!fetureBModel.getCoverImage().isEmpty()) {
            String imgPath = imagepath + "/" + fetureBModel.getCoverImage();
            Picasso.with(context).load(imgPath).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.ivBanner);
        } else {
            Picasso.with(context).load(R.drawable.ic_image_black_24dp).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.ivBanner);
        }
        myViewHolder.tv_rest_name.setText(fetureBModel.getCompanyName());
        myViewHolder.txt_min_distance.setText(fetureBModel.getEstimateDeliveryTime() + " " + "min");
        myViewHolder.tv_rest_des.setText(fetureBModel.getAddress());



        myViewHolder.itemView.setOnClickListener(view -> {

                    listener.feturedBrandItemClick(fetureBModel.getVendorBusinessId());
                    Log.d("Id",featuredBranddataModels.get(position).getVendorid()+"/"+featuredBranddataModels.get(position).getVendorBusinessId());
                }
        );

    }

    @Override
    public int getItemCount() {
        return (featuredBranddataModels.size() > 0) ? featuredBranddataModels.size() : 0;
    }

    /*public void newData(CopyOnWriteArrayList<UserFacAca> list){
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }*/

    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBanner;
        TextView tv_rest_name, txt_min_distance, tv_rest_des;

        private myViewHolder(View itemView) {
            super(itemView);
            ivBanner = itemView.findViewById(R.id.iv_banner);
            tv_rest_name = itemView.findViewById(R.id.tv_rest_name);
            txt_min_distance = itemView.findViewById(R.id.txt_min_distance);
            tv_rest_des = itemView.findViewById(R.id.tv_rest_des);
        }
    }

    public interface HomeItemkListener {
        void feturedBrandItemClick(int businessid);
    }
}
