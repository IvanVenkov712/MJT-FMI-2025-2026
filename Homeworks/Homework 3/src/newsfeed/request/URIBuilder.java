package newsfeed.request;

import newsfeed.apikey.ApiKeyProvider;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class URIBuilder {
    private static final String ENDPOINT = "https://newsapi.org/v2/top-headlines";
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
            .map(e -> e.getKey() + "=" + e.getValue())
            .collect(Collectors.joining("&"));
    }

    public URI createUri() {
        Map<String, String> params = new HashMap<>();
        params.put("apiKey", keyProvider.getApiKey());
        params.put("q", keywords);
        if (country != null) {
            params.put("country", country.toString());
        }
        if (category != null) {
            params.put("category", category.toString());
        }
        String queryStr = getQueryString(params);
        return URI.create(ENDPOINT + "?" + queryStr);
    }
}