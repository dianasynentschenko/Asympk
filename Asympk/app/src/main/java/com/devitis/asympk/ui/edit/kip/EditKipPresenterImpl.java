package com.devitis.asympkfinalversion.ui.edit.kip;

import android.content.DialogInterface;

import com.devitis.asympkfinalversion.data.model.Kip;
import com.devitis.asympkfinalversion.data.model.Skz;
import com.devitis.asympkfinalversion.ui.edit.IEditContract;
import com.devitis.asympkfinalversion.ui.main.IMainContract;


/**
 * Created by Diana on 23.05.2019.
 */

public class EditKipPresenterImpl implements IEditContract.IEditPresenter,
        IEditContract.IEditKipInteractor.IOnFinishedEditKipListener {

    private DialogInterface.OnClickListener onClickListener;
    private IEditContract.IEditKipInteractor editKipInteractor;
    private EditKIPFragment editKIPFragment;
    private IMainContract.IMainView mainView;


    public EditKipPresenterImpl(EditKIPFragment editKIPFragment, SetKipInteractorImpl editKipInteractor) {

        this.editKIPFragment = editKIPFragment;
        this.editKipInteractor = editKipInteractor;
    }

    public EditKipPresenterImpl(DialogInterface.OnClickListener onClickListener, SetKipInteractorImpl editKipInteractor) {

        this.onClickListener = onClickListener;
        this.editKipInteractor = editKipInteractor;
    }



    @Override
    public void onDestroy() {

        editKIPFragment = null;

    }

    @Override
    public void setDataToServer(Skz skz) {


    }

    /**
     * set data to server
     * from fragment
     * @param kip
     */
    @Override
    public void setKipDataToServer(Kip kip) {

        editKipInteractor.setEditKipList(this, kip);

    }


    @Override
    public void onEditKipFailure(Throwable throwable) {

        if (editKIPFragment != null) {

            editKIPFragment.onResponseFailure(throwable);
            editKIPFragment.hideProgress();
        }

    }

    @Override
    public void onEditKipFinished(boolean successful) {

        if (mainView != null) {

            editKIPFragment.onEditFinished(successful);

        }

    }


}
