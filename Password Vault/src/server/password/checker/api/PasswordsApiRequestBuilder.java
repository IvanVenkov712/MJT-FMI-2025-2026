package server.password.checker.api;

import com.google.gson.Gson;
import server.password.checker.api.model.request.PasswordApiRequestBody;
import server.password.hash.SHA256PasswordHasher;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Collectors;

public class PasswordsApiRequestBuilder {
    private static final String ENDPOINT = "https://api.enzoic.com/v1/passwords";
    private static final String EQUAL_SIGN = "=";
    private static final String QUERY_SEPARATOR = "&";
    private static final int HASH_SYMBOLS_COUNT = 10;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";
    private static final String CONTENT_TYPE_HEADER_VALUE = "application/json";
    private static final String ACCEPT_HEADER_NAME = "Accept";
    private static final String ACCEPT_HEADER_VALUE = "*/*";
    private static final String COLON = ":";
    private static final String BASIC = "basic";

    private final String apiKey;
    private final String apiSecret;
    private final Gson gson;

    private final String authorizationEncoded;
    private final String authorizationHeaderString;

    public PasswordsApiRequestBuilder(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        gson = new Gson();
        authorizationEncoded =
            Base64.getEncoder().encodeToString((apiKey + COLON + apiSecret).getBytes(StandardCharsets.UTF_8));

        authorizationHeaderString = BASIC + " " + authorizationEncoded;
    }

    private String getQueryString(Map<String, String> params) {
        return params.entrySet().stream()
            .filter(e -> e.getKey() != null && e.getValue() != null && !e.getKey().isEmpty() && !e.getValue().isEmpty())
            .map(e -> e.getKey() + EQUAL_SIGN + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
            .collect(Collectors.joining(QUERY_SEPARATOR));
    }

    public HttpRequest getRequest(String password) {
        String passwordHash = new SHA256PasswordHasher().getHash(password).substring(0, HASH_SYMBOLS_COUNT);
        PasswordApiRequestBody requestBody = new PasswordApiRequestBody(passwordHash);
        String toJson = gson.toJson(requestBody);

        return HttpRequest.newBuilder(URI.create(ENDPOINT))
            .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(requestBody)))
            .header(AUTHORIZATION_HEADER, authorizationHeaderString)
            .header(CONTENT_TYPE_HEADER_NAME, CONTENT_TYPE_HEADER_VALUE)
            .header(ACCEPT_HEADER_NAME, ACCEPT_HEADER_VALUE)
            .build();
    }
}
