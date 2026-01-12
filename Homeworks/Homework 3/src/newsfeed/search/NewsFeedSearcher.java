package newsfeed.search;

import com.google.gson.Gson;
import newsfeed.apikey.ApiKeyProvider;
import newsfeed.exception.NewsFeedException;
import newsfeed.request.Category;
import newsfeed.request.Country;
import newsfeed.request.URIBuilder;
import newsfeed.response.NewsFeedResponse;
import newsfeed.response.Status;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NewsFeedSearcher {
    private final HttpClient httpClient;
    private final ApiKeyProvider keyProvider;

    public NewsFeedSearcher(ApiKeyProvider keyProvider) {
        this(HttpClient.newHttpClient(), keyProvider);
    }

    public NewsFeedSearcher(HttpClient httpClient, ApiKeyProvider keyProvider) {
        this.httpClient = httpClient;
        this.keyProvider = keyProvider;
    }

    public NewsFeedResponse searchArticles(String keywords, Category category, Country country)
        throws NewsFeedException {
        HttpRequest request = HttpRequest.newBuilder(
            new URIBuilder(keywords, category, country, keyProvider).createUri()
        ).build();

        NewsFeedResponse responseObj;
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            responseObj = gson.fromJson(response.body(), NewsFeedResponse.class);
        } catch (Exception e) {
            throw new NewsFeedException("an exception was thrown", e);
        }
        if (responseObj.status() == Status.ERROR) {
            throw new NewsFeedException(responseObj.code());
        }
        return responseObj;
    }

}
