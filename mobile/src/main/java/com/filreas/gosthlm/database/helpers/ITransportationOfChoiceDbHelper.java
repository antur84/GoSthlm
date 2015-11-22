package com.filreas.gosthlm.database.helpers;

import com.filreas.gosthlm.database.model.TransportationOfChoice;

public interface ITransportationOfChoiceDbHelper {
    void create(TransportationOfChoice transportationOfChoice);

    void update(TransportationOfChoice transportationOfChoice);

    TransportationOfChoice read();
}
