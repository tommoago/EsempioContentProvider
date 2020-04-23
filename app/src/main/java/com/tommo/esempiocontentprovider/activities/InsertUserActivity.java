package com.tommo.esempiocontentprovider.activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tommo.esempiocontentprovider.R;
import com.tommo.esempiocontentprovider.database.ToDoProvider;
import com.tommo.esempiocontentprovider.database.UserTableHelper;

public class InsertUserActivity extends AppCompatActivity {
    EditText name, surname, username;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_user);

        name = findViewById(R.id.editTextName);
        surname = findViewById(R.id.editTextSuername);
        username = findViewById(R.id.editTextUsername);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("") || surname.getText().toString().equals("") || username.getText().toString().equals("")) {
                    Toast.makeText(InsertUserActivity.this, "Devi riempire tutti i campi", Toast.LENGTH_SHORT).show();
                } else {
                    InsertUserActivity.this.doInsert();
                    InsertUserActivity.this.finish();
                }
            }
        });
    }

    private void doInsert() {
        ContentValues values = new ContentValues();
        values.put(UserTableHelper.NAME, name.getText().toString());
        values.put(UserTableHelper.SURNAME, surname.getText().toString());
        values.put(UserTableHelper.USERNAME, username.getText().toString());

        getContentResolver().insert(ToDoProvider.USERS_URI,values);
    }
}
