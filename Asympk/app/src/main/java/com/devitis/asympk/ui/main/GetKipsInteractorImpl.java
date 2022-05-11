package com.devitis.asympkfinalversion.ui.main;

import android.util.Log;


import com.devitis.asympkfinalversion.data.model.Kip;
import com.devitis.asympkfinalversion.data.network.api.APIClient;
import com.devitis.asympkfinalversion.data.network.api.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Diana on 08.05.2019.
 */

public class GetKipsInteractorImpl implements IMainContract.IGetKipsInteractor {


    @Override
    public void getKipsList(final IOnFinishedKipListener iOnFinishedKipListener) {

        /**
         * descriptor for the retrofit instance interface
         */

        APIService service = APIClient.getRetrofitInstance().create(APIService.class);

        /**
         * call method with parameter in the interface to get the notice data
         */
        Call<List<Kip>> callKip = service.getKip();


        /**
         * log the url called
         */
        Log.wtf("URL", callKip.request().url() + "");

        callKip.enqueue(new Callback<List<Kip>>() {

            @Override
            public void onResponse(Call<List<Kip>> call, Response<List<Kip>> response) {


                iOnFinishedKipListener.onKipFinished(response.body());
            }

            @Override
            public void onFailure(Call<List<Kip>> call, Throwable t) {

                iOnFinishedKipListener.onKipFailure(t);
            }
        });
    }
}
