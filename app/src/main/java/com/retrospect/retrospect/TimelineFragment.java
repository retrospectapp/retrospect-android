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
import android.widget.EditText;
import android.widget.TextView;

import com.retrospect.retrospect.model.TimeLineCircle;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimelineFragment extends Fragment implements TimeLineAdapter.onItemClickListener {

    private  ArrayList<String> mImageUrls = new ArrayList<>();
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
        getImages(view);
        return view;
    }

    private void getImages(View view){
        mImageUrls.add("https://imgur.com/ZcLLrkY.jpg");    //doesn't work
        mImageUrls.add("https://imgur.com/ZcLLrkY.jpg");
        mImageUrls.add("https://imgur.com/ZcLLrkY.jpg");
        mImageUrls.add("https://imgur.com/ZcLLrkY.jpg");
        mImageUrls.add("https://imgur.com/ZcLLrkY.jpg");
        mImageUrls.add("https://imgur.com/ZcLLrkY.jpg");
        mImageUrls.add("https://imgur.com/ZcLLrkY.jpg");
        initSidewaysImageScroll(view);
    }

    private void initSidewaysImageScroll(View view){
        RecyclerView imageScroll = view.findViewById(R.id.horiz_image_scroll);
        imageScroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        HorizImageAdapter adapter = new HorizImageAdapter(getActivity(),mImageUrls);
        imageScroll.setAdapter(adapter);
    }

    private void getData() {
        HashMap<String, String> hmapTest = new HashMap<>(); //TODO remove
        HashMap<String, String[]> hmapTest2 = new HashMap<>(); //TODO remove
        eventList.add(new Event("Test 0", "1998-12-16 09:30", TimeLineCircle.INACTIVE, "details1", "location", hmapTest2, hmapTest));
        eventList.add(new Event("Test 1", "1998-12-16 09:30", TimeLineCircle.INACTIVE, "details2", "location", hmapTest2, hmapTest));
        eventList.add(new Event("Test 2", "1998-12-16 09:30", TimeLineCircle.INACTIVE, "details2", "location", hmapTest2, hmapTest));
        eventList.add(new Event("Test 3", "1998-12-16 09:30", TimeLineCircle.INACTIVE, "details3", "location", hmapTest2, hmapTest));
        eventList.add(new Event("Test 4", "1998-12-16 09:30", TimeLineCircle.INACTIVE, "details4", "location", hmapTest2, hmapTest));
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
        TextView title = getActivity().findViewById(R.id.detailsTitle);
        title.setText(eventList.get(position).getTitle());
        EditText details = getActivity().findViewById(R.id.details_description);
        details.setText(eventList.get(position).getDetails());
    }
}
