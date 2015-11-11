package com.filreas.gosthlm.database;

import android.content.Context;

import com.filreas.gosthlm.database.helpers.ICrud;
import com.filreas.gosthlm.database.helpers.TransportationOfChoiceHelper;
import com.filreas.gosthlm.database.model.TransportationOfChoice;

public class GoSthlmRepository implements IGoSthlmRepository {

    private static final String DATABASE_NAME = "com.filreas.gosthlm.database.WearDb";
    private static final int DATABASE_VERSION = 1;
    private ICrud<TransportationOfChoice> transportationOfChoiceHelper;

    public GoSthlmRepository(ICrud<TransportationOfChoice> transportationOfChoiceHelper) {
        this.transportationOfChoiceHelper = transportationOfChoiceHelper;
    }

    public GoSthlmRepository(Context context) {
        transportationOfChoiceHelper =
                new TransportationOfChoiceHelper(context, DATABASE_NAME, DATABASE_VERSION);
    }

    @Override
    public void insertTransportationOfChoice(TransportationOfChoice transportationOfChoice) {
        transportationOfChoiceHelper.create(transportationOfChoice);
    }

    @Override
    public void updateTransportationOfChoice(TransportationOfChoice transportationOfChoice) {
        transportationOfChoiceHelper.update(transportationOfChoice);
    }

    @Override
    public TransportationOfChoice getTransportationOfChoice() {
        TransportationOfChoice result = transportationOfChoiceHelper.read();
        if (result == null) {
            result = new TransportationOfChoice();
            insertTransportationOfChoice(result);
        }

        return result;
    }
}
