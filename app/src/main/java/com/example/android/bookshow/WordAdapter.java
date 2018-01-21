package com.example.android.bookshow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SMDEEPAK on 19-01-2018.
 */

public class WordAdapter extends ArrayAdapter<Word>{


    public WordAdapter(Context context, ArrayList<Word> words){
        super(context, 0, words);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);

        //ImageView image = (ImageView) listItemView.findViewById(R.id.image);
        //image.setImageResource(currentWord.getThumbnail());

        //TextView title = (TextView) listItemView.findViewById(R.id.title);
        //title.setText(currentWord.getTitle());

        TextView author = (TextView) listItemView.findViewById(R.id.authorName);
        author.setText(currentWord.getAuthor());

        //TextView price = (TextView) listItemView.findViewById(R.id.price);
        //price.setText(currentWord.getPrice());

        return listItemView;
    }
}
