package server.repository;

import exception.WebsiteUsernameNotExistsException;
import exception.PasswordNotSecureException;
import exception.PasswordsNotMatchException;
import exception.UserAlreadyExistsException;
import exception.UserLoggedInException;
import exception.UserNotExistsException;
import exception.UserNotLoggedInException;
import exception.WebsiteExistingException;
import exception.WebsiteNotExistsException;
import exception.IncorrectPasswordException;
import server.model.User;
import server.model.WebsitePair;
import server.password.checker.PasswordChecker;
import server.password.encryptor.SymmetricEncryptor;
import server.password.generator.PasswordGenerator;
import server.password.hash.PasswordHasher;
import server.session.Session;

import java.nio.channels.SelectionKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractUserRepository implements UserRepository {
    private final Map<String, User> users;
    private final Map<SelectionKey, Session> sessions;
    private final PasswordHasher hasher;
    private final SymmetricEncryptor encryptor;
    private final PasswordChecker checker;
    private final PasswordGenerator generator;

    public AbstractUserRepository(Map<String, User> users, Map<SelectionKey, Session> sessions, PasswordHasher hasher,
                                  SymmetricEncryptor encryptor, PasswordChecker checker, PasswordGenerator generator) {
        this.users = users;
        this.sessions = sessions;
        this.hasher = hasher;
        this.encryptor = encryptor;
        this.checker = checker;
        this.generator = generator;
    }

    @Override
    public void registerUser(SelectionKey selectionKey, String user, String password, String passwordRepeat)
        throws UserAlreadyExistsException, UserLoggedInException, PasswordsNotMatchException {
        if (selectionKey == null || user == null || password == null || passwordRepeat == null) {
            throw new IllegalArgumentException("Null args are not allowed");
        }
        if (isUserLoggedIn(selectionKey)) {
            throw new UserLoggedInException("You cannot register when you are already logged in");
        }
        if (users.containsKey(user)) {
            throw new UserAlreadyExistsException("This user is already registered");
        }
        if (!password.equals(passwordRepeat)) {
            throw new PasswordsNotMatchException("Password and repeated password not match");
        }
        String hashedPassword = hasher.getHash(password);
        users.put(user, new User(user, hashedPassword, new ConcurrentHashMap<>()));

        // May be removed
        saveChanges();
    }

    @Override
    public void loginUser(SelectionKey selectionKey, String userName, String password)
        throws UserNotExistsException, IncorrectPasswordException, UserLoggedInException {
        if (selectionKey == null || userName == null || password == null) {
            throw new IllegalArgumentException("null args are not allowed");
        }
        if (isUserLoggedIn(selectionKey)) {
            throw new UserLoggedInException("This user has already been logged in!");
        }
        if (!users.containsKey(userName)) {
            throw new UserNotExistsException("This user is not registered!");
        }
        User user = users.get(userName);
        if (!hasher.getHash(password).equals(user.hashedPassword())) {
            throw new IncorrectPasswordException("The password you typed was wrong");
        }
        sessions.put(selectionKey, new Session(selectionKey, user, true));
    }

    @Override
    public void logoutUser(SelectionKey selectionKey) throws UserNotLoggedInException {
        if (selectionKey == null) {
            throw new IllegalArgumentException("null selection key not allowed");
        }
        if (!isUserLoggedIn(selectionKey)) {
            throw new UserNotLoggedInException("The user is not logged in");
        }
        sessions.remove(selectionKey);
    }

    @Override
    public void addPassword(SelectionKey selectionKey, String website, String userName, String password)
        throws PasswordNotSecureException, UserNotLoggedInException, WebsiteExistingException {
        if (selectionKey == null || userName == null || password == null || website == null) {
            throw new IllegalArgumentException("Null args are not allowed");
        }
        if (!isUserLoggedIn(selectionKey)) {
            throw new UserNotLoggedInException("The user must be logged in to change password!");
        }

        if (!checker.isPasswordSecure(password)) {
            throw new PasswordNotSecureException("This password has been compromised. Please choose another!");
        }

        User user = sessions.get(selectionKey).user();
        if (user.passwords().containsKey(website)) {
            throw new WebsiteExistingException("You already have a password for this website.");
        }
        user.passwords().put(website, new WebsitePair(userName, encryptor.encrypt(password)));
        //May be removed
        saveChanges();
    }

    @Override
    public String generatePassword(SelectionKey selectionKey, String website, String userName)
        throws UserNotLoggedInException, WebsiteExistingException, PasswordNotSecureException {
        String generatedPassword = generator.generatePassword();
        addPassword(selectionKey, website, userName, generatedPassword);
        // May be removed
        saveChanges();
        return generatedPassword;
    }

    @Override
    public String retrieveCredentials(SelectionKey selectionKey, String website, String userName)
        throws UserNotLoggedInException, WebsiteUsernameNotExistsException, WebsiteNotExistsException {
        if (selectionKey == null || userName == null || website == null) {
            throw new IllegalArgumentException("Null args are not allowed");
        }
        if (!isUserLoggedIn(selectionKey)) {
            throw new UserNotLoggedInException("User must be logged in to retrieve credentials");
        }
        User user = sessions.get(selectionKey).user();
        if (!user.passwords().containsKey(website)) {
            throw new WebsiteNotExistsException("You have no credentials for this website!");
        }
        WebsitePair pair = user.passwords().get(website);
        if (!userName.equals(pair.userName())) {
            throw new WebsiteUsernameNotExistsException("The username you provided does not match actual user name");
        }
        return pair.userName() + " " + encryptor.decrypt(pair.encryptedPassword());
    }

    @Override
    public void removePassword(SelectionKey selectionKey, String website, String userName)
        throws WebsiteUsernameNotExistsException, UserNotLoggedInException, WebsiteNotExistsException {
        if (selectionKey == null || userName == null || website == null) {
            throw new IllegalArgumentException("Null args are not allowed");
        }
        if (!isUserLoggedIn(selectionKey)) {
            throw new UserNotLoggedInException("User must be logged in to retrieve credentials");
        }
        User user = sessions.get(selectionKey).user();
        if (!user.passwords().containsKey(website)) {
            throw new WebsiteNotExistsException("You have no credentials for this website!");
        }
        WebsitePair pair = user.passwords().get(website);
        if (!userName.equals(pair.userName())) {
            throw new WebsiteUsernameNotExistsException("The username you provided does not match actual user name");
        }
        user.passwords().remove(website);

        // May be removed
        saveChanges();
    }

    private boolean isUserLoggedIn(SelectionKey selectionKey) {
        if (!sessions.containsKey(selectionKey)) {
            return false;
        }
        return sessions.get(selectionKey).loggedIn() && sessions.get(selectionKey).user() != null;
    }

    protected Map<String, User> getUsers() {
        return users;
    }

    protected PasswordChecker getChecker() {
        return checker;
    }
}
