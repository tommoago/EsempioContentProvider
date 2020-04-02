package com.tommo.esempiocontentprovider.database;

import android.provider.BaseColumns;

public class ToDoTableHelper implements BaseColumns {

    public static final String TABLE_NAME = "todos";
    public static final String DESCRIPTION = "description";
    public static final String DONE = "done";
    public static final String DATE = "date";
    public static final String DATE_DONE = "datedone";

    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            DESCRIPTION + " TEXT , " +
            DONE + " INTEGER DEFAULT 0 , " +
            DATE + " TEXT , " +
            DATE_DONE + " TEXT ) ;";
}
