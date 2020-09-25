package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.vibescom.kutanga.Models.ReviewModel.ReviewDataModel;
import com.vibescom.kutanga.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestautansReviewsAdapter extends RecyclerView.Adapter<RestautansReviewsAdapter.myViewHolder> {
    private Context context;
    private CopyOnWriteArrayList<ReviewDataModel> mData;
//    private onItemClickListener listener;

    public RestautansReviewsAdapter(Context context, CopyOnWriteArrayList<ReviewDataModel> mData) {
        this.context = context;
        this.mData=mData;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.row_view_reviews, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {

        ReviewDataModel reviewDataModel = mData.get(position);

        myViewHolder.tv_review.setText(reviewDataModel.getReview());

        /*if(reviewDataModel.getRating().equalsIgnoreCase("")||reviewDataModel.getRating().equalsIgnoreCase("0")){
            myViewHolder.mid.setVisibility(View.GONE);
        }else{
            myViewHolder.mid.setVisibility(View.VISIBLE);


        }*/

        myViewHolder.ratingBar.setRating(Float.parseFloat(String.valueOf(reviewDataModel.getRating())));
        myViewHolder.tv_date.setText(convertUTCDateToLocalDate(reviewDataModel.getCreatedAt()));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(CopyOnWriteArrayList<ReviewDataModel> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void newData(CopyOnWriteArrayList<ReviewDataModel>list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }
    public String convertUTCDateToLocalDate(String date_string) {
        if (date_string.isEmpty()){
            return "";
        }

        SimpleDateFormat oldFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //oldFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        String dueDateAsNormal ="";
        try {
            value = oldFormatter.parse(date_string);
            SimpleDateFormat newFormatter = new SimpleDateFormat("dd MMM yyyy");

            newFormatter.setTimeZone(TimeZone.getDefault());
            dueDateAsNormal = newFormatter.format(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("date",dueDateAsNormal);

        return dueDateAsNormal;
    }
    class myViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name,tv_date,tv_review;
        RatingBar ratingBar;
        LinearLayout mid;
        private myViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.review_tv_name);
            tv_date = itemView.findViewById(R.id.tv_date_text);
            tv_review = itemView.findViewById(R.id.review_tv_msg_text);
            ratingBar = itemView.findViewById(R.id.rating_bar_review);
            //mid=itemView.findViewById(R.id.mid);
        }
    }

    public interface onItemClickListener{
       // void onFacilityClick(UserFacAca facAca);
    }
}
