package com.filreas.gosthlm.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.filreas.gosthlm.database.model.FavouriteSite;
import com.filreas.shared.utils.GoSthlmLog;

import java.util.ArrayList;
import java.util.List;

public class SiteHelper implements IFavouriteSite {

    private static final String TABLE_FAVOURITE_SITE = "favouriteSite";
    private static final String KEY_ID = "id";
    private static final String KEY_SITEID = "siteId";
    private static final String KEY_TYPE = "type";
    private static final String KEY_X = "x";
    private static final String KEY_Y = "y";
    private static final String KEY_NAME = "name";
    private final IDbHelper dbHelper;

    public SiteHelper(IDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_DEFAULT_TRANSPORTATION_TABLE = "CREATE TABLE " + TABLE_FAVOURITE_SITE
                + " ( " + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_SITEID + " INTEGER,"
                + KEY_TYPE + " TEXT,"
                + KEY_X + " TEXT,"
                + KEY_Y + " TEXT,"
                + KEY_NAME + " TEXT);";

        GoSthlmLog.d("onCreate: " + CREATE_DEFAULT_TRANSPORTATION_TABLE);
        db.execSQL(CREATE_DEFAULT_TRANSPORTATION_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITE_SITE);
        onCreate(db);
    }

    public void create(FavouriteSite favouriteSite) {
        SQLiteDatabase db = dbHelper.getDb().getWritableDatabase();

        ContentValues values = createFavouriteSiteContentValues(favouriteSite);

        db.insert(TABLE_FAVOURITE_SITE, null, values);
        db.close();

        GoSthlmLog.d("create", values.toString());
    }

    public void update(FavouriteSite favouriteSite) {
        SQLiteDatabase db = dbHelper.getDb().getWritableDatabase();

        ContentValues values = createFavouriteSiteContentValues(favouriteSite);

        String[] args = new String[]{"" + favouriteSite.getId()};
        db.update(TABLE_FAVOURITE_SITE, values, "id=?", args);
        db.close();

        GoSthlmLog.d("update", values.toString());
    }

    @Override
    public FavouriteSite read() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(FavouriteSite item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<FavouriteSite> readAll() {
        String query = "SELECT * FROM " + TABLE_FAVOURITE_SITE;

        ArrayList<FavouriteSite> result = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getDb().getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                int siteId = cursor.getInt(cursor.getColumnIndex(KEY_SITEID));
                String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                String type = cursor.getString(cursor.getColumnIndex(KEY_TYPE));
                String x = cursor.getString(cursor.getColumnIndex(KEY_X));
                String y = cursor.getString(cursor.getColumnIndex(KEY_Y));

                result.add(new FavouriteSite(id, name, siteId, type, x, y));
                cursor.moveToNext();
            }
        }
        cursor.close();

        return result;
    }

    @NonNull
    private ContentValues createFavouriteSiteContentValues(FavouriteSite favouriteSite) {
        ContentValues values = new ContentValues();

        values.put(KEY_ID, favouriteSite.getId());
        values.put(KEY_SITEID, favouriteSite.getSiteId());
        values.put(KEY_NAME, favouriteSite.getName());
        values.put(KEY_TYPE, favouriteSite.getType());
        values.put(KEY_X, favouriteSite.getX());
        values.put(KEY_Y, favouriteSite.getY());
        return values;
    }
}
