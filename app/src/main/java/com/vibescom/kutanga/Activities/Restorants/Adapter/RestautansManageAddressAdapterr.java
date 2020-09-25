package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vibescom.kutanga.Models.ManageAddressModel.AddressDataModel;
import com.vibescom.kutanga.R;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestautansManageAddressAdapterr extends RecyclerView.Adapter<RestautansManageAddressAdapterr.myViewHolder> {
    private Context context;
    CopyOnWriteArrayList<AddressDataModel>addressModelList;
    Dialog dialog;
    private onItemClickListener listener;
    EditText et_address,et_tag;
    private int lastCheckedItem=-1;

    public RestautansManageAddressAdapterr(Context context, CopyOnWriteArrayList<AddressDataModel>addressModelList,onItemClickListener listener) {
        this.context = context;
        this.addressModelList=addressModelList;
        this.listener=listener;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.raw_manage_addresss, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {

        AddressDataModel addressDataModel = addressModelList.get(position);

        myViewHolder.txt_cart_food_name.setText(addressDataModel.getAddressType());
        myViewHolder.txt_cart_food_details.setText(addressDataModel.getFormattedaddress());

        myViewHolder.img_cart_food.setOnClickListener(v -> {
            lastCheckedItem = position;
            listener.onChceked(addressDataModel.getFullAddress(),addressDataModel.getZip(),addressDataModel.getArea(),addressDataModel.getLandmark(),addressDataModel.getLat(),addressDataModel.getLng(),addressDataModel.getFormattedaddress(),addressDataModel.getAddressType());

            notifyDataSetChanged();
        });

        myViewHolder.rl_address.setOnClickListener(v -> {
            lastCheckedItem = position;
            listener.onChceked(addressDataModel.getFullAddress(),addressDataModel.getZip(),addressDataModel.getArea(),addressDataModel.getLandmark(),addressDataModel.getLat(),addressDataModel.getLng(),addressDataModel.getFormattedaddress(),addressDataModel.getAddressType());

            notifyDataSetChanged();
        });

        myViewHolder.img_cart_food.setChecked(position==lastCheckedItem);



    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public void addData(CopyOnWriteArrayList<AddressDataModel> list) {
        addressModelList.addAll(list);
        notifyDataSetChanged();
    }

    public void newData(CopyOnWriteArrayList<AddressDataModel>list) {
        addressModelList.clear();
        addressModelList.addAll(list);
        notifyDataSetChanged();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView txt_edit, txt_delete,txt_cart_food_name,txt_cart_food_details;
        CheckBox img_cart_food;
        RelativeLayout rl_address;

        private myViewHolder(View itemView) {
            super(itemView);
            txt_cart_food_name = itemView.findViewById(R.id.txt_cart_food_name);
            txt_cart_food_details = itemView.findViewById(R.id.txt_cart_food_details);
            img_cart_food=itemView.findViewById(R.id.img_cart_food);
            rl_address=itemView.findViewById(R.id.rl_address);


        }
    }

    public interface onItemClickListener {
         void onChceked(String fullAddress,String pincode,String area,String landmark,String lat,String lng,String formattedAddress,String addressType );
    }


}
