package com.example.judejoseph.bootcamplocator.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.judejoseph.bootcamplocator.R;
import com.example.judejoseph.bootcamplocator.holders.LocationsViewHolder;
import com.example.judejoseph.bootcamplocator.model.Trails;

import java.util.ArrayList;

/**
 *
 * This adapter class loads the cells of the LocationsViewHolder with the location_card format
 * for however many trails.locations there are in the area
 *
 * Created by judejoseph on 6/6/17.
 */

public class LocationsAdapter extends RecyclerView.Adapter<LocationsViewHolder> {

    private ArrayList<Trails> locations;

    public LocationsAdapter(ArrayList<Trails> locations) {
            this.locations = locations;
    }

    @Override
    public void onBindViewHolder(LocationsViewHolder holder, int position) {
        final Trails location = locations.get(position);
        holder.updateUI(location);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    @Override
    public LocationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_card, parent, false);
        return new LocationsViewHolder(card);
    }
}
