package com.filreas.gosthlm.database.helpers;

import android.content.Context;

public class DbHelperWrapper implements IDbHelper {

    private Context context;

    public DbHelperWrapper(Context context) {
        this.context = context;
    }

    @Override
    public DbHelper getDb() {
        return DbHelper.getInstance(context);
    }
}
