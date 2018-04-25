package com.example.lenovo.qis_todolist.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.qis_todolist.R;
import com.example.lenovo.qis_todolist.javaclass.PersonalTodoHelper;
import com.rengwuxian.materialedittext.MaterialEditText;

public class UpdateTaskActivity extends AppCompatActivity {

    protected Cursor cursor;
    PersonalTodoHelper helper;
    Button btnUpdate, btnCancel;
    MaterialEditText idUpdate, titleUpdate, descriptionUpdate, priorityUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        helper = new PersonalTodoHelper(this);
        btnUpdate = (Button) findViewById(R.id.button_save_update);
        btnCancel = (Button) findViewById(R.id.button_cancel_update);
        idUpdate = (MaterialEditText) findViewById(R.id.id_update_material_edit_text);
        titleUpdate = (MaterialEditText) findViewById(R.id.title_update_material_edit_text);
        descriptionUpdate = (MaterialEditText) findViewById(R.id
                .description_update_material_edit_text);
        priorityUpdate = (MaterialEditText) findViewById(R.id.priority_update_material_edit_text);

        SQLiteDatabase db = helper.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM todo WHERE title = '" +
                getIntent().getStringExtra("update") + "'", null);

        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            idUpdate.setText(cursor.getString(0));
            titleUpdate.setText(cursor.getString(1));
            descriptionUpdate.setText(cursor.getString(2));
            priorityUpdate.setText(cursor.getString(3));
        }

        //region btnUpdate.setOnClickListener
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.execSQL("update todo set title = '"
                        + titleUpdate.getText().toString() + "', description = '"
                        + descriptionUpdate.getText().toString() + "', priority = '"
                        + priorityUpdate.getText().toString() + "'");
                Toast.makeText(UpdateTaskActivity.this, "Task Updated !", Toast
                        .LENGTH_SHORT)
                        .show();
                PersonalTodoActivity.activity.RefreshList();
                finish();
            }
        });
        //endregion

        //region btnCancel.setOnClickListener
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //endregion
    }
}
