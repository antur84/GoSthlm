package com.filreas.slwear.activities.Main;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.filreas.slwear.R;
import com.filreas.slwear.slapi.operations.location_finder.contract.request.response.Site;

import java.util.ArrayList;

public class StationsAdapter extends ArrayAdapter<Site> implements Filterable {

    private ArrayList<Site> siteDataSource;
    private Context context;
    private int resource;
    private ArrayList<OnStationClickListener> onClickListeners;

    public StationsAdapter(Context context, @LayoutRes int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        siteDataSource = new ArrayList<>();
        onClickListeners = new ArrayList<>();
    }

    @Override
    public void add(Site object) {
        siteDataSource.add(object);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return siteDataSource.size();
    }

    @Override
    public Site getItem(int index) {
        return siteDataSource.get(index);
    }

    @Override
    public void clear() {
        siteDataSource.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        StationGuiItem holder;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(resource, parent, false);

            holder = new StationGuiItem();
            holder.imgIcon = (ImageView) row.findViewById(R.id.stationIcon);
            holder.txtTitle = (TextView) row.findViewById(R.id.stationText);

            row.setTag(holder);
        } else {
            holder = (StationGuiItem) row.getTag();
        }

        Site station = this.getItem(position);
        holder.txtTitle.setText(station.getName());
        holder.imgIcon.setImageResource(R.drawable.ic_launcher);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (OnStationClickListener listener :
                        onClickListeners) {
                    listener.onClick(getItem(position));
                }
            }
        });

        return row;
    }

    public void setOnClickListener(OnStationClickListener listener) {
        this.onClickListeners.add(listener);
    }

    static class StationGuiItem {
        ImageView imgIcon;
        TextView txtTitle;
    }
}
