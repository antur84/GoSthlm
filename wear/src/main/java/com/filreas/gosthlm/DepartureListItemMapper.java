package com.filreas.gosthlm;

import com.filreas.shared.dto.BusDto;
import com.filreas.shared.dto.FavouriteSiteLiveUpdateDto;
import com.filreas.shared.dto.MetroDto;
import com.filreas.shared.dto.TrainDto;
import com.filreas.shared.dto.TramDto;

import java.util.ArrayList;

public class DepartureListItemMapper {
    public static ArrayList<DepartureListItem> CreateDepartures(FavouriteSiteLiveUpdateDto dto) {
        ArrayList<DepartureListItem> departures = new ArrayList<>();
        for (MetroDto metro : dto.getMetros()) {
            departures.add(new DepartureListItem(metro));
        }

        for (TrainDto train : dto.getTrains()) {
            departures.add(new DepartureListItem(train));
        }

        for (TramDto tram : dto.getTrams()) {
            departures.add(new DepartureListItem(tram));
        }

        for (BusDto bus : dto.getBuses()) {
            departures.add(new DepartureListItem(bus));
        }

        return departures;
    }
}
