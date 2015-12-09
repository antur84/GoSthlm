package com.filreas.gosthlm.activities.favourites;

public interface ISimpleFavouritesCallbackActions {
    boolean onItemMove(int from, int to);

    void onItemDismissed(int position);
}
