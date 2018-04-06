package com.retrospect.retrospect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class HorizImageAdapter extends RecyclerView.Adapter<HorizImageAdapter.ViewHolder>{

    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;

    public HorizImageAdapter(Context context, ArrayList<String> imageUrls){
        mImageUrls = imageUrls;
        mContext = context;
    }

    
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_images_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(R.drawable.placeholder) //todo change to mImageUrls.get(position)
                .into(holder.detailsImage);

        holder.detailsImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView detailsImage;

        public ViewHolder(View itemView){
            super(itemView);
            detailsImage = itemView.findViewById(R.id.details_image);

        }
    }

    @Override
    public int getItemViewType(int position) {  //from stackoverflow
        return position;
    }
}
