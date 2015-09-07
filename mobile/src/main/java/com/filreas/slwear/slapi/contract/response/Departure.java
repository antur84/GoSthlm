package com.filreas.slwear.slapi.contract.response;

import com.filreas.slwear.slapi.contract.response.vehicles.Bus;
import com.filreas.slwear.slapi.contract.response.vehicles.Metro;
import com.filreas.slwear.slapi.contract.response.vehicles.Ship;
import com.filreas.slwear.slapi.contract.response.vehicles.Train;
import com.filreas.slwear.slapi.contract.response.vehicles.Tram;

import java.util.Date;
import java.util.List;

/**
 * Created by Andreas on 9/7/2015.
 */
public class Departure {
    private Date latestUpdate;
    private int dataAge;
    private List<Bus> buses;
    private List<Metro> metros;
    private List<Train> trains;
    private List<Tram> trams;
    private List<Ship> ships;
    private List<StopPointDeviation> stopPointDeviations;

}
