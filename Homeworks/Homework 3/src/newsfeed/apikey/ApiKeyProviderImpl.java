package newsfeed.apikey;

public class ApiKeyProviderImpl implements ApiKeyProvider {
    private static final String API_KEY = "d9db3b765e464d1bacda982966501513";

    @Override
    public String getApiKey() {
        return API_KEY;
    }
}