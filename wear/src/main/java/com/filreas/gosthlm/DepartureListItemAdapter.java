package com.filreas.gosthlm;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DepartureListItemAdapter extends WearableListView.Adapter {
    private ArrayList<DepartureListItem> stations;
    private final LayoutInflater mInflater;

    public DepartureListItemAdapter(Context context, ArrayList<DepartureListItem> stations) {
        mInflater = LayoutInflater.from(context);
        this.stations = stations;
    }

    public static class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView destinationText;
        private ImageView icon;
        public ItemViewHolder(View itemView) {
            super(itemView);
            destinationText = (TextView) itemView.findViewById(R.id.departure_destination);
            icon = (ImageView)itemView.findViewById(R.id.departure_icon);
        }
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.station_list_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        TextView destinationText = itemHolder.destinationText;
        destinationText.setText(stations.get(position).getDestination());

        ImageView icon = itemHolder.icon;
        icon.setImageResource(R.drawable.common_ic_googleplayservices); // construct icon dynamic

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }
}