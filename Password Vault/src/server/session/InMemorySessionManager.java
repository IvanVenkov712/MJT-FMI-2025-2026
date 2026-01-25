package server.session;

import exception.UserAlreadyLoggedException;
import exception.UserNotExistsException;
import exception.UserNotLoggedInException;
import exception.WrongPasswordException;
import model.User;
import server.authentication.UserAuthenticator;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemorySessionManager implements SessionManager {
    private final Map<SelectionKey, Session> sessions;
    private final UserAuthenticator authenticator;

    InMemorySessionManager(Map<SelectionKey, Session> sessions, UserAuthenticator authenticator) {
        this.sessions = sessions;
        this.authenticator = authenticator;
    }

    public InMemorySessionManager(UserAuthenticator authenticator) {
        this(new ConcurrentHashMap<>(), authenticator);
    }

    @Override
    public void loginUser(Session session, String user, String password)
        throws UserNotExistsException, WrongPasswordException, UserAlreadyLoggedException {
        if (sessions.containsKey(session.channel())) {
            throw new UserAlreadyLoggedException("This user has already been logged in!");
        }
        authenticator.authenticate(user, password);
        sessions.put(session.channel(), new Session(session.channel(), new User(user), true));
    }

    @Override
    public void logoutUser(Session session) throws UserNotLoggedInException {
        if (!sessions.containsKey(session.channel()) || !session.loggedIn()) {
            throw new UserNotLoggedInException("The user is not logged in!");
        }
        sessions.remove(session.channel());
    }

    @Override
    public Session getSession(SelectionKey key) {
        return sessions.get(key);
    }

    @Override
    public boolean sessionExists(SelectionKey key) {
        return sessions.containsKey(key);
    }
}
