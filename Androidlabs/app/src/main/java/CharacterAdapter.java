package com.example.starwarsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.json.JSONObject;
import java.util.ArrayList;

public class CharacterAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<JSONObject> characters;

    public CharacterAdapter(Context context, ArrayList<JSONObject> characters) {
        this.context = context;
        this.characters = characters;
    }

    @Override
    public int getCount() {
        return characters.size();
    }

    @Override
    public Object getItem(int position) {
        return characters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.list_item_character, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.textCharacterName);

        try {
            JSONObject character = characters.get(position);
            textView.setText(character.getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
