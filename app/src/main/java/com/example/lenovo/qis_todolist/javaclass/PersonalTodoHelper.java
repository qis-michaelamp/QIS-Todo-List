package com.example.lenovo.qis_todolist.javaclass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lenovo on 4/23/2018.
 */

public class PersonalTodoHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todolisttable.db";
    private static final int DATABASE_VERSION = 1;

    public PersonalTodoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table todo(id integer primary key autoincrement, title text not " +
                "null, " +
                "description text" +
                " not null, priority text not null);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "drop table if exists todo";
        sqLiteDatabase.execSQL(sql);

        onCreate(sqLiteDatabase);
    }
}
