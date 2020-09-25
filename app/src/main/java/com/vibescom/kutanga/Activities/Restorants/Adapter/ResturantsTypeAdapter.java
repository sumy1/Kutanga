package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.RestaurantsTypeData;
import com.vibescom.kutanga.R;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResturantsTypeAdapter extends RecyclerView.Adapter<ResturantsTypeAdapter.myViewHolder> {
    private Context context;
    CopyOnWriteArrayList<RestaurantsTypeData>restaurantsTypedata;
    private HomeItemkListener listener;

    public ResturantsTypeAdapter(Context context, CopyOnWriteArrayList<RestaurantsTypeData> restaurantsTypedata,HomeItemkListener listener) {
        this.context = context;
        this.restaurantsTypedata=restaurantsTypedata;
        this.listener=listener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.row_view_restorants_home_take_your_pic, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {
        RestaurantsTypeData restaurantsTypeData = restaurantsTypedata.get(position);
        /*if(!facility.getFacBannerImg().isEmpty()){
            String imgPath = kImageBaseUrl+facility.getFacFolder()+"/"+facility.getFacBannerImg();
            Picasso.with(context).load(imgPath).placeholder(R.drawable.running_event).fit().into(myViewHolder.ivBanner);
        }else{
            Picasso.with(context).load(R.drawable.stadium_img).placeholder(R.drawable.stadium_img).fit().into(myViewHolder.ivBanner);
        }*/
        myViewHolder.tv_resturantsName.setText(restaurantsTypeData.getTypeName());


        if(position==0){
            Picasso.with(context).load(R.drawable.seafood).placeholder(R.drawable.seafood).fit().into(myViewHolder.ivBanner);
        }else if(position==1){
        Picasso.with(context).load(R.drawable.chicken).placeholder(R.drawable.chicken).fit().into(myViewHolder.ivBanner);

        }else if(position==2){
        Picasso.with(context).load(R.drawable.mutton).placeholder(R.drawable.mutton).fit().into(myViewHolder.ivBanner);

        }else if(position==3){
        Picasso.with(context).load(R.drawable.beef).placeholder(R.drawable.beef).fit().into(myViewHolder.ivBanner);

        }else if(position==4){
        Picasso.with(context).load(R.drawable.kebab).placeholder(R.drawable.kebab).fit().into(myViewHolder.ivBanner);

        }else if(position==5){
        Picasso.with(context).load(R.drawable.biryani).placeholder(R.drawable.biryani).fit().into(myViewHolder.ivBanner);

        }



        Log.d("id",restaurantsTypeData.getResturantTypeId()+"");
        myViewHolder.itemView.setOnClickListener(view -> listener.takeYourPicItemClick(restaurantsTypeData.getResturantTypeId()));

    }

    @Override
    public int getItemCount() {
        return (restaurantsTypedata.size()>0) ? restaurantsTypedata.size(): 0;
    }

    /*public void newData(CopyOnWriteArrayList<UserFacAca> list){
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }*/

    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBanner;
        TextView tv_resturantsName;
        private myViewHolder(View itemView) {
            super(itemView);
            ivBanner = itemView.findViewById(R.id.profile_image);
            tv_resturantsName=itemView.findViewById(R.id.tv_resturantsName);
        }
    }

    public interface HomeItemkListener{
        void takeYourPicItemClick(int businessId);
    }
}
