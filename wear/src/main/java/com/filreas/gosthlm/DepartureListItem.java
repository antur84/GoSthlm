package com.filreas.gosthlm;

import com.filreas.shared.dto.BusDto;
import com.filreas.shared.dto.MetroDto;
import com.filreas.shared.dto.TrainDto;
import com.filreas.shared.dto.TramDto;

public class DepartureListItem {
    private String destination;

    private String departureTimeText;

    public DepartureListItem(MetroDto metro) {
        destination = metro.getDestination();
        departureTimeText = metro.getDisplayTime();
    }

    public DepartureListItem(TrainDto train) {
        destination = train.getDestination();
        departureTimeText = train.getDisplayTime();
    }

    public DepartureListItem(TramDto tram) {
        destination = tram.getDestination();
        departureTimeText = tram.getDisplayTime();
    }

    public DepartureListItem(BusDto bus) {
        destination = bus.getDestination();
        departureTimeText = bus.getDisplayTime();
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTimeText() {
        return departureTimeText;
    }
}
