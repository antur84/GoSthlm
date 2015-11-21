package com.filreas.gosthlm.database.async;

import com.filreas.gosthlm.database.helpers.ICrud;
import com.filreas.gosthlm.database.model.TransportationOfChoice;

public class GetTransportationOfChoiceCommand implements IGetCommand<TransportationOfChoice> {

    private ICrud<TransportationOfChoice> helper;

    public GetTransportationOfChoiceCommand(ICrud<TransportationOfChoice> helper) {
        this.helper = helper;
    }

    @Override
    public TransportationOfChoice get() {
        TransportationOfChoice result = helper.read();
        if (result == null) {
            result = new TransportationOfChoice();
            helper.create(result);
        }

        return result;
    }
}
