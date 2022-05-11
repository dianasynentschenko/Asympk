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
import com.devitis.asympkfinalversion.data.model.SkzMonitoring;
import com.devitis.asympkfinalversion.data.utils.TextUtils;
import com.devitis.asympkfinalversion.ui.main.IMainContract;


/**
 * Created by Diana on 08.05.2019.
 */

public class MonitoringSkzFragment extends DialogFragment {

    private int idSkz;
    private IMainContract.IPresenter presenter;
    private TextView checkDataTime;
    private TextView current;
    private TextView voltage;
    private TextView sumPot;
    private TextView polPot;

    private SkzMonitoring skzMonitoring;
    private TextUtils textUtils;


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        idSkz = getArguments().getInt("idSkzForMonitoringValue");

        presenter = new MonitoringSkzPresenterImpl(this, new GetSkzMonitoringInteractorImpl());
        presenter.requestMonitoringDataById(idSkz);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_skz_monitoring, null);

        checkDataTime = (TextView) view.findViewById(R.id.txt_check_data_time);
        current = (TextView) view.findViewById(R.id.txt_current);
        voltage = (TextView) view.findViewById(R.id.txt_voltage);
        sumPot = (TextView) view.findViewById(R.id.txt_sum_pot);
        polPot = (TextView) view.findViewById(R.id.txt_pol_pot);

        return builder

                .setPositiveButton("OK", null)
                .setView(view)
                .setTitle("Мониторинг CKЗ")
                .create();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void setSkzMonitoringDataToTV(SkzMonitoring skzMonitoringData) {


        this.skzMonitoring = skzMonitoringData;

        String dateTime = skzMonitoring.getCheckDataTime();

        String[] dateSplit = dateTime.split("T");


        checkDataTime.setText(dateSplit[0] + "    " + dateSplit[1]);
        current.setText(skzMonitoring.getCurrent());
        voltage.setText(skzMonitoring.getVoltage());
        sumPot.setText(skzMonitoring.getSumPot());
        polPot.setText(skzMonitoring.getPolPot());


    }

    public void onResponseFailure(Throwable throwable) {

        Toast.makeText(getActivity(),
                "Ошибка подключения к сети",
                Toast.LENGTH_SHORT)
                .show();
        this.dismiss();

    }


}
