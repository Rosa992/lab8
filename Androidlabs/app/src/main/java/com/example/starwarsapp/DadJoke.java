package com.example.lab8app;

import android.os.Bundle;
import android.widget.TextView;

public class DadJoke extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dad_joke);
        setupDrawer();
        setTitle("Dad Joke");

        TextView textView = findViewById(R.id.joke_text);
        textView.setText("Why don’t skeletons fight each other? They don’t have the guts!");
    }
}
