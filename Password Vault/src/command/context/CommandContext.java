package command.context;

import model.User;

public record CommandContext(User user, boolean loggedIn) {
}
