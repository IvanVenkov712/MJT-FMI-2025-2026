package command.commands;

import command.Command;
import command.CommandResult;
import command.context.CommandContext;

public class GeneratePasswordCommand extends AbstractCommand {
    public static final String COMMAND_STRING = "generate-password";

    public static final int ARGS_COUNT = 2;
    public static final int WEBSITE_INDEX = 0;
    public static final int USER_INDEX = 1;

    private final String userName;
    private final String website;

    public GeneratePasswordCommand(CommandContext context, String[] args) {
        super(context);

        Command.validateArgs(args, ARGS_COUNT);
        this.userName = args[USER_INDEX];
        this.website = args[WEBSITE_INDEX];
    }

    @Override
    public CommandResult execute() {

    }
}
