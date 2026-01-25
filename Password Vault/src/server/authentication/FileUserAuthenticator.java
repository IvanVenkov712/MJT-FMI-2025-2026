package server.authentication;

import exception.UserNotExistsException;
import exception.WrongPasswordException;

public class FileUserAuthenticator implements UserAuthenticator {
    @Override
    public void authenticate(String userName, String password) throws UserNotExistsException, WrongPasswordException {

    }

    @Override
    public void registerUser(String uName, String password) {

    }
}
