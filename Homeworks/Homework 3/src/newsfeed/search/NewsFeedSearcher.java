package newsfeed.search;

import com.google.gson.Gson;
import newsfeed.apikey.ApiKeyProvider;
import newsfeed.exception.NewsFeedException;
import newsfeed.request.Category;
import newsfeed.request.Country;
import newsfeed.request.URIBuilder;
import newsfeed.response.NewsFeedResponse;
import newsfeed.response.Status;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NewsFeedSearcher {

    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int DEFAULT_MAX_RESULTS = 100;
    private static final int FIRST_PAGE = 1;

    private final HttpClient httpClient;
    private final ApiKeyProvider keyProvider;

    public NewsFeedSearcher(ApiKeyProvider keyProvider) {
        this(HttpClient.newHttpClient(), keyProvider);
    }

    public NewsFeedSearcher(HttpClient httpClient, ApiKeyProvider keyProvider) {
        this.httpClient = httpClient;
        this.keyProvider = keyProvider;
    }

    public NewsFeedResponse searchArticles(String keywords, Category category, Country country, int page)
        throws NewsFeedException {

        URIBuilder builder = new URIBuilder(keywords, category, country, keyProvider);
        HttpRequest request = HttpRequest.newBuilder(builder.createUri(DEFAULT_PAGE_SIZE, page)).build();

        NewsFeedResponse responseObj;
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            responseObj = gson.fromJson(response.body(), NewsFeedResponse.class);
        } catch (Exception e) {
            throw new NewsFeedException("an exception was thrown", e);
        }
        if (responseObj.status() == Status.ERROR) {
            throw new NewsFeedException(String.format("%s: %s", responseObj.code(), responseObj.message()));
        }
        return responseObj;
    }

    public NewsFeedResponse searchArticlesAll(String keywords, Category category, Country country)
        throws NewsFeedException {
        int currPage = FIRST_PAGE;
        NewsFeedResponse response = searchArticles(keywords, category, country, FIRST_PAGE);
        int remainingResults = Math.min(response.totalResults(), DEFAULT_MAX_RESULTS) - response.articles().size();
        while (remainingResults > 0) {
            ++currPage;
            NewsFeedResponse pageResponse = searchArticles(keywords, category, country, currPage);
            response.articles().addAll(pageResponse.articles());
            if (pageResponse.articles().isEmpty()) {
                return response;
            }
            remainingResults -= pageResponse.articles().size();
        }
        return response;
    }

}
