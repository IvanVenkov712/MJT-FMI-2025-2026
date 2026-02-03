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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import server.adapter.UserAdapter;
import server.model.User;
import server.model.WebsitePair;
import server.password.checker.PasswordChecker;
import server.password.encryptor.SymmetricEncryptor;
import server.password.generator.PasswordGenerator;
import server.password.hash.PasswordHasher;
import server.model.Session;

import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdapterUserRepositoryTest {
    private AdapterUserRepository userRepository;

    private PasswordHasher hasher;
    private SymmetricEncryptor encryptor;
    private PasswordChecker checker;
    private UserAdapter adapter;
    private PasswordGenerator generator;
    private Map<String, User> users;
    private Map<SelectionKey, Session> sessions;
    private SelectionKey sessionKey;

    @BeforeEach
    void setUp() {
        hasher = mock();
        encryptor = mock();
        checker = mock();
        adapter = mock();
        generator = mock();
        users = new HashMap<>();
        sessions = new HashMap<>();
        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, generator, adapter);
        sessionKey = mock();
    }

    @AfterEach
    void tearDown() {
        userRepository = null;
    }

    @Test
    void testRegisterUserValidInput()
        throws UserLoggedInException, PasswordsNotMatchException, UserAlreadyExistsException {
        userRepository.registerUser(mock(), "Pesho", "PeshoPass", "PeshoPass");
    }

    @Test
    void testRegisterUserNotMatchingPasswords() {
        assertThrows(
            PasswordsNotMatchException.class, () -> userRepository.registerUser(mock(), "Pesho", "PeshoPass", "PeshoPass1"),
            "Expected to throw PasswordsNotMatchException when password and passwordRepeat not match"
        );
    }

    @Test
    void testRegisterUserExistingUser() {
        users.put("Gosho", new User("Gosho", "", Map.of()));
        assertThrows(
            UserAlreadyExistsException.class, () -> userRepository.registerUser(mock(), "Gosho", "GoshoPass", "GoshoPass"),
            "Expected to throw UserAlreadyExistsException when registering with existing username."
        );

    }

    @Test
    void testRegisterUserLoggedUser() {
        User user = new User("Gosho", "", Map.of());
        users.put("Gosho", user);
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        assertThrows(
            UserLoggedInException.class, () -> userRepository.registerUser(sessionKey, "Pesho", "PeshoPass", "PeshoPass"),
            "Expected to throw UserAlreadyLoggedException when registering with existing username."
        );

    }

    @Test
    void testLoginUserValidInput()
        throws UserLoggedInException, UserNotExistsException, IncorrectPasswordException {

        users.put("Pesho", new User("Pesho", "PeshoP", Map.of()));

        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        userRepository.loginUser(mock(), "Pesho", "PeshoP");
    }

    @Test
    void testLoginUserUserAlreadyLogged() {
        User user = new User("Pesho", "PeshoP", Map.of());
        users.put("Pesho", user);
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        assertThrows(
            UserLoggedInException.class,
            () -> userRepository.loginUser(sessionKey, "Pesho", "PeshoP"),
            "UserAlreadyLoggedException expected when trying to log when already been logged in"
        );
    }

    @Test
    void testLoginUserUserNotExisting() {
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        assertThrows(
            UserNotExistsException.class,
            () -> userRepository.loginUser(sessionKey, "Pesho", "PeshoP"),
            "UserNotExistsException expected when trying to login with unexisting username"
        );
    }

    @Test
    void testLoginUserUserIncorrectPassword() {
        User user = new User("Pesho", "PeshoP", Map.of());
        users.put("Pesho", user);

        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");
        when(hasher.getHash("PeshoP1")).thenReturn("PeshoP1");

        assertThrows(
            IncorrectPasswordException.class,
            () -> userRepository.loginUser(sessionKey, "Pesho", "PeshoP1"),
            "IncorrectPasswordException expected when trying to login with incorrect password"
        );
    }

    @Test
    void testLogoutUserLoggedInUser() throws UserNotLoggedInException {
        User user = new User("Pesho", "PeshoP", Map.of());
        users.put("Pesho", user);
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        userRepository.logoutUser(sessionKey);
    }

    @Test
    void testLogoutUserNotLoggedInUser() {
        User user = new User("Pesho", "PeshoP", Map.of());
        users.put("Pesho", user);

        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");

        assertThrows(UserNotLoggedInException.class, () -> userRepository.logoutUser(sessionKey),
            "UserNotLoggedInException expected when logging out without been logged in"
        );
    }

    @Test
    void testAddPasswordValidInput() throws WebsiteExistingException, PasswordNotSecureException, UserNotLoggedInException {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        users.put("Pesho", user);
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        userRepository.addPassword(sessionKey, "facebook", "Pesho@gmail", "PeshoP");
    }

    @Test
    void testAddPasswordNotSecurePassword() {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        users.put("Pesho", user);
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());
        when(checker.isPasswordSecure("PeshoP")).thenReturn(false);

        assertThrows(PasswordNotSecureException.class,
            () -> userRepository.addPassword(sessionKey, "facebook", "Pesho@gmail", "PeshoP"),
            "PasswordNotSecureException expected when password insecure"
        );
    }

    @Test
    void testAddPasswordExistingSite() {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        user.passwords().put("facebook", new WebsitePair("Pesho@gmail", "PeshoP".getBytes()));
        users.put("Pesho", user);
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        assertThrows(WebsiteExistingException.class,
            () -> userRepository.addPassword(sessionKey, "facebook", "Pesho@gmail", "PeshoP"),
            "WebsiteExistingException expected when adding password for existing site"
        );
    }

    @Test
    void testAddPasswordUserNotLoggedIn() {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        users.put("Pesho", user);

        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        assertThrows(UserNotLoggedInException.class,
            () -> userRepository.addPassword(sessionKey, "facebook", "Pesho@gmail", "PeshoP"),
            "UserNotLoggedInException expected when adding password as not logged in user"
        );
    }

    @Test
    void testGeneratePassword() throws PasswordNotSecureException, UserNotLoggedInException, WebsiteExistingException {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        users.put("Pesho", user);
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);
        when(generator.generatePassword()).thenReturn("PeshoP");

        assertEquals("PeshoP", userRepository.generatePassword(sessionKey, "facebook", "Pesho@gmail"),
            "PeshoP expected"
        );
    }

    @Test
    void testRemovePasswordValidInput()
        throws WebsiteNotExistsException, WebsiteUsernameNotExistsException, UserNotLoggedInException {

        User user = new User("Pesho", "PeshoP", new HashMap<>());
        user.passwords().put("facebook", new WebsitePair("Pesho@gmail", "PeshoP".getBytes()));
        users.put("Pesho", user);
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        userRepository.removePassword(sessionKey, "facebook", "Pesho@gmail");
        assertEquals(0, user.passwords().size(), "Expected empty passwords list");
    }

    @Test
    void testRemovePasswordNotExistingSite() {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        users.put("Pesho", user);
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        assertThrows(
            WebsiteNotExistsException.class, () -> userRepository.removePassword(sessionKey, "facebook", "Pesho@gmail"),
            "Expected WebsiteNotExistsException when removing a non-existing site password"
        );
    }

    @Test
    void testRemovePasswordNotExistingCredentials() {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        user.passwords().put("facebook", new WebsitePair("Pesho@gmail", "PeshoP".getBytes()));
        users.put("Pesho", user);
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        assertThrows(WebsiteUsernameNotExistsException.class, () -> userRepository.removePassword(sessionKey, "facebook", "Pesho1@gmail"),
           "Expected CredentialsNotExistException when existing site and non-existing uname"
        );
    }

    @Test
    void testRemovePasswordUserNotLoggedIn() {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        user.passwords().put("facebook", new WebsitePair("Pesho@gmail", "PeshoP".getBytes()));
        users.put("Pesho", user);

        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        assertThrows(UserNotLoggedInException.class, () -> userRepository.removePassword(sessionKey, "facebook", "Pesho1@gmail"),
            "Expected UserNotLoggedInException when removing password as not logged-in user"
        );
    }

    @Test
    void testRetrieveCredentialsValidInput()
        throws WebsiteNotExistsException, WebsiteUsernameNotExistsException, UserNotLoggedInException {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        user.passwords().put("facebook", new WebsitePair("Pesho@gmail", "PeshoP".getBytes()));
        users.put("Pesho", user);
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());
        when(encryptor.decrypt("PeshoP".getBytes())).thenReturn("PeshoP");
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        String credentials = userRepository.retrieveCredentials(sessionKey, "facebook", "Pesho@gmail");
        assertEquals("Pesho@gmail PeshoP", credentials, "Not the expected credentials");
    }

    @Test
    void testRetrieveCredentialsNotExistingWebsite() {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        users.put("Pesho", user);
        sessions.put(sessionKey, new Session(sessionKey, user, true));

        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());
        when(encryptor.decrypt("PeshoP".getBytes())).thenReturn("PeshoP");
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        assertThrows(WebsiteNotExistsException.class, () -> userRepository.retrieveCredentials(sessionKey, "facebook", "Pesho@gmail"),
            "Expected WebsiteNotExistsException when retrieving password of non-existing site"
        );
    }

    @Test
    void testRetrieveCredentialsNotExistingUsername() {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        user.passwords().put("facebook", new WebsitePair("Pesho@gmail", "PeshoP".getBytes()));
        users.put("Pesho", user);

        sessions.put(sessionKey, new Session(sessionKey, user, true));

        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());
        when(encryptor.decrypt("PeshoP".getBytes())).thenReturn("PeshoP");
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        assertThrows(WebsiteUsernameNotExistsException.class, () -> userRepository.retrieveCredentials(sessionKey, "facebook", "Pesho1@gmail"),
            "Expected WebsiteUsernameNotExistsException when retrieving password of non-existing username and existing site"
        );
    }

    @Test
    void testRetrieveCredentialsNotLoggedInUser() {
        User user = new User("Pesho", "PeshoP", new HashMap<>());
        user.passwords().put("facebook", new WebsitePair("Pesho@gmail", "PeshoP".getBytes()));
        users.put("Pesho", user);

        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");
        when(encryptor.encrypt("PeshoP")).thenReturn("PeshoP".getBytes());
        when(encryptor.decrypt("PeshoP".getBytes())).thenReturn("PeshoP");
        when(checker.isPasswordSecure("PeshoP")).thenReturn(true);

        assertThrows(UserNotLoggedInException.class, () -> userRepository.retrieveCredentials(sessionKey, "facebook", "Pesho@gmail"),
            "Expected UserNotLoggedInException when retrieving password as not logged in user"
        );
    }
}