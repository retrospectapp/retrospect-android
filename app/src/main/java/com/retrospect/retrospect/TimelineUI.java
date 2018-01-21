package com.retrospect.retrospect;

import android.os.Bundle;
import android.app.Dialog;
import android.app.ListActivity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.qap.ctimelineview.TimelineRow;
import org.qap.ctimelineview.TimelineViewAdapter;

import java.util.ArrayList;
import java.util.Date;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class TimelineUI extends ListActivity {
    Dialog myDialog;


    FloatingActionMenu fam;
    FloatingActionButton people, event, reminder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timeline_ui);   //Some guy on stackoverflow told me I don't need this?
        myDialog = new Dialog(this);


        // Create Timeline rows List
        ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();

        // Create new timeline row (Row Id)
        TimelineRow myRow = new TimelineRow(0);

// To set the row Date (optional)
        myRow.setDate(new Date(0));  //(new Date());
// To set the row Title (optional)
        myRow.setTitle("Met with contact: Bob Ross");
// To set the row Description (optional)
        myRow.setDescription("description (string)");
// To set the row bitmap image (optional)
        myRow.setImage(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
// To set row Below Line Color (optional)
        myRow.setBellowLineColor(Color.argb(255, 0, 0, 0));
// To set row Below Line Size in dp (optional)
        myRow.setBellowLineSize(6);
// To set row Image Size in dp (optional)
        myRow.setImageSize(40);
// To set background color of the row image (optional)
        myRow.setBackgroundColor(Color.argb(255, 0, 0, 0));
// To set the Background Size of the row image in dp (optional)
        myRow.setBackgroundSize(60);
// To set row Date text color (optional)
        myRow.setDateColor(Color.argb(255, 0, 0, 0));
// To set row Title text color (optional)
        myRow.setTitleColor(Color.argb(255, 0, 0, 0));
// To set row Description text color (optional)
        myRow.setDescriptionColor(Color.argb(255, 0, 0, 0));

// Add the new row to the list
        timelineRowsList.add(myRow);
        timelineRowsList.add(myRow);
        timelineRowsList.add(myRow);
        timelineRowsList.add(myRow);
        timelineRowsList.add(myRow);
        timelineRowsList.add(myRow);
        timelineRowsList.add(myRow);
        timelineRowsList.add(myRow);
        timelineRowsList.add(myRow);
        timelineRowsList.add(myRow);


        // Create the Timeline Adapter
        ArrayAdapter<TimelineRow> myAdapter = new TimelineViewAdapter(this, 0, timelineRowsList,
                //if true, list will be sorted by date
                false);

        // Get the ListView and Bind it with the Timeline Adapter
        ListView myListView = getListView();//(ListView) findViewById(R.id.lis);
        myListView.setAdapter(myAdapter);

//        setContentView(R.layout.activity_timeline_ui);

        fam = (FloatingActionMenu) findViewById(R.id.floatingActionMenu);
        people = (FloatingActionButton) findViewById(R.id.peeps);
        event = (FloatingActionButton) findViewById(R.id.eve);
        reminder = (FloatingActionButton) findViewById(R.id.remi);


        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);
        this.ShowPopup(getListView(), pos);//TODO PLAN: have an array of event details (each index is an event), associate 'id' with index
    }

    public void ShowPopup(View v, int index) {
        TextView txtclose;
        myDialog.setContentView(R.layout.details_popup);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        TextView placeholder;
        placeholder = (TextView) myDialog.findViewById(R.id.indexPlaceholder);
        placeholder.setText(index + "");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}

