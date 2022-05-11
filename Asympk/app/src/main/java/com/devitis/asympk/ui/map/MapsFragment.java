package com.devitis.asympkfinalversion.ui.map;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.devitis.asympkfinalversion.data.network.socket.SocketPresenterImpl;
import com.devitis.asympkfinalversion.data.utils.GPSTracker;
import com.devitis.asympkfinalversion.ui.adapter.MapKipAdapter;
import com.devitis.asympkfinalversion.ui.adapter.MapSkzAdapter;
import com.devitis.asympkfinalversion.ui.adapter.SkzAdapter;
import com.devitis.asympkfinalversion.ui.edit.skz.EditSKZFragment;
import com.devitis.asympkfinalversion.ui.main.GetObjectsInteractorImpl;
import com.devitis.asympkfinalversion.ui.main.GetSkzInteractorImpl;
import com.devitis.asympkfinalversion.ui.main.IMainContract;
import com.devitis.asympkfinalversion.ui.main.IRecyclerItemClickListener;
import com.devitis.asympkfinalversion.ui.monitoring.MonitoringSkzFragment;
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
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MapsFragment extends Fragment
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
    private SocketPresenterImpl socketPresenter;
    private List<ObjectModel> objectModels;
    private static final int overview = 0;
    public final int REQUEST_LOCATION_PERMISSION = 1;
    private SwipeRefreshLayout swipeRefreshLayout;


    public MapsFragment() {
    }

    public static MapsFragment newInstance() {

        return new MapsFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maps, container, false);


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_main);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setColorSchemeResources(R.color.deep_blue);

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_skz_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        /**
         * init presenter
         */
        presenter = new MapSkzPresenterImpl(this, new GetSkzInteractorImpl(), new GetObjectsInteractorImpl());
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


    /**
     * check location permission
     */
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


        /**
         * start activity
         * check location permission
         * set user location to lat, lng
         * move camera position to user location
         */
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


        /**
         * change location zoom button
         */
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

    }


    /**
     * change skz list
     *
     * @param skzList
     * @return
     */
    private List<Skz> getChangeSkzList(final List<Skz> skzList) {

        /**
         * create new thread
         */
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {

                    /**
                     * not sure
                     */

//                    if (!hasConnection(getActivity())) {
//
//                        Toast.makeText(getActivity(), "интернет соединение отсутствует", Toast.LENGTH_LONG).show();
//                    } else {

                    if (skzList == null || objectModels == null) {

                        presenter.requestDataFromApi();
                        presenter.request();
                        showProgress();

                    }


                    for (int i = 0; i < skzList.size(); i++) {

                        for (int ii = 0; ii < objectModels.size(); ii++) {

                            // init variables for object
                            String idObject = skzList.get(i).getObjectId();
                            String idFromObjModel = objectModels.get(ii).getId();

                            // init gps tracker and check connection
                            GPSTracker gpsTracker = new GPSTracker(getActivity());
                            if (gpsTracker.canGetLocation()) {


                                // user location
                                Location userLocation = new Location("");
                                userLocation.setLatitude(gpsTracker.getLatitude());
                                userLocation.setLongitude(gpsTracker.getLongitude());

                                // marker location
                                Location markerLocation = new Location("");
                                markerLocation.setLatitude(Double.parseDouble(skzList.get(i).getLatitude()));
                                markerLocation.setLongitude(Double.parseDouble(skzList.get(i).getLongitude()));


                                // overwrite object id to object value
                                if (idObject.equals(idFromObjModel)) {


                                    String straightDistanceInKm = null;

                                    straightDistanceInKm = String.valueOf(userLocation.distanceTo(markerLocation) / 1000);

                                    Skz skz = new Skz(skzList.get(i).getId(),
                                            skzList.get(i).getName(),
                                            skzList.get(i).getSubjectCode(),
                                            objectModels.get(ii).getObjectName(),
                                            skzList.get(i).getLongitude(),
                                            skzList.get(i).getLatitude(),
                                            skzList.get(i).getKm(),
                                            skzList.get(i).getMtdu(),
                                            straightDistanceInKm
                                    );


                                    skzList.set(i, skz);

                                }


                            }
                        }

                    }

                    //else

//                    }

//                    }

                    // sort list
                    Collections.sort(skzList, new Comparator<Skz>() {
                        @Override
                        public int compare(Skz skz, Skz t1) {
                            return Float.parseFloat(skz.getKmToObj()) < Float.parseFloat(t1.getKmToObj()) ? -1 : (Float.parseFloat(skz.getKmToObj()) > Float.parseFloat(t1.getKmToObj())) ? 1 : 0;
                        }
                    });


                } catch (Exception e) {

                    onResponseFailure(e);


                }


            }
        });


        return skzList;


    }


    /**
     * set skz list to rv
     *
     * @param skzList
     */
    @Override
    public void setSKZDataToRV(List<Skz> skzList) {

        if (getChangeSkzList(skzList) != null) {
            SkzAdapter skzAdapter = new SkzAdapter(getChangeSkzList(skzList), irvSkzClickListener);
            recyclerView.setAdapter(skzAdapter);
        } else {

            presenter.requestDataFromApi();
            presenter.request();
        }


    }


    @Override
    public void onResponseFailure(Throwable throwable) {

        Toast.makeText(getActivity(),
                "Ошибка подключения к сети"
                ,
                Toast.LENGTH_SHORT)
                .show();


    }

    /**
     * create markers
     *
     * @param skzList
     */
    @Override
    public void setSKZLocationDataToMap(List<Skz> skzList) {

        MapSkzAdapter mapSkzAdapter = new MapSkzAdapter(skzList, map);
        mapSkzAdapter.createSkzMarker();


    }


    /**
     * create markers
     *
     * @param kipList
     */
    @Override
    public void setKIPLocationDataToMap(List<Kip> kipList) {

        MapKipAdapter mapKipAdapter = new MapKipAdapter(kipList, map);
        mapKipAdapter.createKipMarker();

    }

    @Override
    public void setObjectDataToList(List<ObjectModel> objectList) {
        this.objectModels = objectList;

    }


    private boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    private IRecyclerItemClickListener.IRVSkzClickListener irvSkzClickListener = new IRecyclerItemClickListener.IRVSkzClickListener() {


        @Override
        public void onItemClick(Double lat, Double lng) {

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 12f),
                    600, null);
            //Toast.makeText(getActivity(), "lat" + lat + "lng" + lng, Toast.LENGTH_SHORT).show();
        }


        /**
         * after click get skzId from rv (in adapter)
         * skzId need to create monitoring request from api server
         *
         * @param skzId
         *
         */
        @Override
        public void onButtonClick(int skzId) {


            /**
             * go to dialog fragment
             * and get skzId
             */

            Bundle bundle = new Bundle();
            bundle.putInt("idSkzForMonitoringValue", skzId);

            MonitoringSkzFragment monitoringSkzFragment = new MonitoringSkzFragment();
            monitoringSkzFragment.setArguments(bundle);
            monitoringSkzFragment.show(getFragmentManager(), "skz");

        }

        /**
         * init opros button
         * @param mtduTxt
         */
        @Override
        public void onOprosButtonClick(String mtduTxt) {


            //init skz presenter
            socketPresenter = new SocketPresenterImpl();

            try {

                socketPresenter.connect(mtduTxt);


            } catch (URISyntaxException e) {

                Toast.makeText(getActivity(), "сокет соединение не установлено  " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }


        }


        /**
         * init edit button
         *
         * @param beforeEditIdSkz
         * @param beforeEditNameSkz
         * @param beforeEditKmSkz
         * @param beforeEditLatitudeSkz
         * @param beforeEditLongitudeSkz
         * @param beforeEditSubjectCodeSkz
         * @param beforeEditObjectValue
         */
        @Override
        public void onEditButtonClick(String beforeEditIdSkz,
                                      String beforeEditNameSkz,
                                      String beforeEditKmSkz,
                                      String beforeEditLatitudeSkz,
                                      String beforeEditLongitudeSkz,
                                      String beforeEditSubjectCodeSkz,
                                      String beforeEditObjectValue) {


            Bundle bundle = new Bundle();
            bundle.putString("id", beforeEditIdSkz);
            bundle.putString("name", beforeEditNameSkz);
            bundle.putString("km", beforeEditKmSkz);
            bundle.putString("lat", beforeEditLatitudeSkz);
            bundle.putString("lon", beforeEditLongitudeSkz);
            bundle.putString("subjectCode", beforeEditSubjectCodeSkz);
            bundle.putString("objectValue", beforeEditObjectValue);
            bundle.putAll(bundle);
            EditSKZFragment editSKZFragment = new EditSKZFragment();
            editSKZFragment.setArguments(bundle);
            editSKZFragment.show(getFragmentManager(), "edit");
        }
    };


    /**
     * google api connection
     *
     * @param origin
     * @param destination
     * @param mode
     * @return
     */
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

    /**
     * get distance data
     *
     * @param results
     * @return
     */
    private String getEndLocationTitle(DirectionsResult results) {
        return "Время:" + results.routes[overview].legs[overview].duration.humanReadable + " Расстояние :" + results.routes[overview].legs[overview].distance.humanReadable;
    }

    private String getEndLocationTime(DirectionsResult result) {

        return result.routes[overview].legs[overview].durationInTraffic.humanReadable;
    }

    /**
     * @return
     */
    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext
                .setQueryRateLimit(3)
                .setApiKey("AIzaSyC8Go0iwhfp6OaNTZRm13XJd9gqB5t2UEQ")
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }


    public static boolean hasConnection(final Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {

            return true;
        }

        wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {

            return true;
        }

        wifiInfo = connectivityManager.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {

            return true;
        }

        return false;
    }


    boolean checkInternet(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork.isAvailable();
    }




    @Override
    public void onRefresh() {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                presenter.request();
                presenter.requestDataFromApi();





            }
        }, 3000);


    }
}
