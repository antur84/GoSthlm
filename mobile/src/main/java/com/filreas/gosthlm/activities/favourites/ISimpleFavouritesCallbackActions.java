package com.filreas.gosthlm.activities.favourites;

public interface ISimpleFavouritesCallbackActions {
    boolean onItemMoved(int from, int to);

    void onItemDismissed(int position);
}
