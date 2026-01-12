package newsfeed.request;

import newsfeed.apikey.ApiKeyProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class URIBuilderTest {
    private static final String ENDPOINT = "https://newsapi.org/v2/top-headlines";
    URIBuilder builder;
    static ApiKeyProvider provider;

    @BeforeAll
    static void setUp() {
        provider = mock();
        when(provider.getApiKey()).thenReturn("myOwnKey");
    }

    @Test
    void testCreateUriAllParams() {
        builder = new URIBuilder("trump", Category.BUSINESS, Country.US, provider);
        URI res = builder.createUri();
        assertEquals(URI.create(ENDPOINT + "?q=trump&country=us&apiKey=myOwnKey&category=business"), res, "Not what was expected");
    }

    @Test
    void testCreateUriNoCountry() {
        builder = new URIBuilder("trump", Category.BUSINESS, null, provider);
        URI res = builder.createUri();
        assertEquals(URI.create(ENDPOINT + "?q=trump&apiKey=myOwnKey&category=business"), res, "Not what was expected");
    }

    @Test
    void testCreateUriNoCountryNoCategory() {
        builder = new URIBuilder("trump", null, null, provider);
        URI res = builder.createUri();
        assertEquals(URI.create(ENDPOINT + "?q=trump&apiKey=myOwnKey"), res, "Not what was expected");
    }
}