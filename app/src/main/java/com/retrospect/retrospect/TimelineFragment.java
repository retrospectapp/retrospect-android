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

import com.retrospect.retrospect.model.TimeLineCircle;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nithin on 2/28/2018.
 *
 */

public class TimelineFragment extends Fragment implements TimeLineAdapter.onItemClickListener {

    private List<Event> eventList = new ArrayList<>();
    private final static String TAG = "TOUCHEVENT";
    private SlidingUpPanelLayout slidingUpPanelLayout;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_timeline, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getData();

        TimeLineAdapter timeLineAdapter = new TimeLineAdapter(eventList, this);
        recyclerView.setAdapter(timeLineAdapter);

        slidingUpPanelLayout = view.findViewById(R.id.sliding_layout);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_DRAGGING)
                    Log.d(TAG, "Dragging");
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            }
        });

        return view;
    }

    private void getData() {
        HashMap<String, String> hmapTest = new HashMap<>(); //TODO remove
        HashMap<String, String[]> hmapTest2 = new HashMap<>(); //TODO remove
        eventList.add(new Event("Test 0", "1998-12-16 09:30", TimeLineCircle.INACTIVE, "details", "location", hmapTest2, hmapTest));
        eventList.add(new Event("Test 1", "1998-12-16 09:30", TimeLineCircle.INACTIVE, "details", "location", hmapTest2, hmapTest));
        eventList.add(new Event("Test 2", "1998-12-16 09:30", TimeLineCircle.INACTIVE, "details", "location", hmapTest2, hmapTest));
        eventList.add(new Event("Test 3", "1998-12-16 09:30", TimeLineCircle.INACTIVE, "details", "location", hmapTest2, hmapTest));
        eventList.add(new Event("Test 4", "1998-12-16 09:30", TimeLineCircle.INACTIVE, "details", "location", hmapTest2, hmapTest));
        eventList.add(new Event("Test 5", "1998-12-16 09:30", TimeLineCircle.INACTIVE, "details", "location", hmapTest2, hmapTest));
        eventList.add(new Event("Test 6", "1998-12-16 09:30", TimeLineCircle.INACTIVE, "details", "location", hmapTest2, hmapTest));
        eventList.add(new Event("Test 7", "1998-12-16 09:30", TimeLineCircle.INACTIVE, "details", "location", hmapTest2, hmapTest));
        eventList.add(new Event("Test 8", "1998-12-16 09:30", TimeLineCircle.INACTIVE, "details", "location", hmapTest2, hmapTest));
        eventList.add(new Event("Test 9", "1998-12-16 09:30", TimeLineCircle.INACTIVE, "details", "location", hmapTest2, hmapTest));
    }

    @Override
    public void onItemClicked(int position) {
        Log.d(TAG, "Title of event being clicked is: " + eventList.get(position).getTitle());
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }
}
