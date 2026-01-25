package server.authentication;

import exception.UserNotExistsException;
import exception.WrongPasswordException;

public interface UserAuthenticator {
    void authenticate(String userName, String password) throws UserNotExistsException, WrongPasswordException;

    void registerUser(String uName, String password);
}
