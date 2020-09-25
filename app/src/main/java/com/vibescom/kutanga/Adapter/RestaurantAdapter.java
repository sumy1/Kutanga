package com.vibescom.kutanga.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Activities.Restorants.Activity.TakeYourPicDetailsActivity;
import com.vibescom.kutanga.Activities.SetDeliveryLocationActivity;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.Models.CurrentUser;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.LandingFood;
import com.vibescom.kutanga.R;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.vibescom.kutanga.Constants.Constants.kAddress;
import static com.vibescom.kutanga.Constants.Constants.kAppPreferences;
import static com.vibescom.kutanga.Constants.Constants.kRestuarantsTypeId;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.myViewHolder>{


    private Context context;
    private CopyOnWriteArrayList<LandingFood> mData;
    private OnItemClickListene listener;
    private int pageSize;
    SharedPreferences mPreferences;
    String latitude,longitude,address,city;

    public RestaurantAdapter(Context context, CopyOnWriteArrayList<LandingFood> data) {
        this.context = context;
        this.mData = data;
        mPreferences=context.getSharedPreferences(kAppPreferences, Context.MODE_PRIVATE);
        latitude = mPreferences.getString("lat", "");
        longitude = mPreferences.getString("Lag", "");
        address = mPreferences.getString(kAddress,"");
        city = mPreferences.getString(Constants.kCity,"");
        //mData.add(new LandingFood(1,"Explore",R.drawable.ic_food));
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
                    .inflate(R.layout.res_table_view, parent, false);
            return new myViewHolder(layoutView);

    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        LandingFood landingFood =mData.get(position);

        if(position==0){
            holder.txt_res_child.setText(landingFood.getTypeName());
            Picasso.with(context).load(R.drawable.seafood).placeholder(R.drawable.seafood).fit().into(holder.img_res_child);
        }else if(position==1){
            holder.txt_res_child.setText(landingFood.getTypeName());
            Picasso.with(context).load(R.drawable.chicken).placeholder(R.drawable.chicken).fit().into(holder.img_res_child);

        }else if(position==2){
            holder.txt_res_child.setText(landingFood.getTypeName());
            Picasso.with(context).load(R.drawable.mutton).placeholder(R.drawable.mutton).fit().into(holder.img_res_child);

        }else if(position==3){
            holder.txt_res_child.setText(landingFood.getTypeName());
            Picasso.with(context).load(R.drawable.beef).placeholder(R.drawable.beef).fit().into(holder.img_res_child);

        }else if(position==4){
            holder.txt_res_child.setText(landingFood.getTypeName());
            Picasso.with(context).load(R.drawable.kebab).placeholder(R.drawable.kebab).fit().into(holder.img_res_child);

        }else if(position==5){
            holder.txt_res_child.setText(landingFood.getTypeName());
            Picasso.with(context).load(R.drawable.biryani).placeholder(R.drawable.biryani).fit().into(holder.img_res_child);

        }
        /*else if(position==6){
            holder.txt_res_child.setText("Explore");
            Picasso.with(context).load(R.drawable.ic_food).placeholder(R.drawable.ic_food).fit().into(holder.img_res_child);
        }*/


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentUser user=ModelManager.modelManager().getCurrentUser();
                if(ModelManager.modelManager().getCurrentUser()==null && latitude.equalsIgnoreCase("") && longitude.equalsIgnoreCase("")){
                    Intent intent=new Intent(context, SetDeliveryLocationActivity.class);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }else if((user==null || user.getUserId()>0) &&latitude.equalsIgnoreCase("") && longitude.equalsIgnoreCase("")){
                    Intent intent=new Intent(context, SetDeliveryLocationActivity.class);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }

                else{

                    Intent intent = new Intent(context, TakeYourPicDetailsActivity.class);
                    intent.putExtra(kRestuarantsTypeId, landingFood.getRestaurantTypeId());
                    context.startActivity(intent);
                    //((Activity)context).finish();

                    /*Intent intent=new Intent(context, HomeActivity.class);
                    intent.putExtra("lat", latitude);
                    intent.putExtra("Lag", longitude);
                    intent.putExtra("FROM", "2");
                    intent.putExtra(kAddress, address);
                    intent.putExtra(kCity, city);
                    context.startActivity(intent);
                    ((Activity)context).finish();*/
                }


            }
        });

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(CopyOnWriteArrayList<LandingFood> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void newData(CopyOnWriteArrayList<LandingFood> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView img_res_child;
        TextView txt_res_child;
        private myViewHolder(View itemView) {
            super(itemView);
            txt_res_child=itemView.findViewById(R.id.txt_res_child);
            img_res_child=itemView.findViewById(R.id.img_res_child);
        }
    }

    public interface OnItemClickListene{
        //void onItemClick(Notifications notifications);
    }

}
