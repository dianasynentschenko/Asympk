package com.devitis.asympkfinalversion.ui.main;


import com.devitis.asympkfinalversion.data.model.Kip;
import com.devitis.asympkfinalversion.data.model.KipMonitoring;
import com.devitis.asympkfinalversion.data.model.ObjectModel;
import com.devitis.asympkfinalversion.data.model.Skz;
import com.devitis.asympkfinalversion.data.model.SkzMonitoring;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Diana on 08.05.2019.
 */

public interface IMainContract {

    /**
     * when user interact with the view
     */

    interface IPresenter {

        void onDestroy();

        /**
         * connect to api
         * request data
         */
        void requestDataFromApi();

        /**
         * request monitoring by id
         */
        void requestMonitoringDataById(int id);

        /**
         * request object data
         */
        void request();

    }


    interface IMainView {


        /**
         * when need to show info about progress
         */

        void showProgress();

        void hideProgress();

        void hideSwipe();

        /**
         * set data from api to rv
         *
         * @param kipList
         */

        void setKipDataToRV(List<Kip> kipList);


        /**
         * set data from api to rv
         *
         * @param skzList
         */
        void setSKZDataToRV(List<Skz> skzList);


        /**
         * show error with network connect
         *
         * @param throwable
         */
        void onResponseFailure(Throwable throwable);

        /**
         * add all location data to map(lat/lon/title)
         *
         * @param skzList
         */

        void setSKZLocationDataToMap(List<Skz> skzList);

        /**
         * add all location data to map(lat/lon/title)
         *
         * @param kipList
         */
        void setKIPLocationDataToMap(List<Kip> kipList);

        /**
         * set object data to list
         *
         * @param objectList
         */
        void setObjectDataToList(List<ObjectModel> objectList);


    }


    /**
     * get object data from webservice
     * need to replace object value <=> id
     */

    interface IGetObjectsInteractor {

        /**
         * will be called when the web service response
         */
        interface IOnFinishedObjectListener {

            /**
             * service response in finished
             *
             * @param objectList
             */
            void onObjectFinished(List<ObjectModel> objectList);

            /**
             * service response is failure
             *
             * @param throwable
             */

            void onObjectFailure(Throwable throwable);
        }


        /**
         * after finished service response  get Object list
         *
         * @param iOnFinishedObjectListener
         */
        void getObjectList(IGetObjectsInteractor.IOnFinishedObjectListener iOnFinishedObjectListener);

    }

    /**
     * get kip data from webservice
     */

    interface IGetKipsInteractor {

        /**
         * will be called when the web service response
         */
        interface IOnFinishedKipListener {

            /**
             * service response in finished
             *
             * @param kipList
             */
            void onKipFinished(List<Kip> kipList);

            /**
             * service response is failure
             *
             * @param throwable
             */

            void onKipFailure(Throwable throwable);
        }


        /**
         * after finished service response  get Kips list
         *
         * @param iOnFinishedKipListener
         */
        void getKipsList(IOnFinishedKipListener iOnFinishedKipListener);
    }


    /**
     * get kips monitoring data from webservice
     */

    interface IGetMonitoringKipsInteractor {

        /**
         * will be called when the web service response
         */

        interface IOnFinishedKipMonitoringListener {

            /**
             * service response in finished
             *
             * @param kipMonitoringList
             */
            void onKipMonitoringFinished(KipMonitoring kipMonitoringList);

            /**
             * service response is failure
             *
             * @param throwable
             */

            void onKipFailure(Throwable throwable);

        }

        /**
         * after finished service response  get Kips monitoring list
         *
         * @param iOnFinishedKipMonitoringListener
         */
        void getKipsMonitoringList(IOnFinishedKipMonitoringListener iOnFinishedKipMonitoringListener, int idKipMonitoring);

    }


    interface IGetSkzInteractor {

        interface IOnFinishedSkzListener {

            /**
             * service response in finished
             *
             * @param skzList
             */
            void onSkzFinished(List<Skz> skzList);


            /**
             * service response is failure
             *
             * @param throwable
             */
            void onSkzFailure(Throwable throwable);

        }

        /**
         * after finished service response  get Skz list
         *
         * @param iOnFinishedSkzListener
         */
        void getSkzList(IOnFinishedSkzListener iOnFinishedSkzListener);


    }


    interface IGetSkzMonitoringInteractor {

        interface IOnFinishedSkzMonitoringListener {


            /**
             * service response in finished
             *
             * @param skzMonitoringList
             */
            void onSkzMonitoringFinished(SkzMonitoring skzMonitoringList);

            /**
             * service response is failure
             *
             * @param throwable
             */
            void onSkzFailure(Throwable throwable);

        }

        /**
         * after finished service response  get Skz monitoring list
         *
         * @param iOnFinishedSkzMonitoringListener
         */
        void getSkzMonitoringList(IOnFinishedSkzMonitoringListener iOnFinishedSkzMonitoringListener, int idSkzMonitoring);

    }


}
