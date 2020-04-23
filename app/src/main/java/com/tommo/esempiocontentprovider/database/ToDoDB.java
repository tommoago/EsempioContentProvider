package com.tommo.esempiocontentprovider.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ToDoDB extends SQLiteOpenHelper {

    public static final String DB_NAME = "todo.db";
    public static final int VERSION = 1;

    public ToDoDB(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ToDoTableHelper.CREATE);
        db.execSQL(UserTableHelper.CREATE);
        ContentValues vValues = new ContentValues();
        vValues.put(UserTableHelper._ID, 0);
        vValues.put(UserTableHelper.NAME, "");
        vValues.put(UserTableHelper.SURNAME, "NESSUN UTENTE");
        vValues.put(UserTableHelper.USERNAME, "");
        db.insert(UserTableHelper.TABLE_NAME, null, vValues);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
