package com.retrospect.retrospect;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Shivam on 3/27/2018.
 */

public class EventImagesRecyclerViewAdapter extends RecyclerView.Adapter<EventImagesRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Uri> mImageUrls = new ArrayList<>();
    private Context mContext;

    public EventImagesRecyclerViewAdapter(Context context, ArrayList<Uri> images){
        this.mContext = context;
        this.mImageUrls = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_image_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        //Get images and attach them to respective cardviews
        Glide.with(mContext).asBitmap().load(new File(mImageUrls.get(position).getPath())).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.eventImage);
        }
    }
}
