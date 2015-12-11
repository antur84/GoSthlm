package com.filreas.gosthlm.datalayer;

import android.content.Context;
import android.widget.Toast;

import com.filreas.gosthlm.database.model.FavouriteSite;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.vehicles.Metro;
import com.filreas.shared.dto.DeparturesDto;
import com.filreas.shared.dto.MetroDto;
import com.filreas.shared.utils.DataLayerUri;
import com.filreas.shared.utils.DtoSerializer;
import com.filreas.shared.utils.GoSthlmLog;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableStatusCodes;

import java.io.IOException;
import java.util.List;

public class WearActions {

    private GoogleApiClient client;
    private Context context;

    public WearActions(GoogleApiClient client, Context context){
        this.client = client;
        this.context = context;
    }

    public void sendDepartureLiveInformation(RealTimeResponse response) {
        PutDataRequest request = PutDataRequest.create(DataLayerUri.DEPARTURE_LIVE_INFO_URL);
        DeparturesDto departures = new DeparturesDto();
        for (Metro metro : response.getResponseData().getMetros()) {
            MetroDto metroDto = new MetroDto();
            metroDto.setLineNumber(metro.getLineNumber());
            metroDto.setGroupOfLine(metro.getGroupOfLineId());
            metroDto.setDisplayTime(metro.getDisplayTime());
            metroDto.setDestination(metro.getDestination());
            metroDto.setStopAreaName(metro.getStopAreaName());
            metroDto.setPlatformMessage(metro.getPlatformMessage());
            departures.getMetros().add(metroDto);
        }

        try {
            request.setData(DtoSerializer.convertToBytes(departures));
            PendingResult<DataApi.DataItemResult> result = Wearable.DataApi.putDataItem(client, request);
            result.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                @Override
                public void onResult(DataApi.DataItemResult dataItemResult) {
                    if (dataItemResult.getStatus().isSuccess()) {
                        GoSthlmLog.d("Update pushed successfully");
                    } else {
                        GoSthlmLog.d("Update failed to push, statusCode: "
                                + dataItemResult.getStatus().getStatusCode() +
                                ", msg: " + WearableStatusCodes.getStatusCodeString(dataItemResult.getStatus().getStatusCode()));
                    }
                }
            });
        } catch (IOException e) {
            GoSthlmLog.e(e);
            Toast.makeText(context, "Error serializing data", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendFavouriteSites(List<FavouriteSite> favouriteSites) {
        GoSthlmLog.d("sendFavouriteSites" + " to watch: " + favouriteSites.size());
    }
}
