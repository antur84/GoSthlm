package com.filreas.gosthlm.database.model;

public class TransportationOfChoice {
    private boolean metro;
    private boolean bus;
    private boolean train;
    private boolean tram;

    public boolean isMetro() {
        return metro;
    }

    public void setMetro(boolean metro) {
        this.metro = metro;
    }

    public boolean isBus() {
        return bus;
    }

    public void setBus(boolean bus) {
        this.bus = bus;
    }

    public boolean isTrain() {
        return train;
    }

    public void setTrain(boolean train) {
        this.train = train;
    }

    public boolean isTram() {
        return tram;
    }

    public void setTram(boolean tram) {
        this.tram = tram;
    }
}
