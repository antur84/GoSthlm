package com.filreas.slwear.slapi.contract.response.vehicles;

import com.filreas.slwear.slapi.contract.response.extras.Deviation;

import java.util.Date;
import java.util.List;

/**
 * Created by Andreas on 9/7/2015.
 */
public class TransportType {
    private int siteId;
    private String transportMode;
    private String stopAreaName;
    private int stopAreaNumber;
    private int stopPointNumber;
    private String lineNumber;
    private String destination;
    private Date timeTabledDateTime;
    private Date expectedDateTime;
    private String displayTime;
    private List<Deviation> deviations;
    private int journeyDirection;
}
