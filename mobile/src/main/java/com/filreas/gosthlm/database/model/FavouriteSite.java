package com.filreas.gosthlm.database.model;

public class FavouriteSite {
    private int id;

    private String name;

    private int siteId;

    private String type;

    private String x;

    private String y;

    public FavouriteSite(int id, String name, int siteId, String type, String x, String y){
        this.id = id;
        this.name = name;
        this.siteId = siteId;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public int getSiteId() {
        return siteId;
    }

    public String getType() {
        return type;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public int getId() {
        return id;
    }
}