package com.example.lenovo.qis_todolist.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


import com.example.lenovo.qis_todolist.R;
import com.example.lenovo.qis_todolist.adapter.MyTodoAdapter;
import com.example.lenovo.qis_todolist.model.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class ListDataActivity extends AppCompatActivity {

    List<TodoModel> todoModelList;
    SQLiteDatabase db;
    ListView listView;
    MyTodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        listView = (ListView) findViewById(R.id.list_view_todo);

        todoModelList = new ArrayList<>();

        db = openOrCreateDatabase(TestActivity.DATABASE_NAME, MODE_PRIVATE, null);

        showTask();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.offline_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_task :
                startActivity(new Intent(ListDataActivity.this, TestActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void showTask() {
        Cursor cursor = db.rawQuery("SELECT * FROM myTodo", null);

        if(cursor.moveToFirst()) {
            do {
                todoModelList.add(new TodoModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        adapter = new MyTodoAdapter(this, R.layout.custom_layout_row, todoModelList, db);
        listView.setAdapter(adapter);
    }
}
