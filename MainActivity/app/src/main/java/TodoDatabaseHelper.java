public class TodoDatabaseHelper {
    package com.example.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

    public class TodoDatabaseHelper extends SQLiteOpenHelper {

        // Static variables for DB schema
        public static final String DATABASE_NAME = "todo.db";
        public static final int DATABASE_VERSION = 1;

        public static final String TABLE_TODOS = "todos";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TEXT = "text";
        public static final String COLUMN_URGENT = "urgent"; // 0 or 1

        // SQL create table statement
        private static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_TODOS + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TEXT + " TEXT NOT NULL, " +
                        COLUMN_URGENT + " INTEGER NOT NULL)";

        public TodoDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
            onCreate(db);
        }
    }


}
