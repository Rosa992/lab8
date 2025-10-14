package com.example.todoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DB_DEBUG";

    private List<TodoItem> todoList;
    private TodoBaseAdapter adapter;
    private EditText todoEditText;
    private Button addButton;
    private ListView listView;
    private TodoDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoEditText = findViewById(R.id.todoEditText);
        addButton = findViewById(R.id.addButton);
        listView = findViewById(R.id.todoListView);

        todoList = new ArrayList<>();
        dbHelper = new TodoDatabaseHelper(this);

        // Load todos from DB
        loadTodosFromDatabase();

        adapter = new TodoBaseAdapter(this, todoList);
        listView.setAdapter(adapter);

        // Add button listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodoToDatabaseAndList(todoEditText.getText().toString(), false);
            }
        });

        // Long-click to delete
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog(position);
                return true;
            }
        });

        // Debug print cursor info
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(TodoDatabaseHelper.TABLE_TODOS, null, null, null, null, null, null);
        printCursor(c);
        c.close();
    }

    private void loadTodosFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TodoDatabaseHelper.TABLE_TODOS,
                null, null, null, null, null, null);

        todoList.clear();
        while (cursor.moveToNext()) {
            String text = cursor.getString(cursor.getColumnIndexOrThrow(TodoDatabaseHelper.COLUMN_TEXT));
            int urgentInt = cursor.getInt(cursor.getColumnIndexOrThrow(TodoDatabaseHelper.COLUMN_URGENT));
            boolean urgent = (urgentInt == 1);

            todoList.add(new TodoItem(text, urgent));
        }
        cursor.close();
    }

    private void addTodoToDatabaseAndList(String text, boolean urgent) {
        if (text.trim().isEmpty()) return;

        // 1. Add to database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TodoDatabaseHelper.COLUMN_TEXT, text);
        values.put(TodoDatabaseHelper.COLUMN_URGENT, urgent ? 1 : 0);
        db.insert(TodoDatabaseHelper.TABLE_TODOS, null, values);

        // 2. Add to in-memory list and update UI
        todoList.add(new TodoItem(text, urgent));
        adapter.notifyDataSetChanged();
        todoEditText.setText("");
    }

    private void showDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.delete_title);
        builder.setMessage(getString(R.string.delete_message) + " " + position);

        builder.setPositiveButton(R.string.yes, (dialog, which) -> {
            deleteTodoFromDatabase(position);
            todoList.remove(position);
            adapter.notifyDataSetChanged();
        });

        builder.setNegativeButton(R.string.no, null);
        builder.show();
    }

    private void deleteTodoFromDatabase(int position) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // For simplicity, delete by matching text (or you could store IDs in TodoItem)
        String textToDelete = todoList.get(position).getText();
        db.delete(TodoDatabaseHelper.TABLE_TODOS,
                TodoDatabaseHelper.COLUMN_TEXT + " = ?",
                new String[]{textToDelete});
    }

    // STEP 6 — Debug function
    private void printCursor(Cursor c) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Log.d(TAG, "DB Version: " + db.getVersion());
        Log.d(TAG, "Column Count: " + c.getColumnCount());

        String[] columnNames = c.getColumnNames();
        for (String name : columnNames) {
            Log.d(TAG, "Column: " + name);
        }

        Log.d(TAG, "Row Count: " + c.getCount());

        while (c.moveToNext()) {
            StringBuilder row = new StringBuilder();
            for (String name : columnNames) {
                row.append(name).append(" = ")
                        .append(c.getString(c.getColumnIndexOrThrow(name))).append("; ");
            }
            Log.d(TAG, "Row: " + row.toString());
        }
    }
}
