package com.example.lenovo.qis_todolist.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lenovo.qis_todolist.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OfflineCreateTaskActivity extends AppCompatActivity{

    public static final String DATABASE_NAME = "todoOfflineDb";

    Button btnAdd, btnCancel;
    MaterialEditText createTitle, createDescription;
    Spinner createPriority;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        btnAdd = (Button) findViewById(R.id.btn_save_new_task);
        btnCancel = (Button) findViewById(R.id.btn_cancel_new_task);
        createTitle = (MaterialEditText) findViewById(R.id.title_new_task);
        createDescription = (MaterialEditText) findViewById(R.id.description_new_task);
        createPriority = (Spinner) findViewById(R.id.priority_new_spinner);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewTask();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        createTodoTable();

    }

    private void createTodoTable() {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS myTodo (\n"
                        + " id INTEGER NOT NULL CONSTRAINT todo_pk PRIMARY KEY AUTOINCREMENT, \n"
                        + " title VARCHAR(200) NOT NULL, \n"
                        + " description VARCHAR(200) NOT NULL, \n"
                        + " priority VARCHAR(200) NOT NULL, \n"
                        + " joiningdate DATETIME NOT NULL);"
        );
    }

    private boolean checkInput (String title, String description)  {
        if(title.isEmpty()){
            createTitle.setError("Please input title of your task");
            createTitle.requestFocus();
            return false;
        }
        if (description.isEmpty()) {
            createDescription.setError("Please input description of your task");
            createDescription.requestFocus();
            return false;
        }
        return true;
    }

    private void createNewTask() {
        String title = createTitle.getText().toString().trim();
        String description = createDescription.getText().toString().trim();
        String priority = createPriority.getSelectedItem().toString();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String joiningdate = sdf.format(calendar.getTime());

        if (checkInput(title, description)){
            String insertTask = "INSERT INTO myTodo \n"
                    + "(title, description, priority, joiningdate) \n"
                    + "VALUES \n"
                    + "(?, ?, ?, ?);";
            db.execSQL(insertTask, new String[]{title, description, priority, joiningdate});
            Toast.makeText(this, "Create task success !", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(OfflineCreateTaskActivity.this, OfflineTaskActivity.class));
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                return true;
        } return super.onOptionsItemSelected(item);
    }
}
