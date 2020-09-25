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
import android.widget.TextView;

import com.vibescom.kutanga.Models.RestaurantsFoodModel.ReturantsDetailscategoryModel;
import com.vibescom.kutanga.R;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestautansHomeInTheSportLightDetailsAdapter extends RecyclerView.Adapter<RestautansHomeInTheSportLightDetailsAdapter.myViewHolder> {
    private Context context;
    CopyOnWriteArrayList<ReturantsDetailscategoryModel> returantsDetailscategoryModels;
    private onItemClickListener listener;
    Dialog dialog;


    public RestautansHomeInTheSportLightDetailsAdapter(Context context) {
        this.context = context;
        //this.returantsDetailscategoryModels=returantsDetailscategoryModels;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.row_view_restorants_home_inthesportlight_details, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {
       // ReturantsDetailscategoryModel returantsDetailscategoryModel=returantsDetailscategoryModels.get(position);




       myViewHolder.txt_add_item.setOnClickListener(v->{
           menuDialog();
       });
    }

    @Override
    public int getItemCount() {
        return 10;
    }



    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBanner;
        TextView tv_item_name,txt_add_item,txt_des,txt_prepration_time,txt_popularity,txt_max_order;

        private myViewHolder(View itemView) {
            super(itemView);
            ivBanner = itemView.findViewById(R.id.iv_banner);
            txt_add_item=itemView.findViewById(R.id.txt_add_item);
            tv_item_name=itemView.findViewById(R.id.tv_item_name);
            txt_des=itemView.findViewById(R.id.txt_des);
            txt_prepration_time=itemView.findViewById(R.id.txt_prepration_time);
            txt_popularity=itemView.findViewById(R.id.txt_popularity);
            txt_max_order=itemView.findViewById(R.id.txt_max_order);

        }
    }

    public interface onItemClickListener{
       // void onFacilityClick(UserFacAca facAca);
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
}
