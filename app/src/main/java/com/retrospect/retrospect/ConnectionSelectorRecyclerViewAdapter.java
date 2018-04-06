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

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Shivam on 4/4/2018.
 */

public class ConnectionSelectorRecyclerViewAdapter extends RecyclerView.Adapter<ConnectionSelectorRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = ConnectionSelectorRecyclerViewAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<User> fetchedUsers;
    private User selectedUser;


    public ConnectionSelectorRecyclerViewAdapter(Context mContext, ArrayList<User> fetchedUsers){
        this.mContext = mContext;
        this.fetchedUsers = fetchedUsers;
    }

    @NonNull
    @Override
    public ConnectionSelectorRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.connection_selector_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ConnectionSelectorRecyclerViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        final User user = fetchedUsers.get(position);
        Glide.with(mContext).asBitmap().load(fetchedUsers.get(position).getImageURL()).into(holder.profileImage);
        //holder.connectionName.setText(connections.get(position).getName());
        holder.userName.setText(fetchedUsers.get(position).getFirstName() + fetchedUsers.get(position).getLastName());
        if (fetchedUsers.get(position).getIsPatient()){
            holder.userIsPatient.setText("Patient");
        }
        else{
            holder.userIsPatient.setText("Caretaker");
        }
        holder.userAge.setText(fetchedUsers.get(position).getAge());
    }

    @Override
    public int getItemCount() {
        return fetchedUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView profileImage;
        TextView userName;
        TextView userIsPatient;
        TextView userAge;

        public ViewHolder(View itemView){
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            userName = itemView.findViewById(R.id.userName);
            userIsPatient = itemView.findViewById(R.id.userIsPatient);
            userAge = itemView.findViewById(R.id.userAge);
        }
    }
}
