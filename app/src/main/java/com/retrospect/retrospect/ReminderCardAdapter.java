package com.retrospect.retrospect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kunalpatel on 3/29/18.
 */


public class ReminderCardAdapter extends RecyclerView.Adapter<ReminderCardAdapter.ReminderCardViewHolder> {
    private Context mContext;
    private List<Reminder> reminderList;

    public ReminderCardAdapter(List<Reminder> reminderList, Context mContext) {
        this.mContext = mContext;
        this.reminderList = reminderList;
    }

    @Override
    public ReminderCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.reminder_card, parent, false);
        return new ReminderCardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReminderCardViewHolder holder, int position) {
        Reminder currReminder = reminderList.get(position);
        holder.title.setText(currReminder.getTitle());
        holder.timings.setText( currReminder.getDate() + " "+ currReminder.getTime() + " " + currReminder.getPeriod());
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    class ReminderCardViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView timings;

        public ReminderCardViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.textViewTitle);
            timings = (TextView) itemView.findViewById(R.id.textViewTimings);
        }
    }
}