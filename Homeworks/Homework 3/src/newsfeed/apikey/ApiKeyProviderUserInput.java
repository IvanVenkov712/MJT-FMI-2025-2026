package newsfeed.apikey;

import java.util.Scanner;

public class ApiKeyProviderUserInput implements ApiKeyProvider {
    private final String apiKey;

    public ApiKeyProviderUserInput() {
        System.out.println("Enter your api key: ");
        Scanner scanner = new Scanner(System.in);
        this.apiKey = scanner.nextLine();
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }
}
