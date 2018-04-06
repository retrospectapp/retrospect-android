package com.retrospect.retrospect;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateReminderFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {


    Calendar now = Calendar.getInstance();
    private Button dateBtn;
    private Button timeBtn;
    private Button submit;
    private Button cancel;

    private String period;
    private String time;
    private String date;
    private String uid;

    private EditText entered_title;
    private EditText entered_descrip;

    private int month_set = -1;
    private int day_set = -1;
    private int year_set = -1;
    private int hour_set = -1;
    private int min_set = -1;

    private TextView dateTextView;
    private TextView timeTextView;

    private ReminderFragment reminderFragment;

    public CreateReminderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_reminder, container, false);

        dateBtn = (Button) v.findViewById(R.id.date);
        timeBtn = (Button) v.findViewById(R.id.time);
        submit = (Button) v.findViewById(R.id.submit_noti);
        cancel = (Button) v.findViewById(R.id.cancel_btn);

        timeTextView = (TextView) v.findViewById(R.id.timeTextView);
        dateTextView = (TextView) v.findViewById(R.id.dateTextView);

        entered_title = (EditText) v.findViewById(R.id.title_input);
        entered_descrip = (EditText) v.findViewById(R.id.descrip_input);

        uid = getArguments().getString("uid");


        final AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);


        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        CreateReminderFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR);
                int min = c.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), CreateReminderFragment.this, hour, min, false);

                timePickerDialog.show();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
//                if(month_set != -1 && day_set != -1 && year_set != -1 && hour_set != -1 && min_set != -1){
//                    if(entered_title.getText().toString() != null && entered_descrip.getText().toString()!=null){
//
//                    }
//                }


                String noti_title = entered_title.getText().toString();
                String noti_descrip = entered_descrip.getText().toString();
                int notifyID = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
                Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                intent.putExtra("noti_title", noti_title);
                intent.putExtra("identify_notify", notifyID);


                PendingIntent broadcast = PendingIntent.getBroadcast(getActivity(), notifyID, intent, 0);
                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.YEAR, year_set);
                calendar.set(Calendar.MONTH, month_set);
                calendar.set(Calendar.DAY_OF_MONTH, day_set);
                calendar.set(Calendar.HOUR_OF_DAY, hour_set);
                calendar.set(Calendar.MINUTE, min_set);
                calendar.set(Calendar.SECOND, 0);

                //TODO: create reminder and push to database
                FirebaseClient client = new FirebaseClient();
                //String title, String date, String time, String period, String details)

                Reminder reminder = new Reminder(noti_title, date, time, period, noti_descrip);
                client.createReminder(uid, Integer.toString(notifyID), reminder);

                Toast.makeText(getContext(), "Reminder Set", Toast.LENGTH_LONG).show();

                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);

                reminderFragment = new ReminderFragment();

                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, reminderFragment);
                fragmentTransaction.commit();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminderFragment = new ReminderFragment();

                Toast.makeText(getContext(), "Reminder Cancelled", Toast.LENGTH_LONG).show();

                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, reminderFragment);
                fragmentTransaction.commit();
            }
        });

        return v;

    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date1 = "Date: " + (month + 1) + "/" + dayOfMonth + "/" + year;
        year_set = year;
        month_set = month;
        day_set = dayOfMonth;
        dateTextView.setText(date1);
        date = (month+1)+ "/" + dayOfMonth + "/" + year;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Boolean morn;

        hour_set = hourOfDay;
        min_set = minute;

        if (hourOfDay > 12) {
            morn = false;
            period = "PM";
            hourOfDay = hourOfDay - 12;
        } else {
            morn = true;
            period = "AM";
        }
        String time1 = "Time: " + hourOfDay + "h" + minute + "m"  + " " + period;
        time = hourOfDay + ":" + minute;

        timeTextView.setText(time1);
    }
}