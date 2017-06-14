package com.example.judejoseph.bootcamplocator.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by judejoseph on 6/14/17.
 */

public class MyLatLong implements Parcelable {

    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public MyLatLong(){

    }

    public MyLatLong(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    private int mData;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }

    public static final Parcelable.Creator<MyLatLong> CREATOR
            = new Parcelable.Creator<MyLatLong>() {
        public MyLatLong createFromParcel(Parcel in) {
            return new MyLatLong(in);
        }

        public MyLatLong[] newArray(int size) {
            return new MyLatLong[size];
        }
    };

    private MyLatLong(Parcel in) {
        mData = in.readInt();
    }


}
