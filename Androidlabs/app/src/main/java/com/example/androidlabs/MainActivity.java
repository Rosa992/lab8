package com.example.androidlabs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView catImageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        catImageView = findViewById(R.id.catImage);
        progressBar = findViewById(R.id.progressBar);

        new CatImagesTask().execute();
    }

    private class CatImagesTask extends AsyncTask<Void, Integer, Void> {
        private Bitmap currentCat;

        @Override
        protected Void doInBackground(Void... voids) {
            while (true) {
                try {
                    // Step 1: Fetch JSON for random cat
                    URL jsonUrl = new URL("https://cataas.com/cat?json=true");
                    HttpURLConnection conn = (HttpURLConnection) jsonUrl.openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    StringBuilder sb = new StringBuilder();
                    int ch;
                    while ((ch = is.read()) != -1) sb.append((char) ch);
                    conn.disconnect();

                    JSONObject json = new JSONObject(sb.toString());
                    String id = json.getString("id");
                    String imageUrl = "https://cataas.com/cat/" + id;

                    // Step 2: Check if file already exists
                    File file = new File(getFilesDir(), id + ".jpg");
                    if (file.exists()) {
                        currentCat = BitmapFactory.decodeFile(file.getAbsolutePath());
                    } else {
                        // Step 3: Download and save
                        HttpURLConnection imgConn = (HttpURLConnection) new URL(imageUrl).openConnection();
                        imgConn.connect();
                        InputStream imgStream = imgConn.getInputStream();
                        currentCat = BitmapFactory.decodeStream(imgStream);
                        imgConn.disconnect();

                        // Save locally
                        FileOutputStream fos = new FileOutputStream(file);
                        currentCat.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.close();
                    }

                    // Step 4: Update UI
                    publishProgress(0); // trigger onProgressUpdate to set new image

                    // Step 5: Progress loop (show progress bar filling)
                    for (int i = 0; i <= 100; i++) {
                        publishProgress(i);
                        Thread.sleep(50);
                    }

                } catch (Exception e) {
                    Log.e("CatTask", "Error downloading cat", e);
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progress = values[0];
            progressBar.setProgress(progress);

            // When progress = 0, new cat just loaded
            if (progress == 0 && currentCat != null) {
                catImageView.setImageBitmap(currentCat);
            }
        }
    }
}
