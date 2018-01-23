package com.example.android.bookshow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
            JSONArray items = jsonObject.getJSONArray("items");
            for(int i =0; i<items.length();i++){

                JSONObject seperateObject = (JSONObject) items.get(i);
                JSONObject volumeInfo = seperateObject.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                String author = "";
                String authors = "";
                if(volumeInfo.has("authors")) {
                    JSONArray authorsArray = volumeInfo.getJSONArray("authors");

                    if(authorsArray.length()>1) {
                        for (int j = 0; j < authorsArray.length(); j++) {
                            if(j==(authorsArray.length())-1){
                                author += authorsArray.getString(j);
                            }else {
                                author += authorsArray.getString(j) + ", ";
                            }
                        }
                        authors = "By " + author;
                    }else{
                        authors = "By " + authorsArray.getString(0);
                    }
                }

                String publisher="";
                String thumbnail = "";
                if(volumeInfo.has("imageLinks")) {
                    thumbnail = volumeInfo.getJSONObject("imageLinks").getString("smallThumbnail");
                }
                String previewLink = volumeInfo.getString("previewLink");
                String buyLink = volumeInfo.getString("infoLink");
                if(volumeInfo.has("publisher")) {
                    publisher = volumeInfo.getString("publisher");
                }
                //if(volumeInfo.getString("publishedDate")!= null) {
                 //   String publishedDate = volumeInfo.getString("publishedDate");
                //}
                String price = "Free";
                Bitmap image = makeImage(thumbnail);
                if(volumeInfo.has("price")) {
                    price = volumeInfo.getString("price");
                }
                myList.add(new Word(title,authors,thumbnail,previewLink,buyLink,publisher,price,image));

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
            httpURLConnection.setReadTimeout(150000);
            httpURLConnection.setConnectTimeout(200000);
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

    private static Bitmap makeImage(String ImageUrl){
        Bitmap bitmapImage = null;
        InputStream ins = null;

        URL imageUrl = createUrl(ImageUrl);

        if(ImageUrl == null){

            Log.e("Queryutils","ImageUrl null!");
            return null;

        }
        try{

            HttpURLConnection httpURLConnection =(HttpURLConnection) imageUrl.openConnection();
            httpURLConnection.setReadTimeout(200000);
            httpURLConnection.setConnectTimeout(500000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode == 200){
                ins = httpURLConnection.getInputStream();
                bitmapImage = BitmapFactory.decodeStream(ins);
            }
        }catch (IOException e){

            Log.e("QueryUtils","Image input stream exception");

        }
        return bitmapImage;
    }
}
