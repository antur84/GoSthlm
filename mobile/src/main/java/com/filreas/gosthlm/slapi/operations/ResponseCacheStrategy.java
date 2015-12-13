package com.filreas.gosthlm.slapi.operations;

public class ResponseCacheStrategy {
    private final CacheType type;
    private final int minutes;

    /***
     * Create a caching strategy.
     *
     * @param type    What kind of strategy, or None for no caching.
     * @param minutes only respected if @type is not None
     */
    public ResponseCacheStrategy(CacheType type, int minutes) {
        this.type = type;
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    public CacheType getType() {
        return type;
    }
}
