package com.example.judejoseph.bootcamplocator.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.judejoseph.bootcamplocator.R;
import com.example.judejoseph.bootcamplocator.activities.TrailDetailsActivity;
import com.example.judejoseph.bootcamplocator.model.Trails;

/**
 * Created by judejoseph on 6/6/17.
 */

public class LocationsViewHolder extends RecyclerView.ViewHolder{

    private ImageView locationImage;
    private String locationAddress;
    private TextView locationTitle;
    private TextView locationDetails;


    public LocationsViewHolder(View itemView) {
        super(itemView);

        locationImage = (ImageView) itemView.findViewById(R.id.location_image);
        locationDetails = (TextView) itemView.findViewById(R.id.locations_detail);
        locationTitle = (TextView) itemView.findViewById(R.id.location_title);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("JUDE", "trail cell listener seleced");
                Intent i = new Intent();
                Context context = v.getContext();
                i.putExtra("LocationTitle", locationTitle.getText());
                i.putExtra("LocationDetails", locationDetails.getText());
                i.putExtra("LocationAddress", locationAddress);
                i.setClass(context, TrailDetailsActivity.class);
                context.startActivity(i);

            }
        });
    }

    public void updateUI(Trails location){
        String uri = location.getStringURL();
        int resource = locationImage.getResources().getIdentifier(uri, null, locationImage.getContext().getPackageName());
        locationImage.setImageResource(resource);
        locationTitle.setText(location.getTrailTitle());
        locationAddress = location.getTrailLocation().toString();
        locationDetails.setText(location.getTrailLocation());

    }

}
