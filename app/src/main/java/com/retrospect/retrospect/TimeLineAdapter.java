package com.retrospect.retrospect;

import android.content.Context;
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

/**
 * Created by nithin.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {

    private List<Event> mFeedList;
    private Context mContext;
    private boolean mWithLinePadding;

    TimeLineAdapter(List<Event> feedList, boolean withLinePadding) {
        mFeedList = feedList;
        mWithLinePadding = withLinePadding;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        View view;
        view = mLayoutInflater.inflate(mWithLinePadding ? R.layout.item_timeline_line_padding : R.layout.item_timeline, parent, false);

        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {

        Event event = mFeedList.get(position);

        if(event.getStatus() == TimeLineCircle.INACTIVE) {
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_inactive, android.R.color.darker_gray));
        } else if(event.getStatus() == TimeLineCircle.ACTIVE) {
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_active, R.color.colorPrimary));
        } else {
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_marker), ContextCompat.getColor(mContext, R.color.colorPrimary));
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
        return (mFeedList!=null? mFeedList.size():0);
    }

}
