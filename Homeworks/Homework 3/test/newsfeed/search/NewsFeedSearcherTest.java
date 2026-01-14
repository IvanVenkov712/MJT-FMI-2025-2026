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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NewsFeedSearcherTest {
    private static final Article DEFAULT_ARTICLE = new Article(null, null, null, null, null, null, null, null);

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
            new URIBuilder("a", Category.BUSINESS, Country.US, provider).createUri(20,1 )
        ).build();

        when(client.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(response);
        NewsFeedResponse responseObjActual = searcher.searchArticles("a", Category.BUSINESS, Country.US, 1);
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
            new URIBuilder("a", Category.BUSINESS, Country.US, provider).createUri(20, 1)
        ).build();

        when(client.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(response);
        assertThrows(NewsFeedException.class, () -> searcher.searchArticles("a", Category.BUSINESS, Country.US, 1), "NewsFeedException expected");
        verify(client).send(request, HttpResponse.BodyHandlers.ofString());
        verify(provider, atLeastOnce()).getApiKey();
    }

    @Test
    void testSearchArticlesAllTwoPages() throws Exception{
        Gson gson = new Gson();

        HttpRequest request1 = HttpRequest.newBuilder(
            new URIBuilder("a", Category.BUSINESS, Country.US, provider).createUri(20,1 )
        ).build();

        HttpRequest request2 = HttpRequest.newBuilder(
            new URIBuilder("a", Category.BUSINESS, Country.US, provider).createUri(20,2)
        ).build();

        List<Article> list1 = List.of(DEFAULT_ARTICLE, DEFAULT_ARTICLE);
        List<Article> list2 = List.of(DEFAULT_ARTICLE, DEFAULT_ARTICLE, DEFAULT_ARTICLE);

        NewsFeedResponse responseObj1 = new NewsFeedResponse(Status.OK, null, null, 5, list1);
        HttpResponse<String> response1 = mock();
        when(response1.body()).thenReturn(gson.toJson(responseObj1));

        NewsFeedResponse responseObj2 = new NewsFeedResponse(Status.OK, null, null, 5, list2);
        HttpResponse<String> response2 = mock();
        when(response2.body()).thenReturn(gson.toJson(responseObj2));

        client = mock();
        when(client.send(request1, HttpResponse.BodyHandlers.ofString())).thenReturn(response1);
        when(client.send(request2, HttpResponse.BodyHandlers.ofString())).thenReturn(response2);

        searcher = new NewsFeedSearcher(client, provider);

        NewsFeedResponse responseObjActual = searcher.searchArticlesAll("a", Category.BUSINESS, Country.US);
        verify(client).send(request1, HttpResponse.BodyHandlers.ofString());
        verify(client).send(request2, HttpResponse.BodyHandlers.ofString());
        verify(provider, atLeastOnce()).getApiKey();

        List<Article> list3 = new ArrayList<>();
        list3.addAll(list1);
        list3.addAll(list2);

        NewsFeedResponse expected = new NewsFeedResponse(
            Status.OK, null, null, 5, list3
        );

        assertEquals(expected, responseObjActual, "Not what was expected");
    }
}