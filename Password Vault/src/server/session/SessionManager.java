package server.session;

import exception.UserAlreadyLoggedException;
import exception.UserNotExistsException;
import exception.UserNotLoggedInException;
import exception.WrongPasswordException;

import java.nio.channels.SelectionKey;

public interface SessionManager {
    void loginUser(Session session, String userName, String password)
        throws UserNotExistsException, WrongPasswordException,
        UserAlreadyLoggedException;

    void logoutUser(Session session) throws UserNotLoggedInException;

    Session getSession(SelectionKey channel);

    public boolean sessionExists(SelectionKey channel);
}
