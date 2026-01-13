package newsfeed.request;

import newsfeed.apikey.ApiKeyProvider;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class URIBuilder {
    private static final String ENDPOINT = "https://newsapi.org/v2/top-headlines";
    private static final String EQUAL_SIGN = "=";
    private static final String QUERY_SEPARATOR = "&";
    private static final String KEY_API_KEY = "apiKey";
    private static final String KEY_KEYWORDS = "q";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_CATEGORY = "category";
    private static final String PATH_QUERY_SEPARATOR = "?";
    private static final String PAGE = "page";
    private static final String PAGE_SIZE = "pageSize";

    private final String keywords;
    private final Category category;
    private final Country country;
    private final ApiKeyProvider keyProvider;

    public URIBuilder(String keywords, Category category, Country country, ApiKeyProvider keyProvider) {
        this.keywords = keywords;
        this.category = category;
        this.country = country;
        this.keyProvider = keyProvider;
    }

    private String getQueryString(Map<String, String> params) {
        return params.entrySet().stream()
            .filter(e -> e.getKey() != null && e.getValue() != null && !e.getKey().isEmpty() && !e.getValue().isEmpty())
            .map(e -> e.getKey() + EQUAL_SIGN + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
            .collect(Collectors.joining(QUERY_SEPARATOR));
    }

    public URI createUri(int pageSize, int page) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put(KEY_API_KEY, keyProvider.getApiKey());
        params.put(KEY_KEYWORDS, keywords);
        if (country != null) {
            params.put(KEY_COUNTRY, country.toString());
        }
        if (category != null) {
            params.put(KEY_CATEGORY, category.toString());
        }
        params.put(PAGE_SIZE, String.valueOf(pageSize));
        params.put(PAGE, String.valueOf(page));
        String queryStr = getQueryString(params);
        return URI.create(ENDPOINT + PATH_QUERY_SEPARATOR + queryStr);
    }
}