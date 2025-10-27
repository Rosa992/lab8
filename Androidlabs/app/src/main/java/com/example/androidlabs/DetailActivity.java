package com.example.starwarsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Only add the fragment once (prevents duplication after rotation)
        if (savedInstanceState == null) {

            // Create an instance of DetailsFragment
            DetailsFragment fragment = new DetailsFragment();

            // Get any data passed from MainActivity
            fragment.setArguments(getIntent().getExtras());

            // Use FragmentManager to replace FrameLayout with the fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detailContainer, fragment)
                    .commit();
        }
    }
}
