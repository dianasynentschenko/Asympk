package com.devitis.asympkfinalversion.ui.edit.kip;

import android.util.Log;


import com.devitis.asympkfinalversion.data.model.Kip;
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

public class SetKipInteractorImpl implements IEditContract.IEditKipInteractor {



    @Override
    public void setEditKipList(final IOnFinishedEditKipListener iOnFinishedEditKipListener, Kip kip) {


        APIService service = APIClient.getRetrofitInstance().create(APIService.class);

        final Call<Kip> kipCallEdit = service.setKip(

                kip.getId(),
                kip.getName(),
                kip.getSubjectCode(),
                kip.getObjectId(),
                kip.getLongitude(),
                kip.getLatitude(),
                kip.getKm()
        );

        Log.wtf("URL", kipCallEdit.request().body() + "");

        kipCallEdit.enqueue(new Callback<Kip>() {

            @Override
            public void onResponse(Call<Kip> call, Response<Kip> response) {

                if (response.code() == 400) {

                    try {
                        Log.v("err", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                iOnFinishedEditKipListener.onEditKipFinished(response.isSuccessful());

            }

            @Override
            public void onFailure(Call<Kip> call, Throwable t) {

                iOnFinishedEditKipListener.onEditKipFailure(t);

            }
        });


    }


}
