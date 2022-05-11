package com.devitis.asympkfinalversion.ui.monitoring;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.devitis.asympkfinalversion.R;
import com.devitis.asympkfinalversion.data.model.KipMonitoring;
import com.devitis.asympkfinalversion.ui.main.IMainContract;


/**
 * Created by Diana on 08.05.2019.
 */

public class MonitoringKipFragment extends DialogFragment {

    private int idKip;
    private IMainContract.IPresenter presenter;
    private TextView checkDataTime;
    private TextView current;
    private TextView voltage;
    private TextView sumPot;
    private TextView polPot;

    private KipMonitoring kipMonitoring;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        idKip = getArguments().getInt("idKipForMonitoringValue");

        presenter = new MonitoringKipPresenterImpl(this, new GetKipsMonitoringInteractorImpl());
        presenter.requestMonitoringDataById(idKip);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_kip_monitoring, null);

        checkDataTime = (TextView) view.findViewById(R.id.txt_check_data_time);
        current = (TextView) view.findViewById(R.id.txt_current);
        voltage = (TextView) view.findViewById(R.id.txt_voltage);
        sumPot = (TextView) view.findViewById(R.id.txt_sum_pot);
        polPot = (TextView) view.findViewById(R.id.txt_pol_pot);


        return builder

                .setPositiveButton("OK", null)
                .setView(view)
                .setTitle("Мониторинг КИП")
                .create();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setKipMonitoringDataToTV(KipMonitoring kipMonitoringData) {

        this.kipMonitoring = kipMonitoringData;


        String dateTime = kipMonitoring.getCheckDataTime();
        String[] dateSplit = dateTime.split("T");

        checkDataTime.setText(dateSplit[0] + "  " + dateSplit[1]);
        current.setText(kipMonitoring.getCurrent());
        voltage.setText(kipMonitoring.getVoltage());
        sumPot.setText(kipMonitoring.getSumPot());
        polPot.setText(kipMonitoring.getPolPot());


    }

    public void onResponseFailure(Throwable throwable) {



        Toast.makeText(getActivity(),
                "Ошибка подключения к сети",
                Toast.LENGTH_SHORT)
                .show();
        this.dismiss();
    }

}
