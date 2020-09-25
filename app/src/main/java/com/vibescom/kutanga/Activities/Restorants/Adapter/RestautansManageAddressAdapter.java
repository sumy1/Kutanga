package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.vibescom.kutanga.Activities.LocationActivity;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.Models.ManageAddressModel.AddressDataModel;
import com.vibescom.kutanga.R;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.vibescom.kutanga.Constants.Constants.kLandMark;
import static com.vibescom.kutanga.Constants.Constants.kaddressType;
import static com.vibescom.kutanga.Constants.Constants.ksubArea;

public class RestautansManageAddressAdapter extends RecyclerView.Adapter<RestautansManageAddressAdapter.myViewHolder> {
    private Context context;
    CopyOnWriteArrayList<AddressDataModel>addressModelList;
    Dialog dialog;
    private onItemClickListener listener;
    EditText et_address,et_tag;

    public RestautansManageAddressAdapter(Context context,CopyOnWriteArrayList<AddressDataModel>addressModelList,onItemClickListener listener) {
        this.context = context;
        this.addressModelList=addressModelList;
        this.listener=listener;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.raw_manage_address, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {

        AddressDataModel addressDataModel = addressModelList.get(position);

        myViewHolder.txt_cart_food_name.setText(addressDataModel.getAddressType());
        myViewHolder.txt_cart_food_details.setText(addressDataModel.getFormattedaddress());


       myViewHolder.txt_edit.setOnClickListener(v -> {

           Intent intent=new Intent(context, LocationActivity.class);
           intent.putExtra("From","1");
           intent.putExtra("lat",addressDataModel.getLat());
           intent.putExtra("Lag",addressDataModel.getLng());
           intent.putExtra(Constants.kAddress,addressDataModel.getFullAddress());
           intent.putExtra(Constants.kCity,addressDataModel.getCity());
           intent.putExtra(ksubArea,addressDataModel.getArea());
           intent.putExtra(kLandMark,addressDataModel.getLandmark());
           intent.putExtra(kaddressType,addressDataModel.getAddressType());
           context.startActivity(intent);

           //listener.onEdit(addressDataModel.getUserAddressId());
       });


       myViewHolder.txt_delete.setOnClickListener(v -> {
           listener.ondelete(addressDataModel.getUserAddressId(),position);
       });


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

        private myViewHolder(View itemView) {
            super(itemView);
            txt_edit = itemView.findViewById(R.id.txt_edit);
            txt_delete = itemView.findViewById(R.id.txt_delete);
            txt_cart_food_name = itemView.findViewById(R.id.txt_cart_food_name);
            txt_cart_food_details = itemView.findViewById(R.id.txt_cart_food_details);


        }
    }

    public interface onItemClickListener {
         void onEdit(int addressId);
         void ondelete(int addressId,int pos);
    }


}
