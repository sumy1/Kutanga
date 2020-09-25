package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Models.FavrouteModel.FavrouteBusinessdata;
import com.vibescom.kutanga.Models.FavrouteModel.favrouteDataModel;
import com.vibescom.kutanga.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.vibescom.kutanga.Constants.Constants.kOrderBookingStatus;

public class RestautansfavroutesAdapter extends RecyclerView.Adapter<RestautansfavroutesAdapter.myViewHolder> {
    private Context context;
    CopyOnWriteArrayList<favrouteDataModel>favrouteDataModels;
    private String imagepath,booking_order_status;
    OnitemClick onitemClick;

    public RestautansfavroutesAdapter(Context context,CopyOnWriteArrayList<favrouteDataModel>favrouteDataModels,OnitemClick onitemClick) {
        this.context = context;
        this.favrouteDataModels=favrouteDataModels;
        this.onitemClick=onitemClick;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.row_view_favroutes, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {
        favrouteDataModel favroutedatamodel=favrouteDataModels.get(position);

        JSONObject  jsonObject=favroutedatamodel.getJsonObjectBusiness();
        FavrouteBusinessdata favrouteBusinessdata1=new FavrouteBusinessdata(jsonObject);

        JSONObject jsonObject1=favrouteBusinessdata1.getInTheSport();
        try {
            booking_order_status=jsonObject1.getString(kOrderBookingStatus);

            if(booking_order_status.equalsIgnoreCase("1")){
                myViewHolder.txt_open_close.setText(context.getResources().getString(R.string.open));
            }else{
                myViewHolder.txt_open_close.setText(context.getResources().getString(R.string.close));
                myViewHolder.txt_open_close.setTextColor(context.getResources().getColor(R.color.user_theme_color));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(!favrouteBusinessdata1.getCoverImg().isEmpty()){
            String imgPath = imagepath+"/"+favrouteBusinessdata1.getCoverImg();
            Picasso.with(context).load(imgPath).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.iv_banner);
        }else{
            Picasso.with(context).load(R.drawable.ic_image_black_24dp).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.iv_banner);
        }

        myViewHolder.tv_rest_name.setText(favrouteBusinessdata1.getCompanyName());
        myViewHolder.tv_venue_address.setText(favrouteBusinessdata1.getAddress());
        myViewHolder.tv_payment_type.setText(favrouteBusinessdata1.getPaymentAcceptMode());
        myViewHolder.txt_min_distance.setText(favrouteBusinessdata1.getDistanceTobeCovered()+" "+"Min");
        myViewHolder.txt_min_order.setText(favrouteBusinessdata1.getMinOrder()+" "+"Kg");

        if(favrouteBusinessdata1.getRating().isEmpty()||favrouteBusinessdata1.getRating().equalsIgnoreCase("0")){
            myViewHolder.ll_rat.setVisibility(View.GONE);
        }else{
            myViewHolder.ll_rat.setVisibility(View.VISIBLE);
            myViewHolder.tv_rating.setText(favrouteBusinessdata1.getRating());
        }
        if(favrouteBusinessdata1.getReviewCunt().isEmpty()||favrouteBusinessdata1.getReviewCunt().equalsIgnoreCase("0") ){
            myViewHolder.ll_review.setVisibility(View.GONE);
        }else{
            myViewHolder.ll_review.setVisibility(View.VISIBLE);
            myViewHolder.tv_review_count.setText(favrouteBusinessdata1.getReviewCunt());
        }

        myViewHolder.txt_remove.setOnClickListener(v -> {
            onitemClick.onRemove(favrouteBusinessdata1.getVendorBusinessId(),position);
        });

    }

    @Override
    public int getItemCount() {
        return favrouteDataModels.size();
    }

    public void addData(CopyOnWriteArrayList<favrouteDataModel>list,String imagepath) {
        favrouteDataModels.addAll(list);
        this.imagepath=imagepath;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        favrouteDataModels.remove(position);
        notifyItemRemoved(position);
    }
    public void newData(CopyOnWriteArrayList<favrouteDataModel>list,String imagepath) {
        favrouteDataModels.clear();
        this.imagepath=imagepath;
        favrouteDataModels.addAll(list);
        notifyDataSetChanged();
    }



    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_banner;
        TextView tv_rest_name,tv_venue_address,tv_restorants_type,txt_min_order,txt_min_distance,tv_payment_type,txt_open_close,
                tv_rating,tv_review_count,txt_remove;
        LinearLayout ll_rat,ll_review;
        private myViewHolder(View itemView) {
            super(itemView);
            iv_banner = itemView.findViewById(R.id.iv_banner);
            tv_rest_name=itemView.findViewById(R.id.tv_rest_name);
            tv_venue_address=itemView.findViewById(R.id.tv_venue_address);
            tv_restorants_type=itemView.findViewById(R.id.tv_restorants_type);
            txt_min_order=itemView.findViewById(R.id.txt_min_order);
            txt_min_distance=itemView.findViewById(R.id.txt_min_distance);
            tv_payment_type=itemView.findViewById(R.id.tv_payment_type);
            txt_open_close=itemView.findViewById(R.id.txt_open_close);
            tv_rating=itemView.findViewById(R.id.tv_rating);
            tv_review_count=itemView.findViewById(R.id.tv_review_count);
            ll_rat=itemView.findViewById(R.id.ll_rat);
            ll_review=itemView.findViewById(R.id.ll_review);
            txt_remove=itemView.findViewById(R.id.txt_remove);
        }
    }

    public interface OnitemClick{
         void onRemove(int vendorBusinessid,int pos);
    }

}
