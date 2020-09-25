package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
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

public class RestautansManageAddresssetDefaultAdapter extends RecyclerView.Adapter<RestautansManageAddresssetDefaultAdapter.myViewHolder> {
    private Context context;
    CopyOnWriteArrayList<AddressDataModel>addressModelList;
    Dialog dialog;
    private onItemClickListener listener;
    EditText et_address,et_tag;
    public  boolean statusChecked;
    boolean checkedStatus;
    public  int lastSelectedPosition = -1;

    public RestautansManageAddresssetDefaultAdapter(Context context,boolean checkedStatus, CopyOnWriteArrayList<AddressDataModel>addressModelList,onItemClickListener listener) {
        this.context = context;
        this.addressModelList=addressModelList;
        this.listener=listener;
        this.checkedStatus=checkedStatus;

        Log.d("chceked",addressModelList.size()+"");

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.raw_set_default_address, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {

        AddressDataModel addressDataModel = addressModelList.get(position);

        myViewHolder.tv_delivery_address.setText(addressDataModel.getFormattedaddress());

        if(position==lastSelectedPosition){
            myViewHolder.ll_mid.setVisibility(View.VISIBLE);
            myViewHolder.rel_deliver_to_this_address.setBackgroundColor(context.getResources().getColor(R.color.orange));
        }else{
            myViewHolder.ll_mid.setVisibility(View.GONE);
            myViewHolder.rel_deliver_to_this_address.setBackgroundColor(context.getResources().getColor(R.color.grey));
        }


        myViewHolder.rel_deliver_to_this_address.setOnClickListener(v -> {
            int addressId=addressModelList.get(lastSelectedPosition).getUserAddressId();
            listener.OnDeliver(addressId);

        });

        myViewHolder.rel_edit_address.setOnClickListener(v -> {
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



        });

        myViewHolder.radio_check_address.setChecked(lastSelectedPosition == position);

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

    public void itemChcekedStatus(){
        lastSelectedPosition=-1;
        notifyDataSetChanged();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView tv_delivery_address;
        RadioButton radio_check_address;
        LinearLayout ll_mid;
        RelativeLayout rel_deliver_to_this_address,rel_edit_address,rl_radio,rl_main;
        MaterialCardView cv;

        private myViewHolder(View itemView) {
            super(itemView);
            tv_delivery_address = itemView.findViewById(R.id.tv_delivery_address);
            radio_check_address = itemView.findViewById(R.id.radio_check_address);
            ll_mid=itemView.findViewById(R.id.ll_mid);
            rel_deliver_to_this_address=itemView.findViewById(R.id.rel_deliver_to_this_address);
            rel_edit_address=itemView.findViewById(R.id.rel_edit_address);
            rl_main=itemView.findViewById(R.id.rl_main);
            cv=itemView.findViewById(R.id.cv);
            rl_radio=itemView.findViewById(R.id.rl_radio);


            radio_check_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();;
                    statusChecked=true;
                    listener.itemchceked(statusChecked);
                    notifyDataSetChanged();

                }
            });

            rl_main.setOnClickListener(v -> {
                lastSelectedPosition = getAdapterPosition();;
                statusChecked=true;
                listener.itemchceked(statusChecked);
                notifyDataSetChanged();
            });


        }
    }

    public boolean getQuantitysSelectionStatus() {
        boolean status = false;

        if (statusChecked == false) {
            status = true;
        }
        return status;
    }

    public interface onItemClickListener {
        void itemchceked(boolean statusChecked);
        void ondelete(int addressId, int pos);

        void OnDeliver(int addressId);
    }


}
