package com.example.starwarsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StarWarsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<StarWarsCharacter> characters;

    public StarWarsAdapter(Context context, ArrayList<StarWarsCharacter> characters) {
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
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_character, parent, false);
            holder = new ViewHolder();
            holder.nameTextView = convertView.findViewById(R.id.textCharacterName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        StarWarsCharacter character = characters.get(position);
        holder.nameTextView.setText(character.getName());

        return convertView;
    }

    static class ViewHolder {
        TextView nameTextView;
    }
}
