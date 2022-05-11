package com.devitis.asympkfinalversion.ui.adapter;


import com.devitis.asympkfinalversion.R;
import com.devitis.asympkfinalversion.data.model.Kip;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by Diana on 06.05.2019.
 */

public class MapKipAdapter {

    private List<Kip> kipArrayList;
    private GoogleMap googleMap;


    public MapKipAdapter(List<Kip> kipArrayList, GoogleMap googleMap) {
        this.kipArrayList = kipArrayList;
        this.googleMap = googleMap;
    }

    public int getSize() {

        return kipArrayList.size();
    }

    /**
     * add kip marker in the map
     *
     * @return
     */
    public GoogleMap createKipMarker() {


        for (int i = 0; i < kipArrayList.size(); i++) {

            googleMap.addMarker(
                    new MarkerOptions()
                            .position(
                                    new LatLng(
                                            Double.parseDouble(kipArrayList.get(i).getLatitude()),
                                            Double.parseDouble(kipArrayList.get(i).getLongitude())))
//                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.kip))
                            .anchor(0.5f, 0.5f)
                            .title(kipArrayList.get(i).getName()));

            LatLng startLocation = new LatLng(Double.parseDouble(kipArrayList.get(i).getLatitude()),
                    Double.parseDouble(kipArrayList.get(i).getLongitude()));



            googleMap.moveCamera(CameraUpdateFactory.newLatLng(startLocation));

        }

        return googleMap;

    }
}