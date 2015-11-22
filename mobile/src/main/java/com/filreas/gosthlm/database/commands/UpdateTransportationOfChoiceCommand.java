package com.filreas.gosthlm.database.commands;

import com.filreas.gosthlm.database.helpers.ITransportationOfChoiceDbHelper;
import com.filreas.gosthlm.database.model.TransportationOfChoice;

public class UpdateTransportationOfChoiceCommand implements ICommand {


    private ITransportationOfChoiceDbHelper transportationOfChoiceHelper;
    private TransportationOfChoice item;

    public UpdateTransportationOfChoiceCommand(
            ITransportationOfChoiceDbHelper transportationOfChoiceHelper,
            TransportationOfChoice item) {
        this.transportationOfChoiceHelper = transportationOfChoiceHelper;
        this.item = item;
    }

    @Override
    public void execute() {
        transportationOfChoiceHelper.update(item);
    }
}
