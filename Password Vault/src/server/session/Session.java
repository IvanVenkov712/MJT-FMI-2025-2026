package server.session;

import server.model.User;

import java.nio.channels.SelectionKey;

public record Session(SelectionKey selectionKey, User user, boolean loggedIn) {
}
