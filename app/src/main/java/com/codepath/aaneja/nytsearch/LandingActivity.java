package com.codepath.aaneja.nytsearch;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.codepath.aaneja.nytsearch.adapters.DocAdapter;
import com.codepath.aaneja.nytsearch.helpers.ItemClickSupport;
import com.codepath.aaneja.nytsearch.helpers.SpacesItemDecoration;
import com.codepath.aaneja.nytsearch.models.Doc;
import com.codepath.aaneja.nytsearch.models.SearchParams;
import com.codepath.aaneja.nytsearch.services.NYTArticleSearch;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LandingActivity extends AppCompatActivity implements SetSearchFiltersDialogFragment.SetSearchFiltersDialogFragmentListener {
    private List<Doc> searchedDocs = new ArrayList<>(10);
    private DocAdapter docAdapter = new  DocAdapter(searchedDocs);
    public SearchParams searchParams = new SearchParams();
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;

    @Override
    public void onFinishSettingParamsDialog(SearchParams searchParams) {
        this.searchParams = searchParams;
        onSearchButtonClick(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_landing,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miSearchFilters:
                CreateSeachFilterDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void CreateSeachFilterDialog() {
        SetSearchFiltersDialogFragment setSearchParams = SetSearchFiltersDialogFragment.newInstance(searchParams);
        setSearchParams.Listener = this;
        setSearchParams.show(getSupportFragmentManager(),"fm_set_searchParams");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        RecyclerView rvArticles =  (RecyclerView) findViewById(R.id.rvArticles);
        rvArticles.setAdapter(docAdapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rvArticles.setLayoutManager(layoutManager);

        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.i("SCROLLTONEWPAGE", String.valueOf(page));
                searchParams.Page = page;
                searchParams.AppendToResults = true;
                getSearchDocsUpdateTask().execute(searchParams);
            }
        };
        rvArticles.addOnScrollListener(endlessRecyclerViewScrollListener);

        ItemClickSupport.addTo(rvArticles).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
                String url = searchedDocs.get(position).webUrl;
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                // set toolbar color and/or setting custom actions before invoking build()
                // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
                CustomTabsIntent customTabsIntent = builder.build();
                // and launch the desired Url with CustomTabsIntent.launchUrl()
                customTabsIntent.launchUrl(v.getContext(), Uri.parse(url));
            }
        });

        rvArticles.addItemDecoration(new SpacesItemDecoration(5));

        getSearchDocsUpdateTask().execute(searchParams);
    }

    @NonNull
    private AsyncTask<SearchParams, Integer, List<Doc>> getSearchDocsUpdateTask() {
        return new AsyncTask<SearchParams, Integer, List<Doc>>() {

            private boolean AppendToResults;
            private final NYTArticleSearch nytArticleSearch = new NYTArticleSearch();

            @Override
            protected List<Doc> doInBackground(SearchParams... searchItem) {

                if (searchItem.length != 1)
                {
                    //throw new Exception("params must have exactly one item");
                }

                this.AppendToResults = searchItem[0].AppendToResults;

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
                if(AppendToResults) {
                    int beforeAddNewCount = searchedDocs.size();
                    searchedDocs.addAll(newResults);
                    Log.i("NEW_ITEMS/APPENDING", String.valueOf(newResults.size()));
                    docAdapter.notifyItemRangeInserted(beforeAddNewCount, newResults.size());
                }
                else {
                    endlessRecyclerViewScrollListener.resetState();
                    searchedDocs.clear();
                    searchedDocs.addAll(newResults);
                    Log.i("NEW_ITEMS/CLEAR_SET", String.valueOf(newResults.size()));
                    docAdapter.notifyDataSetChanged();
                }
            }
        };
    }

    public void onSearchButtonClick(View view) {
        EditText etSearch = (EditText) findViewById(R.id.etSearch);
        String newSearchTerm =  etSearch.getText().toString();

        //Begin a new search for this new term
        endlessRecyclerViewScrollListener.resetState();
        searchParams.Page = 0;
        searchParams.AppendToResults = false;
        searchParams.SearchTerm = newSearchTerm;

        getSearchDocsUpdateTask().execute(searchParams);
    }


}
