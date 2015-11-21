package com.filreas.gosthlm.activities.Main;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.filreas.gosthlm.R;
import com.filreas.gosthlm.activities.About;
import com.filreas.gosthlm.activities.Help;
import com.filreas.gosthlm.activities.MobileBaseActivity;
import com.filreas.gosthlm.database.async.CommandExecuter;
import com.filreas.gosthlm.database.async.GetTransportationOfChoiceCommand;
import com.filreas.gosthlm.database.async.QueryLoader;
import com.filreas.gosthlm.database.async.UpdateTransportationOfChoiceCommand;
import com.filreas.gosthlm.database.helpers.DbHelperWrapper;
import com.filreas.gosthlm.database.helpers.TransportationOfChoiceHelper;
import com.filreas.gosthlm.database.model.TransportationOfChoice;
import com.filreas.gosthlm.slapi.operations.location_finder.contract.request.response.Site;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.vehicles.Metro;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.vehicles.TransportType;
import com.filreas.gosthlm.utils.OnItemClickListener;
import com.filreas.shared.utils.GoSthlmLog;
import com.filreas.shared.utils.SwipeDismissTouchListener;

import java.util.List;

public class MobileMainActivity extends MobileBaseActivity implements LoaderManager.LoaderCallbacks<TransportationOfChoice> {

    private DepartureSearch departureSearch;
    private TransportationOfChoice transportationOfChoice;
    private CheckBox metro;
    private CheckBox bus;
    private CheckBox train;
    private CheckBox tram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTransportationOfChoice();
        initStationsSearch();
        initDeparturesSearch();
        initGetStartedGuide();
    }

    private void initGetStartedGuide() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CardView view = (CardView) findViewById(R.id.top_info_card);
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

    private class OnTransportationOfChoiceCheckboxClicked implements CompoundButton.OnClickListener {

        @Override
        public void onClick(View v) {
            if(transportationOfChoice == null){
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

    private void initDeparturesSearch() {
        departureSearch = new DepartureSearch(getSLApi(), getSLApiKeyFetcher());
        departureSearch.onDepartureSearchListener(new OnDepartureSearchListener() {
            @Override
            public void onSearchCompleted(RealTimeResponse response) {

                TextView textView = (TextView) findViewById(R.id.departuresResults);
                textView.setText("");

                if (transportationOfChoice.isMetro()) {
                    for (Metro transportType : response.getResponseData().getMetros()) {
                        textView.append(transportType.getDestination() + ": " + transportType.getDisplayTime() + "\n");
                    }
                }

                if (transportationOfChoice.isBus()) {
                    printTransportatationResults(response.getResponseData().getBuses());
                }

                if (transportationOfChoice.isTrain()) {
                    printTransportatationResults(response.getResponseData().getTrains());
                }

                if (transportationOfChoice.isTram()) {
                    printTransportatationResults(response.getResponseData().getTrams());
                }

                getMobileClient().sendDepartureLiveInformation(response);
            }
        });
    }

    private void printTransportatationResults(List<? extends TransportType> transportTypes) {
        TextView textView = (TextView) findViewById(R.id.departuresResults);
        for (TransportType transportType : transportTypes) {
            textView.append(transportType.getDestination() + ": " + transportType.getDisplayTime() + "\n");
        }
    }

    private void initStationsSearch() {
        AutoCompleteStationSearch autoCompleteStationSearch = new AutoCompleteStationSearch(slApi, slApiKeyFetcher);
        final AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.stationsSearch);
        autoCompleteStationSearch.setOnClickListener(new OnItemClickListener<Site>() {
            @Override
            public void onClick(Site station) {
                ((TextView) findViewById(R.id.selectedStationText)).setText(station.getName());
                departureSearch.setSiteId(station.getSiteId());
                departureSearch.search();
            }
        });
        autoCompleteStationSearch.init(textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_help:
                startActivity(new Intent(this, Help.class));
                return true;
            case R.id.action_about:
                startActivity(new Intent(this, About.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<TransportationOfChoice> onCreateLoader(int id, Bundle args) {
        GoSthlmLog.d("TransportationOfChoice onCreateLoader");
        Context context = getApplicationContext();
        return new QueryLoader<>(
                context,
                new GetTransportationOfChoiceCommand(
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
