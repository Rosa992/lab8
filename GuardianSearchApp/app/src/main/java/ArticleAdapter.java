package com.example.guardiansearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ArticleAdapter extends ArrayAdapter<Article> {

    public ArticleAdapter(Context context, List<Article> articles) {
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article article = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_article, parent, false);
        }

        TextView title = convertView.findViewById(R.id.titleTextView);
        TextView section = convertView.findViewById(R.id.sectionTextView);

        title.setText(article.getTitle());
        section.setText(article.getSection());

        return convertView;
    }
}
