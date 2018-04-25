package com.example.lenovo.qis_todolist.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.lenovo.qis_todolist.R;
import com.example.lenovo.qis_todolist.javaclass.PersonalTodoHelper;

import java.sql.SQLInput;

public class PersonalTodoActivity extends AppCompatActivity {

    String todoList[];
    ListView listView;
    protected Cursor cursor;
    PersonalTodoHelper helper;
    public static PersonalTodoActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_todo);

        Button btn_add_task = (Button) findViewById(R.id.btn_add_task);

        //region btn_add_task.setOnClickListener
        btn_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalTodoActivity.this, CreateTaskActivity.class);
                startActivity(intent);
            }
        });
        //endregion

        activity = this;
        helper = new PersonalTodoHelper(this);
        RefreshList();

    }

    public void RefreshList() {
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM todo", null);
        todoList = new String[cursor.getCount()];
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            todoList[i] = cursor.getString(1);
        }
        listView = (ListView) findViewById(R.id.personal_todo_id);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, todoList));
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String selection = todoList[i];
                final CharSequence[] dialogItem = {"Read Task", "Update Task", "Delete Task"};
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalTodoActivity.this);
                builder.setTitle("Options :");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                Intent intentRead = new Intent(getApplicationContext(),
                                        ReadTaskActivity.class);
                                intentRead.putExtra("read", selection);
                                startActivity(intentRead);
                                break;
                            case 1 :
                                Intent intentUpdate = new Intent(getApplicationContext(),
                                        UpdateTaskActivity.class);
                                intentUpdate.putExtra("update", selection);
                                startActivity(intentUpdate);
                                break;
                            case 2 :
                                SQLiteDatabase db = helper.getWritableDatabase();
                                db.execSQL("delete from todo where title = '"+selection+"'");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }});
        ((ArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
    }
}
