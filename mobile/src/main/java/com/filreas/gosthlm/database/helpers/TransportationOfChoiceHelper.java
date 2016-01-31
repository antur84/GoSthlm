package com.filreas.gosthlm.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.filreas.gosthlm.database.model.TransportationOfChoice;
import com.filreas.shared.utils.GoSthlmLog;

public class TransportationOfChoiceHelper implements ITransportationOfChoiceDbHelper {

    private static final String TABLE_DEFAULT_TRANSPORTATION_OF_CHOICE
            = "defaultTransportationOfChoice";
    private static final String KEY_ID = "id";
    private static final String KEY_METRO = "metro";
    private static final String KEY_BUS = "bus";
    private static final String KEY_TRAIN = "train";
    private static final String KEY_TRAM = "tram";
    private final IDbHelper dbHelper;

    public TransportationOfChoiceHelper(IDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_DEFAULT_TRANSPORTATION_TABLE = "CREATE TABLE " + TABLE_DEFAULT_TRANSPORTATION_OF_CHOICE
                + " ( " + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_METRO + " INTEGER CHECK (" + KEY_METRO + " IN (0,1)), "
                + KEY_BUS + " INTEGER CHECK (" + KEY_BUS + " IN (0,1)), "
                + KEY_TRAIN + " INTEGER CHECK (" + KEY_TRAIN + " IN (0,1)), "
                + KEY_TRAM + " INTEGER CHECK (" + KEY_TRAM + " IN (0,1)));";

        GoSthlmLog.d("onCreate: " + CREATE_DEFAULT_TRANSPORTATION_TABLE);
        db.execSQL(CREATE_DEFAULT_TRANSPORTATION_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void create(TransportationOfChoice transportationOfChoice) {
        SQLiteDatabase db = dbHelper.getDb().getWritableDatabase();

        ContentValues values = createTransportationOfChoiceContentValues(transportationOfChoice);

        db.insert(TABLE_DEFAULT_TRANSPORTATION_OF_CHOICE, null, values);
        db.close();

        GoSthlmLog.d("db create " + values.toString());
    }

    @Override
    public void update(TransportationOfChoice transportationOfChoice) {
        SQLiteDatabase db = dbHelper.getDb().getWritableDatabase();

        ContentValues values = createTransportationOfChoiceContentValues(transportationOfChoice);

        String[] args = new String[]{"1"};
        db.update(TABLE_DEFAULT_TRANSPORTATION_OF_CHOICE, values, "id=?", args);
        db.close();

        GoSthlmLog.d("db update " + values.toString());
    }

    @Override
    public TransportationOfChoice read() {
        TransportationOfChoice transportationOfChoice = null;
        String query = "SELECT * FROM " + TABLE_DEFAULT_TRANSPORTATION_OF_CHOICE;

        SQLiteDatabase db = dbHelper.getDb().getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            transportationOfChoice = new TransportationOfChoice();
            transportationOfChoice.setMetro(cursor.getInt(1) == 1);
            transportationOfChoice.setBus(cursor.getInt(2) == 1);
            transportationOfChoice.setTrain(cursor.getInt(3) == 1);
            transportationOfChoice.setTram(cursor.getInt(4) == 1);
        }
        cursor.close();

        return transportationOfChoice;
    }

    @NonNull
    private ContentValues createTransportationOfChoiceContentValues(TransportationOfChoice transportationOfChoice) {
        ContentValues values = new ContentValues();

        values.put(KEY_METRO, transportationOfChoice.isMetro() ? 1 : 0);
        values.put(KEY_BUS, transportationOfChoice.isBus() ? 1 : 0);
        values.put(KEY_TRAIN, transportationOfChoice.isTrain() ? 1 : 0);
        values.put(KEY_TRAM, transportationOfChoice.isTram() ? 1 : 0);
        return values;
    }
}
