package com.filreas.gosthlm.database.model;

public class FavouriteSite implements Comparable<FavouriteSite> {
    private int id;

    private String name;

    private int siteId;

    private String type;

    private String x;

    private String y;

    public FavouriteSite(int id, String name, int siteId, String type, String x, String y) {
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

    @Override
    public int compareTo(FavouriteSite another) {
        if (another.getId() < getId()) return -1;
        if (another.getId() > getId()) return 1;
        if (another.getSiteId() < getSiteId()) return -1;
        if (another.getSiteId() > getSiteId()) return 1;
        int nameEquals = another.getName().compareTo(getName());
        if (nameEquals != 0) {
            return nameEquals;
        }
        int typeEquals = another.getType().compareTo(getType());
        if (typeEquals != 0) {
            return typeEquals;
        }
        int xEquals = another.getX().compareTo(getX());
        if (xEquals != 0) {
            return xEquals;
        }
        int yEquals = another.getY().compareTo(getY());
        if (yEquals != 0) {
            return yEquals;
        }

        return 0;
    }
}