package com.retrospect.retrospect;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.retrospect.retrospect.model.TimeLineCircle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nithin on 2/28/2018.
 *
 */

public class TimelineFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<Event> mDataList = new ArrayList<>();
    private boolean mWithLinePadding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_timeline, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        if(mRecyclerView == null)
            Log.d("NULL","RecyclerView is null!");

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        initView();

        return view;
    }

    private void initView() {
        setDataListItems();
        mTimeLineAdapter = new TimeLineAdapter(mDataList, mWithLinePadding);
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }

    private void setDataListItems(){
        HashMap<String, String> hmapTest = new HashMap<String, String>(); //TODO remove
        HashMap<String, String[]> hmapTest2 = new HashMap<String, String[]>(); //TODO remove
        mDataList.add(new Event("Test Event Title", "6h ago", TimeLineCircle.INACTIVE, "details", "location",hmapTest2,hmapTest));
        mDataList.add(new Event("Test Event Title2", "Yesterday", TimeLineCircle.INACTIVE, "details", "location",hmapTest2,hmapTest));
        mDataList.add(new Event("Test Event Title2", "Yesterday", TimeLineCircle.INACTIVE, "details", "location",hmapTest2,hmapTest));
        mDataList.add(new Event("Test Event Title2", "Yesterday", TimeLineCircle.INACTIVE, "details", "location",hmapTest2,hmapTest));
        mDataList.add(new Event("Test Event Title2", "Yesterday", TimeLineCircle.INACTIVE, "details", "location",hmapTest2,hmapTest));
        mDataList.add(new Event("Test Event Title2", "Yesterday", TimeLineCircle.INACTIVE, "details", "location",hmapTest2,hmapTest));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Menu
        switch (item.getItemId()) {
            //When home is clicked
            case android.R.id.home:
                return true;

        }
        Log.d("",""+super.onOptionsItemSelected(item));
        return super.onOptionsItemSelected(item);
    }
}
