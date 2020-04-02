package com.tommo.esempiocontentprovider.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.tommo.esempiocontentprovider.R;
import com.tommo.esempiocontentprovider.database.ToDoTableHelper;


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

        TextView creationDateLabel = view.findViewById(R.id.textViewDataIns),
                doneDateLabel = view.findViewById(R.id.textViewDataDone),
                descriptionLabel = view.findViewById(R.id.textViewDescription);
        ImageView checkImage = view.findViewById(R.id.done);

        creationDateLabel.setText(creationDate);
        doneDateLabel.setText(doneDate);
        descriptionLabel.setText(description);
        checkImage.setImageTintList(ColorStateList.valueOf(isDone ? ContextCompat.getColor(context, R.color.colorAccent) : Color.GRAY));
    }

}
