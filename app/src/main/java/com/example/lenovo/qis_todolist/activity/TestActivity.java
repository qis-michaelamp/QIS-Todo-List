package com.example.lenovo.qis_todolist.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.qis_todolist.R;
import com.example.lenovo.qis_todolist.javaclass.TestHelper;
import com.rengwuxian.materialedittext.MaterialEditText;

public class TestActivity extends AppCompatActivity {

    TestHelper helper;
    Button btnAdd, btnCancel;
    MaterialEditText createTitle, createDescription, createPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        helper = new TestHelper(this);

        btnAdd = (Button) findViewById(R.id.button_save_new_task);
        btnCancel = (Button) findViewById(R.id.button_cancel_new_task);
        createTitle = (MaterialEditText) findViewById(R.id.title_new_task);
        createDescription = (MaterialEditText) findViewById(R.id.description_new_task);
        createPriority = (MaterialEditText) findViewById(R.id.priority_new_task);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTitle = createTitle.getText().toString();
                String newDescription = createDescription.getText().toString();
                String newPriority = createPriority.getText().toString();
                if (newTitle.length() != 0 && newDescription.length() != 0 && newPriority.length()
                        != 0) {
                    AddData(newTitle, newDescription, newPriority);
                    createTitle.setText("");
                    createDescription.setText("");
                    createPriority.setText("");
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AddData(String newTitle, String newDescription, String newPriority) {
        boolean insertData = helper.addData(newTitle, newDescription, newPriority);

        if (insertData) {
            Toast.makeText(this, "Data successfully inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Can't insert the data", Toast.LENGTH_SHORT).show();
        }
    }
}
