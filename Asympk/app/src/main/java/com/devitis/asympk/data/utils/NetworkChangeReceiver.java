package com.devitis.asympkfinalversion.data.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Diana on 07.06.2019.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    boolean checkInternet(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(checkInternet(context)){
            Toast.makeText(context, "интернет подключен", Toast.LENGTH_SHORT).show();
        }

    }
}
