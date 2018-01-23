package com.example.android.bookshow;

import android.graphics.Bitmap;

/**
 * Created by SMDEEPAK on 19-01-2018.
 */

public class Word {

    private String  mAuthor,mTitle,mThumbnail,mPreviewLink,mBuyLink,mPublisher,mPrice;
    Bitmap mImage;
    int mPublishedDate;
    public Word(String title, String author, String thumbnail, String previewLink, String buyLink, String publisher, String price, Bitmap image){
        mTitle = title;
        mAuthor = author;
        mThumbnail = thumbnail;
        mPreviewLink = previewLink;
        mBuyLink = buyLink;
        mPublisher = publisher;
        mPrice = price;
        //mPublishedDate = publishedDate;
        mImage = image;
    }



    public String getAuthor(){
        return mAuthor;
    }

    public String getPublisher(){
        return mPublisher;
    }

    public int getPublishedDate(){
        return mPublishedDate;
    }

    public String getPreviewLink(){
        return mPreviewLink;
    }

    public String getBuyLink(){
        return mBuyLink;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getThumbnail(){
        return mThumbnail;
    }

    public String getPrice(){
        return mPrice;
    }

    public Bitmap getImage(){ return mImage; }
}
