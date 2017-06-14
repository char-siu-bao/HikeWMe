package com.example.judejoseph.bootcamplocator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.judejoseph.bootcamplocator.R;

public class TrailDetailsActivity extends AppCompatActivity {


    private TextView trail_cell_title;
    private TextView trail_cell_address;
    private TextView trail_cell_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_details);

        Intent intent = getIntent();
        trail_cell_address = (TextView) findViewById(R.id.trail_cell_address);
        trail_cell_title = (TextView) findViewById(R.id.trail_cell_title);
        trail_cell_details = (TextView) findViewById(R.id.trail_cell_details);

        trail_cell_title.setText(intent.getStringExtra("LocationTitle"));
        trail_cell_address.setText(intent.getStringExtra("LocationAddress"));
        trail_cell_details.setText(intent.getStringExtra("LocationDetails"));
    }

    public void showTrailOnMapBtnClick(View v){

        Intent i = new Intent();
        i.putExtra("showNewTrail", true);
        i.setClass(this, MapsActivity.class);
        startActivity(i);

    }
}
