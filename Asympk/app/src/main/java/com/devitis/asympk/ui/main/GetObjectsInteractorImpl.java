package com.devitis.asympkfinalversion.ui.main;

import android.util.Log;


import com.devitis.asympkfinalversion.data.model.ObjectModel;
import com.devitis.asympkfinalversion.data.network.api.APIClient;
import com.devitis.asympkfinalversion.data.network.api.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Diana on 17.05.2019.
 */

public class GetObjectsInteractorImpl implements IMainContract.IGetObjectsInteractor {


    @Override
    public void getObjectList(final IOnFinishedObjectListener iOnFinishedObjectListener) {

        /**
         * descriptor for the retrofit instance interface
         */

        APIService service = APIClient.getRetrofitInstance().create(APIService.class);

        /**
         * call method with parameter in the interface to get the notice data
         */
        Call<List<ObjectModel>> callObjectModel = service.getObjectModel();


        /**
         * log the url called
         */
        Log.wtf("URL", callObjectModel.request().url() + "");

        callObjectModel.enqueue(new Callback<List<ObjectModel>>() {

            @Override
            public void onResponse(Call<List<ObjectModel>> call, Response<List<ObjectModel>> response) {

                iOnFinishedObjectListener.onObjectFinished(response.body());
            }

            @Override
            public void onFailure(Call<List<ObjectModel>> call, Throwable t) {

                iOnFinishedObjectListener.onObjectFailure(t);

            }
        });
    }
}
