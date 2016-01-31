package com.filreas.gosthlm.activities.favourites;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AutoCompleteTextView;

import com.filreas.gosthlm.R;
import com.filreas.gosthlm.activities.FavouriteSiteSaveOnClickListener;
import com.filreas.gosthlm.activities.IFavouriteSiteSaveOnClick;
import com.filreas.gosthlm.activities.Main.AutoCompleteStationSearch;
import com.filreas.gosthlm.activities.MobileBaseActivity;
import com.filreas.gosthlm.database.commands.AddOrUpdateFavouriteStationCommand;
import com.filreas.gosthlm.database.commands.CommandExecuter;
import com.filreas.gosthlm.database.commands.DeleteFavouriteStationCommand;
import com.filreas.gosthlm.database.helpers.DbHelperWrapper;
import com.filreas.gosthlm.database.helpers.FavouriteSiteHelper;
import com.filreas.gosthlm.database.model.FavouriteSite;
import com.filreas.gosthlm.database.queries.FavouriteSitesQuery;
import com.filreas.gosthlm.database.queries.IDataSourceChanged;
import com.filreas.gosthlm.database.queries.QueryLoader;
import com.filreas.shared.utils.GoSthlmLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Favourites extends MobileBaseActivity {

    private RecyclerView.Adapter adapter;
    private final List<FavouriteSite> favouriteSites = new ArrayList<>();
    private FloatingActionButton fab;
    private View bottomToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableHomeAsUpNavigation();
        initFavouriteSites();
        initAddButton();
    }

    private void initAddButton() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBottomToolbar();
            }
        });
        bottomToolbar = findViewById(R.id.bottomToolBar);

        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.stationsSearch);
        AutoCompleteStationSearch autoCompleteStationSearch = new AutoCompleteStationSearch(slApi, slApiKeyFetcher);

        autoCompleteStationSearch.setOnClickListener(new FavouriteSiteSaveOnClickListener(
                getApplicationContext(),
                new IFavouriteSiteSaveOnClick() {
                    @Override
                    public void siteSaved(final FavouriteSite site) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                favouriteSites.add(site);
                                adapter.notifyDataSetChanged();
                                toggleBottomToolbar();
                            }
                        });
                    }
                }));
        autoCompleteStationSearch.init(textView);
    }

    private void toggleBottomToolbar() {

        final int cx = bottomToolbar.getWidth() / 2;
        final int cy = bottomToolbar.getHeight() / 2;
        final float radius = Math.max(bottomToolbar.getWidth(), bottomToolbar.getHeight()) * 2.0f;

        if (bottomToolbar.getVisibility() == View.INVISIBLE) {
            final Animator showToolbar = ViewAnimationUtils.createCircularReveal(bottomToolbar, cx, cy, 0, radius);
            fab.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                @Override
                public void onHidden(FloatingActionButton fab) {
                    super.onHidden(fab);
                    bottomToolbar.setVisibility(View.VISIBLE);
                    showToolbar.start();
                }
            });
        } else {
            Animator hideToolbar = ViewAnimationUtils.createCircularReveal(
                    bottomToolbar, cx, cy, radius, 0);
            hideToolbar.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    bottomToolbar.setVisibility(View.INVISIBLE);
                    fab.show();
                }
            });
            hideToolbar.start();
        }
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
                                        new DbHelperWrapper(context))));
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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewFavourites);
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
                    public void onItemDismissed(final int position) {
                        FavouriteSite removed = favouriteSites.remove(position);
                        new CommandExecuter().execute(
                                new DeleteFavouriteStationCommand(
                                        new FavouriteSiteHelper(
                                                new DbHelperWrapper(
                                                        getApplicationContext())),
                                        new IDataSourceChanged() {
                                            @Override
                                            public void dataSourceChanged() {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        adapter.notifyItemRemoved(position);
                                                    }
                                                });
                                            }
                                        },
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
