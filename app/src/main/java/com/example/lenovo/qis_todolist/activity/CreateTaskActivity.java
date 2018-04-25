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

import java.sql.SQLInput;

public class CreateTaskActivity extends AppCompatActivity {

    protected Cursor cursor;
    PersonalTodoHelper helper;
    Button btnSave, btnCancel;
    MaterialEditText idEdit, titleEdit, descriptionEdit, priorityEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        helper = new PersonalTodoHelper(this);
        btnSave = (Button) findViewById(R.id.button_save_task);
        btnCancel = (Button) findViewById(R.id.button_cancel_task);
        idEdit = (MaterialEditText) findViewById(R.id.id_material_edit_text);
        titleEdit = (MaterialEditText) findViewById(R.id.title_material_edit_text);
        descriptionEdit = (MaterialEditText) findViewById(R.id.description_material_edit_text);
        priorityEdit = (MaterialEditText) findViewById(R.id.priority_material_edit_text);

        //region btnSave.setOnClickListener
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.execSQL("insert into todo(id, title, description, priority) values('"
                        + idEdit.getText().toString() + "','"
                        + titleEdit.getText().toString() + "','"
                        + descriptionEdit.getText().toString() + "','"
                        + priorityEdit.getText().toString() + "')");
                Toast.makeText(CreateTaskActivity.this, "Task has been created", Toast
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
