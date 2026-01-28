package command.commands;

import command.Command;
import command.CommandResult;
import command.Status;
import exception.PasswordVaultException;
import server.repository.UserRepository;

import java.nio.channels.SelectionKey;

public class AddPasswordCommand extends AbstractCommand {
    public static final String COMMAND_STRING = "add-password";

    private static final int ARGS_COUNT = 3;
    private static final int WEBSITE_INDEX = 0;
    private static final int USER_INDEX = 1;
    private static final int PASSWORD_INDEX = 2;

    private final String website;
    private final String user;
    private final String password;

    public AddPasswordCommand(UserRepository repository, SelectionKey selectionKey, String[] args) {
        super(repository, selectionKey);

        Command.validateArgs(args, ARGS_COUNT);
        website = args[WEBSITE_INDEX];
        user = args[USER_INDEX];
        password = args[PASSWORD_INDEX];
    }

    @Override
    public CommandResult execute() {
        try {
            getRepository().addPassword(getSelectionKey(), website, user, password);
        } catch (PasswordVaultException e) {
            return new CommandResult(Status.ERROR, e.getMessage());
        }
        return new CommandResult(Status.OK, "Password added successfully");
    }
}
