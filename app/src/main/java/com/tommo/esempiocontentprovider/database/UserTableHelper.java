package com.tommo.esempiocontentprovider.database;

import android.provider.BaseColumns;

public class UserTableHelper implements BaseColumns {
    public static final String TABLE_NAME = "users";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String USERNAME = "username";


    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            NAME + " TEXT , " +
            USERNAME + " TEXT , " +
            SURNAME + " TEXT ) ;";
}