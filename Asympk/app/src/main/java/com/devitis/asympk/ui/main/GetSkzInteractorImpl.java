package com.devitis.asympkfinalversion.ui.main;

import android.util.Log;


import com.devitis.asympkfinalversion.data.model.Skz;
import com.devitis.asympkfinalversion.data.network.api.APIClient;
import com.devitis.asympkfinalversion.data.network.api.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Diana on 08.05.2019.
 */

public class GetSkzInteractorImpl implements IMainContract.IGetSkzInteractor {


    @Override
    public void getSkzList(final IOnFinishedSkzListener iOnFinishedSkzListener) {


        /**
         * descriptor for the retrofit instance interface
         */

        APIService service = APIClient.getRetrofitInstance().create(APIService.class);

        /**
         * call method with parameter in the interface to get the notice data
         */

        final Call<List<Skz>> callSkz = service.getSkz();



        /**
         * log the url called
         */
        Log.wtf("URL", callSkz.request().url() + "");

        callSkz.enqueue(new Callback<List<Skz>>() {

            @Override
            public void onResponse(Call<List<Skz>> call, Response<List<Skz>> response) {

                //

                iOnFinishedSkzListener.onSkzFinished(response.body());

            }

            @Override
            public void onFailure(Call<List<Skz>> call, Throwable t) {

                iOnFinishedSkzListener.onSkzFailure(t);

            }
        });







    }





}
