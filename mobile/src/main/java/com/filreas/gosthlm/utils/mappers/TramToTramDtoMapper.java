package com.filreas.gosthlm.utils.mappers;

import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.vehicles.Tram;
import com.filreas.shared.dto.TramDto;

public class TramToTramDtoMapper {
    public static TramDto map(Tram tram) {
        TramDto tramDto = new TramDto();
        tramDto.setGroupOfLine(tram.getGroupOfLine());
        tramDto.setStopPointDesignation(tram.getStopPointDesignation());

        tramDto.setLineNumber(tram.getLineNumber());
        tramDto.setDeviations(DeviationToDeviationDtoMapper.map(tram.getDeviations()));
        tramDto.setDisplayTime(tram.getDisplayTime());
        tramDto.setDestination(tram.getDestination());
        tramDto.setStopAreaName(tram.getStopAreaName());
        tramDto.setExpectedDateTime(tram.getExpectedDateTime());

        return tramDto;
    }
}
