package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Models.TakeYourPicDetailsModel.TakeYourPicDataModel;
import com.vibescom.kutanga.R;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestautansHomeTakeYourPicDetailsAdapter extends RecyclerView.Adapter<RestautansHomeTakeYourPicDetailsAdapter.myViewHolder> {
    private Context context;
    private CopyOnWriteArrayList<TakeYourPicDataModel> mData;
    String resturantsImagepath;
    private onItemClickListener listener;

    public RestautansHomeTakeYourPicDetailsAdapter(Context context, CopyOnWriteArrayList<TakeYourPicDataModel> mData,String resturantsImagepath,onItemClickListener listener) {
        this.context = context;
        this.mData=mData;
        this.resturantsImagepath=resturantsImagepath;
        this.listener=listener;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.row_view_restorants_home_take_your_pic_details, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {

        TakeYourPicDataModel takeYourPicDataModel = mData.get(position);
        if(!takeYourPicDataModel.getLogoImage().isEmpty()){
            String imgPath = resturantsImagepath+"/"+takeYourPicDataModel.getLogoImage();
            Picasso.with(context).load(imgPath).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.ivBanner);
        }else{
            Picasso.with(context).load(R.drawable.ic_image_black_24dp).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.ivBanner);
        }
        myViewHolder.txt_cill_out.setText(takeYourPicDataModel.getCompanyName());

        if(takeYourPicDataModel.getMinOrder().isEmpty()){
            myViewHolder.txt_min_order_price.setVisibility(View.GONE);
        }else{
            myViewHolder.txt_min_order_price.setVisibility(View.VISIBLE);
            myViewHolder.txt_min_order_price.setText(takeYourPicDataModel.getMinOrder()+" "+"Kz");
        }


        myViewHolder.txt_des.setText(takeYourPicDataModel.getAddress());
        myViewHolder.txt_prepration_time.setText(takeYourPicDataModel.getEstimateDeliveryTime()+" "+"Min");
        if(takeYourPicDataModel.getRating().isEmpty()){
            myViewHolder.txt_rating.setVisibility(View.GONE);
        }else{
            myViewHolder.txt_rating.setVisibility(View.VISIBLE);
            myViewHolder.rating.setText(String.valueOf(Math.floor(Double.parseDouble(takeYourPicDataModel.getRating()))));
        }

        myViewHolder.txt_paymentType.setText(takeYourPicDataModel.getPaymentAcceptMode());

        if(takeYourPicDataModel.getOrderBookingStatus().equalsIgnoreCase("1")){
            myViewHolder.txt_open.setText("OPEN");
        }else{
            myViewHolder.txt_open.setTextColor(context.getResources().getColor(R.color.user_theme_color));
            myViewHolder.txt_open.setText("CLOSE");
        }


        myViewHolder.itemView.setOnClickListener(view -> listener.onrestaurantsClick(takeYourPicDataModel.getVendorBusinessId()));
    }

    @Override
    public int getItemCount() {
        return (mData.size() > 0) ? mData.size() : 0;
    }


    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBanner;
        TextView txt_cill_out,txt_paymentType,txt_open,txt_min_order_price,txt_des,txt_prepration_time,rating;
        LinearLayout txt_rating,ll_viewmenu;
        private myViewHolder(View itemView) {
            super(itemView);
            ivBanner = itemView.findViewById(R.id.iv_banner);
            txt_cill_out=itemView.findViewById(R.id.txt_cill_out);
            txt_min_order_price=itemView.findViewById(R.id.txt_min_order_price);
            txt_des=itemView.findViewById(R.id.txt_des);
            txt_prepration_time=itemView.findViewById(R.id.txt_prepration_time);
            rating=itemView.findViewById(R.id.rating);
            txt_rating=itemView.findViewById(R.id.txt_rating);
            txt_paymentType=itemView.findViewById(R.id.txt_paymentType);
            txt_open=itemView.findViewById(R.id.txt_open);
            ll_viewmenu=itemView.findViewById(R.id.ll_viewmenu);

        }
    }

    public interface onItemClickListener{
        void onrestaurantsClick(int businessid);
    }
}
