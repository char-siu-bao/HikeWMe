package com.example.judejoseph.bootcamplocator.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.judejoseph.bootcamplocator.R;
import com.example.judejoseph.bootcamplocator.adapters.LocationsAdapter;
import com.example.judejoseph.bootcamplocator.services.DataService;


public class LocationsListFragement extends Fragment {

    private static int zipcode;

    public LocationsListFragement() {
        // Required empty public constructor
    }

    public static LocationsListFragement newInstance(int zip) {
        LocationsListFragement fragment = new LocationsListFragement();
        zipcode = zip;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_locations_list, container, false);

        // create list view that is set at the bottom of the parent map view (written in xml file)//
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_locations);
        recyclerView.setHasFixedSize(true);

        LocationsAdapter locationsAdapter = new LocationsAdapter(DataService
                                                                .getInstance()
                                                                .getTrailLocationsFromRadiusOfZipCode(zipcode));
        recyclerView.setAdapter(locationsAdapter);

        // set direction of the scrolling //
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

}
