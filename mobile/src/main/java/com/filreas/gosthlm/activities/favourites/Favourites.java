package com.filreas.gosthlm.activities.favourites;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.filreas.gosthlm.R;
import com.filreas.gosthlm.activities.MobileBaseActivity;
import com.filreas.gosthlm.database.model.FavouriteSite;

public class Favourites extends MobileBaseActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableHomeAsUpNavigation();
        initFavourites();
    }

    private void initFavourites() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        FavouriteSite[] favouriteSites = new FavouriteSite[]{
                new FavouriteSite(1, "test1", 1, "hej", "22", "33"),
                new FavouriteSite(1, "test2", 1, "hej", "22", "33")
        };
        mAdapter = new FavouritesAdapter(favouriteSites);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_favourites;
    }

}
