package exception;

public class CredentialsNotExistException extends PasswordVaultException {
    public CredentialsNotExistException(String message) {
        super(message);
    }

    public CredentialsNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
