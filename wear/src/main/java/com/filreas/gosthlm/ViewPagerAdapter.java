package com.filreas.gosthlm;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.filreas.shared.dto.FavouriteSiteLiveUpdateDto;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    final Context context;
    final List<FavouriteSiteLiveUpdateDto> sites;

    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, List<FavouriteSiteLiveUpdateDto> sites) {
        this.context = context;
        this.sites = sites;
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
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container,
                false);

        TextView siteName = (TextView) itemView.findViewById(R.id.fromText);

        FavouriteSiteLiveUpdateDto current = sites.get(position);
        siteName.setText(current.getName());

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}