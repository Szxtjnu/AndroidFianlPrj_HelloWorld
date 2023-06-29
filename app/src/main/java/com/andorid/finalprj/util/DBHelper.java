package com.andorid.finalprj.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_LOGIN_STATUS = "login_status";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_LOGIN_STATUS + " INTEGER DEFAULT 0);";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(String username, String password) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    public boolean isUsernameExist(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USERNAME};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        boolean result = false;
        if (cursor != null && cursor.getCount() > 0) {
            result = true;
        }
        cursor.close();
        db.close();
        return result;
    }

    public boolean login(String username, String password) {
        boolean result = false;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USERNAME, COLUMN_PASSWORD};
        String selection = COLUMN_USERNAME + " = ? and " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            result = true; // 用户名和密码匹配
            updateLoginStatus(username, 1); // 将登录状态更新为已登录
        }

        cursor.close();
        db.close();
        return result;
    }
    public void updateLoginStatus(String username, int loginStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOGIN_STATUS, loginStatus);

        String whereClause = COLUMN_USERNAME + " = ?";
        String[] whereArgs = {username};

        db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
    }

    public boolean isUserLoggedIn() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_LOGIN_STATUS};
        String selection = COLUMN_LOGIN_STATUS + " = ?";
        String[] selectionArgs = {"1"};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        boolean result = false;
        if (cursor != null && cursor.getCount() > 0) {
            result = true;
        }
        cursor.close();
        db.close();
        return result;
    }

    @SuppressLint("Range")
    public String getLoggedInUsername() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USERNAME};
        String selection = COLUMN_LOGIN_STATUS + " = ?";
        String[] selectionArgs = {"1"};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        String username = "";
        if (cursor != null && cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
        }
        cursor.close();
        db.close();
        return username;
    }

}
