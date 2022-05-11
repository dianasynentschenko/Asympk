package com.devitis.asympkfinalversion.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Diana on 06.05.2019.
 */

public class Kip {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Subject_Code")
    @Expose
    private String subjectCode;
    @SerializedName("Object_Id")
    @Expose
    private String objectId;
    @SerializedName("Longtitude")
    @Expose
    private String longitude;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("KM")
    @Expose
    private String km;

    private String KmToObj;


    public Kip(String id, String name, String subjectCode, String objectId, String longitude, String latitude, String km) {
        this.id = id;
        this.name = name;
        this.subjectCode = subjectCode;
        this.objectId = objectId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.km = km;
    }

    public Kip(String id, String name, String subjectCode, String objectId, String longitude, String latitude, String km, String kmToObj) {
        this.id = id;
        this.name = name;
        this.subjectCode = subjectCode;
        this.objectId = objectId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.km = km;
        KmToObj = kmToObj;
    }


    public String getKmToObj() {
        return KmToObj;
    }

    public void setKmToObj(String kmToObj) {
        KmToObj = kmToObj;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }
}
