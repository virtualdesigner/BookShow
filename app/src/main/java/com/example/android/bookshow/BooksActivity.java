package com.example.android.bookshow;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Word>> {

    private WordAdapter mAdapter;
    private TextView emptyTextView;
    public static final int LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        ListView list = (ListView) findViewById(R.id.list);

        emptyTextView = (TextView) findViewById(R.id.emptyView);

        list.setEmptyView(emptyTextView);
        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new WordAdapter(this, new ArrayList<Word>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        list.setAdapter(mAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Word currentBook = mAdapter.getItem(position);

                if (currentBook.getUrl() != null) {
                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri booksUri = Uri.parse(currentBook.getUrl());


                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, booksUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            }
        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo connectivityStatus = cm.getActiveNetworkInfo();
        if (connectivityStatus != null && connectivityStatus.isConnectedOrConnecting()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            //NO INTERNET CONNECTION
            View progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);

            emptyTextView.setText("Internet Connection Sucks!");
        }
    }


    @Override
    public Loader<List<Word>> onCreateLoader(int i, Bundle bundle) {
        // TODO: Create a new loader for the given URL
        // Create a new loader for the given URL
        return new BooksLoader(this, MainActivity.getBooksUrl());
    }

    @Override
    public void onLoadFinished(Loader<List<Word>> loader, List<Word> books) {
        // TODO: Update the UI with the result

        View progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        emptyTextView.setText("No Book Found!");

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
