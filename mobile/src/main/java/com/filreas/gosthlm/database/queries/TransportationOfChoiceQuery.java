package com.filreas.gosthlm.database.queries;

import com.filreas.gosthlm.database.helpers.ITransportationOfChoiceDbHelper;
import com.filreas.gosthlm.database.model.TransportationOfChoice;

public class TransportationOfChoiceQuery implements IQuery<TransportationOfChoice> {

    private final ITransportationOfChoiceDbHelper helper;

    public TransportationOfChoiceQuery(ITransportationOfChoiceDbHelper helper) {
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
