package com.filreas.gosthlm.utils.mappers;

import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.vehicles.Bus;
import com.filreas.shared.dto.BusDto;

public class BusToBusDtoMapper {
    public static BusDto map(Bus bus){
        BusDto busDto = new BusDto();
        busDto.setStopPointDesignation(bus.getStopPointDesignation());
        busDto.setGroupOfLine(bus.getGroupOfLine());
        busDto.setLineNumber(bus.getLineNumber());
        busDto.setDeviations(DeviationToDeviationDtoMapper.map(bus.getDeviations()));
        busDto.setDisplayTime(bus.getDisplayTime());
        busDto.setDestination(bus.getDestination());
        busDto.setStopAreaName(bus.getStopAreaName());
        busDto.setExpectedDateTime(bus.getExpectedDateTime());

        return busDto;
    }
}
