package com.codepath.aaneja.nytsearch;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.aaneja.nytsearch.adapters.DocAdapter;
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

        AsyncTask<Context, Integer, DocAdapter> asyncTask = new AsyncTask<Context, Integer, DocAdapter>() {
            @Override
            protected DocAdapter doInBackground(Context... params) {
                NYTArticleSearch nytArticleSearch = new NYTArticleSearch();
                List<Doc> searchedDocs = null;
                try {
                    searchedDocs = nytArticleSearch.GetArticles("india");
                    Log.i("FETCHED SUCCESFULLY", String.valueOf(searchedDocs.size()));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new DocAdapter(searchedDocs);
            }

            @Override
            protected void onPostExecute(DocAdapter docAdapter) {
                RecyclerView rvArticles = (RecyclerView) findViewById(R.id.rvArticles);
                rvArticles.setAdapter(docAdapter);
                rvArticles.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
        };
        asyncTask.execute();
    }
}
