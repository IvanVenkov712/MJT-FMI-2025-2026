package command.commands;

import command.Command;
import command.CommandResult;
import command.Status;
import exception.PasswordVaultException;
import server.repository.UserRepository;
import server.session.Session;

public class RegisterCommand extends AbstractCommand {
    public static final String COMMAND_STRING = "register";

    private static final int ARGS_NUM = 3;
    private static final int USER_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;
    private static final int REPEAT_PASSWORD_INDEX = 2;

    private final String user;
    private final String password;
    private final String repeatPassword;

    public RegisterCommand(UserRepository repository, Session context, String[] args) {
        super(repository, context);

        Command.validateArgs(args, ARGS_NUM);
        user = args[USER_INDEX];
        password = args[PASSWORD_INDEX];
        repeatPassword = args[REPEAT_PASSWORD_INDEX];
    }

    @Override
    public CommandResult execute() {
        try {
            getRepository().registerUser(getSelectionKey(), user, password, repeatPassword);
        } catch (PasswordVaultException e) {
            return new CommandResult(Status.ERROR, e.getMessage());
        }
        return new CommandResult(Status.OK, "Registration successful!");
    }
}