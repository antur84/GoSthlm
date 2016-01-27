package com.filreas.gosthlm.database.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "GoSthlm";
    private static DbHelper instance;

    private final FavouriteSiteHelper favouriteSiteHelper;
    private final TransportationOfChoiceHelper transportationOfChoiceHelper;

    private DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        this.favouriteSiteHelper = new FavouriteSiteHelper(new DbHelperWrapper(context));
        this.transportationOfChoiceHelper = new TransportationOfChoiceHelper(new DbHelperWrapper(context));
    }

    public static synchronized DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.favouriteSiteHelper.onCreate(db);
        this.transportationOfChoiceHelper.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int upgradeTo = oldVersion + 1;
        db.beginTransaction();
        try {
            while (upgradeTo <= newVersion)
            {
                switch (upgradeTo)
                {
                    case 2:
                        favouriteSiteHelper.onUpgrade(db, oldVersion, newVersion);
                        transportationOfChoiceHelper.onUpgrade(db, oldVersion, newVersion);
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
                upgradeTo++;
            }
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }
}
