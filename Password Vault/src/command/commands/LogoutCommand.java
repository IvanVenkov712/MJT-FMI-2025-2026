package command.commands;

import command.CommandResult;
import command.Status;
import exception.PasswordVaultException;
import server.repository.UserRepository;

import java.nio.channels.SelectionKey;

public class LogoutCommand extends AbstractCommand {
    public static final String COMMAND_STRING = "logout";

    private static final int ARGS_COUNT = 0;

    public LogoutCommand(UserRepository repository, SelectionKey key, String[] args) {
        super(repository, key);
    }

    @Override
    public CommandResult execute() {
        try {
            getRepository().logoutUser(getSelectionKey());
        } catch (PasswordVaultException e) {
            return new CommandResult(Status.ERROR, e.getMessage());
        }
        return new CommandResult(Status.OK, "Logout successful");
    }
}
