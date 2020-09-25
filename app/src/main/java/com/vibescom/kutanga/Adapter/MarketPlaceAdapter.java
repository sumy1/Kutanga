package com.vibescom.kutanga.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vibescom.kutanga.Models.MarketPlaceModel.MarketPlace;
import com.vibescom.kutanga.R;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MarketPlaceAdapter extends RecyclerView.Adapter<MarketPlaceAdapter.myViewHolder>{


    private Context context;
    private CopyOnWriteArrayList<MarketPlace> mData;
    private OnItemClickListene listener;
    private int pageSize;

    public MarketPlaceAdapter(Context context, CopyOnWriteArrayList<MarketPlace> data) {
        this.context = context;
        this.mData = data;
    }

    /*@Override
    public int getItemViewType(int position) {
        if (position == mData.size()) {
            return 0;
        } else {
            return 1;
        }
    }*/

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(context)
                    .inflate(R.layout.market_table_view, parent, false);
            return new myViewHolder(layoutView);

    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        MarketPlace restaurantsModel=mData.get(position);

        //Picasso.with(context).load(restaurantsModel.getResIcon()).placeholder(R.drawable.rest_icon).fit().into(holder.img_res_child);
        holder.txt_res_child.setText(restaurantsModel.getCateName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent=new Intent(context, SetDeliveryLocationActivity.class);
                context.startActivity(intent);
                ((Activity)context).finish();*/

            }
        });

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(CopyOnWriteArrayList<MarketPlace> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void newData(CopyOnWriteArrayList<MarketPlace> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView img_res_child;
        TextView txt_res_child;
        private myViewHolder(View itemView) {
            super(itemView);
            txt_res_child=itemView.findViewById(R.id.txt_mar_child);
            img_res_child=itemView.findViewById(R.id.img_mar_child);
        }
    }

    public interface OnItemClickListene{
        //void onItemClick(Notifications notifications);
    }

}
