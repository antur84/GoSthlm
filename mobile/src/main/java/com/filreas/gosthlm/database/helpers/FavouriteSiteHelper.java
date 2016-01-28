package com.filreas.gosthlm.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.filreas.gosthlm.database.model.FavouriteSite;
import com.filreas.shared.utils.GoSthlmLog;

import java.util.ArrayList;
import java.util.List;

public class FavouriteSiteHelper implements IFavouriteSiteDbHelper {

    private static final String TABLE_FAVOURITE_SITE = "favouriteSite";
    private static final String KEY_ID = "id";
    private static final String KEY_SITEID = "siteId";
    private static final String KEY_TYPE = "type";
    private static final String KEY_X = "x";
    private static final String KEY_Y = "y";
    private static final String KEY_NAME = "name";
    private static final String KEY_SORT_ORDER = "sortorder";
    private final IDbHelper dbHelper;

    public FavouriteSiteHelper(IDbHelper dbHelper) {
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
        switch (newVersion) {
            case 2:
                addSortOrderColumn(db);
                break;
        }
    }

    private void addSortOrderColumn(SQLiteDatabase db) {
        String addColumn = "ALTER TABLE " + TABLE_FAVOURITE_SITE + " ADD COLUMN "
                + KEY_SORT_ORDER + " INTEGER;";

        db.execSQL(addColumn);

        String migrateData = "UPDATE " + TABLE_FAVOURITE_SITE + " SET " + KEY_SORT_ORDER + " = 0;";
        db.execSQL(migrateData);
    }

    @Override
    public void create(FavouriteSite favouriteSite) {
        SQLiteDatabase db = dbHelper.getDb().getWritableDatabase();

        ContentValues values = createFavouriteSiteContentValues(favouriteSite);

        db.insert(TABLE_FAVOURITE_SITE, null, values);
        db.close();

        GoSthlmLog.d("create", values.toString());
    }

    @Override
    public void update(FavouriteSite favouriteSite) {
        SQLiteDatabase db = dbHelper.getDb().getWritableDatabase();

        ContentValues values = createFavouriteSiteContentValues(favouriteSite);

        String[] args = new String[]{Integer.toString(favouriteSite.getId())};
        db.update(TABLE_FAVOURITE_SITE, values, "id=?", args);
        db.close();

        GoSthlmLog.d("update", values.toString());
    }

    @Override
    public void remove(FavouriteSite favouriteSite) {
        SQLiteDatabase db = dbHelper.getDb().getWritableDatabase();

        ContentValues values = createFavouriteSiteContentValues(favouriteSite);

        String[] args = new String[]{Integer.toString(favouriteSite.getId())};
        db.delete(TABLE_FAVOURITE_SITE, "id=?", args);
        db.close();

        GoSthlmLog.d("remove", values.toString());
    }

    @Override
    public List<FavouriteSite> readAll() {
        String query = "SELECT * FROM " + TABLE_FAVOURITE_SITE;

        ArrayList<FavouriteSite> result = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getDb().getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                result.add(createFavouriteSiteFromCursor(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();

        return result;
    }

    @Override
    public FavouriteSite getBySiteId(int id) {
        FavouriteSite result = null;

        SQLiteDatabase db = dbHelper.getDb().getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_FAVOURITE_SITE + " WHERE " + KEY_SITEID + " = ?";
        String[] args = new String[]{Integer.toString(id)};
        Cursor cursor = db.rawQuery(query, args);

        if (cursor.moveToFirst()) {
            result = createFavouriteSiteFromCursor(cursor);
        }
        cursor.close();

        return result;
    }

    @NonNull
    private ContentValues createFavouriteSiteContentValues(FavouriteSite favouriteSite) {
        ContentValues values = new ContentValues();

        values.put(KEY_SITEID, favouriteSite.getSiteId());
        values.put(KEY_NAME, favouriteSite.getName());
        values.put(KEY_TYPE, favouriteSite.getType());
        values.put(KEY_X, favouriteSite.getX());
        values.put(KEY_Y, favouriteSite.getY());
        values.put(KEY_SORT_ORDER, favouriteSite.getSortPosition());
        return values;
    }

    private FavouriteSite createFavouriteSiteFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
        int siteId = cursor.getInt(cursor.getColumnIndex(KEY_SITEID));
        String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
        String type = cursor.getString(cursor.getColumnIndex(KEY_TYPE));
        String x = cursor.getString(cursor.getColumnIndex(KEY_X));
        String y = cursor.getString(cursor.getColumnIndex(KEY_Y));
        int sortPosition = cursor.getInt(cursor.getColumnIndex(KEY_SORT_ORDER));
        return new FavouriteSite(id, name, siteId, type, x, y, sortPosition);
    }
}
