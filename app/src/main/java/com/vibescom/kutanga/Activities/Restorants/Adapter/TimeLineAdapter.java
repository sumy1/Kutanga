package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.github.vipulasri.timelineview.TimelineView;
import com.vibescom.kutanga.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {
    private Context context;
    //private CopyOnWriteArrayList<UserFacAca> mData;
    private onItemClickListener listener;

    public TimeLineAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = View.inflate(parent.getContext(), R.layout.item_time_line, null);

        return new TimeLineViewHolder(view, viewType);

    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {

    }


    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }
    @Override
    public int getItemCount() {
        return 5;
    }


    public class TimeLineViewHolder extends RecyclerView.ViewHolder {
        public TimelineView mTimelineView;

        public TimeLineViewHolder(View itemView, int viewType) {
            super(itemView);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.txt_timeline);
            mTimelineView.initLine(viewType);
        }
    }

    public interface onItemClickListener {
        // void onFacilityClick(UserFacAca facAca);
    }
}
