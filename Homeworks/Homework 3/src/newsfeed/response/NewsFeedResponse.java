package newsfeed.response;

import com.google.gson.Gson;

public record NewsFeedResponse(STATUS status, String code, String message,  int totalResults, Article[] articles) {
    public static NewsFeedResponse getFromJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, NewsFeedResponse.class);
    }

    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}