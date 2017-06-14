package com.example.judejoseph.bootcamplocator.services;

import android.util.Log;

import com.example.judejoseph.bootcamplocator.model.Trails;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

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
        ref.child("Title").setValue(trail.getTrailTitle());
        ref.child("Location").setValue(trail.getTrailLocation());
        ref.child("TrailCoordinates").setValue(trail.getTrailCoordinates());
    }

    public ArrayList<Trails> getTrailLocationsFromRadiusOfZipCode(final int zipcode){
        // take in data from server but for now using pretend data //
        final ArrayList<Trails> trailsList = new ArrayList<Trails>();

        final DatabaseReference trailsFoundForZip = database.getReference()
                                                    .child("Trails")
                                                    .child(String.valueOf(zipcode));

        Log.v("JUDE", "finding trail for zip");

        trailsFoundForZip.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap<String, Object> newPost = (HashMap<String, Object>) dataSnapshot.getValue();
                ArrayList<LatLng> list = (ArrayList<LatLng>) newPost.get("TrailCoordinates");

                trailsList.add(new Trails(list, newPost.get("Title").toString(),
                                            newPost.get("Location").toString(), "ucsc",
                                            String.valueOf(zipcode)));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return trailsList;
    }

}
