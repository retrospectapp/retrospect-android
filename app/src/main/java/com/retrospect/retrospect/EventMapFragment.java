package com.retrospect.retrospect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Shivam on 3/26/2018.
 */

public class EventMapFragment extends Fragment {

    private static final String TAG = EventMapFragment.class.getSimpleName();
    private View view;
    private TextView mTabName;
    private EditText mLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.event_map_fragment, container, false);
        mTabName = view.findViewById(R.id.eventMapTab);

        Log.d(TAG, String.valueOf(view == null));

        return view;
    }

    public String getmLocation(){
        return mLocation.getText().toString();
    }
}
