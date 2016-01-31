package com.filreas.gosthlm.activities.favourites;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

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
import java.util.Comparator;
import java.util.List;

public class Favourites extends MobileBaseActivity {

    private RecyclerView.Adapter adapter;
    private final List<FavouriteSite> favouriteSites = new ArrayList<>();
    private FloatingActionButton fab;
    private View bottomToolbar;
    private AutoCompleteTextView autoCompleteTextView;
    private RecyclerView recyclerView;

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
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.stationsSearch);
        autoCompleteTextView.setFocusableInTouchMode(true);
        AutoCompleteStationSearch autoCompleteStationSearch = new AutoCompleteStationSearch(slApi, slApiKeyFetcher);

        autoCompleteStationSearch.setOnClickListener(new FavouriteSiteSaveOnClickListener(
                getApplicationContext(),
                new IFavouriteSiteSaveOnClick() {
                    @Override
                    public void siteSaved(final FavouriteSite site) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                site.setSortPosition(favouriteSites.size());
                                new CommandExecuter().execute(
                                        new AddOrUpdateFavouriteStationCommand(
                                                new FavouriteSiteHelper(
                                                        new DbHelperWrapper(
                                                                getApplicationContext())),
                                                null, site));
                                
                                favouriteSites.add(site);
                                adapter.notifyDataSetChanged();
                                toggleBottomToolbar();
                                recyclerView.smoothScrollToPosition(favouriteSites.indexOf(site));
                            }
                        });
                    }
                }));
        autoCompleteStationSearch.init(autoCompleteTextView);
    }

    private void toggleBottomToolbar() {

        final int cx = bottomToolbar.getWidth() / 2;
        final int cy = bottomToolbar.getHeight() / 2;
        final float radius = Math.max(bottomToolbar.getWidth(), bottomToolbar.getHeight()) * 2.0f;

        if (bottomToolbar.getVisibility() == View.INVISIBLE) {
            final Animator showToolbar = ViewAnimationUtils.createCircularReveal(bottomToolbar, cx, cy, 0, radius);
            showToolbar.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    autoCompleteTextView.requestFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(autoCompleteTextView, 0);
                }
            });
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
                    super.onAnimationEnd(animation);
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewFavourites);
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
                        final FavouriteSite removed = favouriteSites.remove(position);
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
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                adapter.notifyItemRemoved(position);
                                                                showStationRemovedSnackbar(removed);
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        },
                                        removed));
                    }
                });
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if (bottomToolbar.getVisibility() == View.VISIBLE) {
                    toggleBottomToolbar();
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    private void showStationRemovedSnackbar(final FavouriteSite favouriteSite) {
        String removed = String.format(getString(R.string.stationRemovedFromFavourites), favouriteSite.getName());

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.favView), removed, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new CommandExecuter().execute(
                                new AddOrUpdateFavouriteStationCommand(
                                        new FavouriteSiteHelper(
                                                new DbHelperWrapper(
                                                        getApplicationContext())),
                                        new IDataSourceChanged() {
                                            @Override
                                            public void dataSourceChanged() {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        favouriteSites.add(favouriteSite);
                                                        Collections.sort(favouriteSites, new Comparator<FavouriteSite>() {
                                                            @Override
                                                            public int compare(FavouriteSite lhs, FavouriteSite rhs) {
                                                                return lhs.getSortPosition() - rhs.getSortPosition();
                                                            }
                                                        });
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                });
                                            }
                                        },
                                        favouriteSite
                                )
                        );
                    }
                });
        View view = snackbar.getView();
        TextView actionText = (TextView) view.findViewById(android.support.design.R.id.snackbar_action);
        actionText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.accent));
        snackbar.show();
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
