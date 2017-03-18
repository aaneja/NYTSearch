package com.codepath.aaneja.nytsearch;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.codepath.aaneja.nytsearch.adapters.DocAdapter;
import com.codepath.aaneja.nytsearch.models.Doc;
import com.codepath.aaneja.nytsearch.models.SearchParams;
import com.codepath.aaneja.nytsearch.services.NYTArticleSearch;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LandingActivity extends AppCompatActivity {

    private List<Doc> searchedDocs = new ArrayList<>(10);
    private DocAdapter docAdapter = new  DocAdapter(searchedDocs);
    private SearchParams searchParams = new SearchParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        RecyclerView rvArticles = (RecyclerView) findViewById(R.id.rvArticles);
        rvArticles.setAdapter(docAdapter);
        rvArticles.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        getSearchDocsUpdateTask().execute(searchParams);

    }

    @NonNull
    private AsyncTask<SearchParams, Integer, List<Doc>> getSearchDocsUpdateTask() {
        return new AsyncTask<SearchParams, Integer, List<Doc>>() {

            private final NYTArticleSearch nytArticleSearch = new NYTArticleSearch();

            @Override
            protected List<Doc> doInBackground(SearchParams... searchItem) {

                if (searchItem.length != 1)
                {
                    //throw new Exception("params must have exactly one item");
                }

                List searchedDocsNew = Collections.EMPTY_LIST;
                try {
                    searchedDocsNew = nytArticleSearch.GetArticles(searchItem[0]);
                    Log.i("FETCHED SUCCESSFULLY", String.valueOf(searchedDocsNew.size()));
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

                return searchedDocsNew;
            }

            @Override
            protected void onPostExecute(List<Doc> newResults) {
                searchedDocs.clear();
                searchedDocs.addAll(newResults);
                docAdapter.notifyDataSetChanged();
            }
        };
    }

    public void onSearchButtonClick(View view) {
        EditText etSearch = (EditText) findViewById(R.id.etSearch);
        searchParams.SearchTerm =  etSearch.getText().toString();
        getSearchDocsUpdateTask().execute(searchParams);
    }
}
