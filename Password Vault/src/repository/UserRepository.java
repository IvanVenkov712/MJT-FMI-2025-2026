package repository;

import exception.UserAlreadyExistsException;

public interface UserRepository {
    void registerUser(String user, String password, String passwordRepeat) throws UserAlreadyExistsException;

    void loginUser(String user, String password);

    void logoutUser(String userName);

    void
}
