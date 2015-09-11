package com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.extras;

import com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.vehicles.Bus;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.vehicles.Metro;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.vehicles.Ship;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.vehicles.Train;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.vehicles.Tram;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Andreas on 9/7/2015.
 */
public class Departure {
    @SerializedName("LatestUpdate")
    private Date latestUpdate;

    @SerializedName("DataAge")
    private int dataAge;

    @SerializedName("Buses")
    private List<Bus> buses;

    @SerializedName("Metros")
    private List<Metro> metros;

    @SerializedName("Trains")
    private List<Train> trains;

    @SerializedName("Trams")
    private List<Tram> trams;

    @SerializedName("Ships")
    private List<Ship> ships;

    @SerializedName("StopPointDeviations")
    private List<StopPointDeviation> stopPointDeviations;

    public Date getLatestUpdate() {
        return latestUpdate;
    }

    public int getDataAge() {
        return dataAge;
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public List<Metro> getMetros() {
        return metros;
    }

    public List<Train> getTrains() {
        return trains;
    }

    public List<Tram> getTrams() {
        return trams;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public List<StopPointDeviation> getStopPointDeviations() {
        return stopPointDeviations;
    }
}
