package com.codepath.aaneja.nytsearch;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.codepath.aaneja.nytsearch.models.Doc;
import com.codepath.aaneja.nytsearch.services.NYTArticleSearch;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        AsyncTask<Context, Integer, Integer> asyncTask = new AsyncTask<Context, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Context... params) {
                NYTArticleSearch nytArticleSearch = new NYTArticleSearch();

                try {
                    List<Doc> allDocs = nytArticleSearch.GetArticles("india");
                    Log.i("FETCHED SUCCESFULLY", String.valueOf(allDocs.size()));
                    final Gson gson = new Gson();
                    Log.i("index 1", gson.toJson(allDocs));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return 1;
            }
        };
        asyncTask.execute();
    }
}
