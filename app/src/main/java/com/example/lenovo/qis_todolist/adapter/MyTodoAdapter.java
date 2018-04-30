package com.example.lenovo.qis_todolist.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.qis_todolist.R;
import com.example.lenovo.qis_todolist.model.TodoModel;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

/**
 * Created by Lenovo on 4/30/2018.
 */

public class MyTodoAdapter extends ArrayAdapter<TodoModel> {

    Context context;
    int listLayoutRes;
    List<TodoModel> todoModelList;
    SQLiteDatabase db;

    public MyTodoAdapter(@NonNull Context context, int
            listLayoutRes, List<TodoModel> todoModelList, SQLiteDatabase db) {
        super(context, listLayoutRes, todoModelList);
        this.context = context;
        this.listLayoutRes = listLayoutRes;
        this.todoModelList = todoModelList;
        this.db = db;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_layout_row, null);

        final TodoModel todoModel = todoModelList.get(position);

        TextView textViewTitle = view.findViewById(R.id.text_view_title);
        TextView textViewDescription = view.findViewById(R.id.text_view_description);
        TextView textViewPriority = view.findViewById(R.id.text_view_priority);
        TextView textViewJoiningDate = view.findViewById(R.id.text_view_joining_date);

        textViewTitle.setText(todoModel.getTitle());
        textViewDescription.setText(todoModel.getDescription());
        textViewPriority.setText(todoModel.getPriority());
        textViewJoiningDate.setText(todoModel.getJoiningDate());

        Button btnEdit = view.findViewById(R.id.button_edit_task);
        Button btnDelete = view.findViewById(R.id.button_delete_task);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTask(todoModel);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setCancelable(false);
                dialog.setTitle("Are you sure want to delete this task?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM myTodo WHERE id = ?";
                        db.execSQL(sql, new Integer[]{todoModel.getId()});
                        reloadTask();
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        dialogInterface.dismiss();
                    }
                });
                dialog.create().show();
            }
        });

        return view;
    }

    private void reloadTask() {
        Cursor cursor = db.rawQuery("SELECT * FROM myTodo", null);
        if (cursor.moveToFirst()) {
            todoModelList.clear();
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
        notifyDataSetChanged();
    }

    private void updateTask(final TodoModel todoModel) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_add_edit_task, null);
        dialog.setView(view);

        final MaterialEditText titleEdit = view.findViewById(R.id.title_edit_task);
        final MaterialEditText descriptionEdit = view.findViewById(R.id.description_edit_task);
        final Spinner priorityEdit = view.findViewById(R.id.priority_edit_spinner);
        final Button btnUpdate = view.findViewById(R.id.btn_save_update);
        final Button btnCancel = view.findViewById(R.id.btn_cancel_update);

        titleEdit.setText(todoModel.getTitle());
        descriptionEdit.setText(todoModel.getDescription());

        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEdit.getText().toString().trim();
                String description = descriptionEdit.getText().toString().trim();
                String priority = priorityEdit.getSelectedItem().toString();

                if (title.isEmpty()) {
                    titleEdit.setError("Title cannot be empty");
                    titleEdit.requestFocus();
                    return;
                }
                if (description.isEmpty()) {
                    descriptionEdit.setError("Description cannot be empty");
                    descriptionEdit.requestFocus();
                    return;
                }

                String sql = "UPDATE myTodo \n"
                        + "SET title = ?, \n"
                        + "description = ?, \n"
                        + "priority = ? \n"
                        + "WHERE id = ?;\n";
                db.execSQL(sql, new String[]{title, description, priority, String.valueOf
                        (todoModel.getId())});
                Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                reloadTask();

                alertDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

}
