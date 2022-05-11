package com.devitis.asympkfinalversion.ui.monitoring;


import com.devitis.asympkfinalversion.data.model.KipMonitoring;
import com.devitis.asympkfinalversion.ui.main.IMainContract;

/**
 * Created by Diana on 08.05.2019.
 */

public class MonitoringKipPresenterImpl implements IMainContract.IPresenter,
        IMainContract.IGetMonitoringKipsInteractor.IOnFinishedKipMonitoringListener {


    private MonitoringKipFragment monitoringKipFragment;
    private IMainContract.IGetMonitoringKipsInteractor getMonitoringKipsInteractor;


    public MonitoringKipPresenterImpl(MonitoringKipFragment monitoringKipFragment, IMainContract.IGetMonitoringKipsInteractor getMonitoringKipsInteractor) {
        this.monitoringKipFragment = monitoringKipFragment;
        this.getMonitoringKipsInteractor = getMonitoringKipsInteractor;
    }


    @Override
    public void onDestroy() {

        monitoringKipFragment = null;

    }


    @Override
    public void requestDataFromApi() {

    }

    @Override
    public void requestMonitoringDataById(int id) {

        getMonitoringKipsInteractor.getKipsMonitoringList(this, id);
    }

    @Override
    public void request() {

    }


    @Override
    public void onKipMonitoringFinished(KipMonitoring kipMonitoringList) {


        if (monitoringKipFragment != null) {

            monitoringKipFragment.setKipMonitoringDataToTV(kipMonitoringList);

        }
    }

    @Override
    public void onKipFailure(Throwable throwable) {

        if (monitoringKipFragment != null) {

            monitoringKipFragment.onResponseFailure(throwable);
        }

    }
}
