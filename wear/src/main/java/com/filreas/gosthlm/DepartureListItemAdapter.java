package com.filreas.gosthlm;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public class DepartureListItemAdapter extends WearableListView.Adapter {
    private ArrayList<DepartureListItem> departures;
    private final LayoutInflater mInflater;

    public DepartureListItemAdapter(Context context, ArrayList<DepartureListItem> departures) {
        mInflater = LayoutInflater.from(context);
        this.departures = departures;
        sort();
    }

    public static class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView destinationText;
        private TextView timeText;
        private ImageView icon;

        public ItemViewHolder(View itemView) {
            super(itemView);
            destinationText = (TextView) itemView.findViewById(R.id.departure_destination);
            timeText = (TextView) itemView.findViewById(R.id.departure_time);
            icon = (ImageView) itemView.findViewById(R.id.departure_icon);
        }
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.station_list_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;

        ImageView icon = itemHolder.icon;
        icon.setImageResource(R.drawable.common_ic_googleplayservices); // todo construct icon dynamic

        TextView destinationText = itemHolder.destinationText;
        destinationText.setText(departures.get(position).getDestination());

        TextView timeText = itemHolder.timeText;
        timeText.setText(departures.get(position).getDepartureTimeText());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return departures.size();
    }

    public void updateDepartures(ArrayList<DepartureListItem> departures) {
        this.departures = departures;
        sort();
        this.notifyDataSetChanged();
    }

    private void sort() {
        ArrayList<DepartureListItem> sorted = new ArrayList<>();
        LinkedHashMap<String, List<DepartureListItem>> arranged = new LinkedHashMap<>();
        for (DepartureListItem departure :
                departures) {
            List<DepartureListItem> departuresForThisDestination = arranged.get(departure.getDestination());

            if (departuresForThisDestination == null) {
                departuresForThisDestination = new ArrayList<>();
                arranged.put(departure.getDestination(), departuresForThisDestination);
            }

            departuresForThisDestination.add(departure);
        }

        while (sorted.size() < departures.size()) {
            for (String key : arranged.keySet()) {
                DepartureListItem item = getFirstItemOrNull(arranged.get(key));
                if (item != null) {
                    sorted.add(item);
                }
            }
        }

        departures = sorted;
    }

    private DepartureListItem getFirstItemOrNull(List<DepartureListItem> list) {
        if (list.isEmpty()) {
            return null;
        }

        return list.remove(0);
    }
}