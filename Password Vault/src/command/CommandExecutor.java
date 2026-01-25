package command;

import command.factory.CommandFactory;

import java.nio.channels.SelectionKey;

public class CommandExecutor {
    private final CommandFactory factory;

    public CommandExecutor(CommandFactory factory) {
        this.factory = factory;
    }

    public CommandResult execute(SelectionKey key, String clientInput) {
        return factory.newCommand(key, clientInput).execute();
    }
}
