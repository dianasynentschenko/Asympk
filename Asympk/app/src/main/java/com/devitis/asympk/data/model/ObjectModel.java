package com.devitis.asympkfinalversion.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Diana on 17.05.2019.
 */

public class ObjectModel {

    @SerializedName("Object_id")
    private String id;
    @SerializedName("Name")
    private String objectName;

    public ObjectModel(String id, String objectName) {
        this.id = id;
        this.objectName = objectName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
}
