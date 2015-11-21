package com.filreas.gosthlm.database.async;

import com.filreas.gosthlm.database.helpers.ICrud;
import com.filreas.gosthlm.database.model.TransportationOfChoice;

public class UpdateTransportationOfChoiceCommand implements ICommand {


    private ICrud<TransportationOfChoice> transportationOfChoiceHelper;
    private TransportationOfChoice transportationOfChoice;

    public UpdateTransportationOfChoiceCommand(
            ICrud<TransportationOfChoice> transportationOfChoiceHelper,
            TransportationOfChoice transportationOfChoice) {
        this.transportationOfChoiceHelper = transportationOfChoiceHelper;
        this.transportationOfChoice = transportationOfChoice;
    }

    @Override
    public void execute() {
        transportationOfChoiceHelper.update(transportationOfChoice);
    }
}
