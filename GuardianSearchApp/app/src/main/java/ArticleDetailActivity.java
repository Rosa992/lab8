package com.example.guardiansearch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class ArticleDetailActivity extends AppCompatActivity {

    private TextView titleText, sectionText;
    private Button openUrlButton, saveButton;

    private String title, section, url;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dbHelper = new DatabaseHelper(this);

        titleText = findViewById(R.id.detailTitle);
        sectionText = findViewById(R.id.detailSection);
        openUrlButton = findViewById(R.id.openUrlButton);
        saveButton = findViewById(R.id.saveButton);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        section = intent.getStringExtra("section");
        url = intent.getStringExtra("url");

        titleText.setText(title);
        sectionText.setText(section);

        openUrlButton.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });

        saveButton.setOnClickListener(v -> {
            dbHelper.addFavourite(title, section, url);
            Toast.makeText(this, "Saved to favourites", Toast.LENGTH_SHORT).show();
        });
    }
}
