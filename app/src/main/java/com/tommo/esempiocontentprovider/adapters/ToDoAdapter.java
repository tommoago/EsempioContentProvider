package com.tommo.esempiocontentprovider.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.tommo.esempiocontentprovider.R;
import com.tommo.esempiocontentprovider.database.ToDoDB;
import com.tommo.esempiocontentprovider.database.ToDoProvider;
import com.tommo.esempiocontentprovider.database.ToDoTableHelper;
import com.tommo.esempiocontentprovider.database.UserTableHelper;


public class ToDoAdapter extends CursorAdapter {

    public ToDoAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater vInflater = LayoutInflater.from(context);
        View vView = vInflater.inflate(R.layout.cell_todo, null);
        return vView;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        String creationDate = cursor.getString(cursor.getColumnIndex(ToDoTableHelper.DATE));
        String doneDate = cursor.getString(cursor.getColumnIndex(ToDoTableHelper.DATE_DONE));
        String description = cursor.getString(cursor.getColumnIndex(ToDoTableHelper.DESCRIPTION));
        boolean isDone = cursor.getInt(cursor.getColumnIndex(ToDoTableHelper.DONE)) == 1;
        int id = cursor.getInt(cursor.getColumnIndex(ToDoTableHelper.ID_USER));
        Log.d("asda", "bindView: " + id);

        String username = "nessun utente";
        if (id != 0) {

            Cursor datas = context.getContentResolver().query(ToDoProvider.USERS_URI, new String[]{UserTableHelper.USERNAME,
                            UserTableHelper._ID}, UserTableHelper._ID + " = " + id,
                    null, null);
            datas.moveToNext();
            if (datas.getCount() >= 1) {
                username = datas.getString(datas.getColumnIndex(UserTableHelper.USERNAME));
            }else{
                username="utente cancellato";
            }
            datas.close();

        }
        TextView creationDateLabel = view.findViewById(R.id.textViewDataIns),
                doneDateLabel = view.findViewById(R.id.textViewDataDone),
                descriptionLabel = view.findViewById(R.id.textViewDescription),
                usernameLabel = view.findViewById(R.id.textViewUsername);
        ImageView checkImage = view.findViewById(R.id.done);

        usernameLabel.setText(username);
        creationDateLabel.setText(creationDate);
        doneDateLabel.setText(doneDate);
        descriptionLabel.setText(description);
        checkImage.setImageTintList(ColorStateList.valueOf(isDone ? ContextCompat.getColor(context, R.color.colorAccent) : Color.GRAY));
    }

}
