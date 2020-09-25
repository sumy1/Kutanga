package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.OfferData;
import com.vibescom.kutanga.R;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestautansOfferAdapter extends RecyclerView.Adapter<RestautansOfferAdapter.myViewHolder> {
    private Context context;

    CopyOnWriteArrayList<OfferData> offerData;
    String ImagePath;
    public int mSelectedItem = -1;

    public RestautansOfferAdapter(Context context, CopyOnWriteArrayList<OfferData> offerData, String ImagePath) {
        this.context = context;
        this.offerData = offerData;
        this.ImagePath = ImagePath;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.row_view_restorants_offer, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {
        OfferData offerDatamodel = offerData.get(position);
        if (!offerDatamodel.getImage().isEmpty()) {
            String imgPath = "http://markfoodbetav4.kutanga.store/storage/images/offers" + "/" + offerDatamodel.getImage();
            Picasso.with(context).load(imgPath).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.ivBanner);
        } else {
            Picasso.with(context).load(R.drawable.ic_image_black_24dp).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.ivBanner);
        }

        myViewHolder.txt_coupon_code.setText(offerDatamodel.getPromoCode());
        myViewHolder.txt_type.setText(offerDatamodel.getOffername());
        myViewHolder.tv_offer_given_by.setText(offerDatamodel.getDescription());

        /*Spannable wordtoSpan = new SpannableString(context.getResources().getString(R.string.use_code)+" "+offerDatamodel.getPromoCode()+" "+context.getResources().getString(R.string.get)+" "+offerDatamodel.getOffferDiscount()+"% "+context.getResources().getString(R.string.use_code_get)+" "+context.getResources().getString(R.string.kz_order)+" "+offerDatamodel.getMinOffer()+" "+context.getResources().getString(R.string.kg)+" "+context.getResources().getString(R.string.above_and));

        wordtoSpan.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.green)), 10, 42, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        wordtoSpan.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red)), 43, 51, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red)), 61, 68, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/

        myViewHolder.txt_use_code_des.setText(context.getResources().getString(R.string.use_code)+" "+offerDatamodel.getPromoCode()+" "+context.getResources().getString(R.string.get)+" "+offerDatamodel.getOffferDiscount()+"% "+context.getResources().getString(R.string.use_code_get)+" "+context.getResources().getString(R.string.kz_order)+" "+offerDatamodel.getMinOffer()+" "+context.getResources().getString(R.string.kg)+" "+context.getResources().getString(R.string.above_and));

        myViewHolder.txt_more.setOnClickListener(v -> {
            if(myViewHolder.lay_tersms.getVisibility()== View.VISIBLE){
                myViewHolder.lay_tersms.setVisibility(View.GONE);
                myViewHolder.txt_more.setText(context.getResources().getString(R.string.plus_more));
            }else{
                myViewHolder.lay_tersms.setVisibility(View.VISIBLE);
                myViewHolder.txt_more.setText(context.getResources().getString(R.string.minus_more));
            }
        });

        myViewHolder.txt_copied.setOnClickListener(v -> {
            mSelectedItem = position;
            String copyText=offerDatamodel.getPromoCode();
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
                        context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(copyText);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                        context.getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData
                        .newPlainText("Your coupon ", copyText);
                clipboard.setPrimaryClip(clip);
            }
            myViewHolder.txt_copied.setBackground(context.getResources().getDrawable(R.drawable.canvas_signin_btn_bg));
            myViewHolder.txt_copied.setText(context.getResources().getString(R.string.copied));
            myViewHolder.txt_copied.setTextColor(context.getResources().getColor(R.color.white));
            notifyDataSetChanged();

        });

        if(position==mSelectedItem){
            myViewHolder.txt_copied.setBackground(context.getResources().getDrawable(R.drawable.canvas_signin_btn_bg));
            myViewHolder.txt_copied.setText(context.getResources().getString(R.string.copied));
            myViewHolder.txt_copied.setTextColor(context.getResources().getColor(R.color.white));
        }else{
            myViewHolder.txt_copied.setBackground(context.getResources().getDrawable(R.drawable.canvas_signin_btn_bg_border));
            myViewHolder.txt_copied.setText(context.getResources().getString(R.string.copy_code));
            myViewHolder.txt_copied.setTextColor(context.getResources().getColor(R.color.user_theme_color));
        }
        myViewHolder.txt_copied.setChecked(position == mSelectedItem);
    }

    @Override
    public int getItemCount() {
        return (offerData.size() > 0) ? offerData.size() : 0;
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBanner;
        TextView txt_coupon_code,txt_type,txt_use_code_des,txt_more,tv_offer_given_by;
        LinearLayout lay_tersms;
        RadioButton txt_copied;

        private myViewHolder(View itemView) {
            super(itemView);
            ivBanner = itemView.findViewById(R.id.iv_banner);
            txt_coupon_code=itemView.findViewById(R.id.txt_coupon_Type);
            txt_type=itemView.findViewById(R.id.txt_type);
            txt_use_code_des=itemView.findViewById(R.id.txt_use_code_des);
            lay_tersms=itemView.findViewById(R.id.lay_tersms);
            txt_more=itemView.findViewById(R.id.txt_more);
            tv_offer_given_by=itemView.findViewById(R.id.tv_offer_given_by);
            txt_copied=itemView.findViewById(R.id.txt_copied);
        }
    }


}
