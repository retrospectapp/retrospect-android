package com.retrospect.retrospect;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sniradi on 3/26/18.
 *
 */

public class ConnectionsFragment extends Fragment implements ConnectionsAdapter.onItemClickListener {

    private List<Connection> connectionsList = new ArrayList<>();
    private final static String TAG = TimelineFragment.class.getSimpleName();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.connection_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.connectionRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getData();

        ConnectionsAdapter connectionsAdapter = new ConnectionsAdapter(connectionsList, this);
        recyclerView.setAdapter(connectionsAdapter);

        return view;
    }

    private void getData() {
    }

    @Override
    public void onItemClicked(int position) {
        Log.d(TAG, "Title of event being clicked is: " + connectionsList.get(position).getUser().getFullName());
    }
}

