package com.filreas.gosthlm;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.Collections;

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
        private ImageView icon;

        public ItemViewHolder(View itemView) {
            super(itemView);
            destinationText = (TextView) itemView.findViewById(R.id.departure_destination);
            //icon = (ImageView) itemView.findViewById(R.id.departure_icon);
        }
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.station_list_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;

        //ImageView icon = itemHolder.icon;
        //icon.setImageResource(R.drawable.common_ic_googleplayservices); // todo construct icon dynamic

        DepartureListItem item = departures.get(position);
        String text = item.getLineNumberText() + " "
                + item.getDestination() + " "
                + item.getDepartureTimeText();

        TextView destinationText = itemHolder.destinationText;
        destinationText.setText(text);

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
        Collections.sort(departures, new DepartureListItemByDepartureTimeComparator(LocalTime.now()));
    }
}