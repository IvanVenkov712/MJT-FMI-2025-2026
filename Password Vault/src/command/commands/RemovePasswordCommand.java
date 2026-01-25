package command.commands;

import command.Command;
import command.CommandResult;
import command.context.CommandContext;

public class RemovePasswordCommand extends AbstractCommand {
    public static final String COMMAND_STRING = "remove-password";

    public static final int ARGS_COUNT = 2;
    public static final int WEBSITE_INDEX = 0;
    public static final int USER_INDEX = 1;

    private final String userName;
    private final String website;

    public RemovePasswordCommand(CommandContext context, String[] args) {
        super(context);

        Command.validateArgs(args, ARGS_COUNT);
        this.userName = args[USER_INDEX];
        this.website = args[WEBSITE_INDEX];
    }

    @Override
    public CommandResult execute() {
        return null;
    }
}
