package com.tommo.esempiocontentprovider.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.tommo.esempiocontentprovider.R;
import com.tommo.esempiocontentprovider.database.UserTableHelper;

public class UserAdapter extends CursorAdapter {

    public UserAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater vInflater = LayoutInflater.from(context);
        View vView = vInflater.inflate(R.layout.cell_user, null);
        return vView;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex(UserTableHelper.NAME));
        String surname = cursor.getString(cursor.getColumnIndex(UserTableHelper.SURNAME));
        String username = cursor.getString(cursor.getColumnIndex(UserTableHelper.USERNAME));

        TextView nameLaber = view.findViewById(R.id.textViewName),
                surnameLabel = view.findViewById(R.id.textViewSurname),
                usernameLabel = view.findViewById(R.id.textViewUsername);


        nameLaber.setText(name);
        surnameLabel.setText(surname);
        usernameLabel.setText(username);

    }

}
