package com.retrospect.retrospect;

import android.app.Activity;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Shivam on 3/26/2018.
 */

public class EventDetailsFragment extends Fragment {

    private static final String TAG = EventDetailsFragment.class.getSimpleName();

    private TextView mDisplayDate;
    private Button mSelectDate;
    private EditText mEventTitle;
    private EditText mEventDetails;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.event_details_fragment, container, false);

        mDisplayDate = view.findViewById(R.id.eventDateTextView);
        mSelectDate = view.findViewById(R.id.selectDateButton);
        mEventTitle = view.findViewById(R.id.eventTitleEditText);
        mEventDetails = view.findViewById(R.id.eventDetailsEditText);

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        mDisplayDate.setText(dateFormat.format(date));

        mSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        return view;
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            String date = String.valueOf(monthOfYear+1) + "/" + String.valueOf(dayOfMonth) + "/" + String.valueOf(year);
            mDisplayDate.setText(date);
        }
    };

    public String getDate(){
        return mDisplayDate.getText().toString();
    }

    public String getEventTitle(){
        return mEventTitle.getText().toString();
    }

    public String getEventDetails(){
        return mEventDetails.getText().toString();
    }

}

