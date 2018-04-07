package com.retrospect.retrospect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by sniradi on 3/26/18.
 *
 */

public class ConnectionsAdapter extends RecyclerView.Adapter<ConnectionViewHolder> {
    private List<Connection> connectionsList;
    private Context context;

    private ConnectionsAdapter.onItemClickListener onClickListener;

    ConnectionsAdapter(List<Connection> connectionsList, ConnectionsAdapter.onItemClickListener onClickListener) {
        this.connectionsList = connectionsList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ConnectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater mLayoutInflater = LayoutInflater.from(context);
        View view = mLayoutInflater.inflate(R.layout.connection_card, parent, false);
        return new ConnectionViewHolder(view, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ConnectionViewHolder holder, int position) {
        ImageView profPic = holder.profilePicture;
        Glide.with(context).load(connectionsList.get(position).getUser().getImageURL()).into(profPic);
        TextView name = holder.nameView;
        name.setText(connectionsList.get(position).getUser().getFullName());
        TextView relationship = holder.relationshipView;
        relationship.setText(connectionsList.get(position).getRelation());
    }

    @Override
    public int getItemCount() {
        return (connectionsList !=null? connectionsList.size():0);
    }

    public interface onItemClickListener{
        void onItemClicked(int position);
    }
}
