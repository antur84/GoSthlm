package com.filreas.gosthlm.utils.mappers;

import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.extras.Deviation;
import com.filreas.shared.dto.DeviationDto;

import java.util.ArrayList;
import java.util.List;

public class DeviationToDeviationDtoMapper {
    public static List<DeviationDto> map(List<Deviation> deviations) {
        List<DeviationDto> result = new ArrayList<>();
        if (deviations != null) {
            for (Deviation deviation : deviations) {
                DeviationDto dto = new DeviationDto();
                dto.setConsequence(deviation.getConsequence());
                dto.setImportanceLevel(deviation.getImportanceLevel());
                dto.setText(deviation.getText());
                result.add(dto);
            }
        }
        return result;
    }
}
