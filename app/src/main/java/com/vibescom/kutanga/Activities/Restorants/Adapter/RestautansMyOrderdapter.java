package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Models.ItemCountModel;
import com.vibescom.kutanga.Models.PastOrderModle.OrderDetailsModel;
import com.vibescom.kutanga.Models.PastOrderModle.PastOrderData;
import com.vibescom.kutanga.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.vibescom.kutanga.Constants.Constants.kCoverImage;

public class RestautansMyOrderdapter extends RecyclerView.Adapter<RestautansMyOrderdapter.myViewHolder> {
    private Context context;
    CopyOnWriteArrayList<PastOrderData> pastOrderData;
    CopyOnWriteArrayList<OrderDetailsModel> pastOrderDetailsList;
    OrderDetailsModel orderDetailsModel;
    JSONObject jsonObjectVendor;
    private onItemClickListener listener;
    ItemCountModel itemCountModel;
    boolean checked;
    String productImgPath;
    CopyOnWriteArrayList<ItemCountModel> productPriceslist;

    public RestautansMyOrderdapter(Context context, CopyOnWriteArrayList<PastOrderData> pastOrderData, onItemClickListener listener) {
        this.context = context;
        this.pastOrderData = pastOrderData;
        this.listener = listener;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.row_view_myorder, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {
        PastOrderData pastOrderData1 = pastOrderData.get(position);

        pastOrderDetailsList = pastOrderData1.getInTheSport();

        jsonObjectVendor = pastOrderDetailsList.get(0).getInTheSport();

        myViewHolder.txt_order_id.setText(pastOrderData1.getOrderCode());
        myViewHolder.txt_name.setText(pastOrderDetailsList.get(0).getVendorName());
        myViewHolder.tv_address.setText(pastOrderDetailsList.get(0).getVendorAddress());
        myViewHolder.tv_totalpabale.setText(pastOrderData1.getOrderPrice() + " " + "kz");
        myViewHolder.txt_order_pending.setText(pastOrderData1.getOrderStatus() + " " + "on");
        myViewHolder.txt_order_status.setText(convertUTCDateToLocalDate(pastOrderData1.getTransitionOn()));


        try {
            Log.d("Image", productImgPath + "/" + jsonObjectVendor.getString(kCoverImage));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (!jsonObjectVendor.getString(kCoverImage).isEmpty()) {
                String imgPath = productImgPath + "/" + jsonObjectVendor.getString(kCoverImage);
                Picasso.with(context).load(imgPath).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.ivBanner);
            } else {
                Picasso.with(context).load(R.drawable.ic_image_black_24dp).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.ivBanner);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        myViewHolder.tv_viewdetails.setOnClickListener(v -> {

            pastOrderDetailsList = pastOrderData.get(position).getInTheSport();
            productPriceslist = new CopyOnWriteArrayList<>();
            int subtotal=0;
            for (int i = 0; i < pastOrderDetailsList.size(); i++) {
                subtotal= subtotal+Integer.parseInt(pastOrderDetailsList.get(i).getItemQuantity())*Integer.parseInt(pastOrderDetailsList.get(i).getItemPrice());
                itemCountModel = new ItemCountModel(pastOrderDetailsList.get(i).getItemName(), pastOrderDetailsList.get(i).getItemQuantity(), pastOrderDetailsList.get(i).getItemPrice());
                productPriceslist.add(itemCountModel);
            }


            listener.onViewDetails(pastOrderData.get(position).getOrderCode(),subtotal,pastOrderData1, pastOrderDetailsList.get(0).getVendorName(), pastOrderDetailsList.get(0).getVendorAddress(), pastOrderDetailsList.get(0).getItemQuantityType(), pastOrderDetailsList.get(0).getItemQuantity(), pastOrderDetailsList.get(0).getItemPrice(), pastOrderDetailsList.get(0).getItemName(), productPriceslist);
        });

        if (pastOrderData1.getOrderStatus().equalsIgnoreCase("cancel")) {
            myViewHolder.tv_cacel_orderr.setVisibility(View.GONE);
            myViewHolder.img_end_order_cancel.setImageResource((R.drawable.ic_fill_corcle_tick));
            myViewHolder.img_start_order_placed.setImageResource((R.drawable.ic_fill_corcle_tick));
            myViewHolder.view_placed.setBackgroundColor(context.getResources().getColor(R.color.green));
            myViewHolder.view_cancel.setBackgroundColor(context.getResources().getColor(R.color.green));
            myViewHolder.tv_order_placed.setTextColor(context.getResources().getColor(R.color.green));
            myViewHolder.tv_order_cancel.setTextColor(context.getResources().getColor(R.color.green));
            myViewHolder.img_end_order_placed.setImageResource((R.drawable.ic_fill_corcle_tick));

        } else if (pastOrderData1.getOrderStatus().equalsIgnoreCase("pending")) {
            myViewHolder.tv_cacel_orderr.setVisibility(View.VISIBLE);
            myViewHolder.img_start_order_placed.setImageResource((R.drawable.ic_fill_corcle_tick));
            myViewHolder.view_placed.setBackgroundColor(context.getResources().getColor(R.color.green));
            myViewHolder.tv_order_placed.setTextColor(context.getResources().getColor(R.color.green));
            myViewHolder.img_end_order_placed.setImageResource((R.drawable.ic_fill_corcle_tick));

        } else if (pastOrderData1.getOrderStatus().equalsIgnoreCase("accepted")) {
            myViewHolder.tv_cacel_orderr.setVisibility(View.VISIBLE);
            myViewHolder.img_start_order_placed.setImageResource((R.drawable.ic_fill_corcle_tick));
            myViewHolder.view_placed.setBackgroundColor(context.getResources().getColor(R.color.green));
            myViewHolder.view_cancel.setBackgroundColor(context.getResources().getColor(R.color.green));
            myViewHolder.view_accepted.setBackgroundColor(context.getResources().getColor(R.color.green));


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(10, 0, 0, 0);
            myViewHolder.view_accepted.setLayoutParams(params);

            myViewHolder.tv_order_placed.setTextColor(context.getResources().getColor(R.color.green));
            myViewHolder.tv_order_cancel.setTextColor(context.getResources().getColor(R.color.green));
            myViewHolder.tv_order_accepted.setTextColor(context.getResources().getColor(R.color.green));
            myViewHolder.img_end_order_placed.setImageResource((R.drawable.ic_fill_corcle_tick));
            myViewHolder.img_end_order_cancel.setImageResource((R.drawable.ic_fill_corcle_tick));
            myViewHolder.img_end_order_accepted.setImageResource((R.drawable.ic_fill_corcle_tick));
        }

        myViewHolder.tv_track_status.setOnClickListener(v -> {
            if (myViewHolder.track_ll.getVisibility() == View.VISIBLE) {
                myViewHolder.track_ll.setVisibility(View.GONE);
            } else {
                myViewHolder.track_ll.setVisibility(View.VISIBLE);
            }
        });

        myViewHolder.tv_cacel_orderr.setOnClickListener(v -> {
            myViewHolder.tv_cacel_orderr.setVisibility(View.GONE);
            listener.onCnacelOrder(pastOrderData1.getOrderCode());
        });
    }

    @Override
    public int getItemCount() {
        return pastOrderData.size();
    }

    public void addData(CopyOnWriteArrayList<PastOrderData> list, String imagepath) {
        this.productImgPath = imagepath;
        pastOrderData.addAll(list);
        notifyDataSetChanged();
    }

    public void newData(CopyOnWriteArrayList<PastOrderData> list, String imagepath) {
        this.productImgPath = imagepath;
        pastOrderData.clear();
        pastOrderData.addAll(list);
        notifyDataSetChanged();
    }

    public String convertUTCDateToLocalDate(String date_string) {
        if (date_string.isEmpty()) {
            return "";
        }

        SimpleDateFormat oldFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //oldFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        String dueDateAsNormal = "";
        try {
            value = oldFormatter.parse(date_string);
            SimpleDateFormat newFormatter = new SimpleDateFormat("dd MMM yyyy (hh:mm a)");

           /* Date date = new SimpleDateFormat("yyyy-M-d").parse(date_string);
            String dayOfWeek = new SimpleDateFormat("EEE", Locale.ENGLISH).format(newFormatter);

            Log.d("day",dayOfWeek);*/
            newFormatter.setTimeZone(TimeZone.getDefault());
            dueDateAsNormal = newFormatter.format(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("date", dueDateAsNormal);

        return dueDateAsNormal;
    }


    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBanner;
        LinearLayout track_ll;
        TextView txt_order_id, txt_name, tv_cacel_orderr, tv_order_placed, tv_order_cancel, tv_order_accepted, txt_order_status, tv_address, txt_order_pending, tv_track_status, tv_viewdetails, tv_cacel_order, tv_totalpabale;
        ImageView img_start_order_placed, img_end_order_placed, img_end_order_cancel, img_end_order_accepted;
        View view_placed, view_cancel, view_accepted;

        private myViewHolder(View itemView) {
            super(itemView);
            ivBanner = itemView.findViewById(R.id.iv_banner);
            txt_order_id = itemView.findViewById(R.id.txt_order_id);
            txt_order_status = itemView.findViewById(R.id.txt_order_status);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_track_status = itemView.findViewById(R.id.tv_track_status);
            tv_viewdetails = itemView.findViewById(R.id.tv_viewdetails);
            tv_cacel_orderr = itemView.findViewById(R.id.tv_cacel_orderr);
            tv_totalpabale = itemView.findViewById(R.id.tv_totalpabale);
            txt_order_pending = itemView.findViewById(R.id.txt_order_pending);
            track_ll = itemView.findViewById(R.id.track_ll);
            txt_name = itemView.findViewById(R.id.txt_name);

            img_start_order_placed = itemView.findViewById(R.id.img_start_order_placed);
            img_end_order_placed = itemView.findViewById(R.id.img_end_order_placed);
            img_end_order_cancel = itemView.findViewById(R.id.img_end_order_cancel);
            img_end_order_accepted = itemView.findViewById(R.id.img_end_order_accepted);


            tv_order_placed = itemView.findViewById(R.id.tv_order_placed);
            tv_order_cancel = itemView.findViewById(R.id.tv_order_cancel);
            tv_order_accepted = itemView.findViewById(R.id.tv_order_accepted);

            view_placed = itemView.findViewById(R.id.view_placed);
            view_cancel = itemView.findViewById(R.id.view_cancel);
            view_accepted = itemView.findViewById(R.id.view_accepted);
        }
    }

    public interface onItemClickListener {
        void onViewDetails(String orderno,int subtotal,PastOrderData pastOrderData1, String vendorname, String vendorAddress, String itemQuantityType, String itemquantity, String itemPrice, String itemname, CopyOnWriteArrayList<ItemCountModel> productPriceslist);

        void onCnacelOrder(String orderId);


    }
}
