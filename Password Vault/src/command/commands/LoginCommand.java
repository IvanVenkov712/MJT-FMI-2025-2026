package command.commands;

import command.Command;
import command.CommandResult;
import command.context.CommandContext;

public class LoginCommand extends AbstractCommand {
    public static final String COMMAND_STRING = "login";

    private static final int ARGS_COUNT = 2;
    private static final int USER_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    private final String user;
    private final String password;

    public LoginCommand(CommandContext context, String[] args) {
        super(context);

        Command.validateArgs(args, ARGS_COUNT);
        user = args[USER_INDEX];
        password = args[PASSWORD_INDEX];
    }

    @Override
    public CommandResult execute() {

    }
}
