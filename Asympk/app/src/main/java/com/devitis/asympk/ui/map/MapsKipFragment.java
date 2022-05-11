package com.devitis.asympkfinalversion.ui.map;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.devitis.asympkfinalversion.R;
import com.devitis.asympkfinalversion.data.model.Kip;
import com.devitis.asympkfinalversion.data.model.ObjectModel;
import com.devitis.asympkfinalversion.data.model.Skz;
import com.devitis.asympkfinalversion.data.utils.GPSTracker;
import com.devitis.asympkfinalversion.ui.adapter.KipAdapter;
import com.devitis.asympkfinalversion.ui.adapter.MapKipAdapter;
import com.devitis.asympkfinalversion.ui.adapter.MapSkzAdapter;
import com.devitis.asympkfinalversion.ui.edit.kip.EditKIPFragment;
import com.devitis.asympkfinalversion.ui.main.GetKipsInteractorImpl;
import com.devitis.asympkfinalversion.ui.main.GetObjectsInteractorImpl;
import com.devitis.asympkfinalversion.ui.main.IMainContract;
import com.devitis.asympkfinalversion.ui.main.IRecyclerItemClickListener;
import com.devitis.asympkfinalversion.ui.monitoring.MonitoringKipFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MapsKipFragment extends Fragment
        implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener,
        IMainContract.IMainView,
        SwipeRefreshLayout.OnRefreshListener {

    private GoogleMap map;
    MapView mapView;
    private IMainContract.IPresenter presenter;
    private ProgressBar progressBar;
    private Double lat;
    private Double lng;
    private RecyclerView recyclerView;
    private List<ObjectModel> objectModels;
    private static final int overview = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    MapsKipFragment mapsKipFragment;


    public final int REQUEST_LOCATION_PERMISSION = 1;


    public MapsKipFragment() {
    }

    public static MapsKipFragment newInstance() {

        return new MapsKipFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        mapsKipFragment = new MapsKipFragment();

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_main);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setColorSchemeResources(R.color.deep_blue);


        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        /**
         * in default value skz
         * create skz marker in map
         * request data from api
         */

//        getChangeKipList();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_kip_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        // create after button click
        presenter = new MapKipPresenterImpl(this, new GetKipsInteractorImpl(), new GetObjectsInteractorImpl());
        presenter.requestDataFromApi();
        presenter.request();


        /**
         * all about progress bar
         */

        progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(getActivity());
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        getActivity().addContentView(relativeLayout, params);


        return view;
    }


    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            map.setMyLocationEnabled(true);
            Toast.makeText(getActivity(), "Геолокация включена", Toast.LENGTH_SHORT).show();
        } else {
            EasyPermissions.requestPermissions(this, "Включите геолокацию", REQUEST_LOCATION_PERMISSION, perms);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMinZoomPreference(9f);


        GPSTracker gpsTracker = new GPSTracker(getActivity());
        if (gpsTracker.canGetLocation()) {

            lat = Double.valueOf(String.valueOf(gpsTracker.getLatitude()));
            lng = Double.valueOf(String.valueOf(gpsTracker.getLongitude()));
        }


        LatLng startLocation = new LatLng(lat, lng);


        requestLocationPermission();
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        map.moveCamera(CameraUpdateFactory.newLatLng(startLocation));

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                marker.showInfoWindow();
                marker.getPosition();


                GPSTracker gpsTracker = new GPSTracker(getActivity());

                if (gpsTracker.canGetLocation()) {


                    Location userLocation = new Location("");
                    userLocation.setLatitude(gpsTracker.getLatitude());
                    userLocation.setLongitude(gpsTracker.getLongitude());

                    DirectionsResult results = getDirectionsDetails(gpsTracker.getLatitude() + "," + gpsTracker.getLongitude(),
                            marker.getPosition().latitude + "," + marker.getPosition().longitude,
                            TravelMode.DRIVING);


                    if (results != null) {

                        marker.setTitle(getEndLocationTitle(results));

                    }
                }
                return false;
            }
        });


        View zoomButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("1"));
        RelativeLayout.LayoutParams zoomParams = (RelativeLayout.LayoutParams) zoomButton.getLayoutParams();
        zoomParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        zoomParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        zoomParams.setMargins(0, 855, 180, 0);
        /**
         * change location parameter in locationButton
         * set before rv
         */
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        layoutParams.setMargins(0, 750, 180, 0);


        /**
         * change visibility parameters toolbar
         */
        View toolbar = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("4"));
        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        layoutParams1.setMargins(0, 955, 160, 0);


        /**
         * doesnt work
         */
        View locationCompass = ((View) mapView.findViewById(Integer.parseInt("2")).getParent()).findViewById(Integer.parseInt("5"));
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) locationCompass.getLayoutParams();
        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        layoutParams2.setMargins(0, 1260, 180, 0);

    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        presenter.requestDataFromApi();
        presenter.request();


    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }


    @Override
    public void onMyLocationClick(@NonNull Location location) {


    }

    @Override
    public void showProgress() {

        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {

        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideSwipe() {

        swipeRefreshLayout.setRefreshing(false);


    }


    @Override
    public void setKipDataToRV(List<Kip> kipList) {

        KipAdapter kipAdapter = new KipAdapter(getChangeKipList(kipList), irvKipClickListener);
        recyclerView.setAdapter(kipAdapter);

    }


    @Override
    public void setSKZDataToRV(List<Skz> skzList) {

    }


    @Override
    public void onResponseFailure(Throwable throwable) {


        Toast.makeText(getActivity(),
                "Ошибка подключения к сети ",
                Toast.LENGTH_SHORT)
                .show();

    }


    @Override
    public void setSKZLocationDataToMap(List<Skz> skzList) {

        MapSkzAdapter mapSkzAdapter = new MapSkzAdapter(skzList, map);
        mapSkzAdapter.createSkzMarker();

    }


    @Override
    public void setKIPLocationDataToMap(List<Kip> kipList) {

        MapKipAdapter mapKipAdapter = new MapKipAdapter(kipList, map);
        mapKipAdapter.createKipMarker();

    }

    @Override
    public void setObjectDataToList(List<ObjectModel> objectList) {

        this.objectModels = objectList;

    }


    private IRecyclerItemClickListener.IRVKipClickListener irvKipClickListener = new IRecyclerItemClickListener.IRVKipClickListener() {


        @Override
        public void onItemClick(Double lat, Double lng) {

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 17f),
                    600, null);
            Toast.makeText(getActivity(), "lat" + lat + "lng" + lng, Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onKipMonitoringButtonClick(int kipId) {


            Bundle bundle = new Bundle();
            bundle.putInt("idKipForMonitoringValue", kipId);

            MonitoringKipFragment monitoringkipFragment = new MonitoringKipFragment();
            monitoringkipFragment.setArguments(bundle);
            monitoringkipFragment.show(getFragmentManager(), "kip");
        }

        @Override
        public void onKipEditButtonClick(String beforeEditIdKip,
                                         String beforeEditNameKip,
                                         String beforeEditKmKip,
                                         String beforeEditLatitudeKip,
                                         String beforeEditLongitudeKip,
                                         String beforeEditSubjectCodeKip,
                                         String beforeEditObjectValue) {


            Bundle bundle = new Bundle();
            bundle.putString("id", beforeEditIdKip);
            bundle.putString("name", beforeEditNameKip);
            bundle.putString("km", beforeEditKmKip);
            bundle.putString("lat", beforeEditLatitudeKip);
            bundle.putString("lon", beforeEditLongitudeKip);
            bundle.putString("subjectCode", beforeEditSubjectCodeKip);
            bundle.putString("objectValue", beforeEditObjectValue);
            bundle.putAll(bundle);
            EditKIPFragment editKIPFragment = new EditKIPFragment();
            editKIPFragment.setArguments(bundle);
            editKIPFragment.show(getFragmentManager(), "edit");
        }
    };


    private DirectionsResult getDirectionsDetails(String origin, String destination, TravelMode mode) {
        DateTime now = new DateTime();
        try {
            return DirectionsApi.newRequest(getGeoContext())
                    .mode(mode)
                    .language("ru")
                    .origin(origin)
                    .destination(destination)
                    .departureTime(now)
                    .await();
        } catch (com.google.maps.errors.ApiException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void setupGoogleMapScreenSettings(GoogleMap mMap) {
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setTrafficEnabled(true);
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
    }

    private void addMarkersToMap(DirectionsResult results, GoogleMap mMap) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[overview].legs[overview].startLocation.lat, results.routes[overview].legs[overview].startLocation.lng)).title(results.routes[overview].legs[overview].startAddress));
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[overview].legs[overview].endLocation.lat, results.routes[overview].legs[overview].endLocation.lng)).title(results.routes[overview].legs[overview].startAddress).snippet(getEndLocationTitle(results)));
    }

    private void positionCamera(DirectionsRoute route, GoogleMap mMap) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(route.legs[overview].startLocation.lat, route.legs[overview].startLocation.lng), 12));
    }

    private void addPolyline(DirectionsResult results, GoogleMap mMap) {
        List<LatLng> decodedPath = PolyUtil.decode(results.routes[overview].overviewPolyline.getEncodedPath());
        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
    }


    private String getEndLocationTitle(DirectionsResult results) {
        return "Время:" + results.routes[overview].legs[overview].duration.humanReadable + " Расстояние :" + results.routes[overview].legs[overview].distance.humanReadable;
    }

    private String getEndLocationTime(DirectionsResult result) {

        return result.routes[overview].legs[overview].durationInTraffic.humanReadable;
    }

    private List<Kip> getChangeKipList(final List<Kip> kipList) {


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {

                    if (kipList == null || objectModels == null) {

                        presenter.requestDataFromApi();
                        presenter.request();
                        showProgress();
                    }


                    for (int i = 0; i < kipList.size(); i++) {

                        for (int ii = 0; ii < objectModels.size(); ii++) {


                            String idObject = kipList.get(i).getObjectId();
                            String idFromObjModel = objectModels.get(ii).getId();

                            GPSTracker gpsTracker = new GPSTracker(getActivity());

                            if (gpsTracker.canGetLocation()) {


                                Location userLocation = new Location("");
                                userLocation.setLatitude(gpsTracker.getLatitude());
                                userLocation.setLongitude(gpsTracker.getLongitude());

                                Location markerLocation = new Location("");
                                markerLocation.setLatitude(Double.parseDouble(kipList.get(i).getLatitude()));
                                markerLocation.setLongitude(Double.parseDouble(kipList.get(i).getLongitude()));


                                if (idObject.equals(idFromObjModel)) {

                                    String straightDistanceInKm = null;

                                    straightDistanceInKm = String.valueOf(userLocation.distanceTo(markerLocation) / 1000);


                                    Kip kip = new Kip(
                                            kipList.get(i).getId(),
                                            kipList.get(i).getName(),
                                            kipList.get(i).getSubjectCode(),
                                            objectModels.get(ii).getObjectName(),
                                            kipList.get(i).getLongitude(),
                                            kipList.get(i).getLatitude(),
                                            kipList.get(i).getKm(),
                                            straightDistanceInKm
                                    );

                                    kipList.set(i, kip);

//                                    }


                                }
                            }
                        }
                    }


                    Collections.sort(kipList, new Comparator<Kip>() {
                        @Override
                        public int compare(Kip kip, Kip t1) {
                            return Float.parseFloat(kip.getKmToObj()) < Float.parseFloat(t1.getKmToObj()) ? -1 : (Float.parseFloat(kip.getKmToObj()) > Float.parseFloat(t1.getKmToObj())) ? 1 : 0;
                        }
                    });


                } catch (Exception e) {

                    onResponseFailure(e);


                }


            }
        });

        return kipList;

    }


    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext
                .setQueryRateLimit(3)
                .setApiKey("AIzaSyC8Go0iwhfp6OaNTZRm13XJd9gqB5t2UEQ")
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }


    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                presenter.request();
                presenter.requestDataFromApi();


            }
        }, 6000);


    }
}
