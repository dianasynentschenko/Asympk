package com.devitis.asympkfinalversion.ui.edit.skz;

import android.content.DialogInterface;

import com.devitis.asympkfinalversion.data.model.Kip;
import com.devitis.asympkfinalversion.data.model.Skz;
import com.devitis.asympkfinalversion.ui.edit.IEditContract;
import com.devitis.asympkfinalversion.ui.main.IMainContract;


/**
 * Created by Diana on 23.05.2019.
 */

public class EditSkzPresenterImpl implements IEditContract.IEditPresenter,
        IEditContract.IEditSkzInteractor.IOnFinishedEditSkzListener {

    private DialogInterface.OnClickListener onClickListener;
    private IEditContract.IEditSkzInteractor editSkzInteractor;

    private EditSKZFragment editSKZFragment;
    private IMainContract.IMainView mainView;


    public EditSkzPresenterImpl(EditSKZFragment editSKZFragment, SetSkzInteractorImpl editSkzInteractor) {

        this.editSKZFragment = editSKZFragment;
        this.editSkzInteractor = editSkzInteractor;
    }

    public EditSkzPresenterImpl(DialogInterface.OnClickListener onClickListener, SetSkzInteractorImpl editSkzInteractor) {

        this.onClickListener = onClickListener;
        this.editSkzInteractor = editSkzInteractor;
    }


    @Override
    public void onDestroy() {

        editSKZFragment = null;

    }

    @Override
    public void setDataToServer(Skz skz) {


        editSkzInteractor.setEditSkzList(this, skz);

    }

    @Override
    public void setKipDataToServer(Kip kip) {

    }



    @Override
    public void onEditSkzFailure(Throwable throwable) {

        if (editSKZFragment != null) {

            editSKZFragment.onResponseFailure(throwable);
            editSKZFragment.hideProgress();

        }

    }

    @Override
    public void onEditSkzFinished(boolean successful) {

        if (mainView != null) {

            editSKZFragment.onEditFinished(successful);

        }

    }

}
