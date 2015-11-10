package com.filreas.gosthlm.database;

import android.content.Context;

import com.filreas.gosthlm.database.model.TransportationOfChoice;

public class GoSthlmRepository implements IGoSthlmRepository {

    private static final String DATABASE_NAME = "com.filreas.gosthlm.database.WearDb";
    private static final int DATABASE_VERSION = 1;
    private ITransportationOfChoice transportationOfChoiceHelper;

    public GoSthlmRepository(ITransportationOfChoice transportationOfChoiceHelper) {
        this.transportationOfChoiceHelper = transportationOfChoiceHelper;
    }

    public GoSthlmRepository(Context context) {
        transportationOfChoiceHelper =
                new TransportationOfChoiceHelper(context, DATABASE_NAME, DATABASE_VERSION);
    }

    @Override
    public void insertTransportationOfChoice(TransportationOfChoice transportationOfChoice) {
        transportationOfChoiceHelper.insertTransportationOfChoice(transportationOfChoice);
    }

    @Override
    public void updateTransportationOfChoice(TransportationOfChoice transportationOfChoice) {
        transportationOfChoiceHelper.updateTransportationOfChoice(transportationOfChoice);
    }

    @Override
    public TransportationOfChoice getTransportationOfChoice() {
        TransportationOfChoice result = transportationOfChoiceHelper.getTransportationOfChoice();
        if (result == null) {
            result = new TransportationOfChoice();
            insertTransportationOfChoice(result);
        }

        return result;
    }
}
