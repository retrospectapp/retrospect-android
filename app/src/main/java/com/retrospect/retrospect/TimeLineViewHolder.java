package com.retrospect.retrospect;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.github.vipulasri.timelineview.TimelineView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shreyas
 *
 */

public class TimeLineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    @BindView(R.id.text_timeline_date)
    TextView mDate;
    @BindView(R.id.text_timeline_title)
    TextView mMessage;
    @BindView(R.id.time_marker)
    TimelineView mTimelineView;

    private TimeLineAdapter.onItemClickListener onClickListener;

    TimeLineViewHolder(View itemView, int viewType, TimeLineAdapter.onItemClickListener onClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mTimelineView.initLine(viewType);
        this.onClickListener = onClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewPosition = getAdapterPosition();
        onClickListener.onItemClicked(viewPosition);
    }
}