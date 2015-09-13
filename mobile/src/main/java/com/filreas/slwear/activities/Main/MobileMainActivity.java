package com.filreas.slwear.activities.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.filreas.slwear.R;
import com.filreas.slwear.activities.About;
import com.filreas.slwear.activities.BaseMobileActivity;
import com.filreas.slwear.activities.Help;
import com.filreas.slwear.slapi.operations.location_finder.contract.request.response.Site;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.vehicles.Metro;
import com.filreas.slwear.utils.OnItemClickListener;

public class MobileMainActivity extends BaseMobileActivity {

    private DepartureSearch departureSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initStationsSearch();
        initDeparturesSearch();
    }

    private void initDeparturesSearch() {
        departureSearch = new DepartureSearch(getSlApi(), getSlApiKeyFetcher());
        Button btn = (Button) findViewById(R.id.btnDepartures);
        departureSearch.onDepartureSearchListener(new OnDepartureSearchListener() {
            @Override
            public void onSearchCompleted(RealTimeResponse response) {

                TextView textView = (TextView) findViewById(R.id.departuresResults);
                textView.setText("");
                for (Metro metro : response.getResponseData().getMetros()) {
                    textView.append(metro.getDestination() + ": " + metro.getDisplayTime() + "\n");
                }
            }
        });
        departureSearch.init(btn);
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

}
