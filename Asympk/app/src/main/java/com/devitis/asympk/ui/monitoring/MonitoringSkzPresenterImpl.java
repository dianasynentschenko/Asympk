package com.devitis.asympkfinalversion.ui.monitoring;


import com.devitis.asympkfinalversion.data.model.SkzMonitoring;
import com.devitis.asympkfinalversion.ui.main.IMainContract;

/**
 * Created by Diana on 08.05.2019.
 */

public class MonitoringSkzPresenterImpl implements IMainContract.IPresenter,
        IMainContract.IGetSkzMonitoringInteractor.IOnFinishedSkzMonitoringListener {


    private MonitoringSkzFragment monitoringSkzFragment;
    private IMainContract.IGetSkzMonitoringInteractor getSkzMonitoringInteractor;

    public MonitoringSkzPresenterImpl(MonitoringSkzFragment monitoringSkzFragment, GetSkzMonitoringInteractorImpl getSkzMonitoringInteractor) {

        this.monitoringSkzFragment = monitoringSkzFragment;
        this.getSkzMonitoringInteractor = getSkzMonitoringInteractor;
    }


    @Override
    public void onDestroy() {

        monitoringSkzFragment = null;

    }



    @Override
    public void requestDataFromApi() {

    }

    @Override
    public void requestMonitoringDataById(int id) {

        getSkzMonitoringInteractor.getSkzMonitoringList(this, id);
    }

    @Override
    public void request() {

    }



    @Override
    public void onSkzMonitoringFinished(SkzMonitoring skzMonitoringList) {

        if (monitoringSkzFragment != null) {

            monitoringSkzFragment.setSkzMonitoringDataToTV(skzMonitoringList);

        }
    }

    @Override
    public void onSkzFailure(Throwable throwable) {

        if (monitoringSkzFragment != null) {

            monitoringSkzFragment.onResponseFailure(throwable);

        }

    }
}
