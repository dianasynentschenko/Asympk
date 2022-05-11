package com.devitis.asympkfinalversion;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devitis.asympkfinalversion.data.network.socket.SocketPresenterImpl;
import com.devitis.asympkfinalversion.ui.main.IMainContract;
import com.devitis.asympkfinalversion.ui.map.MapsFragment;
import com.devitis.asympkfinalversion.ui.settings.SettingsFragment;

import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity

{

    private ProgressBar progressBar;

    private IMainContract.IPresenter presenter;
    private SocketPresenterImpl socketPresenter;
    private TextView listenerText;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listenerText = (TextView) findViewById(R.id.listener);
        socketPresenter = new SocketPresenterImpl();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            socketPresenter.response();

                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }

                        String s = socketPresenter.getMessage();
                        listenerText.setText(s);
                    }
                });

            }
        }, 0, 1000);


        progressBar = new

                ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == R.id.filter_menu) {

            SettingsFragment settingsFragment = new SettingsFragment();
            settingsFragment.show(getFragmentManager(), "");

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

}
