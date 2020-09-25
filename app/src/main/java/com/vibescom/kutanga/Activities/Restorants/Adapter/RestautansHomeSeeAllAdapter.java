package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vibescom.kutanga.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestautansHomeSeeAllAdapter extends RecyclerView.Adapter<RestautansHomeSeeAllAdapter.myViewHolder> {
    private Context context;
    //private CopyOnWriteArrayList<UserFacAca> mData;
    private onItemClickListener listener;

    public RestautansHomeSeeAllAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.row_view_restorants_home_seeall, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {
       /* DisplayMetrics displayMetrics=new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        myViewHolder.itemView.getLayoutParams().width = (int)(displayMetrics.widthPixels/1.2);
        UserFacAca facility = mData.get(position);
        if(!facility.getFacBannerImg().isEmpty()){
            String imgPath = kImageBaseUrl+facility.getFacFolder()+"/"+facility.getFacBannerImg();
            Picasso.with(context).load(imgPath).placeholder(R.drawable.running_event).fit().into(myViewHolder.ivBanner);
        }else{
            Picasso.with(context).load(R.drawable.stadium_img).placeholder(R.drawable.stadium_img).fit().into(myViewHolder.ivBanner);
        }
        myViewHolder.tvName.setText(facility.getFacName());
        myViewHolder.tvAdd.setText(facility.getFacCity());
        myViewHolder.tvRating.setText(String.valueOf(facility.getRatingAvg()));
        myViewHolder.itemView.setOnClickListener(view -> listener.onFacilityClick(facility));*/
    }

    @Override
    public int getItemCount() {
        return 10;
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

    public interface onItemClickListener{
       // void onFacilityClick(UserFacAca facAca);
    }
}
