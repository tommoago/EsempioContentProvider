package com.tommo.esempiocontentprovider.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.tommo.esempiocontentprovider.R;
import com.tommo.esempiocontentprovider.adapters.ToDoAdapter;
import com.tommo.esempiocontentprovider.database.ToDoProvider;

public class ToDoListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int MY_ID = 1;
    ListView list;
    ToDoAdapter adapter;
    Button newToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.listToDo);
        adapter = new ToDoAdapter(this, null);
        list.setAdapter(adapter);
        getSupportLoaderManager().initLoader(MY_ID, null, this);
        newToDo = findViewById(R.id.buttonNewToDo);
        newToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToDoListActivity.this, InsertActivity.class));
            }
        });
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
}
