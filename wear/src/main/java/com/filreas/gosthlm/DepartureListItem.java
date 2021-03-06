package com.filreas.gosthlm;

import com.filreas.shared.dto.BusDto;
import com.filreas.shared.dto.MetroDto;
import com.filreas.shared.dto.TrainDto;
import com.filreas.shared.dto.TramDto;

public class DepartureListItem {
    private String destination;

    private String departureTimeText;

    private String lineNumberText;

    public DepartureListItem(MetroDto metro) {
        destination = metro.getDestination();
        departureTimeText = metro.getDisplayTime();
        lineNumberText = metro.getLineNumber();
    }

    public DepartureListItem(TrainDto train) {
        destination = train.getDestination();
        departureTimeText = train.getDisplayTime();
        lineNumberText = train.getLineNumber();
    }

    public DepartureListItem(TramDto tram) {
        destination = tram.getDestination();
        departureTimeText = tram.getDisplayTime();
        lineNumberText = tram.getLineNumber();
    }

    public DepartureListItem(BusDto bus) {
        destination = bus.getDestination();
        departureTimeText = bus.getDisplayTime();
        lineNumberText = bus.getLineNumber();
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTimeText() {
        return departureTimeText;
    }

    public String getLineNumberText() {
        return lineNumberText;
    }

}