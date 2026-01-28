package exception;

public class WebsiteAlreadyExists extends PasswordVaultException {
    public WebsiteAlreadyExists(String message) {
        super(message);
    }

    public WebsiteAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }
}
