package com.gmardon.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by gmardon on 02/02/2018.
 */

public class Database extends SQLiteOpenHelper {
    private static final String DB_NAME = "gmardon_todolist";
    private static final int DB_VER = 1;
    public static final String DB_TABLE = "tasks";
    public static final String DB_COLUMN_ID = "id";
    public static final String DB_COLUMN_NAME = "name";
    public static final String DB_COLUMN_DESCRIPTION = "description";
    public static final String DB_COLUMN_DUEDATE = "due_date";

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT, %s varchar(256));", DB_TABLE, DB_COLUMN_ID, DB_COLUMN_NAME, DB_COLUMN_DESCRIPTION, DB_COLUMN_DUEDATE);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s", DB_TABLE);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertNewTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN_NAME, task.getName());
        values.put(DB_COLUMN_DESCRIPTION, task.getDescription());
        values.put(DB_COLUMN_DUEDATE, task.getDueDate());
        db.insertWithOnConflict(DB_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, DB_COLUMN_ID + " = ?", new String[]{String.valueOf(task.getId())});
        db.close();
    }

    public ArrayList<Task> getTaskList() {
        ArrayList<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{DB_COLUMN_ID, DB_COLUMN_NAME, DB_COLUMN_DESCRIPTION, DB_COLUMN_DUEDATE}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Task task = new Task(cursor.getInt(cursor.getColumnIndex(DB_COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DB_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(DB_COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(DB_COLUMN_DUEDATE)));

            taskList.add(task);
        }
        cursor.close();
        db.close();
        return taskList;
    }
}