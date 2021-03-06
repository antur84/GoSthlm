package com.filreas.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TrainDto implements Serializable {
    private String stopPointDesignation;
    private String groupOfLine;
    private String lineNumber;
    private String destination;
    private String stopAreaName;
    private String displayTime;
    private Date expectedDateTime;
    private List<DeviationDto> deviations;
    private String secondaryDestinationName;

    public void setStopPointDesignation(String stopPointDesignation) {
        this.stopPointDesignation = stopPointDesignation;
    }

    public void setGroupOfLine(String groupOfLine) {
        this.groupOfLine = groupOfLine;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setStopAreaName(String stopAreaName) {
        this.stopAreaName = stopAreaName;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }

    public void setExpectedDateTime(Date expectedDateTime) {
        this.expectedDateTime = expectedDateTime;
    }

    public void setDeviations(List<DeviationDto> deviations) {
        this.deviations = deviations;
    }

    public void setSecondaryDestinationName(String secondaryDestinationName) {
        this.secondaryDestinationName = secondaryDestinationName;
    }

    public String getDestination() {
        return destination;
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public String getLineNumber() {
        return "J" + lineNumber;
    }
}
