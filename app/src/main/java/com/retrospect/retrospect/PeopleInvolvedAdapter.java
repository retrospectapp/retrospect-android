package com.retrospect.retrospect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class PeopleInvolvedAdapter extends RecyclerView.Adapter<PeopleInvolvedAdapter.ViewHolder>{

    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();
    private Context mContext;

    public PeopleInvolvedAdapter(Context context, ArrayList<String> imageUrls, ArrayList<String> names){
        mNames = names;
        mImageUrls = imageUrls;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.people_involved_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(R.drawable.placeholder) //todo change to mImageUrls.get(position)
                .into(holder.profileImage);
        holder.name.setText(mNames.get(position));

        holder.profileImage.setOnClickListener(new View.OnClickListener(){
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
        ImageView profileImage;
        TextView name;

        public ViewHolder(View itemView){
            super(itemView);
            profileImage = itemView.findViewById(R.id.people_involved_image);
            name = itemView.findViewById(R.id.people_involved_name);

        }
    }

    @Override
    public int getItemViewType(int position) {  //from stackoverflow
        return position;
    }
}
