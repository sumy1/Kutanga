package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vibescom.kutanga.Models.NotificationModel.NotificationData;
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

import static com.vibescom.kutanga.Constants.Constants.kCreatedate;
import static com.vibescom.kutanga.Constants.Constants.ktemplateDetails;
import static com.vibescom.kutanga.Constants.Constants.ktemplateSubject;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.myViewHolder> {
    private Context context;
    CopyOnWriteArrayList<NotificationData> notifactiondata;
    private onItemClickListener listener;

    public NotificationsAdapter(Context context, CopyOnWriteArrayList<NotificationData> notifactiondata) {
        this.context = context;
        this.notifactiondata=notifactiondata;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.row_view_notifications, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {
        NotificationData notificationDataa=notifactiondata.get(position);

        JSONObject jsonObject=notificationDataa.getJsonObject();

        if(jsonObject.has(ktemplateSubject)){
            try {
                myViewHolder.tv_name.setText(jsonObject.getString(ktemplateSubject));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        if(jsonObject.has(ktemplateDetails)){
            try {
                myViewHolder.tv_sucess.setText(jsonObject.getString(ktemplateDetails));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        if(jsonObject.has(kCreatedate)){
            try {
                myViewHolder.tv_date.setText(convertUTCDateToLocalDate(jsonObject.getString(kCreatedate)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }




    }

    @Override
    public int getItemCount() {
        return notifactiondata.size();
    }

    public void addData(CopyOnWriteArrayList<NotificationData> list) {
        notifactiondata.addAll(list);
        notifyDataSetChanged();
    }

    public void newData(CopyOnWriteArrayList<NotificationData> list) {
        notifactiondata.clear();
        notifactiondata.addAll(list);
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
        TextView tv_sucess,tv_name,tv_date;
        private myViewHolder(View itemView) {
            super(itemView);
            tv_sucess=itemView.findViewById(R.id.tv_sucess);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_date=itemView.findViewById(R.id.tv_date);
        }
    }

    public interface onItemClickListener{
       // void onFacilityClick(UserFacAca facAca);
    }
}
