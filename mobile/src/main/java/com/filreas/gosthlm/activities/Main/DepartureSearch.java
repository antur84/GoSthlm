package com.filreas.gosthlm.activities.Main;

import com.filreas.gosthlm.async.ISLApiCall;
import com.filreas.gosthlm.async.ISLApiTaskResponseHandler;
import com.filreas.gosthlm.async.SLApiRequestTask;
import com.filreas.gosthlm.async.SLApiTaskResult;
import com.filreas.gosthlm.slapi.ISLApi;
import com.filreas.gosthlm.slapi.ISLApiKeyFetcher;
import com.filreas.gosthlm.slapi.operations.CacheType;
import com.filreas.gosthlm.slapi.operations.ResponseCacheStrategy;
import com.filreas.gosthlm.slapi.operations.ResponseFormat;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.request.RealTimeRequest;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;
import com.filreas.shared.utils.GoSthlmLog;

import java.util.ArrayList;
import java.util.List;

public class DepartureSearch {
    private final RealTimeRequest request;
    private ISLApi slApi;
    private int siteId;
    private List<OnDepartureSearchListener> listeners;

    public DepartureSearch(ISLApi slApi, ISLApiKeyFetcher slApiKeyFetcher) {
        this.slApi = slApi;
        listeners = new ArrayList<>();

        request = new RealTimeRequest(
                ResponseFormat.JSON,
                slApiKeyFetcher.getKey("slrealtidsinformation3"),
                -1,
                30,
                new ResponseCacheStrategy(CacheType.ABSOLUTE_EXPIRATION, 1));
    }

    public void search(int siteId) {
        request.setSiteId(siteId);
        SLApiRequestTask<RealTimeRequest, RealTimeResponse> getDeparturesTask = new SLApiRequestTask<>(new ISLApiCall<RealTimeRequest, RealTimeResponse>() {
            @Override
            public RealTimeResponse perform(RealTimeRequest request) {
                return slApi.getRealTimeStationInfo(request);
            }
        },
                new ISLApiTaskResponseHandler<RealTimeResponse>() {
                    @Override
                    public void onTaskComplete(SLApiTaskResult<RealTimeResponse> result) {
                        if (result.getResponse().getStatusCode() != 0 &&
                                result.getResponse().getResponseData() != null) {
                            GoSthlmLog.d("SL Api responded: " + result.getResponse().getMessage());
                        } else {
                            GoSthlmLog.d("number of metro departures: " + result.getResponse().getResponseData().getMetros().size());
                            notifySearchCompleted(result.getResponse());
                        }
                    }
                });

        getDeparturesTask.execute(request);
    }

    private void notifySearchCompleted(RealTimeResponse response) {
        for (OnDepartureSearchListener listener : listeners) {
            listener.onSearchCompleted(response);
        }
    }

    public void onDepartureSearchListener(OnDepartureSearchListener listener) {
        listeners.add(listener);
    }
}
