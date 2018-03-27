package com.retrospect.retrospect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vipulasri.timelineview.TimelineView;
import com.retrospect.retrospect.model.*;
import com.retrospect.retrospect.utils.DateTimeUtils;
import com.retrospect.retrospect.utils.VectorDrawableUtils;

import java.util.List;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {

    private List<Event> eventList;
    private Context context;
    private onItemClickListener onClickListener;

    TimeLineAdapter(List<Event> eventList, onItemClickListener onClickListener) {
        this.eventList = eventList;
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override @NonNull
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater mLayoutInflater = LayoutInflater.from(context);
        View view = mLayoutInflater.inflate(R.layout.item_timeline, parent, false);
        return new TimeLineViewHolder(view, viewType, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {

        Event event = eventList.get(position);

        if(event.getStatus() == TimeLineCircle.INACTIVE) {
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(context, R.drawable.ic_marker_inactive, android.R.color.darker_gray));
        } else if(event.getStatus() == TimeLineCircle.ACTIVE) {
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(context, R.drawable.ic_marker_active, R.color.colorPrimary));
        } else {
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(context, R.drawable.ic_marker), ContextCompat.getColor(context, R.color.colorPrimary));
        }

        if(!event.getDate().isEmpty()) {
            holder.mDate.setVisibility(View.VISIBLE);
            holder.mDate.setText(DateTimeUtils.parseDateTime(event.getDate(), "yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy"));
        }
        else
            holder.mDate.setVisibility(View.GONE);

        holder.mMessage.setText(event.getTitle());
    }

    @Override
    public int getItemCount() {
        return (eventList !=null? eventList.size():0);
    }

    public interface onItemClickListener{
        void onItemClicked(int position);
    }
}
