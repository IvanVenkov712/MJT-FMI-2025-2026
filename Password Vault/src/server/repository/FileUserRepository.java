package server.repository;

import exception.CredentialsNotExistException;
import exception.PasswordNotSecureException;
import exception.UserAlreadyExistsException;
import exception.UserAlreadyLoggedException;
import exception.UserNotExistsException;
import exception.UserNotLoggedInException;
import exception.WrongPasswordException;
import server.session.Session;

public class FileUserRepository implements UserRepository {
    @Override
    public void registerUser(Session session, String user, String password, String passwordRepeat)
        throws UserAlreadyExistsException, UserAlreadyLoggedException {

    }

    @Override
    public void loginUser(Session session, String user, String password)
        throws UserNotExistsException, WrongPasswordException, UserAlreadyLoggedException {

    }

    @Override
    public void logoutUser(Session session) throws UserNotLoggedInException {

    }

    @Override
    public void addPassword(Session session, String website, String user, String password)
        throws PasswordNotSecureException {

    }

    @Override
    public String generatePassword(Session session, String website, String userName) throws UserNotLoggedInException {
        return "";
    }

    @Override
    public String retrieveCredentials(Session session, String website, String userName)
        throws UserNotLoggedInException, CredentialsNotExistException {
        return "";
    }

    @Override
    public void removePassword(Session session, String website, String userName)
        throws CredentialsNotExistException, UserNotLoggedInException {

    }
}
