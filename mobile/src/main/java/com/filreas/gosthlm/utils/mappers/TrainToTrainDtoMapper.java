package com.filreas.gosthlm.utils.mappers;

import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.vehicles.Train;
import com.filreas.shared.dto.TrainDto;

public class TrainToTrainDtoMapper {
    public static TrainDto map(Train train) {
        TrainDto trainDto = new TrainDto();
        trainDto.setStopPointDesignation(train.getStopPointDesignation());
        trainDto.setSecondaryDestinationName(train.getSecondaryDestinationName());

        trainDto.setLineNumber(train.getLineNumber());
        trainDto.setDeviations(DeviationToDeviationDtoMapper.map(train.getDeviations()));
        trainDto.setDisplayTime(train.getDisplayTime());
        trainDto.setDestination(train.getDestination());
        trainDto.setStopAreaName(train.getStopAreaName());
        trainDto.setExpectedDateTime(train.getExpectedDateTime());

        return trainDto;
    }
}
