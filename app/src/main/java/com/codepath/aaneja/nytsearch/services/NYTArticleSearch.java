package com.codepath.aaneja.nytsearch.services;

import com.codepath.aaneja.nytsearch.models.Article;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static final String DeveloperKey = "786fe47ff99f463e92dd0595ac70a308";
    private OkHttpClient Client = new OkHttpClient();

    public List<Article> GetArticles(String searchTerm) throws IOException, JSONException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.nytimes.com/svc/search/v2/articlesearch.json").newBuilder();
        urlBuilder.addQueryParameter("api-key", DeveloperKey);
        urlBuilder.addQueryParameter("q", searchTerm);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;

        response = Client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        return HydrateIntoResults(response);
    }

    private List<Article> HydrateIntoResults(Response response) throws JSONException, IOException {
        String responseData = response.body().string();
        JSONObject json = new JSONObject(responseData);
        final String owner = json.getString("name");
    }

}
