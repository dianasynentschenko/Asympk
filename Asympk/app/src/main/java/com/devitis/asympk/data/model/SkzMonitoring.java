package com.devitis.asympkfinalversion.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Diana on 08.05.2019.
 */

public class SkzMonitoring {

    @SerializedName("Id")
    private String id;
    @SerializedName("CheckDateTime")
    private String checkDataTime;
    @SerializedName("Current")
    private String current;
    @SerializedName("Voltage")
    private String voltage;
    @SerializedName("Sum_Pot")
    private String sumPot;
    @SerializedName("Pol_Pot")
    private String polPot;

    public SkzMonitoring(String id, String checkDataTime, String current, String voltage, String sumPot, String polPot) {
        this.id = id;
        this.checkDataTime = checkDataTime;
        this.current = current;
        this.voltage = voltage;
        this.sumPot = sumPot;
        this.polPot = polPot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckDataTime() {
        return checkDataTime;
    }

    public void setCheckDataTime(String checkDataTime) {
        this.checkDataTime = checkDataTime;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getSumPot() {
        return sumPot;
    }

    public void setSumPot(String sumPot) {
        this.sumPot = sumPot;
    }

    public String getPolPot() {
        return polPot;
    }

    public void setPolPot(String polPot) {
        this.polPot = polPot;
    }
}
