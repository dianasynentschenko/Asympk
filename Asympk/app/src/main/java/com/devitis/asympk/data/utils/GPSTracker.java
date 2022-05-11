package com.devitis.asympkfinalversion.data.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Diana on 23.05.2019.
 */

public class GPSTracker extends Service implements LocationListener {

    private final Context context;

    /**
     * GPS status
     */
    boolean isGPSEnabled = false;

    /**
     * Network status
     */
    boolean isNetworkEnabled = false;

    /**
     * GPS status for location
     */
    boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;

    /**
     * meters
     */
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    /**
     * milliseconds -> 1 min
     */
    private static final long MIN_TIME_BETWEEN_UPDATE = 1000 * 60 * 1;

    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.context = context;
        getLocation();
    }

    @SuppressLint("MissingPermission")
    private Location getLocation() {
        try {

            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            /**
             * get GPS status
             */
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            /**
             * get Network status
             */
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {

            } else {

                this.canGetLocation = true;

                /**
                 * get location from network provider
                 */

                if (isNetworkEnabled) {

                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BETWEEN_UPDATE,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

                /**
                 * if GPS enabled get lat, lon using gps services
                 */

                if (isGPSEnabled) {

                    if (location == null) {

                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BETWEEN_UPDATE,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");

                        if (locationManager != null) {

                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (location != null) {

                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }

                    }
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }


    /**
     * stop using jps listener
     */
    public void stopUsingGPS() {

        if (locationManager != null) {

            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    public double getLatitude() {

        if (location != null) {

            latitude = location.getLatitude();
        }

        return latitude;
    }

    public double getLongitude() {

        if (location != null) {

            longitude = location.getLongitude();
        }

        return longitude;
    }

    /**
     * check gps wifi location
     *
     * @return
     */
    public boolean canGetLocation() {

        return this.canGetLocation;
    }

    /**
     * showSettings
     */
    public void showSettingsAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle("Настройки GPS");
        alertDialog.setMessage("GPS не включен, перейдите в меню настройки");
        alertDialog.setPositiveButton("Настройки", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Oтмена", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });
        alertDialog.show();

    }

    @Nullable
    @Override

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
