package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.SearchDishesModel;
import com.vibescom.kutanga.R;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DishesSearchAdapter extends RecyclerView.Adapter<DishesSearchAdapter.myViewHolder> {
    private Context context;
    private onItemClickListener listener;
    private CopyOnWriteArrayList<SearchDishesModel> searchDishesModel;
    String resturansImagePath,productImagepath;
    public DishesSearchAdapter(Context context,CopyOnWriteArrayList<SearchDishesModel> searchDishesModel,String resturansImagePath,String productImagepath) {
        this.context = context;
        this.searchDishesModel=searchDishesModel;
        this.productImagepath=productImagepath;
        this.resturansImagePath=resturansImagePath;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.row_view_dishes, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {
        SearchDishesModel searchDishesModeldata=searchDishesModel.get(position);

        if (!searchDishesModeldata.getCoverImage().isEmpty()) {
            String imgPath = productImagepath + "/" + searchDishesModeldata.getCoverImage();
            Picasso.with(context).load(imgPath).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.iv_banner);
        } else {
            Picasso.with(context).load(R.drawable.ic_image_black_24dp).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.iv_banner);
        }

        myViewHolder.txt_cill_out.setText(searchDishesModeldata.getCompanyName());
        myViewHolder.txt_des.setText(searchDishesModeldata.getBusinessType());
        //myViewHolder.txt_discount.setText(searchDishesModeldata.get);
    }

    @Override
    public int getItemCount() {
        return (searchDishesModel.size() > 0) ? searchDishesModel.size() : 0;
    }

    /*public void newData(CopyOnWriteArrayList<UserFacAca> list){
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }*/

    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_banner;
        TextView txt_cill_out,txt_des,txt_discount,tv_customize;
        RelativeLayout rl_add;
        private myViewHolder(View itemView) {
            super(itemView);
            iv_banner = itemView.findViewById(R.id.iv_banner);
            txt_cill_out=itemView.findViewById(R.id.txt_cill_out);
            txt_des=itemView.findViewById(R.id.txt_des);
            txt_discount=itemView.findViewById(R.id.txt_discount);
            tv_customize=itemView.findViewById(R.id.tv_customize);
            rl_add=itemView.findViewById(R.id.rl_add);

        }
    }

    public interface onItemClickListener{
       // void onFacilityClick(UserFacAca facAca);
    }
}
