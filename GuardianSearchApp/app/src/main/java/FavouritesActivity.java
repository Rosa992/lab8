package com.example.guardiansearch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseHelper dbHelper;
    private ArrayList<Article> favourites;
    private ArticleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        listView = findViewById(R.id.favouritesList);
        dbHelper = new DatabaseHelper(this);

        // Load favourites from SQLite
        favourites = dbHelper.getAllFavourites();
        adapter = new ArticleAdapter(this, favourites);
        listView.setAdapter(adapter);

        // Open article details on tap
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Article article = favourites.get(position);
            Intent intent = new Intent(FavouritesActivity.this, ArticleDetailActivity.class);
            intent.putExtra("title", article.getTitle());
            intent.putExtra("section", article.getSection());
            intent.putExtra("url", article.getUrl());
            startActivity(intent);
        });

        // Delete favourite on long press
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Article article = favourites.get(position);
            dbHelper.deleteFavourite(article.getTitle());
            favourites.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Deleted from favourites", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}
