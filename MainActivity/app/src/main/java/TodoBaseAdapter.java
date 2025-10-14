package com.example.todoapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TodoBaseAdapter extends BaseAdapter {

    private Context context;
    private List<TodoItem> todoList;

    public TodoBaseAdapter(Context context, List<TodoItem> todoList) {
        this.context = context;
        this.todoList = todoList;
    }

    // 1. Number of items
    @Override
    public int getCount() {
        return todoList.size();
    }

    // 2. Return item at a specific position
    @Override
    public Object getItem(int position) {
        return todoList.get(position);
    }

    // 3. Unique ID — we just return position
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 4. Inflate and return a view for each row
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Reuse old view if possible
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        TodoItem item = todoList.get(position);

        // Set the text
        textView.setText(item.getText());

        // If urgent, set background to red, text to white
        if (item.isUrgent()) {
            convertView.setBackgroundColor(Color.RED);
            textView.setTextColor(Color.WHITE);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
            textView.setTextColor(Color.BLACK);
        }

        return convertView;
    }
}
