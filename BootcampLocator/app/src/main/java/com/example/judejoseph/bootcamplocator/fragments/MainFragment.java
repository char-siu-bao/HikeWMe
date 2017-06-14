package com.example.judejoseph.bootcamplocator.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.judejoseph.bootcamplocator.R;
import com.example.judejoseph.bootcamplocator.model.Trails;
import com.example.judejoseph.bootcamplocator.services.DataService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions userMarker;
    public int currentZip;


    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    public void produceFakeTrail(PolylineOptions options){

        Log.v("JUDE", "RETRIEVED OPTIONS TO DRAW LINE");
        Polyline line = mMap.addPolyline(options);
    }

    public void setUserMarkers(LatLng latLng){
        // if location changed in MapsActivity update map to current user location
        // need to take old markers off as well as add new markers
        // so we need to store it

        if (userMarker == null){
            //first time we are adding user marker to map //
            userMarker = new MarkerOptions().position(latLng).title("Current Location");
            mMap.addMarker(userMarker);
            Log.v("JUDE", "Current location = Lat : " + latLng.latitude
                    + " -- Lng : "+ latLng.longitude);
        }

        // get zipcode from users current location //
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        // need the try/catch block for IOException //
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            int zip = Integer.parseInt(addresses.get(0).getPostalCode());
            currentZip = zip;
            updateMapForZip(zip);

        }catch(IOException exception){ Log.v("JUDE", "error IOException : " + exception);}

        //updateMapForZip(95060);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    /*
    *
    * Get locations of trails saved in backend database and extract data
    * so we can place markers on the map with those trails
    *
    * */
    private void updateMapForZip(int zipcode){

        ArrayList<Trails> locations = DataService.getInstance()
                                      .getTrailLocationsFromRadiusOfZipCode(zipcode);

        for (int x = 0; x < locations.size(); x++){
            Trails trail = locations.get(x);
            MarkerOptions marker = new MarkerOptions()
                                   .position(trail.getTrailCoordinates().get(0));
            marker.title(trail.getTrailTitle());
            marker.snippet(trail.getTrailLocation());
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
            mMap.addMarker(marker);
        }
    }



}
