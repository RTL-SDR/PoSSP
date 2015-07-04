package org.bfr.periodicquery.sdr.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by amjed on 20/02/15.
 */

/**
 * This class extends SQLiteOpenHelper that will give me the ability to
 * create database update it and open a connection to it and close it
 */
public class LocationDbOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "DATABASE_TAG";

    private static final String DB_NAME = "LocationDb";
    private static final int DB_VERSION = 4;
    public static final String TABLE_LOCATION = "LocationT";
    public static final String COL_ID = "id";
    public static final String COL_LAT = "lat";
    public static final String COL_LON = "lon";
    public static final String COL_TIME = "time";




    public static final String CREATE_TABLE ="CREATE TABLE " + TABLE_LOCATION +" ( "+
             COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_LAT + " DOUBLE, " +
            COL_LON + " DOUBLE, " +
            COL_TIME + " DOUBLE " +
            ")";



    public LocationDbOpenHelper(Context context) {
        super(context, DB_NAME, null , DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

        Log.i(TAG , "Database is created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        onCreate(db);
        Log.i(TAG , "Database is upgraded");
    }
}
