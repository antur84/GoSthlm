package com.filreas.gosthlm;

import com.filreas.shared.dto.BusDto;
import com.filreas.shared.dto.MetroDto;
import com.filreas.shared.dto.TrainDto;
import com.filreas.shared.dto.TramDto;

public class DepartureListItem {
    private String destination;

    public DepartureListItem(MetroDto metro) {
        destination = metro.getDestination();
    }

    public DepartureListItem(TrainDto train) {
        destination = train.getDestination();
    }

    public DepartureListItem(TramDto tram) {
        destination = tram.getDestination();
    }

    public DepartureListItem(BusDto bus) {
        destination = bus.getDestination();
    }

    public String getDestination() {
        return destination;
    }
}
