package server.repository;

import server.adapter.FileUserAdapter;
import server.adapter.UserAdapter;
import server.model.User;
import server.password.checker.PasswordChecker;
import server.password.checker.api.APIPasswordChecker;
import server.password.encryptor.SymmetricEncryptor;
import server.password.encryptor.SymmetricEncryptorImpl;
import server.password.hash.PasswordHasher;
import server.password.hash.SHA256PasswordHasher;
import server.session.Session;

import java.nio.channels.SelectionKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AdapterUserRepository extends AbstractUserRepository {
    private final UserAdapter adapter;

    AdapterUserRepository(Map<String, User> users,
                          Map<SelectionKey, Session> sessions,
                          PasswordHasher hasher, SymmetricEncryptor encryptor,
                          PasswordChecker checker, UserAdapter adapter) {

        super(users, sessions, hasher, encryptor, checker);
        this.adapter = adapter;
    }

    AdapterUserRepository(UserAdapter adapter) {
        super(adapter.loadUsers(), new ConcurrentHashMap<>(), new SHA256PasswordHasher(), new SymmetricEncryptorImpl(),
            new APIPasswordChecker());
        this.adapter = adapter;
    }

    public AdapterUserRepository() {
        this(new FileUserAdapter());
    }

    @Override
    public void saveChanges() {
        adapter.saveUsers(getUsers());
    }
}
