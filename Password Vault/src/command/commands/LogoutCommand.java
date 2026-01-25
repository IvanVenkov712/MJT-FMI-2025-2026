package command.commands;

import command.CommandResult;
import command.context.CommandContext;

public class LogoutCommand extends AbstractCommand {
    public static final String COMMAND_STRING = "logout";

    private static final int ARGS_COUNT = 0;

    public LogoutCommand(CommandContext context, String[] args) {
        super(context);
    }

    @Override
    public CommandResult execute() {

    }
}
