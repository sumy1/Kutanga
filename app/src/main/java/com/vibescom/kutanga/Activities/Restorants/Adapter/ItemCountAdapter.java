package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vibescom.kutanga.Models.ItemCountModel;
import com.vibescom.kutanga.R;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemCountAdapter extends RecyclerView.Adapter<ItemCountAdapter.myViewHolder> {
    private Context context;
    CopyOnWriteArrayList<ItemCountModel> productPriceslist;
    String imagepath;
    public int mSelectedItem = -1;
    private selectItemInterface listener;
    static boolean statusChecked;

    public ItemCountAdapter(Context context, CopyOnWriteArrayList<ItemCountModel> productPrices) {
        this.context = context;
        this.productPriceslist = productPrices;

        Log.d("SizeItem",productPriceslist.size()+"");


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.row_itemcount, parent, false);
        myViewHolder mViewHold = new myViewHolder(layoutView);
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {
        ItemCountModel productPrice=productPriceslist.get(position);
        myViewHolder.txt_item_price.setText(Integer.parseInt(productPrice.getPrice())*Integer.parseInt(productPrice.getCount())+" "+context.getResources().getString(R.string.kg));
        myViewHolder.txt_item_name.setText(productPrice.getName());
        myViewHolder.txt_item_name_count.setText(productPrice.getCount());

    }

    @Override
    public int getItemCount() {
        return (productPriceslist.size() > 0) ? productPriceslist.size() : 0;
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView txt_item_price,txt_item_name,txt_item_name_count;

        private myViewHolder(View itemView) {
            super(itemView);
            txt_item_price = itemView.findViewById(R.id.txt_item_price);
            txt_item_name = itemView.findViewById(R.id.txt_item_name);
            txt_item_name_count = itemView.findViewById(R.id.txt_item_name_count);


        }
    }
    public void itemChcekedStatus(){
        mSelectedItem=-1;
        notifyDataSetChanged();
    }
    public interface selectItemInterface{
        void selectItem(String total, String quantity);
    }

    public boolean getQuantitysSelectionStatus() {
        boolean status = false;

        if (statusChecked == false) {
            status = true;
        }
        return status;
    }

}
