package com.example.judejoseph.bootcamplocator.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.judejoseph.bootcamplocator.R;
import com.example.judejoseph.bootcamplocator.model.Trails;
import com.example.judejoseph.bootcamplocator.services.DataService;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class SaveTrailActivity extends AppCompatActivity {

    public Trails trail;
    public ArrayList<LatLng> mTrailCoords;
    public Integer trail_zip;

    private EditText trail_title;
    private EditText trail_address;
    private EditText trail_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_trail);

        trail_title = (EditText) findViewById(R.id.trail_address);
        trail_address = (EditText) findViewById(R.id.trail_title);
        trail_description = (EditText) findViewById(R.id.trail_description);

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            mTrailCoords = b.getParcelableArrayList("TRAILCOORDS");
            trail_zip = b.getInt("CURRENTZIP");
        }

    }

    public void databaseSaveBtnClick(View v){

        //TODO: CHECK IF ALL FIELDS ARE FILLED WITH APPROPRIATE VALUES //
        trail = new Trails(mTrailCoords,
                            trail_title.getText().toString(),
                            trail_address.getText().toString(),
                            trail_description.getText().toString(),
                            String.valueOf(trail_zip));
        DataService.getInstance().saveTrail(trail);
    }

}
