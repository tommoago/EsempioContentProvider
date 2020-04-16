package com.tommo.esempiocontentprovider.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tommo.esempiocontentprovider.R;
import com.tommo.esempiocontentprovider.database.ToDoProvider;
import com.tommo.esempiocontentprovider.database.ToDoTableHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InsertActivity extends AppCompatActivity {

    TextView dateLabel;
    EditText descriptionInput;
    Button saveButton;
    Spinner spinnerUsers;
    long id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        setTitle("aggiungi attività");

        dateLabel = findViewById(R.id.creationDate);
        descriptionInput = findViewById(R.id.descriptionInput);
        saveButton = findViewById(R.id.saveButton);
        spinnerUsers = findViewById(R.id.spinnerUsers);
        popolateSpinnerUsers();
        spinnerUsers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InsertActivity.this.id = id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Date today = new Date();
        String todayFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(today);

        dateLabel.setText(todayFormat);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertToDo();
            }
        });
    }

    private void popolateSpinnerUsers() {
//        ToDoDB db = new ToDoDB(this);
//        SQLiteDatabase toDoDatabase = db.getReadableDatabase();
//        Cursor cursor=toDoDatabase.query(UserTableHelper.TABLE_NAME,null,null,null,null,null,null);
//
//        UserAdapter adapter=new UserAdapter(this,cursor);
//        spinnerUsers.setAdapter(adapter);

    }

    private void insertToDo() {

        ContentValues values=new ContentValues();
        values.put(ToDoTableHelper.DATE, dateLabel.getText().toString());
        values.put(ToDoTableHelper.DESCRIPTION, descriptionInput.getText().toString());

        getContentResolver().insert(ToDoProvider.TODOS_URI, values);


    }

}
