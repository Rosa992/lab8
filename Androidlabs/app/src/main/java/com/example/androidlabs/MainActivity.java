package com.example.starwarsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.AdapterView;
import android.view.View;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<StarWarsCharacter> characters = new ArrayList<>();
    private StarWarsAdapter adapter; // your custom BaseAdapter class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        adapter = new StarWarsAdapter(this, characters);
        listView.setAdapter(adapter);

        // Fetch the data from the API
        new FetchStarWarsData().execute();

        //  🔹 Step 11: Add OnItemClickListener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StarWarsCharacter selectedCharacter = characters.get(position);

                // Check if FrameLayout (for tablet) exists
                View frameLayout = findViewById(R.id.detailContainer);

                if (frameLayout == null) {
                    // 🟢 PHONE MODE — open the DetailActivity
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                    // Put character info into a bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("name", selectedCharacter.getName());
                    bundle.putString("height", selectedCharacter.getHeight());
                    bundle.putString("mass", selectedCharacter.getMass());
                    intent.putExtras(bundle);

                    // Start the new Activity
                    startActivity(intent);

                } else {
                    // 🟦 TABLET MODE — show fragment on same screen
                    DetailsFragment fragment = new DetailsFragment();

                    // Pass data through a bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("name", selectedCharacter.getName());
                    bundle.putString("height", selectedCharacter.getHeight());
                    bundle.putString("mass", selectedCharacter.getMass());
                    fragment.setArguments(bundle);

                    // Replace FrameLayout with DetailsFragment
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.detailContainer, fragment)
                            .commit();
                }
            }
        });
    }

    // 🔹 AsyncTask to fetch Star Wars data
    private class FetchStarWarsData extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://swapi.dev/api/people/?format=json");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String json) {
            if (json != null) {
                try {
                    JSONObject root = new JSONObject(json);
                    JSONArray results = root.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject person = results.getJSONObject(i);
                        String name = person.getString("name");
                        String height = person.getString("height");
                        String mass = person.getString("mass");

                        characters.add(new StarWarsCharacter(name, height, mass));
                    }

                    adapter.notifyDataSetChanged();

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Parsing error", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
