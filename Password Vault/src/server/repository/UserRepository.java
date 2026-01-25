package server.repository;

import exception.PasswordNotSecureException;
import exception.UserAlreadyExistsException;
import exception.UserAlreadyLoggedException;
import exception.UserNotExistsException;
import exception.UserNotLoggedInException;
import exception.CredentialsNotExistException;
import exception.WrongPasswordException;
import server.session.Session;

public interface UserRepository {
    void registerUser(Session session, String user, String password, String passwordRepeat)
        throws UserAlreadyExistsException, UserAlreadyLoggedException;

    void loginUser(Session session, String user, String password) throws UserNotExistsException, WrongPasswordException,
        UserAlreadyLoggedException;

    void logoutUser(Session session) throws UserNotLoggedInException;

    void addPassword(Session session, String website, String user, String password) throws PasswordNotSecureException;

    String generatePassword(Session session, String website, String userName) throws UserNotLoggedInException;

    String retrieveCredentials(Session session, String website, String userName) throws UserNotLoggedInException,
        CredentialsNotExistException;

    void removePassword(Session session, String website, String userName)
        throws CredentialsNotExistException, UserNotLoggedInException;

}
