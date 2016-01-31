package com.filreas.gosthlm.activities.Main;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.filreas.gosthlm.R;
import com.filreas.gosthlm.activities.FavouriteSiteSaveOnClickListener;
import com.filreas.gosthlm.activities.IFavouriteSiteSaveOnClick;
import com.filreas.gosthlm.activities.MobileBaseActivity;
import com.filreas.gosthlm.database.commands.CommandExecuter;
import com.filreas.gosthlm.database.commands.DeleteFavouriteStationCommand;
import com.filreas.gosthlm.database.commands.UpdateTransportationOfChoiceCommand;
import com.filreas.gosthlm.database.helpers.DbHelperWrapper;
import com.filreas.gosthlm.database.helpers.FavouriteSiteHelper;
import com.filreas.gosthlm.database.helpers.TransportationOfChoiceHelper;
import com.filreas.gosthlm.database.model.FavouriteSite;
import com.filreas.gosthlm.database.model.TransportationOfChoice;
import com.filreas.gosthlm.database.queries.FavouriteSitesQuery;
import com.filreas.gosthlm.database.queries.QueryLoader;
import com.filreas.gosthlm.database.queries.TransportationOfChoiceQuery;
import com.filreas.shared.utils.GoSthlmLog;
import com.filreas.shared.utils.SwipeDismissTouchListener;

import java.util.List;

public class MobileMainActivity extends MobileBaseActivity implements LoaderManager.LoaderCallbacks<TransportationOfChoice> {

    private TransportationOfChoice transportationOfChoice;
    private CheckBox metro;
    private CheckBox bus;
    private CheckBox train;
    private CheckBox tram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFavouriteSites();
        initTransportationOfChoice();
        initStationsSearch();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    private void initFavouriteSites() {
        this.getLoaderManager().initLoader(1, null, new LoaderManager.LoaderCallbacks<List<FavouriteSite>>() {
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
                setFavouriteSites(data);
            }

            @Override
            public void onLoaderReset(Loader<List<FavouriteSite>> loader) {
                GoSthlmLog.d("initFavouriteSites onLoaderReset");
            }
        });
    }

    private void initGetStartedGuide() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CardView view = (CardView) findViewById(R.id.top_info_card);
            view.setVisibility(View.VISIBLE);
            view.setOnTouchListener(new SwipeDismissTouchListener(view, null, new SwipeDismissTouchListener.DismissCallbacks() {
                @Override
                public boolean canDismiss(Object token) {
                    return true;
                }

                @Override
                public void onDismiss(View view, Object token) {
                    ((ViewGroup) view.getParent()).removeView(view);
                }
            }));
        }
    }

    private void initTransportationOfChoice() {
        this.getLoaderManager().initLoader(0, null, this);
        OnTransportationOfChoiceCheckboxClicked clickListener = new OnTransportationOfChoiceCheckboxClicked();
        metro = (CheckBox) findViewById(R.id.checkBoxMetro);
        metro.setOnClickListener(clickListener);
        bus = (CheckBox) findViewById(R.id.checkBoxBus);
        bus.setOnClickListener(clickListener);
        train = (CheckBox) findViewById(R.id.checkBoxTrain);
        train.setOnClickListener(clickListener);
        tram = (CheckBox) findViewById(R.id.checkBoxTram);
        tram.setOnClickListener(clickListener);
    }

    public void setFavouriteSites(List<FavouriteSite> favouriteSites) {
        if (favouriteSites.size() == 0) {
            initGetStartedGuide();
        }
    }

    private class OnTransportationOfChoiceCheckboxClicked implements CompoundButton.OnClickListener {

        @Override
        public void onClick(View v) {
            if (transportationOfChoice == null) {
                transportationOfChoice = new TransportationOfChoice();
            }

            transportationOfChoice.setMetro(metro.isChecked());
            transportationOfChoice.setBus(bus.isChecked());
            transportationOfChoice.setTrain(train.isChecked());
            transportationOfChoice.setTram(tram.isChecked());

            UpdateTransportationOfChoiceCommand update =
                    new UpdateTransportationOfChoiceCommand(
                            new TransportationOfChoiceHelper(
                                    new DbHelperWrapper(getApplicationContext())),
                            transportationOfChoice);
            new CommandExecuter().execute(update);
        }
    }

    private void initStationsSearch() {
        final AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.stationsSearch);
        AutoCompleteStationSearch autoCompleteStationSearch = new AutoCompleteStationSearch(slApi, slApiKeyFetcher);

        autoCompleteStationSearch.setOnClickListener(new FavouriteSiteSaveOnClickListener(
                getApplicationContext(),
                new IFavouriteSiteSaveOnClick() {
                    @Override
                    public void siteSaved(FavouriteSite site) {
                        showStationAddedSnackbar(site);
                    }
                }));
        autoCompleteStationSearch.init(textView);
    }

    private void showStationAddedSnackbar(final FavouriteSite favouriteSite) {
        String added = String.format(getString(R.string.stationAddedToFavourites), favouriteSite.getName());

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.mainWindowScroll), added, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new CommandExecuter().execute(
                                new DeleteFavouriteStationCommand(
                                        new FavouriteSiteHelper(
                                                new DbHelperWrapper(
                                                        getApplicationContext())),
                                        null,
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

    @Override
    public Loader<TransportationOfChoice> onCreateLoader(int id, Bundle args) {
        GoSthlmLog.d("TransportationOfChoice onCreateLoader");
        Context context = getApplicationContext();
        return new QueryLoader<>(
                context,
                new TransportationOfChoiceQuery(
                        new TransportationOfChoiceHelper(
                                new DbHelperWrapper(context))));
    }

    @Override
    public void onLoadFinished(Loader<TransportationOfChoice> loader, TransportationOfChoice data) {
        GoSthlmLog.d("TransportationOfChoice onLoadFinished");
        transportationOfChoice = data;
        metro.setChecked(transportationOfChoice.isMetro());
        bus.setChecked(transportationOfChoice.isBus());
        train.setChecked(transportationOfChoice.isTrain());
        tram.setChecked(transportationOfChoice.isTram());
    }

    @Override
    public void onLoaderReset(Loader<TransportationOfChoice> loader) {
        GoSthlmLog.d("TransportationOfChoice onLoaderRest");
    }

}
