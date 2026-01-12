package newsfeed.search;

import newsfeed.apikey.ApiKeyProvider;
import newsfeed.exception.NewsFeedException;
import newsfeed.request.Category;
import newsfeed.request.Country;
import newsfeed.request.RequestBuilder;
import newsfeed.response.NewsFeedResponse;
import newsfeed.response.STATUS;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NewsFeedSearcher {
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private final ApiKeyProvider keyProvider;

    public NewsFeedSearcher(ApiKeyProvider keyProvider) {
        this.keyProvider = keyProvider;
    }

    public NewsFeedResponse searchArticles(String keywords, Category category, Country country)
        throws NewsFeedException {
        HttpRequest request = new RequestBuilder(keywords, category, country, keyProvider).createRequest();
        NewsFeedResponse responseObj;
        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            responseObj = NewsFeedResponse.getFromJSON(response.body());
        } catch (IOException e) {
            throw new NewsFeedException("an io exception was thrown", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (responseObj.status() != STATUS.OK) {
            throw new NewsFeedException(responseObj.code());
        }
        return responseObj;
    }

}
