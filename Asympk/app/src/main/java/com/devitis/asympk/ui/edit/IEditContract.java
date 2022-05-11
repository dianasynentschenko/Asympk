package com.devitis.asympkfinalversion.ui.edit;


import com.devitis.asympkfinalversion.data.model.Kip;
import com.devitis.asympkfinalversion.data.model.Skz;

/**
 * Created by Diana on 23.05.2019.
 */

public interface IEditContract {

    interface IEditPresenter {


        void onDestroy();

        /**
         * connect to api
         * set data
         */

        void setDataToServer(Skz skz);

        void setKipDataToServer(Kip kip);

    }


    interface IEditView {

        /**
         * when need to show info about progress
         */

        void showProgress();

        void hideProgress();


        /**
         * show error with network connect
         *
         * @param throwable
         */
        void onResponseFailure(Throwable throwable);

        void onEditFinished(boolean successful);


    }


    interface IEditSkzInteractor {

        interface IOnFinishedEditSkzListener {


            void onEditSkzFailure(Throwable throwable);

            void onEditSkzFinished(boolean successful);

        }

        void setEditSkzList(IOnFinishedEditSkzListener iOnFinishedEditSkzListener, Skz skz);
    }


    interface IEditKipInteractor {

        interface IOnFinishedEditKipListener {

            void onEditKipFinished(boolean successful);

            void onEditKipFailure(Throwable throwable);
        }

        void setEditKipList(IOnFinishedEditKipListener iOnFinishedEditKipListener, Kip kip);
    }

}
