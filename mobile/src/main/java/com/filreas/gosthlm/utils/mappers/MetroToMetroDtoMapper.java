package com.filreas.gosthlm.utils.mappers;

import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.vehicles.Metro;
import com.filreas.shared.dto.MetroDto;

public class MetroToMetroDtoMapper {
    public static MetroDto map(Metro metro){
        MetroDto metroDto = new MetroDto();
        metroDto.setLineNumber(metro.getLineNumber());
        metroDto.setGroupOfLine(metro.getGroupOfLineId());
        metroDto.setDisplayTime(metro.getDisplayTime());
        metroDto.setDestination(metro.getDestination());
        metroDto.setStopAreaName(metro.getStopAreaName());
        metroDto.setPlatformMessage(metro.getPlatformMessage());
        return metroDto;
    }
}
