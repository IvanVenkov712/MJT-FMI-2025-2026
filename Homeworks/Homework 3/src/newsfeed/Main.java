package newsfeed;

import com.google.gson.Gson;
import newsfeed.apikey.ApiKeyProvider;
import newsfeed.apikey.ApiKeyProviderFromFile;
import newsfeed.exception.NewsFeedException;
import newsfeed.request.Category;
import newsfeed.request.Country;
import newsfeed.response.NewsFeedResponse;
import newsfeed.search.NewsFeedSearcher;

import java.nio.file.Path;

public class Main {
    static void main() throws NewsFeedException {
        ApiKeyProvider provider = new ApiKeyProviderFromFile(Path.of("apikey"));
        NewsFeedSearcher searcher = new NewsFeedSearcher(provider);
        NewsFeedResponse response = searcher.searchArticlesAll("a", Category.GENERAL, Country.US);
        Gson gson = new Gson();
        System.out.println(gson.toJson(response));
    }
}