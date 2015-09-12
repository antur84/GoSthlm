package com.filreas.slwear.activities.Main;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

/**
 * @author Filip G
 */

public class StationSpinnerItemSelectedListener implements OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        Toast.makeText(parent.getContext(),
                "Selected station: " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
