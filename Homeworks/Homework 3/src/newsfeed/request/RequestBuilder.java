package newsfeed.request;

import newsfeed.apikey.ApiKeyProvider;

import java.net.URI;
import java.net.http.HttpRequest;

public class RequestBuilder {
    private static final String ENTRY_POINT = "https://newsapi.org/v2/top-headlines";
    private final String keywords;
    private final Category category;
    private final Country country;
    private final ApiKeyProvider keyProvider;

    public RequestBuilder(String keywords, Category category, Country country, ApiKeyProvider keyProvider) {
        this.keywords = keywords;
        this.category = category;
        this.country = country;
        this.keyProvider = keyProvider;
    }

    private <T> String asUrlParameter(String name, T obj) {
        if (obj == null) {
            return "";
        }
        return String.format("%s=%s", name, obj);
    }

    public String getQueryString(Object... pairs) {
        if (pairs.length == 0) {
            return "";
        }
        if (pairs.length % 2 != 0) {
            throw new IllegalArgumentException("arg format: name, obj pairs");
        }
        StringBuilder sb = new StringBuilder(asUrlParameter(pairs[0].toString(), pairs[1]));
        for (int i = 2; i < pairs.length - 1; i += 2) {
            String param = asUrlParameter(pairs[i].toString(), pairs[i + 1]);
            if (!param.isEmpty()) {
                sb.append("&").append(param);
            }
        }
        return sb.toString();
    }

    private URI createUri() {
        String queryStr = getQueryString(
            "apiKey", keyProvider.getApiKey(),
            "q", keywords,
            "country", country,
            "category", category
        );
        return URI.create(ENTRY_POINT + "?" + queryStr);
    }

    public HttpRequest createRequest() {
        return HttpRequest.newBuilder(createUri()).build();
    }
}
