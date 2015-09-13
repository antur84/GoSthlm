package com.filreas.slwear.activities.Main;

import android.view.View;
import android.widget.Button;

import com.filreas.slwear.async.ISLApiCall;
import com.filreas.slwear.async.ISLApiTaskResponseHandler;
import com.filreas.slwear.async.SLApiRequestTask;
import com.filreas.slwear.async.SLApiTaskResult;
import com.filreas.slwear.slapi.ISLApi;
import com.filreas.slwear.slapi.ISLApiKeyFetcher;
import com.filreas.slwear.slapi.operations.CacheType;
import com.filreas.slwear.slapi.operations.ResponseCacheStrategy;
import com.filreas.slwear.slapi.operations.ResponseFormat;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.request.RealTimeRequest;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;
import com.filreas.slwear.utils.SLWearLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andreas on 9/13/2015.
 */
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
                getSiteId(),
                30,
                new ResponseCacheStrategy(CacheType.ABSOLUTE_EXPIRATION, 1));
    }

    public void init(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public void search() {
        request.setSiteId(getSiteId());
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
                            SLWearLog.d("SL Api responded: " + result.getResponse().getMessage());
                        } else {
                            SLWearLog.d("number of metro departures: " + result.getResponse().getResponseData().getMetros().size());
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
