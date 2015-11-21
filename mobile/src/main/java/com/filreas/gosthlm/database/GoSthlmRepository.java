package com.filreas.gosthlm.database;

import com.filreas.gosthlm.database.helpers.ICrud;
import com.filreas.gosthlm.database.helpers.IDbHelper;
import com.filreas.gosthlm.database.helpers.IFavouriteSite;
import com.filreas.gosthlm.database.helpers.SiteHelper;
import com.filreas.gosthlm.database.helpers.TransportationOfChoiceHelper;
import com.filreas.gosthlm.database.model.TransportationOfChoice;

public class GoSthlmRepository implements IGoSthlmRepository {

    private IFavouriteSite siteHelper;
    private ICrud<TransportationOfChoice> transportationOfChoiceHelper;

    public GoSthlmRepository(
            ICrud<TransportationOfChoice> transportationOfChoiceHelper,
            IFavouriteSite siteHelper) {
        this.transportationOfChoiceHelper = transportationOfChoiceHelper;
        this.siteHelper = siteHelper;
    }

    public GoSthlmRepository(IDbHelper dbHelper) {
        this(new TransportationOfChoiceHelper(dbHelper), new SiteHelper(dbHelper));
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
