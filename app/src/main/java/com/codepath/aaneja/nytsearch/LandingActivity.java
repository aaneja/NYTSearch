package com.codepath.aaneja.nytsearch;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.codepath.aaneja.nytsearch.adapters.DocAdapter;
import com.codepath.aaneja.nytsearch.models.Doc;
import com.codepath.aaneja.nytsearch.models.SearchParams;
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

        AsyncTask<SearchParams, Integer, DocAdapter> asyncTask = new AsyncTask<SearchParams, Integer, DocAdapter>() {

            private final NYTArticleSearch nytArticleSearch = new NYTArticleSearch();
            private final RecyclerView rvArticles = (RecyclerView) findViewById(R.id.rvArticles);

            @Override
            protected DocAdapter doInBackground(SearchParams... searchItem) {

                if (searchItem.length != 1)
                {
                    //throw new Exception("params must have exactly one item");
                }

                List<Doc> searchedDocs = null;
                try {
                    searchedDocs = nytArticleSearch.GetArticles(searchItem[0]);
                    Log.i("FETCHED SUCCESSFULLY", String.valueOf(searchedDocs.size()));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new DocAdapter(searchedDocs);
            }

            @Override
            protected void onPostExecute(DocAdapter docAdapter) {
                rvArticles.setAdapter(docAdapter);
                rvArticles.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
            }
        };

        SearchParams searchParams = new SearchParams();
        searchParams.SearchTerm = "food";
        asyncTask.execute(searchParams);

    }
}
