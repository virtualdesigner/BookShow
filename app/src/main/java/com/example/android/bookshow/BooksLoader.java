package com.example.android.bookshow;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by SMDEEPAK on 20-01-2018.
 */

public class BooksLoader extends AsyncTaskLoader<List<Word>> {

    private String url ;
    public BooksLoader(Context context, String mUrl){
        super(context);
        url = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Word> loadInBackground() {
        List<Word> books = QueryUtils.fetchBooksDataFromJson(url);
        return books;
    }
}
