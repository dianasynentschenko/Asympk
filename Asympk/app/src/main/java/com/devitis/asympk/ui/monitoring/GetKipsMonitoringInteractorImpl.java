package com.devitis.asympkfinalversion.ui.monitoring;

import android.util.Log;


import com.devitis.asympkfinalversion.data.model.KipMonitoring;
import com.devitis.asympkfinalversion.data.network.api.APIClient;
import com.devitis.asympkfinalversion.data.network.api.APIService;
import com.devitis.asympkfinalversion.ui.main.IMainContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Diana on 08.05.2019.
 */

public class GetKipsMonitoringInteractorImpl implements IMainContract.IGetMonitoringKipsInteractor {

    @Override
    public void getKipsMonitoringList(final IOnFinishedKipMonitoringListener iOnFinishedKipMonitoringListener, final int idKipMonitoring) {

        /**
         *
         * descriptor for the retrofit instance interface
         */
        APIService service = APIClient.getRetrofitInstance().create(APIService.class);

        /**
         * call method with  with parameter in the interface to get the notice data
         */
        Call<KipMonitoring> callKipMonitoring = service.getKipMonitoringById(idKipMonitoring);

        /**Log the URL called*/
        Log.wtf("URL ", callKipMonitoring.request().url() + "");

        callKipMonitoring.enqueue(new Callback<KipMonitoring>() {

            @Override
            public void onResponse(Call<KipMonitoring> call, Response<KipMonitoring> response) {

                iOnFinishedKipMonitoringListener.onKipMonitoringFinished(response.body());
            }

            @Override
            public void onFailure(Call<KipMonitoring> call, Throwable t) {

                iOnFinishedKipMonitoringListener.onKipFailure(t);

            }
        });
    }
}
