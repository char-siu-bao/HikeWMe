package com.example.judejoseph.bootcamplocator.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.judejoseph.bootcamplocator.R;
import com.example.judejoseph.bootcamplocator.model.Trails;

/**
 * Created by judejoseph on 6/6/17.
 */

public class LocationsViewHolder extends RecyclerView.ViewHolder{

    private ImageView locationImage;
    private TextView locationTitle;
    private TextView locationDetails;


    public LocationsViewHolder(View itemView) {
        super(itemView);

        locationImage = (ImageView) itemView.findViewById(R.id.location_image);
        locationDetails = (TextView) itemView.findViewById(R.id.locations_detail);
        locationTitle = (TextView) itemView.findViewById(R.id.location_title);
    }

    public void updateUI(Trails location){

        String uri = location.getStringURL();
        int resource = locationImage.getResources().getIdentifier(uri, null, locationImage.getContext().getPackageName());
        locationImage.setImageResource(resource);
        locationTitle.setText(location.getTrailTitle());
        locationDetails.setText(location.getTrailLocation());

    }
}
