package com.example.lenovo.qis_todolist.activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.qis_todolist.R;
import com.example.lenovo.qis_todolist.javaclass.TestHelper;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {

    TestHelper helper;
    ListView listView;
    Button btnAddTask;

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String PRIORITY = "priority";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        helper = new TestHelper(this);
        listView = (ListView) findViewById(R.id.list_view_todo);
        btnAddTask = (Button) findViewById(R.id.btn_add_new_task);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListDataActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });

        populateListView();
    }

    private void populateListView() {

        Cursor data = helper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = adapterView.getItemAtPosition(i).toString();
                String description = adapterView.getItemAtPosition(i).toString();
                String priority = adapterView.getItemAtPosition(i).toString();

                Cursor data = helper.getItemID(title, description, priority);
                int itemId = -1;
                while (data.moveToNext()) {
                    itemId = data.getInt(0);
                }
                if (itemId > 1) {
                    Intent intent = new Intent(ListDataActivity.this, AddEditTaskActivity.class);
                    intent.putExtra(ID, itemId);
                    intent.putExtra(TITLE, title);
                    intent.putExtra(DESCRIPTION, description);
                    intent.putExtra(PRIORITY, priority);
                    startActivity(intent);
                } else {
                    Toast.makeText(ListDataActivity.this, "No ID associated", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });

    }
}
