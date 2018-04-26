package com.example.lenovo.qis_todolist.javaclass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

/**
 * Created by Lenovo on 4/25/2018.
 */

public class TestHelper extends SQLiteOpenHelper {

    private static final String TAG = "TestHelper";

    private static final String DATABASE_NAME = "todotest.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "testTodo";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String PRIORITY = "priority";

    public TestHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE + "TEXT NOT NULL, "
                + DESCRIPTION + "TEXT NOT NULL, "
                + PRIORITY + "TEXT NOT NULL)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String title, String description, String priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, title);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(PRIORITY, priority);

        Log.d(TAG, "Add : " + title + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //get all the data
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);

        return data;
    }

    //returns only the ID
    public Cursor getItemID(String title, String description, String priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + ID + " WHERE "
                + TITLE + " = '" + title + "', "
                + DESCRIPTION + " = '" + description + "', "
                + PRIORITY + " = '" + priority + "'";
        Cursor data = db.rawQuery(query, null);

        return data;
    }

    //update items
    public void updateItem(String newTitle, String newDescription, String newPriority, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET "
                + TITLE + " = '" + newTitle + "', "
                + DESCRIPTION + " = '" + newDescription + "', "
                + PRIORITY + " = '" + newPriority + "' "
                + " WHERE " + ID + " = '" + id + "'";
        db.execSQL(query);
    }

    //delete items
    public void deleteItem(int id, String title){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME+ " WHERE "
                + ID + " = '" + id + "'"
                + " AND " + TITLE + " = '" + title + "'";
        db.execSQL(query);
    }

}
