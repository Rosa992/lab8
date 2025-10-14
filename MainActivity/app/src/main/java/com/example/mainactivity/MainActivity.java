package com.example.mainactivity;



import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

            // Initialize UI elements
            todoEditText = findViewById(R.id.todoEditText);
            addButton = findViewById(R.id.addButton);


            // Create list and adapter
            todoList = new ArrayList<>();
            adapter = new TodoAdapter(this, todoList);
            listView.setAdapter(adapter);

            // Add button click listener
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String taskText = todoEditText.getText().toString().trim();

                    if (!taskText.isEmpty()) {
                        // Create a new TodoItem (not urgent by default)
                        TodoItem newItem = new TodoItem(taskText, false);

                        // Add it to the list
                        todoList.add(newItem);

                        // Refresh the adapter so the new item shows up
                        adapter.notifyDataSetChanged();

                        // Clear the EditText
                        todoEditText.setText("");

                    }
                }
            }}}
}

                    // Long click to delete
                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                            // Show confirmation dialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Do you want to delete this?");
                            builder.setMessage("The selected row is: " + position);

                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    todoList.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                            });

                            builder.setNegativeButton("No", null);

                            builder.show();

                            // Return true to indicate we've handled the long-click
                            return true;
                        }
                    });
