package com.filreas.shared.dto;

import java.io.Serializable;

public class MetroDto implements Serializable {
    private String displayTime;

    private String stopAreaName;

    private String destination;

    private String lineNumber;

    private int groupOfLineId;

    private String platformMessage;

    public String getStopAreaName() {
        return stopAreaName;
    }

    public void setStopAreaName(String name) {
        this.stopAreaName = name;
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public GroupOfLine getGroupOfLine() {
        switch (groupOfLineId){
            case 1:
                return GroupOfLine.Green;
            case 2:
                return GroupOfLine.Red;
            case 3:
                return GroupOfLine.Blue;
            default:
                throw new IllegalArgumentException("No GroupOfLine exists with id " + groupOfLineId);
        }
    }

    public void setGroupOfLine(int groupOfLineId) {
        this.groupOfLineId = groupOfLineId;
    }

    public String getPlatformMessage() {
        return platformMessage;
    }

    public void setPlatformMessage(String platformMessage) {
        this.platformMessage = platformMessage;
    }
}
