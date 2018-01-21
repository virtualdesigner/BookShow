package com.example.android.bookshow;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Word>>{

    private WordAdapter mAdapter;

    public static final String BOOKS_URL = "https://www.googleapis.com/books/v1/volumes/1Y7jY06XNF4C";
    public static final int LOADER_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = findViewById(R.id.list);
        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new WordAdapter(this, new ArrayList<Word>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        list.setAdapter(mAdapter);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo connectivityStatus = cm.getActiveNetworkInfo();
        if(connectivityStatus!=null && connectivityStatus.isConnectedOrConnecting()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        }else{
            //NO INTERNET CONNECTION
        }
    }

    @Override
    public Loader<List<Word>> onCreateLoader(int i, Bundle bundle) {
        // TODO: Create a new loader for the given URL
        // Create a new loader for the given URL
        return new BooksLoader(this, BOOKS_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Word>> loader, List<Word> books) {
        // TODO: Update the UI with the result

        mAdapter.clear();
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Word>> loader) {
        // TODO: Loader reset, so we can clear out our existing data.
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }


}


