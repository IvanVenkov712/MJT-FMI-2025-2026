package newsfeed.response;

import java.util.List;

public record NewsFeedResponse(Status status, String code, String message, int totalResults, List<Article> articles) { }