package com.example.judejoseph.bootcamplocator.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cocoahero.android.geojson.GeoJSON;
import com.cocoahero.android.geojson.GeoJSONObject;
import com.example.judejoseph.bootcamplocator.R;
import com.example.judejoseph.bootcamplocator.fragments.MainFragment;
import com.example.judejoseph.bootcamplocator.model.MyLatLong;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        LocationListener{

    final int PERMISSION_LOCATION = 777;

    private ArrayList<ArrayList<MyLatLong>> points;
    private ArrayList<MyLatLong> trailCoordinates;
    public boolean makeTrail;
    Polyline line;

    private GoogleApiClient mGoogleApiClient;
    private GoogleMap googleMap;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Log.v("JUDE", "onCreate");

        points = new ArrayList<ArrayList<MyLatLong>>();

        // access Location Services API //
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.container_main);

        if(mainFragment == null){
            mainFragment = MainFragment.newInstance();
            getSupportFragmentManager().
                    beginTransaction().
                    add(R.id.container_main, mainFragment).
                    commit();
        }


    }

    @Override protected void onResume() {
        super.onResume();
        Log.v("JUDE", "onResume");
        makeTrail = getIntent().getBooleanExtra("showNewTrail", false);
        Log.v("JUDE", String.valueOf(makeTrail));

        Log.v("JUDE", String.valueOf(makeTrail));
        if (makeTrail){
            Log.v("JUDE", "MAKING FAKE TRAIL");
            makeFakeTrail();
        }
        makeTrail = false;

    }

    public void findBtnClick(View v){
        Log.v("JUDE", "BUTTON WAS CLICKED");
        switch(v.getId()) {
            case R.id.button_find:
                startActivity(new Intent(this, UserActivity.class));
            break;
        }
    }

    public void makeBtnClick(View v){
        Log.v("JUDE", "Make Button CLICKED");
        switch (v.getId()){
            case R.id.button_make:
                Button btn = (Button) findViewById(R.id.button_make);
                btn.setAlpha(0);
                Button saveBtn = (Button)findViewById(R.id.save_button);
                saveBtn.setAlpha(1);
                makeFakeTrail();

                //makeTrail = true;
            break;
        }
    }

    public void saveBtnClick(View v){
        Log.v("JUDE", "SAVE BTN CLICKED");
        switch (v.getId()){
            case R.id.save_button:
                // no longer building trail so we will stop adding onto trailCoordinates //
                makeTrail = false;
                //points.add(trailCoordinates);

                // now we changed to a new Activity where the user will build the trail object //
                Intent i = new Intent();
                Bundle b = new Bundle();
                b.putParcelableArrayList("TRAILCOORDS", trailCoordinates);
                b.putInt("CURRENTZIP", mainFragment.currentZip);
                i.putExtras(b);
                i.setClass(this, SaveTrailActivity.class);
                startActivity(i);

                //DataService.getInstance().testSave();
        }
    }

    public void makeFakeTrail(){
        Log.v("JUDE", "CALLED FAKE TRAIL");
        openGeoJson();
    }

    protected void openGeoJson() {

        try {
            InputStream targetStream = getResources().openRawResource(R.raw.geojson_hiking_trail2);
            GeoJSONObject geoJSON = GeoJSON.parse(targetStream);
            JSONObject json = geoJSON.toJSON();
            JSONArray jsonArray = json.getJSONArray("features");
            for (int i = 0; i< 3; i++){
                JSONArray coordinates = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");
                points.add(new ArrayList<MyLatLong>());
                for ( int j = 0; j < coordinates.length(); j++){
                    JSONArray coordinate = coordinates.getJSONArray(j);
                    points.get(i).add(new MyLatLong(coordinate.getDouble(1), coordinate.getDouble(0)));
                }

                // upload fake data to work with //
                if(i == 0){
                    trailCoordinates = new ArrayList<MyLatLong>();
                    ArrayList<MyLatLong> data = points.get(0);

                    for(int k = 0; k < data.size(); k++){
                        trailCoordinates.add(data.get(k));
                    }
                }

                redrawLine();

            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // connect to API services and check if permission was given //
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            // REQUEST permission if not granted //
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_LOCATION);

            Log.v("JUDE", "Requesting Permissions");
        }else{
            // if permission has already been given //
            startLocationServices();
            Log.v("JUDE", "Starting Location Services from onConnected");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("JUDE", "Lat:" + location.getLatitude() + " -- Log:" + location.getLongitude());

        if (makeTrail){
            //trailCoordinates.add(new LatLng(location.getLatitude(), location.getLongitude()));
        }else{
            // update user marker when location is changed to display on map //
            mainFragment.setUserMarkers(new LatLng(location.getLatitude(), location.getLongitude()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case PERMISSION_LOCATION : {
                // if there are permissions granted then check the first one //
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startLocationServices();
                    Log.v("JUDE", "Permisison Granted - starting services");
                }else{
                    // show dialog that user needs to allow location access for the app to work //
                    Log.v("JUDE", "Permission Denied - need location access");
                }
            }
        }
    }

    private void redrawLine(){

        for (int i = 0; i < points.size(); i++) {
            PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
            for(int j = 0; j < points.get(i).size(); j++){
                MyLatLong point = points.get(i).get(j);
                com.google.android.gms.maps.model.LatLng mapsLatLng =
                        new com.google.android.gms.maps.model.LatLng(point.getLat(),
                                point.getLng());
                options.add(mapsLatLng);
            }
            mainFragment.produceFakeTrail(options);
        }
    }

    /*
        * Override start and stop of life cycle
        * Minimize data usage by taking care of calls from API at certain parts of lifecycle
        *
        * */
    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    /*
    * HELPER :
    * Start service in difference locations if needed
    * Low priority - only user location service when needed ( dont have it running in the back )
    * Ask user for location permission ( handler if user does not allow so )
    *
    * */
    public void startLocationServices(){
        Log.v("JUDE", "Starting LocationServices called");

        try {
            LocationRequest req = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,req, this);
            Log.v("JUDE", "Requesting Location Updates");

        } catch (SecurityException exception) {
            // Show dialog to user asking for location permission //
            Log.v("JUDE", exception.toString());
        }
    }
}
