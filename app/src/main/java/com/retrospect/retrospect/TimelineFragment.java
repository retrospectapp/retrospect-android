package com.retrospect.retrospect;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.retrospect.retrospect.model.TimeLineCircle;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimelineFragment extends Fragment implements TimeLineAdapter.onItemClickListener, OnMapReadyCallback {

    private  ArrayList<String> mImageUrls = new ArrayList<>();
    private  ArrayList<String> mProfileImageUrls = new ArrayList<>();
    private  ArrayList<String> mProfileNames = new ArrayList<>();
    private List<Event> eventList = new ArrayList<>();
    private final static String TAG = "TOUCHEVENT";
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private MapView mMapView;
    private GoogleMap mMap;
    private FirebaseClient client = new FirebaseClient();


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
        initSidewaysImageScroll(view);
        initPeopleInvolved(view);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.detailsMap);
        mapFragment.getMapAsync(this);

        SlidingUpPanelLayout mSlideUpLayout = new SlidingUpPanelLayout(getContext());
        mSlideUpLayout.setScrollableView(view.findViewById(R.id.slidingScrollView));

        return view;
    }

    private void initPeopleInvolved(View view){
        //begin hardcoding//
        mProfileImageUrls.add("urlgoeshere");
        mProfileNames.add("Booooob");
        RecyclerView imageScroll = view.findViewById(R.id.people_involved);
        imageScroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        PeopleInvolvedAdapter adapter = new PeopleInvolvedAdapter(getActivity(), mProfileImageUrls, mProfileNames);
        imageScroll.setAdapter(adapter);

    }

    private void initSidewaysImageScroll(View view){
        //hardcoding start//
        mImageUrls.add("urlgoeshere");
        //end hardcoding

        RecyclerView imageScroll = view.findViewById(R.id.horiz_image_scroll);
        imageScroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        HorizImageAdapter adapter = new HorizImageAdapter(getActivity(),mImageUrls);
        imageScroll.setAdapter(adapter);
    }

    private void getData() {
        //List<Event> listofevents = client.fetchEvents();
        HashMap<String, String> hmapTest = new HashMap<>(); //TODO remove hardcoding
        HashMap<String, String[]> hmapTest2 = new HashMap<>(); //TODO remove
        eventList.add(new Event("Test 0", "1998-12-16 09:30", TimeLineCircle.INACTIVE, "details1", "Inklings Coffee, Pleasanton", hmapTest2, hmapTest));
        eventList.add(new Event("Test 1", "1998-12-16 09:30", TimeLineCircle.ACTIVE, "details2", "3526 Sandalford Way, San Ramon, CA", hmapTest2, hmapTest));
        eventList.add(new Event("Test 2", "1998-12-16 09:30", TimeLineCircle.COMPLETED, "details2", "location", hmapTest2, hmapTest));
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
        LatLng address = getLocationFromAddress(getContext(),eventList.get(position).getLocation());
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(address).title(eventList.get(position).getLocation()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(address));
        mMap.setMinZoomPreference(11);
        mMap.setMaxZoomPreference(12);
        //mImageUrls.clear();

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //map.setMyLocationEnabled(true);
        //map.setTrafficEnabled(true);

        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(false);
    }

    public LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;
    }

}
