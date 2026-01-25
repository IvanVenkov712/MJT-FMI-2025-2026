package command.commands;

import command.CommandResult;
import command.Status;
import exception.PasswordVaultException;
import server.repository.UserRepository;
import server.session.Session;

public class LogoutCommand extends AbstractCommand {
    public static final String COMMAND_STRING = "logout";

    private static final int ARGS_COUNT = 0;

    public LogoutCommand(UserRepository repository, Session context, String[] args) {
        super(repository, context);
    }

    @Override
    public CommandResult execute() {
        try {
            getRepository().logoutUser(getSession());
        } catch (PasswordVaultException e) {
            return new CommandResult(Status.ERROR, e.getMessage());
        }
        return new CommandResult(Status.OK, "Logout successful");
    }
}
