package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.vibescom.kutanga.Models.RestaurantsFoodModel.ProductPrice;
import com.vibescom.kutanga.R;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryPriceAdapterr extends RecyclerView.Adapter<CategoryPriceAdapterr.myViewHolder> {
    private Context context;
    CopyOnWriteArrayList<ProductPrice> productPriceslist;
    String imagepath,selcetedcartPrice;
    public int mSelectedItem = -1;
    private selectItemInterface listner;
    static boolean statusChecked;

    public CategoryPriceAdapterr(Context context, String selcetedcartPrice, CopyOnWriteArrayList<ProductPrice> productPrices, selectItemInterface listner) {
        this.context = context;
        this.productPriceslist = productPrices;
        this.selcetedcartPrice=selcetedcartPrice;
        this.listner=listner;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.row_view_category_price, parent, false);
        myViewHolder mViewHold = new myViewHolder(layoutView);
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {
        ProductPrice productPrice=productPriceslist.get(position);


        myViewHolder.radia_id1.setOnClickListener(v -> {
            mSelectedItem = position;
            statusChecked=true;
            listner.selectItem(productPrice.getProductprice(),productPrice.getQuantity());
            notifyDataSetChanged();
        });
        if(position==mSelectedItem){
            myViewHolder.radia_id1.setTextColor(context.getResources().getColor(R.color.user_theme_color));
        }else{
            myViewHolder.radia_id1.setTextColor(context.getResources().getColor(R.color.dim_grey));
        }
        myViewHolder.radia_id1.setChecked(position == mSelectedItem);
        myViewHolder.radia_id1.setText(productPrice.getProductprice()+" Kz"+" "+"("+productPrice.getQuantity()+")");


    }
    public void itemChcekedStatus(){
        mSelectedItem=-1;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return (productPriceslist.size() > 0) ? productPriceslist.size() : 0;
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        RadioButton radia_id1;

        private myViewHolder(View itemView) {
            super(itemView);
            radia_id1 = itemView.findViewById(R.id.radia_id1);

        }
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
