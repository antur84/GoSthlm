package com.filreas.gosthlm.activities;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.filreas.gosthlm.R;

/**
 * @author Filip G
 */

public class About extends MobileBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableHomeAsUpNavigation();
        TextView github = (TextView) findViewById(R.id.githubLink);
        github.setText(
                Html.fromHtml(
                        "<a href=\"https://github.com/antur84/GoSthlm\">GoSthlm @ GitHub</a>"));
        github.setMovementMethod(LinkMovementMethod.getInstance());

        TextView trafiklab = (TextView) findViewById(R.id.trafiklabLink);
        trafiklab.setText(
                Html.fromHtml(
                        "<a href=\"https://www.trafiklab.se/\">Trafiklab.se</a>"));
        trafiklab.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_about;
    }
}
