package com.devitis.asympkfinalversion.ui.adapter;

import com.devitis.asympkfinalversion.R;
import com.devitis.asympkfinalversion.data.model.Skz;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by Diana on 06.05.2019.
 */

public class MapSkzAdapter {

    private List<Skz> skzArrayList;
    private GoogleMap googleMap;
    private Double lat;
    private Double lng;
    private LatLng latLng;



    public MapSkzAdapter(List<Skz> skzArrayList, GoogleMap googleMap) {
        this.skzArrayList = skzArrayList;
        this.googleMap = googleMap;
    }


    public int getSize() {

        return skzArrayList.size();
    }

    /**
     * add skz marker in the map
     *
     * @return
     */
    public GoogleMap createSkzMarker() {

        for (int i = 0; i < skzArrayList.size(); i++) {

            googleMap.addMarker(
                    new MarkerOptions()
                            .position(
                                    new LatLng(
                                            Double.parseDouble(skzArrayList.get(i).getLatitude()),
                                            Double.parseDouble(skzArrayList.get(i).getLongitude())))
//                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.skz))
                            .anchor(0.5f, 0.5f)
                            .title(skzArrayList.get(i).getName())
            );


            LatLng startLocation = new LatLng(Double.parseDouble(skzArrayList.get(i).getLatitude()),
                    Double.parseDouble(skzArrayList.get(i).getLongitude()));
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(startLocation));


        }

        return googleMap;

    }

}