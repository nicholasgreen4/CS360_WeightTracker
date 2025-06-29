package com.example.cs360_projecttwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userDB.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create goals table
        db.execSQL("CREATE TABLE IF NOT EXISTS goals (" +
                "id INTEGER PRIMARY KEY," +
                "goal_weight REAL" +
                ")");

        // Create other tables if needed, e.g. weights and users
        db.execSQL("CREATE TABLE IF NOT EXISTS weights (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date TEXT," +
                "weight REAL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "password TEXT" +
                ")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS goals");
        db.execSQL("DROP TABLE IF EXISTS weights");
        onCreate(db);
    }


    public boolean registerUser(String username, String password) {
        if (checkUserExists(username)) {
            return false; // Username already exists
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_USERNAME},
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }

    public boolean checkUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_USERNAME},
                COLUMN_USERNAME + "=?", new String[]{username}, null, null, null);
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }
}
