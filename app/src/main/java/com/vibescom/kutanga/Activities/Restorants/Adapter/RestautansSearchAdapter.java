package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Activities.Restorants.Activity.InTheSportLightDetailsActivity;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.SearchResturantsModel;
import com.vibescom.kutanga.R;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.vibescom.kutanga.Constants.Constants.kBusinessId;

public class RestautansSearchAdapter extends RecyclerView.Adapter<RestautansSearchAdapter.myViewHolder> {
    private Context context;
    private CopyOnWriteArrayList<SearchResturantsModel> searchResturantsModel;
    private onItemClickListener listener;
    String resturansImagePath,productImagepath;
    public RestautansSearchAdapter(Context context,CopyOnWriteArrayList<SearchResturantsModel> searchResturantsModel,String resturansImagePath,String productImagepath) {
        this.context = context;
        this.searchResturantsModel=searchResturantsModel;
        this.productImagepath=productImagepath;
        this.resturansImagePath=resturansImagePath;

        Log.d("sizeA","A"+searchResturantsModel.size()+"");

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.row_view_restorants_search, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {
        SearchResturantsModel offerDatamodel = searchResturantsModel.get(position);
        if (!offerDatamodel.getCoverImage().isEmpty()) {
            String imgPath = resturansImagePath + "/" + offerDatamodel.getCoverImage();
            Picasso.with(context).load(imgPath).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.ivBanner);
        } else {
            Picasso.with(context).load(R.drawable.ic_image_black_24dp).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.ivBanner);
        }

        if(offerDatamodel.getOrderBookingSttaus().equalsIgnoreCase("1")){
            myViewHolder.txt_open.setText(context.getResources().getString(R.string.open));
        }else{
            myViewHolder.txt_open.setText(context.getResources().getString(R.string.close));
            myViewHolder.txt_open.setTextColor(context.getResources().getColor(R.color.user_theme_color));
        }

        myViewHolder.txt_res_name.setText(offerDatamodel.getCompanyName());
        myViewHolder.txt_des.setText(offerDatamodel.getTypeName());
        myViewHolder.txt_address.setText(offerDatamodel.getAddress());
        myViewHolder.txt_prepration_time.setText(offerDatamodel.getEstimateDeliveryTime()+" "+context.getResources().getString(R.string.min));

        if(offerDatamodel.getRating().equalsIgnoreCase("")||offerDatamodel.getRating().equalsIgnoreCase("0")){
            myViewHolder.ll_rating.setVisibility(View.GONE);
        }else{
            myViewHolder.ll_rating.setVisibility(View.VISIBLE);
            myViewHolder.ratingBar.setText(offerDatamodel.getRating());
        }


        myViewHolder.txt_paymentType.setText(offerDatamodel.getPaymentAcceptMode());
        //myViewHolder.txt_discount.setText(offerDatamodel.getOffferDiscount()+" "+context.getResources().getString(R.string.offerabove)+" "+offerDatamodel.getMinOffer());
        myViewHolder.txt_min_order.setText(offerDatamodel.getMinOrder()+" "+context.getResources().getString(R.string.kg));
        myViewHolder.ll_viewmenu.setOnClickListener(view -> {
            Intent intent = new Intent(context, InTheSportLightDetailsActivity.class);
            intent.putExtra(kBusinessId, offerDatamodel.getVendorBusinessId());
            context.startActivity(intent);});
    }


    
    @Override
    public int getItemCount() {
        return searchResturantsModel.size();
    }

    public void newData(CopyOnWriteArrayList<SearchResturantsModel> list){
        searchResturantsModel.clear();
        searchResturantsModel.addAll(list);
        Log.d("sizeA","A"+searchResturantsModel.size()+"");
        notifyDataSetChanged();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBanner;
        LinearLayout ll_rating,ll_viewmenu;
        TextView txt_res_name, txt_des,txt_paymentType, txt_discount,txt_open, txt_prepration_time,txt_distance_covered, txt_address, txt_min_order,ratingBar;

        private myViewHolder(View itemView) {
            super(itemView);
            ivBanner = itemView.findViewById(R.id.iv_banner);
            txt_res_name = itemView.findViewById(R.id.txt_res_name);
            txt_des = itemView.findViewById(R.id.txt_des);
            txt_discount = itemView.findViewById(R.id.txt_discount);
            txt_prepration_time = itemView.findViewById(R.id.txt_min_distance);
            txt_address = itemView.findViewById(R.id.txt_address);
            txt_min_order = itemView.findViewById(R.id.txt_min_order);
            txt_open=itemView.findViewById(R.id.txt_open);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            txt_paymentType=itemView.findViewById(R.id.txt_paymentType);
            ll_rating=itemView.findViewById(R.id.ll_rating);
            ll_viewmenu=itemView.findViewById(R.id.ll_viewmenu);
        }
    }

    public interface onItemClickListener{
       // void onFacilityClick(UserFacAca facAca);
    }
}
