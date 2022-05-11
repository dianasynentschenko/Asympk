package com.devitis.asympkfinalversion.data.network.api;



import com.devitis.asympkfinalversion.data.model.Kip;
import com.devitis.asympkfinalversion.data.model.KipMonitoring;
import com.devitis.asympkfinalversion.data.model.ObjectModel;
import com.devitis.asympkfinalversion.data.model.Skz;
import com.devitis.asympkfinalversion.data.model.SkzMonitoring;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by Diana on 26.04.2019.
 */

public interface APIService {


    @GET("WebApi/api/KIP")
    Call<List<Kip>> getKip();

    @GET("WebApi/api/SKZ")
    Call<List<Skz>> getSkz();

    @GET("WebApi/api/object")
    Call<List<ObjectModel>> getObjectModel();


    @GET("WebApi/api/Monitoring_KIP")
    Call<KipMonitoring> getKipMonitoringById(@Query("id") int idKipMonitoring);

    @GET("WebApi/api/Monitoring_SKZ")
    Call<SkzMonitoring> getSkzMonitoringById(@Query("id") int idSkzMonitoring);

    @PUT("WebApi/api/SKZ/")
    @FormUrlEncoded
    Call<Skz> setSkz(
            @Field("Id") String id,
            @Field("Name") String name,
            @Field("Subject_Code") String code,
            @Field("Object_Id") String objectId,
            @Field("Longtitude") String lon,
            @Field("Latitude") String lat,
            @Field("KM") String km
    );

    @PUT("WebApi/api/KIP")
    @FormUrlEncoded
    Call<Kip> setKip(

            @Field("Id") String id,
            @Field("Name") String name,
            @Field("Subject_Code") String code,
            @Field("Object_Id") String objectId,
            @Field("Longtitude") String lon,
            @Field("Latitude") String lat,
            @Field("KM") String km
    );


}
