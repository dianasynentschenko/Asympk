package com.devitis.asympkfinalversion.ui.edit.skz;

import android.util.Log;


import com.devitis.asympkfinalversion.data.model.Skz;
import com.devitis.asympkfinalversion.data.network.api.APIClient;
import com.devitis.asympkfinalversion.data.network.api.APIService;
import com.devitis.asympkfinalversion.ui.edit.IEditContract;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Diana on 23.05.2019.
 */

public class SetSkzInteractorImpl implements IEditContract.IEditSkzInteractor {

    @Override
    public void setEditSkzList(final IOnFinishedEditSkzListener iOnFinishedEditSkzListener, Skz skz) {


        APIService service = APIClient.getRetrofitInstance().create(APIService.class);

        final Call<Skz> skzCallEdit = service.setSkz(

                skz.getId(),
                skz.getName(),
                skz.getSubjectCode(),
                skz.getObjectId(),
                skz.getLongitude(),
                skz.getLatitude(),
                skz.getKm()
        );

        Log.wtf("URL", skzCallEdit.request().body() + "");


        skzCallEdit.enqueue(new Callback<Skz>() {

            @Override
            public void onResponse(Call<Skz> call, Response<Skz> response) {

                if (response.code() == 400) {

                    try {
                        Log.v("err", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                iOnFinishedEditSkzListener.onEditSkzFinished(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Skz> call, Throwable t) {

                iOnFinishedEditSkzListener.onEditSkzFailure(t);

            }
        });


    }


}
