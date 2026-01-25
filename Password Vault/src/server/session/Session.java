package server.session;

import model.User;

import java.nio.channels.SelectionKey;

public record Session(SelectionKey channel, User user, boolean loggedIn) {
}
