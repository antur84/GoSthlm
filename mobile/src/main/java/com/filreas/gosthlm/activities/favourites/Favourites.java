package com.filreas.gosthlm.activities.favourites;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.filreas.gosthlm.R;
import com.filreas.gosthlm.activities.MobileBaseActivity;
import com.filreas.gosthlm.database.helpers.DbHelperWrapper;
import com.filreas.gosthlm.database.helpers.FavouriteSiteHelper;
import com.filreas.gosthlm.database.model.FavouriteSite;
import com.filreas.gosthlm.database.queries.FavouriteSitesQuery;
import com.filreas.gosthlm.database.queries.IDataSourceCallbackListener;
import com.filreas.gosthlm.database.queries.IDataSourceChanged;
import com.filreas.gosthlm.database.queries.QueryLoader;
import com.filreas.shared.utils.GoSthlmLog;

import java.util.ArrayList;
import java.util.List;

public class Favourites extends MobileBaseActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private IDataSourceChanged favouriteSitesChangedListener;
    private List<FavouriteSite> favouriteSites = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableHomeAsUpNavigation();
        initFavouriteSites();
    }

    private void initFavouriteSites() {
        this.initFavouritesView();
        this.getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<List<FavouriteSite>>() {
            @Override
            public Loader<List<FavouriteSite>> onCreateLoader(int id, Bundle args) {
                Context context = getApplicationContext();
                return new QueryLoader<>(
                        context,
                        new FavouriteSitesQuery(
                                new FavouriteSiteHelper(
                                        new DbHelperWrapper(context))),
                        new IDataSourceCallbackListener() {

                            @Override
                            public void setOnDataChangedListener(IDataSourceChanged dataChangedListener) {
                                favouriteSitesChangedListener = dataChangedListener;
                            }
                        });
            }

            @Override
            public void onLoadFinished(Loader<List<FavouriteSite>> loader, List<FavouriteSite> data) {
                GoSthlmLog.d("initFavouriteSites onLoadFinished");
                favouriteSites.addAll(data);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoaderReset(Loader<List<FavouriteSite>> loader) {
                GoSthlmLog.d("initFavouriteSites onLoaderReset");
            }
        });
    }

    private void initFavouritesView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FavouritesAdapter(favouriteSites);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_favourites;
    }

}
