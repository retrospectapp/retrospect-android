package com.retrospect.retrospect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Shivam on 3/27/2018.
 */

public class EventParticipantsRecyclerViewAdapter extends RecyclerView.Adapter<EventParticipantsRecyclerViewAdapter.EventParticipantsViewHolder> {

    private static final String TAG = EventParticipantsRecyclerViewAdapter.class.getSimpleName();
    Context mContext;
    ArrayList<Connection> connections = new ArrayList<>();
    ArrayList<Connection> selectedConnections = new ArrayList<>();
    ConnectionsInvolvedClickListener listener;


    public EventParticipantsRecyclerViewAdapter(Context mContext, ArrayList<Connection> connections, ConnectionsInvolvedClickListener listener){
        this.mContext = mContext;
        this.connections = connections;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventParticipantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.event_participant_card_view, parent, false);
        return new EventParticipantsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventParticipantsViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        final Connection connection = connections.get(position);
        Glide.with(mContext).asBitmap().load(connections.get(position).getUser().getImageURL()).into(holder.profileImage);
        //holder.connectionName.setText(connections.get(position).getName());
        holder.connectionRelation.setText(connections.get(position).getRelation());
        holder.checkBox.setChecked(false);
        holder.checkBox.setTag(connections.get(position));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    connection.setIsSelected(true);
                    selectedConnections.add(connection);
                }
                else{
                    connection.setIsSelected(false);
                    selectedConnections.remove(connection);
                }
            }
        });
        holder.checkBox.setChecked(connection.isSelected());
    }

    @Override
    public int getItemCount() {
        return connections.size();
    }

    public class EventParticipantsViewHolder extends RecyclerView.ViewHolder{

        CircleImageView profileImage;
        TextView connectionName;
        TextView connectionRelation;
        CheckBox checkBox;

        public EventParticipantsViewHolder(View itemView){
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            connectionName = itemView.findViewById(R.id.connectionName);
            connectionRelation = itemView.findViewById(R.id.connectionRelation);
            checkBox = itemView.findViewById(R.id.connectionCardCheckBox);
        }
    }

    public ArrayList<Connection> getSelectedConnections(){
        return selectedConnections;
    }
}
