package server.repository;

import exception.WebsiteUsernameNotExistsException;
import exception.PasswordNotSecureException;
import exception.PasswordsNotMatchException;
import exception.UserAlreadyExistsException;
import exception.UserLoggedInException;
import exception.UserNotExistsException;
import exception.IncorrectPasswordException;
import exception.UserNotLoggedInException;
import exception.WebsiteExistingException;
import exception.WebsiteNotExistsException;
import org.junit.jupiter.api.Test;
import server.adapter.UserAdapter;
import server.model.User;
import server.model.WebsitePair;
import server.password.checker.PasswordChecker;
import server.password.encryptor.SymmetricEncryptor;
import server.password.generator.PasswordGenerator;
import server.password.hash.PasswordHasher;
import server.session.Session;

import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdapterUserRepositoryTest {
    private AdapterUserRepository userRepository;

    @Test
    void testRegisterUserValidInput()
        throws UserLoggedInException, PasswordsNotMatchException, UserAlreadyExistsException {

        Map<String, User> users = new ConcurrentHashMap<>();
        Map<SelectionKey, Session> sessions = Map.of();
        PasswordHasher hasher = mock();
        SymmetricEncryptor encryptor = mock();
        PasswordChecker checker = mock();
        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        userRepository.registerUser(mock(), "Pesho", "PeshoPass", "PeshoPass");
    }

    @Test
    void testRegisterUserNotMatchingPasswords() {
        Map<String, User> users = new ConcurrentHashMap<>();
        Map<SelectionKey, Session> sessions = Map.of();
        PasswordHasher hasher = mock();
        SymmetricEncryptor encryptor = mock();
        PasswordChecker checker = mock();
        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        assertThrows(
            PasswordsNotMatchException.class, () -> userRepository.registerUser(mock(), "Pesho", "PeshoPass", "PeshoPass1"),
            "Expected to throw PasswordsNotMatchException when password and passwordRepeat not match"
        );

    }

    @Test
    void testRegisterUserExistingUser() {
        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Gosho", new User("Gosho", "", Map.of()));

        Map<SelectionKey, Session> sessions = Map.of();
        PasswordHasher hasher = mock();
        SymmetricEncryptor encryptor = mock();
        PasswordChecker checker = mock();
        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        assertThrows(
            UserAlreadyExistsException.class, () -> userRepository.registerUser(mock(), "Gosho", "GoshoPass", "GoshoPass"),
            "Expected to throw UserAlreadyExistsException when registering with existing username."
        );

    }

    @Test
    void testRegisterUserLoggedUser() {
        User user = new User("Gosho", "", Map.of());
        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Gosho", user);

        SelectionKey sessionKey = mock();
        Map<SelectionKey, Session> sessions = Map.of(sessionKey, new Session(sessionKey, user, true));

        PasswordHasher hasher = mock();
        SymmetricEncryptor encryptor = mock();
        PasswordChecker checker = mock();
        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        assertThrows(
            UserLoggedInException.class, () -> userRepository.registerUser(sessionKey, "Pesho", "PeshoPass", "PeshoPass"),
            "Expected to throw UserAlreadyLoggedException when registering with existing username."
        );

    }

    @Test
    void testLoginUserValidInput()
        throws UserLoggedInException, UserNotExistsException, IncorrectPasswordException {

        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", new User("Pesho", "PeshoP", Map.of()));
        Map<SelectionKey, Session> sessions = new HashMap<>();
        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");
        SymmetricEncryptor encryptor = mock();
        PasswordChecker checker = mock();
        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        userRepository.loginUser(mock(), "Pesho", "PeshoP");
    }

    @Test
    void testLoginUserUserAlreadyLogged() {
        User user = new User("Pesho", "PeshoP", Map.of());

        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", user);

        Map<SelectionKey, Session> sessions = new HashMap<>();
        SelectionKey sessionKey = mock();
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        SymmetricEncryptor encryptor = mock();
        PasswordChecker checker = mock();
        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        assertThrows(
            UserLoggedInException.class,
            () -> userRepository.loginUser(sessionKey, "Pesho", "PeshoP"),
            "UserAlreadyLoggedException expected when trying to log when already been logged in"
        );
    }

    @Test
    void testLoginUserUserNotExisting() {
        User user = new User("Pesho", "PeshoP", Map.of());

        Map<String, User> users = new ConcurrentHashMap<>();

        Map<SelectionKey, Session> sessions = new HashMap<>();
        SelectionKey sessionKey = mock();

        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        SymmetricEncryptor encryptor = mock();
        PasswordChecker checker = mock();
        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        assertThrows(
            UserNotExistsException.class,
            () -> userRepository.loginUser(sessionKey, "Pesho", "PeshoP"),
            "UserNotExistsException expected when trying to login with unexisting username"
        );
    }

    @Test
    void testLoginUserUserIncorrectPassword() {
        User user = new User("Pesho", "PeshoP", Map.of());

        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", user);

        Map<SelectionKey, Session> sessions = new HashMap<>();
        SelectionKey sessionKey = mock();

        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");
        when(hasher.getHash("PeshoP1")).thenReturn("PeshoP1");

        SymmetricEncryptor encryptor = mock();
        PasswordChecker checker = mock();
        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        assertThrows(
            IncorrectPasswordException.class,
            () -> userRepository.loginUser(sessionKey, "Pesho", "PeshoP1"),
            "IncorrectPasswordException expected when trying to login with incorrect password"
        );
    }

    @Test
    void testLogoutUserLoggedInUser() throws UserNotLoggedInException {
        User user = new User("Pesho", "PeshoP", Map.of());

        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", user);

        Map<SelectionKey, Session> sessions = new HashMap<>();
        SelectionKey sessionKey = mock();
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        SymmetricEncryptor encryptor = mock();
        PasswordChecker checker = mock();
        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        userRepository.logoutUser(sessionKey);
    }

    @Test
    void testLogoutUserNotLoggedInUser() {
        User user = new User("Pesho", "PeshoP", Map.of());

        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", user);

        Map<SelectionKey, Session> sessions = new HashMap<>();
        SelectionKey sessionKey = mock();

        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        SymmetricEncryptor encryptor = mock();
        PasswordChecker checker = mock();
        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        assertThrows(UserNotLoggedInException.class, () -> userRepository.logoutUser(sessionKey),
            "UserNotLoggedInException expected when logging out without been logged in"
        );
    }

    @Test
    void testAddPasswordValidInput() throws WebsiteExistingException, PasswordNotSecureException, UserNotLoggedInException {
        User user = new User("Pesho", "PeshoP", new HashMap<>());

        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", user);

        Map<SelectionKey, Session> sessions = new HashMap<>();
        SelectionKey sessionKey = mock();
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        SymmetricEncryptor encryptor = mock();
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());

        PasswordChecker checker = mock();
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        userRepository.addPassword(sessionKey, "facebook", "Pesho@gmail", "PeshoP");
    }

    @Test
    void testAddPasswordNotSecurePassword() {
        User user = new User("Pesho", "PeshoP", new HashMap<>());

        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", user);

        Map<SelectionKey, Session> sessions = new HashMap<>();
        SelectionKey sessionKey = mock();
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        SymmetricEncryptor encryptor = mock();
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());

        PasswordChecker checker = mock();
        when(checker.isPasswordSecure("PeshoP")).thenReturn(false);

        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        assertThrows(PasswordNotSecureException.class,
            () -> userRepository.addPassword(sessionKey, "facebook", "Pesho@gmail", "PeshoP"),
            "PasswordNotSecureException expected when password insecure"
        );
    }

    @Test
    void testAddPasswordExistingSite() {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        user.passwords().put("facebook", new WebsitePair("Pesho@gmail", "PeshoP".getBytes()));

        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", user);

        Map<SelectionKey, Session> sessions = new HashMap<>();
        SelectionKey sessionKey = mock();
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        SymmetricEncryptor encryptor = mock();
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());

        PasswordChecker checker = mock();
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        assertThrows(WebsiteExistingException.class,
            () -> userRepository.addPassword(sessionKey, "facebook", "Pesho@gmail", "PeshoP"),
            "WebsiteExistingException expected when adding password for existing site"
        );
    }

    @Test
    void testAddPasswordUserNotLoggedIn() {
        User user = new User("Pesho", "PeshoP", new HashMap<>());

        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", user);

        Map<SelectionKey, Session> sessions = new HashMap<>();
        SelectionKey sessionKey = mock();

        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        SymmetricEncryptor encryptor = mock();
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());

        PasswordChecker checker = mock();
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        assertThrows(UserNotLoggedInException.class,
            () -> userRepository.addPassword(sessionKey, "facebook", "Pesho@gmail", "PeshoP"),
            "UserNotLoggedInException expected when adding password as not logged in user"
        );
    }

    @Test
    void testGeneratePassword() throws PasswordNotSecureException, UserNotLoggedInException, WebsiteExistingException {
        User user = new User("Pesho", "PeshoP", new HashMap<>());

        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", user);

        Map<SelectionKey, Session> sessions = new HashMap<>();
        SelectionKey sessionKey = mock();
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        SymmetricEncryptor encryptor = mock();
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());

        PasswordChecker checker = mock();
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();
        when(generator.generatePassword()).thenReturn("PeshoP");

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        assertEquals("PeshoP", userRepository.generatePassword(sessionKey, "facebook", "Pesho@gmail"),
            "PeshoP expected"
        );
    }

    @Test
    void testRemovePasswordValidInput()
        throws WebsiteNotExistsException, WebsiteUsernameNotExistsException, UserNotLoggedInException {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        user.passwords().put("facebook", new WebsitePair("Pesho@gmail", "PeshoP".getBytes()));

        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", user);

        Map<SelectionKey, Session> sessions = new HashMap<>();
        SelectionKey sessionKey = mock();
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        SymmetricEncryptor encryptor = mock();
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());

        PasswordChecker checker = mock();
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        userRepository.removePassword(sessionKey, "facebook", "Pesho@gmail");
        assertEquals(0, user.passwords().size(), "Expected empty passwords list");
    }

    @Test
    void testRemovePasswordNotExistingSite() {
        User user = new User("Pesho", "PeshoP", new HashMap<>());

        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", user);

        Map<SelectionKey, Session> sessions = new HashMap<>();
        SelectionKey sessionKey = mock();
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        SymmetricEncryptor encryptor = mock();
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());

        PasswordChecker checker = mock();
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        assertThrows(
            WebsiteNotExistsException.class, () -> userRepository.removePassword(sessionKey, "facebook", "Pesho@gmail"),
            "Expected WebsiteNotExistsException when removing a non-existing site password"
        );
    }

    @Test
    void testRemovePasswordNotExistingCredentials() {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        user.passwords().put("facebook", new WebsitePair("Pesho@gmail", "PeshoP".getBytes()));

        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", user);

        Map<SelectionKey, Session> sessions = new HashMap<>();
        SelectionKey sessionKey = mock();
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        SymmetricEncryptor encryptor = mock();
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());

        PasswordChecker checker = mock();
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        assertThrows(WebsiteUsernameNotExistsException.class, () -> userRepository.removePassword(sessionKey, "facebook", "Pesho1@gmail"),
           "Expected CredentialsNotExistException when existing site and non-existing uname"
        );
    }

    @Test
    void testRemovePasswordUserNotLoggedIn() {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        user.passwords().put("facebook", new WebsitePair("Pesho@gmail", "PeshoP".getBytes()));

        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", user);

        Map<SelectionKey, Session> sessions = new HashMap<>();
        SelectionKey sessionKey = mock();

        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        SymmetricEncryptor encryptor = mock();
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());

        PasswordChecker checker = mock();
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        assertThrows(UserNotLoggedInException.class, () -> userRepository.removePassword(sessionKey, "facebook", "Pesho1@gmail"),
            "Expected UserNotLoggedInException when removing password as not logged-in user"
        );
    }

    @Test
    void testRetrieveCredentialsValidInput()
        throws WebsiteNotExistsException, WebsiteUsernameNotExistsException, UserNotLoggedInException {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        user.passwords().put("facebook", new WebsitePair("Pesho@gmail", "PeshoP".getBytes()));

        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", user);

        Map<SelectionKey, Session> sessions = new HashMap<>();
        SelectionKey sessionKey = mock();
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        SymmetricEncryptor encryptor = mock();
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());
        when(encryptor.decrypt("PeshoP".getBytes())).thenReturn("PeshoP");

        PasswordChecker checker = mock();
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        String credentials = userRepository.retrieveCredentials(sessionKey, "facebook", "Pesho@gmail");
        assertEquals("Pesho@gmail PeshoP", credentials, "Not the expected credentials");
    }

    @Test
    void testRetrieveCredentialsNotExistingWebsite() {
        User user = new User("Pesho", "PeshoP", new HashMap<>());

        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", user);

        Map<SelectionKey, Session> sessions = new HashMap<>();
        SelectionKey sessionKey = mock();
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        SymmetricEncryptor encryptor = mock();
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());
        when(encryptor.decrypt("PeshoP".getBytes())).thenReturn("PeshoP");

        PasswordChecker checker = mock();
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        assertThrows(WebsiteNotExistsException.class, () -> userRepository.retrieveCredentials(sessionKey, "facebook", "Pesho@gmail"),
            "Expected WebsiteNotExistsException when retrieving password of non-existing site"
        );
    }

    @Test
    void testRetrieveCredentialsNotExistingUsername() {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        user.passwords().put("facebook", new WebsitePair("Pesho@gmail", "PeshoP".getBytes()));

        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", user);

        Map<SelectionKey, Session> sessions = new HashMap<>();
        SelectionKey sessionKey = mock();
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        SymmetricEncryptor encryptor = mock();
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());
        when(encryptor.decrypt("PeshoP".getBytes())).thenReturn("PeshoP");

        PasswordChecker checker = mock();
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        assertThrows(WebsiteUsernameNotExistsException.class, () -> userRepository.retrieveCredentials(sessionKey, "facebook", "Pesho1@gmail"),
            "Expected WebsiteUsernameNotExistsException when retrieving password of non-existing username and existing site"
        );
    }

    @Test
    void testRetrieveCredentialsNotLoggedInUser() {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        user.passwords().put("facebook", new WebsitePair("Pesho@gmail", "PeshoP".getBytes()));

        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", user);

        Map<SelectionKey, Session> sessions = new HashMap<>();
        SelectionKey sessionKey = mock();

        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        SymmetricEncryptor encryptor = mock();
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());
        when(encryptor.decrypt("PeshoP".getBytes())).thenReturn("PeshoP");

        PasswordChecker checker = mock();
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        UserAdapter adapter = mock();
        PasswordGenerator generator = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        assertThrows(UserNotLoggedInException.class, () -> userRepository.retrieveCredentials(sessionKey, "facebook", "Pesho@gmail"),
            "Expected UserNotLoggedInException when retrieving password as not logged in user"
        );
    }


}