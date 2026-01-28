package server.session;

import exception.UserAlreadyLoggedException;
import exception.UserNotExistsException;
import exception.UserNotLoggedInException;
import exception.WrongPasswordException;
import model.User;
import password.hash.PasswordHasher;

import java.nio.channels.SelectionKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemorySessionManager implements SessionManager {
    private final Map<SelectionKey, Session> sessions;
    private final Map<String, User> users;
    private final PasswordHasher hasher;

    InMemorySessionManager(Map<SelectionKey, Session> sessions, Map<String, User> users, PasswordHasher hasher) {
        this.sessions = sessions;
        this.users = users;
        this.hasher = hasher;
    }

    public InMemorySessionManager(Map<String, User> users, PasswordHasher hasher) {
        this(new ConcurrentHashMap<>(), users, hasher);
    }

    @Override
    public void loginUser(Session session, String userName, String password)
        throws UserNotExistsException, WrongPasswordException, UserAlreadyLoggedException {
        if (session == null || userName == null || password == null) {
            throw new IllegalArgumentException("null args are not allowed");
        }
        if (sessions.containsKey(session.selectionKey())) {
            throw new UserAlreadyLoggedException("This user has already been logged in!");
        }
        if (!users.containsKey(userName)) {
            throw new UserNotExistsException("This user is not registered!");
        }
        User user = users.get(userName);
        if (!hasher.getHash(password).equals(user.hashedPassword())) {
            throw new WrongPasswordException("The password you typed was wrong");
        }
        sessions.put(session.selectionKey(), new Session(session.selectionKey(), user, true));
    }

    @Override
    public void logoutUser(Session session) throws UserNotLoggedInException {
        if (!sessions.containsKey(session.selectionKey()) || !session.loggedIn()) {
            throw new UserNotLoggedInException("The user is not logged in!");
        }
        sessions.remove(session.selectionKey());
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
