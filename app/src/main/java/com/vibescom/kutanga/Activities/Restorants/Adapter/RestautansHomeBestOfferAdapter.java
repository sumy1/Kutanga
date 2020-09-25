package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.OfferData;
import com.vibescom.kutanga.R;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestautansHomeBestOfferAdapter extends RecyclerView.Adapter<RestautansHomeBestOfferAdapter.myViewHolder> {
    private Context context;
    CopyOnWriteArrayList<OfferData> offerData;
    private HomeItemkListener listener;
    String imagePath;

    public RestautansHomeBestOfferAdapter(Context context, CopyOnWriteArrayList<OfferData> offerData,String imagePath,HomeItemkListener listener) {
        this.context = context;
        this.offerData=offerData;
        this.imagePath=imagePath;
        this.listener=listener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.row_view_restorants_home, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {
        DisplayMetrics displayMetrics=new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        myViewHolder.itemView.getLayoutParams().width = (int)(displayMetrics.widthPixels/1.2);
        OfferData offerDatamodel = offerData.get(position);
        if(!offerDatamodel.getImage().isEmpty()){
            String imgPath = "http://markfoodbetav4.kutanga.store/storage/images/offers" + "/" + offerDatamodel.getImage();
            Picasso.with(context).load(imgPath).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.ivBanner);
        }else{
            Picasso.with(context).load(R.drawable.ic_image_black_24dp).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.ivBanner);
        }

        myViewHolder.itemView.setOnClickListener(view -> listener.bestOfferClick(offerDatamodel));
    }

    @Override
    public int getItemCount() {


        return (offerData.size()>0) ? offerData.size(): 0;
    }

    /*public void newData(CopyOnWriteArrayList<UserFacAca> list){
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }*/

    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBanner;
        private myViewHolder(View itemView) {
            super(itemView);
            ivBanner = itemView.findViewById(R.id.iv_banner);
        }
    }

    public interface HomeItemkListener{
        void bestOfferClick(OfferData offersModel);

    }
}
