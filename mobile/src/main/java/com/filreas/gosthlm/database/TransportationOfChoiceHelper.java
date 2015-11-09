package com.filreas.gosthlm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.filreas.gosthlm.database.model.TransportationOfChoice;
import com.filreas.shared.utils.GoSthlmLog;

public class TransportationOfChoiceHelper extends SQLiteOpenHelper implements ITransportationOfChoice {

    private static final String TABLE_DEFAULT_TRANSPORTATION_OF_CHOICE
            = "defaultTransportationOfChoice";
    private static final String KEY_ID = "id";
    private static final String KEY_METRO = "metro";
    private static final String KEY_BUS = "bus";
    private static final String KEY_TRAIN = "train";
    private static final String KEY_TRAM = "tram";

    public TransportationOfChoiceHelper(Context context, String databaseName, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEFAULT_TRANSPORTATION_OF_CHOICE);
        onCreate(db);
    }

    @Override
    public void insertTransportationOfChoice(TransportationOfChoice transportationOfChoice) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = createTransportationOfChoiceContentValues(transportationOfChoice);

        db.insert(TABLE_DEFAULT_TRANSPORTATION_OF_CHOICE, null, values);
        db.close();

        GoSthlmLog.d("insertTransportationOfChoice", values.toString());
    }

    @Override
    public void updateTransportationOfChoice(TransportationOfChoice transportationOfChoice) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = createTransportationOfChoiceContentValues(transportationOfChoice);

        String[] args = new String[]{"1"};
        db.update(TABLE_DEFAULT_TRANSPORTATION_OF_CHOICE, values, "Id=?", args);
        db.close();

        GoSthlmLog.d("updateTransportationOfChoice", values.toString());
    }

    @Override
    public TransportationOfChoice getTransportationOfChoice() {
        TransportationOfChoice transportationOfChoice = null;
        String query = "SELECT * FROM " + TABLE_DEFAULT_TRANSPORTATION_OF_CHOICE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            transportationOfChoice = new TransportationOfChoice();
            transportationOfChoice.setMetro(cursor.getInt(1) == 1);
            transportationOfChoice.setBus(cursor.getInt(2) == 1);
            transportationOfChoice.setTrain(cursor.getInt(3) == 1);
            transportationOfChoice.setTram(cursor.getInt(4) == 1);
            GoSthlmLog.d("getTransportationOfChoice", transportationOfChoice.toString());
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
