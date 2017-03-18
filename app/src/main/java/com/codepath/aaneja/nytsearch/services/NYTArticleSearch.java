package com.codepath.aaneja.nytsearch.services;

import com.codepath.aaneja.nytsearch.models.ArticleSearchResponse;
import com.codepath.aaneja.nytsearch.models.Doc;
import com.codepath.aaneja.nytsearch.models.SearchParams;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by aaneja on 14/03/17.
 */

public class NYTArticleSearch {

    private static final String DeveloperKey = "786fe47ff99f463e92dd0595ac70a308";
    private static final Gson GSON = new Gson();
    private OkHttpClient Client = new OkHttpClient();

    public List<Doc> GetArticles(SearchParams searchParams) throws IOException, JSONException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.nytimes.com/svc/search/v2/articlesearch.json").newBuilder();
        urlBuilder.addQueryParameter("api-key", DeveloperKey);

        if(searchParams !=null)  {
            if(searchParams.SearchTerm !=null && searchParams.SearchTerm != "") {
                urlBuilder.addQueryParameter("q", searchParams.SearchTerm);
            }
            urlBuilder.addQueryParameter("page", String.valueOf(searchParams.Page));
        }

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = Client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        return HydrateIntoResults(response);
    }

    private List<Doc> HydrateIntoResults(Response response) throws JSONException, IOException {
        ArticleSearchResponse parsedResponse = GSON.fromJson(response.body().charStream(), ArticleSearchResponse.class);
        return parsedResponse.response.docs;
    }

}
