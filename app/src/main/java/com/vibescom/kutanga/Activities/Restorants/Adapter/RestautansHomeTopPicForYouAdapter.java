package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.TopPicData;
import com.vibescom.kutanga.R;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestautansHomeTopPicForYouAdapter extends RecyclerView.Adapter<RestautansHomeTopPicForYouAdapter.myViewHolder> {
    private Context context;
    CopyOnWriteArrayList<TopPicData>topPicdata;
    private HomeItemkListener listener;
    Dialog dialog;
    String imagepath;
    public RestautansHomeTopPicForYouAdapter(Context context, CopyOnWriteArrayList<TopPicData> topPicdata, String imagepath,HomeItemkListener listener) {
        this.context = context;
        this.topPicdata=topPicdata;
        this.imagepath = imagepath;
        this.listener=listener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.raw_res_home_top_pic_for_you, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {

        TopPicData topPicdataModel = topPicdata.get(position);

        if (!topPicdataModel.getCoverImage().isEmpty()) {
            String imgPath = imagepath + "/" + topPicdataModel.getCoverImage();
            Picasso.with(context).load(imgPath).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.ivBanner);
        } else {
            Picasso.with(context).load(R.drawable.ic_image_black_24dp).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.ivBanner);
        }
        myViewHolder.txt_min_distance.setText(topPicdataModel.getEstimateDeliveryTime()+" "+"min");
        myViewHolder.tv_rest_name.setText(topPicdataModel.getCompanyName());
        if(topPicdataModel.getRating().isEmpty()){
            myViewHolder.ll_rating.setVisibility(View.GONE);
            myViewHolder.v.setVisibility(View.GONE);
        }else{
            myViewHolder.ll_rating.setVisibility(View.VISIBLE);
            myViewHolder.v.setVisibility(View.VISIBLE);
            myViewHolder.ratingBar.setText(String.valueOf(Math.floor(Double.parseDouble(topPicdataModel.getRating()))));
        }
        
        myViewHolder.itemView.setOnClickListener(view -> listener.topPicItemClick(topPicdataModel));
    }

    @Override
    public int getItemCount() {
        return (topPicdata.size() > 0) ? topPicdata.size() : 0;
    }

    /*public void newData(CopyOnWriteArrayList<UserFacAca> list){
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }*/

    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBanner;
        TextView tv_rest_name,txt_min_distance,ratingBar;
        LinearLayout ll_rating;
        View v;
        private myViewHolder(View itemView) {
            super(itemView);
            ivBanner = itemView.findViewById(R.id.iv_banner);
            tv_rest_name = itemView.findViewById(R.id.tv_rest_name);
            txt_min_distance = itemView.findViewById(R.id.txt_min_distance);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            ll_rating=itemView.findViewById(R.id.ll_rating);
            v=itemView.findViewById(R.id.v);
        }
    }

    private void menuDialog(){
        // dialog
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_recommended_menu);
        dialog.findViewById(R.id.btn_close).setOnClickListener(view->{
            dialog.dismiss();
        });

        dialog.show();
    }

    public interface HomeItemkListener{
        void topPicItemClick(TopPicData topPicdataModel);

    }
}
