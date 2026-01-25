package command.commands;

import command.Command;
import command.context.CommandContext;

public abstract class AbstractCommand implements Command {
    private final CommandContext context;

    public AbstractCommand(CommandContext context) {
        this.context = context;
    }

    protected CommandContext getContext() {
        return context;
    }
}
