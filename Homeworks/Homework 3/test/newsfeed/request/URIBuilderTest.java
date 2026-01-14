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
        URI res = builder.createUri(20, 1);
        assertEquals(URI.create(ENDPOINT + "?apiKey=myOwnKey&q=trump&country=us&category=business&pageSize=20&page=1"), res, "Not what was expected");
    }

    @Test
    void testCreateUriNoCountry() {
        builder = new URIBuilder("trump", Category.BUSINESS, null, provider);
        URI res = builder.createUri(20, 1);
        assertEquals(URI.create(ENDPOINT + "?apiKey=myOwnKey&q=trump&category=business&pageSize=20&page=1"), res, "Not what was expected");
    }

    @Test
    void testCreateUriNoCountryNoCategory() {
        builder = new URIBuilder("trump", null, null, provider);
        URI res = builder.createUri(20, 1);
        assertEquals(URI.create(ENDPOINT + "?apiKey=myOwnKey&q=trump&pageSize=20&page=1"), res, "Not what was expected");
    }

    @Test
    void testCreateUriAllParamsSpaces() {
        builder = new URIBuilder("trump putin", Category.BUSINESS, Country.US, provider);
        URI res = builder.createUri(20, 1);
        assertEquals(URI.create(ENDPOINT + "?apiKey=myOwnKey&q=trump+putin&country=us&category=business&pageSize=20&page=1"), res, "Not what was expected");
    }

    @Test
    void testCreateUriEmptyKeywords() {
        builder = new URIBuilder("   ", Category.BUSINESS, Country.US, provider);
        assertThrows(IllegalArgumentException.class, () ->  builder.createUri(20, 1), "IllegalArgumentException expected for empty or null keywords");
    }

    @Test
    void testCreateUriEmptyApiKey() {
        ApiKeyProvider newProvider = mock();
        when(newProvider.getApiKey()).thenReturn("    ");
        builder = new URIBuilder("trump putin", Category.BUSINESS, Country.US, newProvider);
        assertThrows(IllegalArgumentException.class, () ->  builder.createUri(20, 1), "IllegalArgumentException expected for empty or null apiKey");
    }
}