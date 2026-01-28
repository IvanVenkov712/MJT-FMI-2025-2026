package server.repository;

import model.User;
import password.checker.PasswordChecker;
import password.checker.api.APIPasswordChecker;
import password.encryptor.SymmetricEncryptor;
import password.encryptor.SymmetricEncryptorImpl;
import password.hash.PasswordHasher;
import password.hash.SHA256PasswordHasher;
import server.session.Session;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.SelectionKey;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileUserRepository extends AbstractUserRepository {

    private static final String USERS_FILE_PATH = "users";

    public FileUserRepository(Map<String, User> users,
                              Map<SelectionKey, Session> sessions,
                              PasswordHasher hasher, SymmetricEncryptor encryptor,
                              PasswordChecker checker) {
        super(users, sessions, hasher, encryptor, checker);
    }

    public FileUserRepository() {

        this(loadUsers(), new ConcurrentHashMap<>(), new SHA256PasswordHasher(), new SymmetricEncryptorImpl(),
            new APIPasswordChecker());

    }

    @Override
    public void saveChanges() {
        try (ObjectOutputStream output = new ObjectOutputStream(
            new BufferedOutputStream(new FileOutputStream(USERS_FILE_PATH)))) {

            output.writeObject(getUsers());
            output.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, User> loadUsers() {
        Map<String, User> users;
        if (!Files.exists(Path.of(USERS_FILE_PATH))) {
            users = new ConcurrentHashMap<>();
        } else {
            try (var stream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(USERS_FILE_PATH)))) {
                users = (Map<String, User>) stream.readObject();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return users;
    }
}
