package com.example.android.bookshow;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SMDEEPAK on 19-01-2018.
 */

public final class QueryUtils {

    public static List<Word> extractFeatureFromJson(String JSON_RESPONSE){

        if (TextUtils.isEmpty(JSON_RESPONSE)) {
            return null;
        }

        List<Word> myList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(JSON_RESPONSE);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for(int i =0; i<jsonArray.length();i++){

                JSONObject volumeInfo = jsonArray.getJSONObject(0);
                //String title = volumeInfo.getString("");
                String author = volumeInfo.getJSONArray("authors").getString(0);
                //String thumbnail = volumeInfo.getJSONObject("imageLinks").getString("smallThumbnail");
               // String previewLink = volumeInfo.getString("previewLink");
               // String buyLink = volumeInfo.getString("infoLink");
                //String publisher = volumeInfo.getString("publisher");
                //String publishedDate = volumeInfo.getString("publishedDate");
                //String price = volumeInfo.getString("price");

                myList.add(new Word( author));

            }
        }catch (JSONException e){
            Log.e("QueryUtils","Problem parsing JSON",e);
        }


        return myList;
    }

    public static List<Word> fetchBooksDataFromJson(String StringUrl){

        URL url = null;
        url = createUrl(StringUrl);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpConnection(url);

        }catch (IOException e){
            Log.e("QueryUtils", "Problem making the HTTP request.",e);
        }
        List<Word> booksList = extractFeatureFromJson(jsonResponse);
        return booksList;
    }

    private static URL createUrl(String url){
        URL Url  = null;
        if(url == null){
            return null;
        }
        try {
            Url = new URL(url);
        }catch(MalformedURLException e){
            Log.e("QueryUtils","MALFORMED URL",e);
        }
        return Url;
    }

    private static String makeHttpConnection(URL url) throws IOException{

        String jsonResponse = "";

        if(url == null){
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try{
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode() == 200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            }else{
                Log.e("QueryUtils", "Error Response Code:"+httpURLConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e("QueryUtils","Problem retrieving the earthquake JSON results.",e);
        }finally{
            if(httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException{
        StringBuilder jsonString = new StringBuilder();
        if(inputStream!=null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                jsonString.append(line);
                line = bufferedReader.readLine();
            }
        }


        return jsonString.toString();
    }
}
