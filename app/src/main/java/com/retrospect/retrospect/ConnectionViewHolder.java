package com.retrospect.retrospect;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

/**
 * Created by sniradi on 3/26/18.
 *
 */

public class ConnectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    @BindView(R.id.connection_name)
    TextView nameView;

    @BindView(R.id.connection_relationship)
    TextView relationshipView;

    @BindView(R.id.connection_picture)
    ImageView profilePicture;

    private ConnectionsAdapter.onItemClickListener onClickListener;

    ConnectionViewHolder(View itemView, ConnectionsAdapter.onItemClickListener onClickListener) {
        super(itemView);
        this.onClickListener = onClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewPosition = getAdapterPosition();
        onClickListener.onItemClicked(viewPosition);
    }

}
