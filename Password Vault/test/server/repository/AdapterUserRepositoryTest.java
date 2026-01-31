package server.repository;

import exception.PasswordsNotMatchException;
import exception.UserAlreadyExistsException;
import exception.UserAlreadyLoggedException;
import exception.UserNotExistsException;
import exception.WrongPasswordException;
import org.junit.jupiter.api.Test;
import server.adapter.UserAdapter;
import server.model.User;
import server.password.checker.PasswordChecker;
import server.password.encryptor.SymmetricEncryptor;
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
        throws UserAlreadyLoggedException, PasswordsNotMatchException, UserAlreadyExistsException {

        Map<String, User> users = new ConcurrentHashMap<>();
        Map<SelectionKey, Session> sessions = Map.of();
        PasswordHasher hasher = mock();
        SymmetricEncryptor encryptor = mock();
        PasswordChecker checker = mock();
        UserAdapter adapter = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, adapter);
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

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, adapter);
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

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, adapter);
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

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, adapter);
        assertThrows(
            UserAlreadyLoggedException.class, () -> userRepository.registerUser(sessionKey, "Pesho", "PeshoPass", "PeshoPass"),
            "Expected to throw UserAlreadyLoggedException when registering with existing username."
        );

    }

    @Test
    void testLoginUserValidInput()
        throws UserAlreadyLoggedException, UserNotExistsException, WrongPasswordException {
        Map<String, User> users = new ConcurrentHashMap<>();
        users.put("Pesho", new User("Pesho", "PeshoP", Map.of()));
        Map<SelectionKey, Session> sessions = new HashMap<>();
        PasswordHasher hasher = mock();
        when(hasher.getHash("PeshoP")).thenReturn("PeshoP");
        SymmetricEncryptor encryptor = mock();
        PasswordChecker checker = mock();
        UserAdapter adapter = mock();

        userRepository = new AdapterUserRepository(users, sessions, hasher, encryptor, checker, adapter);
        userRepository.loginUser(mock(), "Pesho", "PeshoP");
    }


}