package newsfeed.search;

import com.google.gson.Gson;
import newsfeed.apikey.ApiKeyProvider;
import newsfeed.exception.NewsFeedException;
import newsfeed.request.Category;
import newsfeed.request.Country;
import newsfeed.request.URIBuilder;
import newsfeed.response.Article;
import newsfeed.response.NewsFeedResponse;
import newsfeed.response.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NewsFeedSearcherTest {
    NewsFeedSearcher searcher;
    HttpClient client;
    static ApiKeyProvider provider;
    HttpResponse<String> response;

    @BeforeAll
    static void setUp() {
        provider = mock();
        when(provider.getApiKey()).thenReturn("myApiKey");
    }

    @Test
    void testSearchArticleOK() throws Exception {
        Gson gson = new Gson();
        NewsFeedResponse responseObjExpected = new NewsFeedResponse(Status.OK, null, null, 0, List.of());
        response = mock();
        when(response.body()).thenReturn(gson.toJson(responseObjExpected));
        client = mock();
        searcher = new NewsFeedSearcher(client, provider);
        HttpRequest request = HttpRequest.newBuilder(
            new URIBuilder("a", Category.BUSINESS, Country.US, provider).createUri()
        ).build();

        when(client.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(response);
        NewsFeedResponse responseObjActual = searcher.searchArticles("a", Category.BUSINESS, Country.US);
        verify(client).send(request, HttpResponse.BodyHandlers.ofString());
        verify(provider, atLeastOnce()).getApiKey();
        assertEquals(responseObjExpected, responseObjActual, "Not what was expected");
    }

    @Test
    void testSearchArticleERROR() throws Exception {
        Gson gson = new Gson();
        NewsFeedResponse responseObjExpected = new NewsFeedResponse(Status.ERROR, "unexpectedError", "", 0, List.of());
        response = mock();
        when(response.body()).thenReturn(gson.toJson(responseObjExpected));
        client = mock();
        searcher = new NewsFeedSearcher(client, provider);
        HttpRequest request = HttpRequest.newBuilder(
            new URIBuilder("a", Category.BUSINESS, Country.US, provider).createUri()
        ).build();

        when(client.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(response);
        assertThrows(NewsFeedException.class, () -> searcher.searchArticles("a", Category.BUSINESS, Country.US), "NewsFeedException expected");
        verify(client).send(request, HttpResponse.BodyHandlers.ofString());
        verify(provider, atLeastOnce()).getApiKey();
    }

}