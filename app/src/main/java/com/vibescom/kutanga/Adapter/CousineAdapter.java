package com.vibescom.kutanga.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vibescom.kutanga.Models.CousineModel;
import com.vibescom.kutanga.R;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CousineAdapter extends RecyclerView.Adapter<CousineAdapter.myViewHolder>{


    private Context context;
    private CopyOnWriteArrayList<CousineModel> mData;
    private OnItemClickListene listener;
    private int pageSize;

    public CousineAdapter(Context context, CopyOnWriteArrayList<CousineModel> data) {
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
                    .inflate(R.layout.cousines_table_view, parent, false);
            return new myViewHolder(layoutView);

    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        CousineModel restaurantsModel=mData.get(position);


        holder.txt_cousines.setText(restaurantsModel.getResName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.itemView.setBackgroundColor(;

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

    public void addData(CopyOnWriteArrayList<CousineModel> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void newData(CopyOnWriteArrayList<CousineModel> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView txt_cousines;
        private myViewHolder(View itemView) {
            super(itemView);
            txt_cousines=itemView.findViewById(R.id.txt_cousines);
        }
    }

    public interface OnItemClickListene{
        //void onItemClick(Notifications notifications);
    }

}
