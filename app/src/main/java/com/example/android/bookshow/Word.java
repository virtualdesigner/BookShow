package com.example.android.bookshow;

import android.graphics.Bitmap;

/**
 * Created by SMDEEPAK on 19-01-2018.
 */

public class Word {

    private String mAuthor, mTitle, mUrl, mPages;
    Bitmap mImage;

    public Word(String title, String author, Bitmap image, String pages, String url) {
        mTitle = title;
        mAuthor = author;
        mImage = image;
        mPages = pages;
        mUrl = url;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public String getPages() {
        return mPages;
    }

    public String getUrl() {
        return mUrl;
    }
}
