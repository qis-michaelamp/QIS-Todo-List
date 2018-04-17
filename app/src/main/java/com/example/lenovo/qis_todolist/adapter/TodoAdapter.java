package com.example.lenovo.qis_todolist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.qis_todolist.MainActivity;
import com.example.lenovo.qis_todolist.R;
import com.example.lenovo.qis_todolist.model.ToDo;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Lenovo on 4/17/2018.
 */

class ListItemViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener, View
        .OnCreateContextMenuListener{

    ItemClickListener itemClickListener;
    TextView item_title, item_description;

    public ListItemViewAdapter(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);

        item_title = (TextView) itemView.findViewById(R.id.item_title);
        item_description = (TextView) itemView.findViewById(R.id.item_description);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu
            .ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Options :");
        contextMenu.add(0, 0, getAdapterPosition(), "Delete Task");
    }
}

public class TodoAdapter extends RecyclerView.Adapter<ListItemViewAdapter>{

    MainActivity mainActivity;
    List<ToDo> toDoList = new ArrayList<>();

    public TodoAdapter(MainActivity mainActivity, List<ToDo> toDoList) {
    }

    @Override
    public ListItemViewAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mainActivity.getBaseContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ListItemViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(ListItemViewAdapter holder, int position) {
        //set data for items
        holder.item_title.setText(toDoList.get(position).getTitle());
        holder.item_description.setText(toDoList.get(position).getDescription());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                //when task selected, the data inside card view will automatically set into edit text
                mainActivity.title.setText(toDoList.get(position).getTitle());
                mainActivity.description.setText(toDoList.get(position).getDescription());

                mainActivity.isUpdate = true;
                mainActivity.idUpdate = toDoList.get(position).getId();

            }
        });
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }
}
