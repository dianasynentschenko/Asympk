package com.devitis.asympkfinalversion.ui.monitoring;

import android.util.Log;


import com.devitis.asympkfinalversion.data.model.SkzMonitoring;
import com.devitis.asympkfinalversion.data.network.api.APIClient;
import com.devitis.asympkfinalversion.data.network.api.APIService;
import com.devitis.asympkfinalversion.ui.main.IMainContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Diana on 08.05.2019.
 */

public class GetSkzMonitoringInteractorImpl implements IMainContract.IGetSkzMonitoringInteractor {

    @Override
    public void getSkzMonitoringList(final IOnFinishedSkzMonitoringListener iOnFinishedSkzMonitoringListener, final int idSkzMonitoring) {

        /**
         *
         * descriptor for the retrofit instance interface
         */
        APIService service = APIClient.getRetrofitInstance().create(APIService.class);

        /**
         * call method with  with parameter in the interface to get the notice data
         */
        final Call<SkzMonitoring> callSkzMonitoring = service.getSkzMonitoringById(idSkzMonitoring);

        /**Log the URL called*/
        Log.wtf("URL ", callSkzMonitoring.request().url() + "");

        callSkzMonitoring.enqueue(new Callback<SkzMonitoring>() {
            @Override
            public void onResponse(Call<SkzMonitoring> call, Response<SkzMonitoring> response) {

                iOnFinishedSkzMonitoringListener.onSkzMonitoringFinished(response.body());
            }

            @Override
            public void onFailure(Call<SkzMonitoring> call, Throwable t) {

                iOnFinishedSkzMonitoringListener.onSkzFailure(t);

            }
        });



    }
}
