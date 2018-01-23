package com.example.android.bookshow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = (Button) findViewById(R.id.bookButton);
        final EditText editText = (EditText) findViewById(R.id.enterText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = "";
                content = editText.getText().toString();
                Intent booksIntent = new Intent(MainActivity.this, BooksActivity.class);
                startActivity(booksIntent);

            }
        });

    }

public static final String getBooksUrl(){
        String url = "https://www.googleapis.com/books/v1/volumes?q="+content+"&maxResults=20";
        return url;
}

}


