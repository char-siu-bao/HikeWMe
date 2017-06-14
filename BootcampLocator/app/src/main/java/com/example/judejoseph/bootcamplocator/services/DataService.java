package com.example.judejoseph.bootcamplocator.services;

import android.util.Log;

import com.example.judejoseph.bootcamplocator.model.Trails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 *
 * Created by judejoseph on 6/1/17.
 *
 */

public class DataService {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private static final DataService instance = new DataService();

    public static DataService getInstance() {
        return instance;
    }

    private DataService() {
    }

    public void testSave(){
        DatabaseReference ref = database.getReference().child("Trails").child("95060");
        ref.child("Cloud").child("Address").setValue("android_app");
        Log.v("JUDE", "SENT DATA TO DATABASE");
    }

    public void saveTrail(Trails trail){
        DatabaseReference ref = database.getReference()
                                .child("Trails")
                                .child(trail.getZipCode())
                                .child(trail.getTrailTitle());
        ref.child("Location").setValue(trail.getTrailLocation());
        ref.child("TrailCoordinates").setValue(trail.getTrailCoordinates());
    }

    public ArrayList<Trails> getTrailLocationsFromRadiusOfZipCode(int zipcode){
        // take in data from server but for now using pretend data //

        ArrayList<Trails> trailsList = new ArrayList<>();

        if (zipcode == 95060) {
            // this data will come from the backend server after user saves hiking trail //
            //trailsList.add(new Trails(points.get(0),
            //        "Pogonip Hike", "333 Golf Club Dr, Santa Cruz, CA 95060, USA", "pogonip"));
            //trailsList.add(new Trails(points.get(1),
            //        "UCSC Upper Campus", "7487 Red Hill Rd, Santa Cruz, CA 95064, USA", "upper_campus"));
        }
        return trailsList;
    }

}
