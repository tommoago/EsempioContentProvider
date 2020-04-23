package com.tommo.esempiocontentprovider.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.tommo.esempiocontentprovider.R;
import com.tommo.esempiocontentprovider.database.ToDoProvider;
import com.tommo.esempiocontentprovider.adapters.ToDoAdapter;
import com.tommo.esempiocontentprovider.database.ToDoTableHelper;
import com.tommo.esempiocontentprovider.fragments.ConfirmDialogFragment;
import com.tommo.esempiocontentprovider.fragments.ConfirmDialogFragmentListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDoListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, ConfirmDialogFragmentListener {
    private static final int MY_ID = 1;
    ListView list;
    ToDoAdapter adapter;
    Button newToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.listViewToDo);
        adapter = new ToDoAdapter(this, null);
        list.setAdapter(adapter);

        getSupportLoaderManager().initLoader(MY_ID, null, this);

        newToDo = findViewById(R.id.buttonNew);
        newToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToDoListActivity.this, InsertActivity.class));
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateToDo(position);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                ConfirmDialogFragment dialogFragment = new ConfirmDialogFragment(
                        getString(R.string.delete_todo_title),
                        getString(R.string.delete_todo_message),
                        id
                );
                dialogFragment.show(fragmentManager, ConfirmDialogFragment.class.getName());
                return true;
            }
        });

        findViewById(R.id.buttonListUsers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoListActivity.this.startActivity(new Intent(ToDoListActivity.this, ListUsersActivity.class));
            }
        });
    }

    private void updateToDo(int position) {
        if (adapter != null) {
            Cursor toDoCursor = (Cursor) adapter.getItem(position);

            if (toDoCursor != null) {
                boolean isDone = toDoCursor.getInt(toDoCursor.getColumnIndex(ToDoTableHelper.DONE)) == 1;

                if (isDone) {
                    Toast.makeText(this, R.string.todo_already_done, Toast.LENGTH_SHORT).show();
                } else {
                    int id = toDoCursor.getInt(toDoCursor.getColumnIndex(ToDoTableHelper._ID));
                    String creationDate = toDoCursor.getString(toDoCursor.getColumnIndex(ToDoTableHelper.DATE));
                    String description = toDoCursor.getString(toDoCursor.getColumnIndex(ToDoTableHelper.DESCRIPTION));

                    ContentValues values = new ContentValues();
                    values.put(ToDoTableHelper.DATE, creationDate);
                    values.put(ToDoTableHelper.DESCRIPTION, description);
                    values.put(ToDoTableHelper.DATE_DONE, new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                    values.put(ToDoTableHelper.DONE, 1);

                    getContentResolver().update(ToDoProvider.TODOS_URI,values,ToDoTableHelper._ID + "=" + id,null);
                    Toast.makeText(this, R.string.todo_update_success, Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, ToDoProvider.TODOS_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.changeCursor(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.changeCursor(null);

    }

    @Override
    public void onPositivePressed(long toDoId) {
        getContentResolver().delete(ToDoProvider.TODOS_URI, ToDoTableHelper._ID + "=" + toDoId, null);
    }

    @Override
    public void onNegativePressed() {

    }
}
