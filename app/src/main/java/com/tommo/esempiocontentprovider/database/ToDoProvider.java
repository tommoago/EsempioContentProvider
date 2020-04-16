package com.tommo.esempiocontentprovider.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ToDoProvider extends ContentProvider {

    public static final String AUTORITY = "com.tommo.esempiocontentprovider.database.ContentProvider";

    public static final String BASE_PATH_TODOS = "todos";
    public static final String BASE_PATH_USERS = "users";

    public static final int ALL_TODO = 1;
    public static final int SINGLE_TODO = 0;
    public static final int ALL_USER = 2;
    public static final int SINGLE_USER = 3;

    public static final String MIME_TYPE_TODOS = ContentResolver.CURSOR_DIR_BASE_TYPE + "vnd.all_todos";
    public static final String MIME_TYPE_TODO = ContentResolver.CURSOR_ITEM_BASE_TYPE + "vnd.single_todo";
    public static final String MIME_TYPE_USERS = ContentResolver.CURSOR_DIR_BASE_TYPE + "vnd.all_users";
    public static final String MIME_TYPE_USER = ContentResolver.CURSOR_ITEM_BASE_TYPE + "vnd.single_user";

    public static final Uri TODOS_URI = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTORITY
            + "/" + BASE_PATH_TODOS);
    public static final Uri USERS_URI = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTORITY
            + "/" + BASE_PATH_USERS);


    private ToDoDB database;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTORITY, BASE_PATH_TODOS, ALL_TODO);
        uriMatcher.addURI(AUTORITY, BASE_PATH_TODOS + "/#", SINGLE_TODO);
        uriMatcher.addURI(AUTORITY, BASE_PATH_USERS, ALL_USER);
        uriMatcher.addURI(AUTORITY, BASE_PATH_USERS + "/#", SINGLE_USER);
    }


    @Override
    public boolean onCreate() {
        database = new ToDoDB(getContext());
        return true;

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = database.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)) {
            case SINGLE_TODO:
                builder.setTables(ToDoTableHelper.TABLE_NAME);
                builder.appendWhere(ToDoTableHelper._ID + " = " + uri.getLastPathSegment());
                break;
            case ALL_TODO:
                builder.setTables(ToDoTableHelper.TABLE_NAME);
                break;
            case SINGLE_USER:
                builder.setTables(UserTableHelper.TABLE_NAME);
                builder.appendWhere(UserTableHelper._ID + " = " + uri.getLastPathSegment());
                break;
            case ALL_USER:
                builder.setTables(UserTableHelper.TABLE_NAME);
                break;
        }
        Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case SINGLE_TODO:
                return MIME_TYPE_TODO;
            case ALL_TODO:
                return MIME_TYPE_TODOS;
            case SINGLE_USER:
                return MIME_TYPE_USER;
            case ALL_USER:
                return MIME_TYPE_USERS;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (uriMatcher.match(uri) == ALL_TODO) {
            SQLiteDatabase db = database.getWritableDatabase();
            long result = db.insert(ToDoTableHelper.TABLE_NAME, null, values);
            String resultString = ContentResolver.SCHEME_CONTENT + "://" + BASE_PATH_TODOS + "/" + result;
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse(resultString);
        } else if (uriMatcher.match(uri) == ALL_USER) {
            SQLiteDatabase db = database.getWritableDatabase();
            long result = db.insert(UserTableHelper.TABLE_NAME, null, values);
            String resultString = ContentResolver.SCHEME_CONTENT + "://" + BASE_PATH_USERS + "/" + result;
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse(resultString);
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String table = "", query = "";
        SQLiteDatabase db = database.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case SINGLE_TODO:
                table = ToDoTableHelper.TABLE_NAME;
                query = ToDoTableHelper._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;
            case ALL_TODO:
                table = ToDoTableHelper.TABLE_NAME;
                query = selection;
                break;
            case SINGLE_USER:
                table = UserTableHelper.TABLE_NAME;
                query = UserTableHelper._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;
            case ALL_USER:
                table = UserTableHelper.TABLE_NAME;
                query = selection;
                break;
        }
        int deletedRows = db.delete(table, query, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return deletedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String table = "", query = "";
        SQLiteDatabase db = database.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case SINGLE_TODO:
                table = ToDoTableHelper.TABLE_NAME;
                query = ToDoTableHelper._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;
            case ALL_TODO:
                table = ToDoTableHelper.TABLE_NAME;
                query = selection;
                break;
            case SINGLE_USER:
                table = UserTableHelper.TABLE_NAME;
                query = UserTableHelper._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;
            case ALL_USER:
                table = UserTableHelper.TABLE_NAME;
                query = selection;
                break;
        }
        int updatedRows = db.update(table, values, query, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return updatedRows;
    }
}
