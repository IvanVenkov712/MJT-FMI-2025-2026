package server.repository;

import exception.PasswordNotSecureException;
import exception.PasswordsNotMatchException;
import exception.UserAlreadyExistsException;
import exception.UserAlreadyLoggedException;
import exception.UserNotExistsException;
import exception.UserNotLoggedInException;
import exception.CredentialsNotExistException;
import exception.WebsiteAlreadyExists;
import exception.WebsiteNotExistsException;
import exception.WrongPasswordException;

import java.nio.channels.SelectionKey;

public interface UserRepository {
    void registerUser(SelectionKey selectionKey, String user, String password, String passwordRepeat)
        throws UserAlreadyExistsException, UserAlreadyLoggedException, PasswordsNotMatchException;

    void loginUser(SelectionKey selectionKey, String user, String password)
        throws UserNotExistsException, WrongPasswordException,
        UserAlreadyLoggedException;

    void logoutUser(SelectionKey selectionKey) throws UserNotLoggedInException;

    void addPassword(SelectionKey selectionKey, String website, String user, String password)
        throws PasswordNotSecureException, UserNotLoggedInException, WebsiteAlreadyExists;

    String generatePassword(SelectionKey selectionKey, String website, String userName)
        throws UserNotLoggedInException, WebsiteAlreadyExists, PasswordNotSecureException;

    String retrieveCredentials(SelectionKey selectionKey, String website, String userName)
        throws UserNotLoggedInException, CredentialsNotExistException, WebsiteNotExistsException;

    void removePassword(SelectionKey selectionKey, String website, String userName)
        throws CredentialsNotExistException, UserNotLoggedInException, WebsiteNotExistsException;

    void saveChanges();

}
