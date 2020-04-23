package com.tommo.esempiocontentprovider.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.tommo.esempiocontentprovider.R;
import com.tommo.esempiocontentprovider.adapters.UserAdapter;
import com.tommo.esempiocontentprovider.database.ToDoDB;
import com.tommo.esempiocontentprovider.database.ToDoProvider;
import com.tommo.esempiocontentprovider.database.UserTableHelper;
import com.tommo.esempiocontentprovider.fragments.ConfirmDialogFragment;
import com.tommo.esempiocontentprovider.fragments.ConfirmDialogFragmentListener;

public class ListUsersActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, ConfirmDialogFragmentListener {
    private static final int ID = 23143;


    ListView list;
    UserAdapter adapter;
    Button newUser;
    public static final String USER_ID = "USER_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

        adapter = new UserAdapter(this, null);

        list = findViewById(R.id.listUsers);
        newUser = findViewById(R.id.buttonNewUser);

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListUsersActivity.this.startActivity(new Intent(ListUsersActivity.this, InsertUserActivity.class));
            }
        });

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListUsersActivity.this, UpdateUserActivity.class);
                intent.putExtra(USER_ID, l);
                ListUsersActivity.this.startActivity(intent);
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) adapter.getItem(position);

                String username = cursor.getString(cursor.getColumnIndex(UserTableHelper.USERNAME));

                ConfirmDialogFragment dialogFragment = new ConfirmDialogFragment("ATTENZIONE", "Sei sicuro di voler cancellare l'utente " + username + " ?", id);
                dialogFragment.show(ListUsersActivity.this.getSupportFragmentManager(), null);
                return true;
            }
        });

        getSupportLoaderManager().initLoader(ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter.notifyDataSetChanged();

//        db = toDoDB.getReadableDatabase();
//        if (db != null) {
//            loadUsers();
//        } else {
//            //TODO show an empty view inside ListView
//            newUser.setEnabled(false);
//            Toast.makeText(this, R.string.database_error, Toast.LENGTH_SHORT).show();
//        }
    }
//
//    private void loadUsers() {
//        users = getContentResolver().query()
//
//
//        users = db.query(UserTableHelper.TABLE_NAME, null, UserTableHelper._ID + " != 0", null,
//                null, null, sortOrder);
//        if (users != null) {
//            if (adapter == null) {
//                adapter = new UserAdapter(this, users);
//                list.setAdapter(adapter);
//            } else {
//                adapter.changeCursor(users);
//                adapter.notifyDataSetChanged();
//            }
//        }
//    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onPositivePressed(long toDoId) {
        getContentResolver().delete(ToDoProvider.USERS_URI, UserTableHelper._ID + "=" + toDoId, null);
        Toast.makeText(this, "Utente Eliminato", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNegativePressed() {
        Toast.makeText(this, "Operazione annullata", Toast.LENGTH_LONG).show();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, ToDoProvider.USERS_URI, null, UserTableHelper._ID + " != " + 0, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.changeCursor(null);
    }
}
