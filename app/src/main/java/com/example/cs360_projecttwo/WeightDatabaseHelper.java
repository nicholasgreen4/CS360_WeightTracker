package com.example.cs360_projecttwo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class WeightDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WeightTracker.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "daily_weights";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_WEIGHT = "weight";

    public WeightDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_WEIGHT + " REAL)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertWeight(String date, float weight) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_WEIGHT, weight);
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    public Cursor getAllWeights() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, COLUMN_DATE + " ASC");
    }

    public boolean updateWeight(int id, float weight) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_WEIGHT, weight);
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    public boolean deleteWeight(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
}
