package com.filreas.slwear.activities.Main;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.filreas.slwear.R;
import com.filreas.slwear.async.ISLApiCall;
import com.filreas.slwear.async.ISLApiTaskResponseHandler;
import com.filreas.slwear.async.SLApiRequestTask;
import com.filreas.slwear.async.SLApiTaskResult;
import com.filreas.slwear.slapi.ISLApi;
import com.filreas.slwear.slapi.ISLApiKeyFetcher;
import com.filreas.slwear.slapi.operations.CacheType;
import com.filreas.slwear.slapi.operations.ResponseCacheStrategy;
import com.filreas.slwear.slapi.operations.ResponseFormat;
import com.filreas.slwear.slapi.operations.location_finder.contract.request.LocationFinderRequest;
import com.filreas.slwear.slapi.operations.location_finder.contract.request.response.LocationFinderResponse;
import com.filreas.slwear.slapi.operations.location_finder.contract.request.response.Site;
import com.filreas.slwear.utils.OnItemClickListener;
import com.filreas.slwear.utils.SLWearLog;

import java.util.ArrayList;

/**
 * Created by Andreas on 9/12/2015.
 */
public class AutoCompleteStationSearch implements IAutoCompleteStationSearch {

    private ArrayList<OnItemClickListener> onClickListeners;
    private ISLApi slApi;
    private ISLApiKeyFetcher slApiKeyFetcher;
    private LocationFinderRequest request;
    private AutoCompleteTextView autoCompleteTextView;
    private StationsAdapter dataAdapter;
    private boolean stationSelected;

    public AutoCompleteStationSearch(ISLApi slApi, ISLApiKeyFetcher slApiKeyFetcher) {
        this.slApi = slApi;
        this.slApiKeyFetcher = slApiKeyFetcher;
        this.onClickListeners = new ArrayList<>();
    }

    @Override
    public void init(final AutoCompleteTextView autoCompleteTextView) {
        request = new LocationFinderRequest(ResponseFormat.JSON, slApiKeyFetcher.getKey("slplatsuppslag"), "", new ResponseCacheStrategy(CacheType.ABSOLUTE_EXPIRATION, 1));

        this.autoCompleteTextView = autoCompleteTextView;

        dataAdapter = new StationsAdapter(autoCompleteTextView.getContext(), R.layout.station_item_row);
        dataAdapter.setOnClickListener(new OnItemClickListener<Site>() {

            @Override
            public void onClick(Site site) {
                stationSelected = true;
                autoCompleteTextView.setText(site.getName());
                autoCompleteTextView.selectAll();
                notifyOnClickListeners(site);
            }
        });

        this.autoCompleteTextView.setAdapter(dataAdapter);

        this.autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (autoCompleteTextView.isPerformingCompletion()) {
                    return;
                }
                if (charSequence.length() < autoCompleteTextView.getThreshold()) {
                    return;
                }

                if(stationSelected){
                    return;
                }

                request.setSearchString(charSequence.toString());

                SLApiRequestTask<LocationFinderRequest, LocationFinderResponse> searchTask = new SLApiRequestTask<>(new ISLApiCall<LocationFinderRequest, LocationFinderResponse>() {
                    @Override
                    public LocationFinderResponse perform(LocationFinderRequest request) {
                        return slApi.getLocations(request);
                    }
                },
                        new ISLApiTaskResponseHandler<LocationFinderResponse>() {
                            @Override
                            public void onTaskComplete(SLApiTaskResult<LocationFinderResponse> result) {
                                dataAdapter.clear();
                                if (result.getResponse().getStatusCode() != 0 &&
                                        result.getResponse().getResponseData() != null) {
                                    Toast.makeText(autoCompleteTextView.getContext(), "SL Api responded: " + result.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    SLWearLog.d("number of stations: " + result.getResponse().getResponseData().size());
                                    for (Site site : result.getResponse().getResponseData()) {
                                        dataAdapter.add(site);
                                    }

                                    autoCompleteTextView.setAdapter(dataAdapter);
                                    dataAdapter.notifyDataSetChanged();
                                }
                            }
                        });

                searchTask.execute(request);
            }

            @Override
            public void afterTextChanged(Editable s) {
                stationSelected = false;
            }
        });
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.onClickListeners.add(listener);
    }

    private void notifyOnClickListeners(Site site) {
        for (OnItemClickListener listener :
                onClickListeners) {
            listener.onClick(site);
        }
    }
}
