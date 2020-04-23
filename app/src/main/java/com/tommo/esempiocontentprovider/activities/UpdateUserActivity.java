package com.tommo.esempiocontentprovider.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tommo.esempiocontentprovider.R;
import com.tommo.esempiocontentprovider.database.ToDoDB;
import com.tommo.esempiocontentprovider.database.ToDoProvider;
import com.tommo.esempiocontentprovider.database.UserTableHelper;

public class UpdateUserActivity extends AppCompatActivity {

    EditText updateUsername, updateSurname, updateName;
    SQLiteDatabase db;
    long user_id;
    Button buttonUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        updateName = findViewById(R.id.editTextUpdateName);
        updateSurname = findViewById(R.id.editTextUpdateSuername);
        updateUsername = findViewById(R.id.editTextUpdateUsername);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        user_id = getIntent().getLongExtra(ListUsersActivity.USER_ID, 0);

        Cursor cursor = getContentResolver().query(ToDoProvider.USERS_URI, null, UserTableHelper._ID+ "=" + user_id, null, null);
        cursor.moveToNext();

        updateName.setText(cursor.getString(cursor.getColumnIndex(UserTableHelper.NAME)));
        updateUsername.setText(cursor.getString(cursor.getColumnIndex(UserTableHelper.USERNAME)));
        updateSurname.setText(cursor.getString(cursor.getColumnIndex(UserTableHelper.SURNAME)));

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updateName.getText().toString().equals("") || updateSurname.getText().toString().equals("") || updateUsername.getText().toString().equals("")) {
                    Toast.makeText(UpdateUserActivity.this, "Devi riempire tutti i campi", Toast.LENGTH_SHORT).show();
                } else {
                    UpdateUserActivity.this.doUpdate();
                }
            }
        });
    }

    private void doUpdate() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserTableHelper.NAME, updateName.getText().toString());
        contentValues.put(UserTableHelper.SURNAME, updateSurname.getText().toString());
        contentValues.put(UserTableHelper.USERNAME, updateUsername.getText().toString());

        int result = getContentResolver().update(ToDoProvider.USERS_URI,contentValues,UserTableHelper._ID + " = " + user_id,null);

        if (result > 0) {
            Toast.makeText(UpdateUserActivity.this, "Update Successful", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(UpdateUserActivity.this, "Update ERROR", Toast.LENGTH_LONG).show();
        }

        finish();
    }
}
