package com.example.lenovo.qis_todolist.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.qis_todolist.R;
import com.example.lenovo.qis_todolist.javaclass.PersonalTodoHelper;

public class ReadTaskActivity extends AppCompatActivity {

    protected Cursor cursor;
    PersonalTodoHelper helper;
    Button btnBack;
    TextView titleRead, descriptionRead, priorityRead, PK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_task);

        helper = new PersonalTodoHelper(this);
        btnBack = (Button) findViewById(R.id.button_back_task);
        PK = (TextView) findViewById(R.id.PK);
        titleRead = (TextView) findViewById(R.id.titleRead);
        descriptionRead = (TextView) findViewById(R.id.descriptionRead);
        priorityRead = (TextView) findViewById(R.id.priorityRead);
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM todo WHERE title = '" + getIntent().getStringExtra
                ("read") + "'", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            PK.setText(cursor.getString(0));
            titleRead.setText(cursor.getString(1));
            descriptionRead.setText(cursor.getString(2));
            priorityRead.setText(cursor.getString(3));
        }

        //region btnBack.setOnClickListener
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //endregion
    }
}
