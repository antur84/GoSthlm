package com.filreas.gosthlm.utils;

import com.filreas.gosthlm.activities.Main.DepartureSearch;
import com.filreas.gosthlm.activities.Main.OnDepartureSearchListener;
import com.filreas.gosthlm.database.model.FavouriteSite;
import com.filreas.gosthlm.database.model.TransportationOfChoice;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.extras.Departure;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.vehicles.Bus;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.vehicles.Metro;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.vehicles.Train;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.vehicles.Tram;
import com.filreas.gosthlm.utils.mappers.BusToBusDtoMapper;
import com.filreas.gosthlm.utils.mappers.MetroToMetroDtoMapper;
import com.filreas.gosthlm.utils.mappers.TrainToTrainDtoMapper;
import com.filreas.gosthlm.utils.mappers.TramToTramDtoMapper;
import com.filreas.shared.dto.FavouriteSiteLiveUpdateDto;

import java.util.List;

public class FavouriteSitesLiveUpdater implements IFavouriteSitesLiveUpdater, OnDepartureSearchListener {

    private final DepartureSearch departureSearch;
    private TransportationOfChoice transportationOfChoice;
    private IFavouriteSiteLiveUpdateCallback callback;
    private int itemsLeftToProcessInBatch = 0;

    public FavouriteSitesLiveUpdater(DepartureSearch departureSearch){
        this.departureSearch = departureSearch;
        departureSearch.addDepartureSearchListener(this);
    }

    @Override
    public void updateAllOneAtATime(List<FavouriteSite> favouriteSites,
                                    TransportationOfChoice transportationOfChoice,
                                    IFavouriteSiteLiveUpdateCallback callback) {

        this.transportationOfChoice = transportationOfChoice;
        this.callback = callback;
        this.itemsLeftToProcessInBatch += favouriteSites.size();
        for (FavouriteSite site :
                favouriteSites) {
            departureSearch.search(site);
        }
    }

    @Override
    public void onSearchCompleted(FavouriteSite site, RealTimeResponse response) {
        if(callback != null){
            FavouriteSiteLiveUpdateDto updatedSite = new FavouriteSiteLiveUpdateDto();
            updatedSite.setName(site.getName());
            updatedSite.setSiteId(site.getSiteId());
            Departure responseData = response.getResponseData();
            if(transportationOfChoice.isMetro()) {
                for (Metro metro : responseData.getMetros()) {
                    updatedSite.getMetros().add(MetroToMetroDtoMapper.map(metro));
                }
            }
            if(transportationOfChoice.isBus()){
                for (Bus bus : responseData.getBuses()) {
                    updatedSite.getBuses().add(BusToBusDtoMapper.map(bus));
                }
            }
            if(transportationOfChoice.isTrain()){
                for (Train train : responseData.getTrains()) {
                    updatedSite.getTrains().add(TrainToTrainDtoMapper.map(train));
                }
            }
            if(transportationOfChoice.isTram()){
                for (Tram tram : responseData.getTrams()) {
                    updatedSite.getTrams().add(TramToTramDtoMapper.map(tram));
                }
            }

            callback.onFavouriteSiteUpdated(updatedSite);

            finishItemProcessing();
        }
    }

    @Override
    public void onSearchFailed(FavouriteSite site, String reason) {
        FavouriteSiteLiveUpdateDto updatedSite = new FavouriteSiteLiveUpdateDto();
        updatedSite.setName(site.getName());
        updatedSite.setSiteId(site.getSiteId());
        updatedSite.setErrorMessage(reason);

        callback.onFavouriteSiteUpdateFailed(updatedSite);

        finishItemProcessing();
    }

    private void finishItemProcessing() {
        itemsLeftToProcessInBatch--;
        if(itemsLeftToProcessInBatch == 0){
            callback.allFavouriteSitesInBatchUpdated();
        }
    }
}
