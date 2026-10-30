# Homework 3: News Feed

A news-headline client built with Java's HTTP client and Gson. It builds search URIs, maps JSON responses to Java records, and collects results across pages.

## Structure

Sources are under `src/newsfeed/`.

- `apikey/` contains the `ApiKeyProvider` contract and `ApiKeyProviderFromFile`.
- `request/URIBuilder` builds requests for the NewsAPI `v2/top-headlines` endpoint, with keywords and optional category and country filters.
- `search/NewsFeedSearcher` executes requests, parses responses, and handles pagination.
- `response/` contains article, source, response, and status models.
- `exception/NewsFeedException` represents request or API response failures.

`searchArticles(...)` requests a single page with a page size of 20. `searchArticlesAll(...)` fetches additional pages toward a maximum-results target of 100, stopping if a page is empty.

## Setup and use

Use JDK 25 and add Gson (`com.google.code.gson:gson`) to the compile and runtime classpaths. See the [repository setup and compilation instructions](../../README.md#setup). Gson is referenced by the module but is not bundled. There is no console entry point.

For live requests, provide a NewsAPI key through `ApiKeyProvider`. To use the existing file-based provider, put only the key in a local file named `apikey` in this homework directory, without a trailing newline. The provider reads the entire file without trimming it. This filename is ignored by the project's `.gitignore`.

The following can be used in a driver program or JShell with this homework as the working directory and Gson on the classpath:

```java
import java.nio.file.Path;
import newsfeed.apikey.ApiKeyProviderFromFile;
import newsfeed.request.Category;
import newsfeed.request.Country;
import newsfeed.search.NewsFeedSearcher;

var provider = new ApiKeyProviderFromFile(Path.of("apikey"));
var searcher = new NewsFeedSearcher(provider);
var response = searcher.searchArticles("Java", Category.TECHNOLOGY, Country.US, 1);
System.out.println(response.articles());
```

Keywords and the API key must be non-blank. Category and country may be `null`. Page numbering starts at 1. `NewsFeedSearcher` also accepts an injected `HttpClient` for testing.

## Tests

Mark `test/` as a test source root and configure JUnit Jupiter and Mockito, with Gson available to the module. Run the tests in the IDE.

The suites cover file-based key loading, URI creation, response handling, errors, and multi-page searches. HTTP interactions are mocked, and key-file tests use temporary files, so these tests do not require a live API key or network requests.

[Repository overview](../../README.md)
