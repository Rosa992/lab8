package com.example.guardiansearch;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

/**
 * Fragment showing the article details and Save button.
 */
public class ArticleDetailFragment extends Fragment {
    private String id, title, section, url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_article_detail, container, false);
        Bundle args = getArguments();
        if (args != null) {
            id = args.getString("id");
            title = args.getString("title");
            section = args.getString("section");
            url = args.getString("url");
        }

        TextView tvTitle = v.findViewById(R.id.detail_title);
        TextView tvSection = v.findViewById(R.id.detail_section);
        Button btnOpen = v.findViewById(R.id.btn_open);
        Button btnSave = v.findViewById(R.id.btn_save);

        tvTitle.setText(title);
        tvSection.setText(section);

        btnOpen.setOnClickListener(view -> {
            Intent it = new Intent(getActivity(), WebViewActivity.class);
            it.putExtra("url", url);
            startActivity(it);
        });

        btnSave.setOnClickListener(view -> {
            FavoritesDbHelper db = new FavoritesDbHelper(getActivity());
            boolean ok = db.addFavorite(new Article(title, section, url));
            if (ok) {
                Toast.makeText(getActivity(), R.string.toast_saved, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), R.string.toast_already_saved, Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
