package com.devitis.asympkfinalversion.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Diana on 08.05.2019.
 */

public class Skz {

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

    @SerializedName("MTDU_Code")
    @Expose(serialize = false)
    private String mtdu;

    private String KmToObj;

    public Skz(String objectId) {
        this.objectId = objectId;
    }

    public Skz(String id, String name, String subjectCode, String objectId, String longitude, String latitude, String km, String mtdu) {
        this.id = id;
        this.name = name;
        this.subjectCode = subjectCode;
        this.objectId = objectId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.km = km;
        this.mtdu = mtdu;
    }


    public Skz(String id, String name, String subjectCode, String objectId, String longitude, String latitude, String km, String mtdu, String kmToObj) {
        this.id = id;
        this.name = name;
        this.subjectCode = subjectCode;
        this.objectId = objectId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.km = km;
        this.mtdu = mtdu;
        this.KmToObj = kmToObj;
    }

    public Skz(String idSkzValue, String s, String s1, String objectId, String s2, String s3, String s4) {
        this.id = idSkzValue;
        this.name = s;
        this.subjectCode = s1;
        this.objectId = objectId;
        this.longitude = s2;
        this.latitude = s3;
        this.km = s4;
    }

    public String getKmToObj() {
        return KmToObj;
    }

    public void setKmToObj(String kmToObj) {
        KmToObj = kmToObj;
    }

    public String getMtdu() {
        return mtdu;
    }

    public void setMtdu(String mtdu) {
        this.mtdu = mtdu;
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
