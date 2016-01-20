package com.filreas.gosthlm;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.filreas.shared.dto.FavouriteSiteLiveUpdateDto;
import com.filreas.shared.utils.GoSthlmLog;

import java.util.ArrayList;
import java.util.List;

public class StationViewPagerAdapter extends PagerAdapter {
    final Context context;
    final List<FavouriteSiteLiveUpdateDto> sites;
    private ISwipeToRefreshEnabler swipeToRefreshEnabler;

    LayoutInflater inflater;
    private DisabledSwipeToRefreshOnCentralPositionChangedListener positionChangedListener;

    public StationViewPagerAdapter(
            Context context,
            List<FavouriteSiteLiveUpdateDto> sites,
            ISwipeToRefreshEnabler swipeToRefreshEnabler) {
        this.context = context;
        this.sites = sites;
        this.swipeToRefreshEnabler = swipeToRefreshEnabler;
        positionChangedListener = new DisabledSwipeToRefreshOnCentralPositionChangedListener();
    }

    @Override
    public int getCount() {
        return sites.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        GoSthlmLog.d("created an item @ " + position);

        inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.viewpager_item, container,
                false);

        FavouriteSiteLiveUpdateDto current = sites.get(position);
        TextView siteName = (TextView) itemView.findViewById(R.id.fromText);
        siteName.setText(current.getName());

        ArrayList<DepartureListItem> departures = DepartureListItemMapper.CreateDepartures(current);
        WearableListView departureListView =
                (WearableListView) itemView.findViewById(R.id.departures_list);
        DepartureListItemAdapter adapter = new DepartureListItemAdapter(context, departures);
        departureListView.setAdapter(adapter);

        container.addView(itemView);

        itemView.setTag(current.getSiteId());

        updateGuiVisibilityDependingOnResult(itemView, current, departures);
        return itemView;
    }

    private void updateGuiVisibilityDependingOnResult(
            View currentPage,
            FavouriteSiteLiveUpdateDto updatedSite,
            ArrayList<DepartureListItem> departures) {
        View errorLayout = currentPage.findViewById(R.id.departure_error_layout);
        View successLayout = currentPage.findViewById(R.id.departure_success_layout);
        WearableListView departureListView = (WearableListView) currentPage.findViewById(R.id.departures_list);

        departureListView.removeOnCentralPositionChangedListener(positionChangedListener);

        String errorMessage = updatedSite.getErrorMessage();

        if (departures.isEmpty() || errorMessage != null) {
            errorLayout.setVisibility(View.VISIBLE);
            successLayout.setVisibility(View.GONE);

            if (errorMessage != null) {
                TextView errorText = (TextView) errorLayout.findViewById(R.id.departures_errorText);
                errorText.setText(errorMessage);
            }
        } else {
            errorLayout.setVisibility(View.GONE);
            successLayout.setVisibility(View.VISIBLE);
            departureListView.addOnCentralPositionChangedListener(positionChangedListener);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void addItem(FavouriteSiteLiveUpdateDto updatedSite) {
        sites.add(updatedSite);
        notifyDataSetChanged();
    }

    public void updateItem(View parent, int index, FavouriteSiteLiveUpdateDto updatedSite) {
        sites.set(index, updatedSite);

        View view = parent.findViewWithTag(updatedSite.getSiteId());
        if (view != null) {
            WearableListView listView =
                    (WearableListView) view.findViewById(R.id.departures_list);
            DepartureListItemAdapter departureListItemAdapter =
                    (DepartureListItemAdapter) listView.getAdapter();
            ArrayList<DepartureListItem> departures = DepartureListItemMapper.CreateDepartures(updatedSite);
            departureListItemAdapter.updateDepartures(departures);
            updateGuiVisibilityDependingOnResult(view, updatedSite, departures);

            notifyDataSetChanged();
        }
    }

    private class DisabledSwipeToRefreshOnCentralPositionChangedListener implements WearableListView.OnCentralPositionChangedListener {
        @Override
        public void onCentralPositionChanged(int i) {
            swipeToRefreshEnabler.onSwipeToRefreshEnabled(i == 0);
        }
    }
}