package com.devitis.asympkfinalversion.ui.map;

import com.devitis.asympkfinalversion.data.model.Kip;
import com.devitis.asympkfinalversion.data.model.ObjectModel;
import com.devitis.asympkfinalversion.ui.main.IMainContract;

import java.util.List;

/**
 * Created by Diana on 08.05.2019.
 */

public class MapKipPresenterImpl implements IMainContract.IPresenter,
        IMainContract.IGetKipsInteractor.IOnFinishedKipListener,
        IMainContract.IGetObjectsInteractor.IOnFinishedObjectListener {

    private IMainContract.IMainView mainView;
    private IMainContract.IGetKipsInteractor getKipsInteractor;
    private IMainContract.IGetObjectsInteractor getObjectsInteractor;


    public MapKipPresenterImpl(IMainContract.IMainView mainView, IMainContract.IGetKipsInteractor getKipsInteractor, IMainContract.IGetObjectsInteractor getObjectsInteractor) {
        this.mainView = mainView;
        this.getKipsInteractor = getKipsInteractor;
        this.getObjectsInteractor = getObjectsInteractor;
    }


    public MapKipPresenterImpl(IMainContract.IMainView mainView, IMainContract.IGetObjectsInteractor getObjectsInteractor) {
        this.mainView = mainView;
        this.getObjectsInteractor = getObjectsInteractor;
    }

    @Override
    public void onDestroy() {

        mainView = null;

    }


    /**
     * request kip data
     */
    @Override
    public void requestDataFromApi() {

        getKipsInteractor.getKipsList(this);

    }

    @Override
    public void requestMonitoringDataById(int id) {

    }

    /**
     * request object data
     */
    @Override
    public void request() {

        getObjectsInteractor.getObjectList(this);

    }


    /**
     * set kip list to map and rv
     *
     * @param kipList
     */
    @Override
    public void onKipFinished(List<Kip> kipList) {

        if (mainView != null) {

            mainView.setKIPLocationDataToMap(kipList);
            mainView.setKipDataToRV(kipList);
            mainView.hideProgress();
            mainView.hideSwipe();


        }

    }

    @Override
    public void onKipFailure(Throwable throwable) {

        if (mainView != null) {

            mainView.onResponseFailure(throwable);
            mainView.hideProgress();
        }

    }

    /**
     * set object data to list
     *
     * @param objectList
     */
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
