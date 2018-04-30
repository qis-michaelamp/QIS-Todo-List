package com.example.lenovo.qis_todolist.model;

/**
 * Created by Lenovo on 4/30/2018.
 */

public class TodoModel {
    int id;
    String title, description,priority, joiningDate;

    public TodoModel(int id, String title, String description, String priority, String
            joiningDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.joiningDate = joiningDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public String getJoiningDate() {
        return joiningDate;
    }
}
