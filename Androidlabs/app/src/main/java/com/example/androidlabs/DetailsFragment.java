package com.example.starwarsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailsFragment extends Fragment {

    private TextView textTitle;
    private TextView textSubtitle;
    private TextView textDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // Find the TextViews
        textTitle = view.findViewById(R.id.textTitle);
        textSubtitle = view.findViewById(R.id.textSubtitle);
        textDescription = view.findViewById(R.id.textDescription);

        // Get the bundle arguments
        if (getArguments() != null) {
            String name = getArguments().getString("name", "N/A");
            String height = getArguments().getString("height", "N/A");
            String mass = getArguments().getString("mass", "N/A");

            // Set the text in the TextViews
            textTitle.setText(name);
            textSubtitle.setText("Height: " + height);
            textDescription.setText("Mass: " + mass);
        }

        return view;
    }
}
