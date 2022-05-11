package com.devitis.asympkfinalversion.ui.map;


import com.devitis.asympkfinalversion.data.model.ObjectModel;
import com.devitis.asympkfinalversion.data.model.Skz;
import com.devitis.asympkfinalversion.ui.main.GetObjectsInteractorImpl;
import com.devitis.asympkfinalversion.ui.main.IMainContract;
import com.devitis.asympkfinalversion.ui.main.IRecyclerItemClickListener;

import java.util.List;

/**
 * Created by Diana on 08.05.2019.
 */

public class MapSkzPresenterImpl implements IMainContract.IPresenter,
        IMainContract.IGetSkzInteractor.IOnFinishedSkzListener,
        IMainContract.IGetObjectsInteractor.IOnFinishedObjectListener {

    private IMainContract.IMainView mainView;
    private IMainContract.IGetSkzInteractor getSkzInteractor;
    IRecyclerItemClickListener.IRVSkzClickListener irvSkzClickListener;
    private MapsFragment mapsFragment;
    private IMainContract.IGetObjectsInteractor getObjectsInteractor;


    public MapSkzPresenterImpl(IMainContract.IMainView mainView, IMainContract.IGetSkzInteractor getSkzInteractor, GetObjectsInteractorImpl getObjectsInteractor) {
        this.mainView = mainView;
        this.getSkzInteractor = getSkzInteractor;
        this.getObjectsInteractor = getObjectsInteractor;
    }

    public MapSkzPresenterImpl(IMainContract.IMainView mainView, IMainContract.IGetObjectsInteractor getObjectsInteractor) {
        this.mainView = mainView;
        this.getObjectsInteractor = getObjectsInteractor;
    }


    public MapSkzPresenterImpl(IMainContract.IGetSkzInteractor getSkzInteractor, MapsFragment mapsFragment) {
        this.getSkzInteractor = getSkzInteractor;
        this.mapsFragment = mapsFragment;
    }

    public MapSkzPresenterImpl() {
    }


    @Override
    public void onDestroy() {

        mainView = null;
    }


    @Override
    public void requestDataFromApi() {

        getSkzInteractor.getSkzList(this);


    }

    @Override
    public void requestMonitoringDataById(int id) {

    }

    @Override
    public void request() {

        getObjectsInteractor.getObjectList(this);


    }


    @Override
    public void onSkzFinished(List<Skz> skzList) {

        if (mainView != null) {

            mainView.setSKZLocationDataToMap(skzList);
            mainView.setSKZDataToRV(skzList);

            mainView.hideProgress();
            mainView.hideSwipe();

        }

    }

    @Override
    public void onSkzFailure(Throwable throwable) {

        if (mainView != null) {

            mainView.onResponseFailure(throwable);
            mainView.hideProgress();
        }

    }


    @Override
    public void onObjectFinished(List<ObjectModel> objectList) {

        if (mainView != null) {

            mainView.setObjectDataToList(objectList);
            mainView.hideProgress();



        }


    }

    @Override
    public void onObjectFailure(Throwable throwable) {

        if (mainView != null) {

            mainView.onResponseFailure(throwable);
            mainView.hideProgress();
        }
    }
}
