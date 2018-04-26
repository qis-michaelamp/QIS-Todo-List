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

public class AddEditTaskActivity extends AppCompatActivity {

    TestHelper helper;
    Button btnAddEdit, btnCancelEdit, btnDeleteEdit;
    MaterialEditText editTitle, editDescription, editPriority;

    private String selectedTitle, selectedDescription, selectedPriority;
    private int selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        helper = new TestHelper(this);
        btnAddEdit = (Button) findViewById(R.id.btn_add_new_task);
        btnCancelEdit = (Button) findViewById(R.id.btn_save_edit_task);
        btnDeleteEdit = (Button) findViewById(R.id.btn_delete_edit_task);
        editTitle = (MaterialEditText) findViewById(R.id.title_edit_task);
        editDescription = (MaterialEditText) findViewById(R.id.description_edit_task);
        editPriority = (MaterialEditText) findViewById(R.id.priority_edit_task);

        btnAddEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemTitle = editTitle.getText().toString();
                String itemDescription = editDescription.getText().toString();
                String itemPriority = editPriority.getText().toString();
                if(itemTitle.equals("") || itemDescription.equals("") || itemPriority.equals("")) {
                    helper.updateItem(itemTitle, itemDescription, itemPriority, selectedId);
                } else {
                    Toast.makeText(AddEditTaskActivity.this, "You must fill all the fields", Toast
                            .LENGTH_SHORT).show();
                }
            }
        });

        btnCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditTaskActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });

        btnDeleteEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.deleteItem(selectedId, selectedTitle);
                editTitle.setText("");
                editDescription.setText("");
                editPriority.setText("");
            }
        });

        Intent receivedIntent = getIntent();
        selectedId = receivedIntent.getIntExtra("id", -1);
        selectedTitle = receivedIntent.getStringExtra("title");
        selectedDescription = receivedIntent.getStringExtra("description");
        selectedPriority = receivedIntent.getStringExtra("priority");

        editTitle.setText(selectedTitle);
        editDescription.setText(selectedDescription);
        editPriority.setText(selectedPriority);
    }
}
