package com.filreas.gosthlm.activities.favourites;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.filreas.gosthlm.R;
import com.filreas.gosthlm.activities.MobileBaseActivity;
import com.filreas.gosthlm.database.commands.AddOrUpdateFavouriteStationCommand;
import com.filreas.gosthlm.database.commands.CommandExecuter;
import com.filreas.gosthlm.database.commands.DeleteFavouriteStationCommand;
import com.filreas.gosthlm.database.helpers.DbHelperWrapper;
import com.filreas.gosthlm.database.helpers.FavouriteSiteHelper;
import com.filreas.gosthlm.database.model.FavouriteSite;
import com.filreas.gosthlm.database.queries.FavouriteSitesQuery;
import com.filreas.gosthlm.database.queries.IDataSourceCallbackListener;
import com.filreas.gosthlm.database.queries.IDataSourceChanged;
import com.filreas.gosthlm.database.queries.QueryLoader;
import com.filreas.shared.utils.GoSthlmLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Favourites extends MobileBaseActivity {

    private RecyclerView.Adapter adapter;
    private IDataSourceChanged favouriteSitesChangedListener;
    private final List<FavouriteSite> favouriteSites = new ArrayList<>();

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
                favouriteSites.clear();
                favouriteSites.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLoaderReset(Loader<List<FavouriteSite>> loader) {
                GoSthlmLog.d("initFavouriteSites onLoaderReset");
            }
        });
    }

    private void initFavouritesView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FavouritesAdapter(favouriteSites);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback =
                new SimpleFavouritesCallback(new ISimpleFavouritesCallbackActions() {
                    @Override
                    public void onItemMove(int from, int to) {
                        if (from < to) {
                            for (int i = from; i < to; i++) {
                                Collections.swap(favouriteSites, i, i + 1);
                            }
                        } else {
                            for (int i = from; i > to; i--) {
                                Collections.swap(favouriteSites, i, i - 1);
                            }
                        }

                        updateSortPositionOfFavouriteSite(from);
                        updateSortPositionOfFavouriteSite(to);

                        adapter.notifyItemMoved(from, to);
                    }

                    @Override
                    public void onItemDismissed(int position) {
                        FavouriteSite removed = favouriteSites.remove(position);
                        adapter.notifyItemRemoved(position);
                        new CommandExecuter().execute(
                                new DeleteFavouriteStationCommand(
                                        new FavouriteSiteHelper(
                                                new DbHelperWrapper(
                                                        getApplicationContext())),
                                        favouriteSitesChangedListener,
                                        removed));
                    }
                });
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private void updateSortPositionOfFavouriteSite(int position) {
        FavouriteSite site = favouriteSites.get(position);
        site.setSortPosition(position);
        new CommandExecuter().execute(
                new AddOrUpdateFavouriteStationCommand(
                        new FavouriteSiteHelper(
                                new DbHelperWrapper(
                                        getApplicationContext())),
                        null,
                        site));
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_favourites;
    }

}
