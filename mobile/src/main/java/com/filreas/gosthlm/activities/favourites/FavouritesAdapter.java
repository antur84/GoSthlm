package com.filreas.gosthlm.activities.favourites;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.filreas.gosthlm.R;
import com.filreas.gosthlm.database.model.FavouriteSite;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouriteItemViewHolder> {
    private final List<FavouriteSite> favouriteSites;

    @Override
    public FavouriteItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourites_adapter_item, parent, false);
        return new FavouriteItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FavouriteItemViewHolder holder, int position) {
        holder.name.setText(favouriteSites.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return favouriteSites.size();
    }

    public static class FavouriteItemViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;

        public FavouriteItemViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.favourite_name);
        }

        public void onItemSelected() {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.primary_light));
            itemView.setTranslationZ(10);
        }

        public void onItemClear() {
            itemView.setTranslationZ(0);
            itemView.setBackgroundColor(0);
        }
    }

    public FavouritesAdapter(List<FavouriteSite> favouriteSites) {
        this.favouriteSites = favouriteSites;
    }
}
