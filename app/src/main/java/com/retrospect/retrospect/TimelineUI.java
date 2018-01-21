package com.retrospect.retrospect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class TimelineUI extends AppCompatActivity {

    FloatingActionMenu fam;
    FloatingActionButton people, event, reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_ui);
        fam = (FloatingActionMenu) findViewById(R.id.floatingActionMenu);
        people = (FloatingActionButton) findViewById(R.id.peeps);
        event = (FloatingActionButton) findViewById(R.id.eve);
        reminder = (FloatingActionButton) findViewById(R.id.remi);

        fam.setOnClickListener(new View.OnClickListener() {
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
}
