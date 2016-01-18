package com.filreas.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class FavouriteSiteLiveUpdateDto implements Serializable {
    private String name;
    private int siteId;
    private String errorMessage;
    private int lastUpdatedInMillies;

    private final List<MetroDto> metros = new ArrayList<>();
    private final List<BusDto> buses = new ArrayList<>();
    private final List<TrainDto> trains = new ArrayList<>();
    private final List<TramDto> trams = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public int getSiteId() {
        return siteId;
    }

    public Collection<MetroDto> getMetros() {
        return metros;
    }

    public Collection<BusDto> getBuses() {
        return buses;
    }

    public Collection<TrainDto> getTrains() {
        return trains;
    }

    public Collection<TramDto> getTrams() {
        return trams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavouriteSiteLiveUpdateDto that = (FavouriteSiteLiveUpdateDto) o;
        return Objects.equals(siteId, that.siteId) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, siteId);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getLastUpdatedInMillies() {
        return lastUpdatedInMillies;
    }

    public void setLastUpdatedInMillies(int lastUpdatedInMillies) {
        this.lastUpdatedInMillies = lastUpdatedInMillies;
    }
}
