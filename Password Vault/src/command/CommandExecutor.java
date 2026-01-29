package command;

import command.factory.CommandFactory;
import exception.ErrorHandler;
import exception.PasswordVaultException;
import server.repository.UserRepository;

import java.nio.channels.SelectionKey;

public class CommandExecutor {
    private final CommandFactory factory;

    public CommandExecutor(CommandFactory factory) {
        this.factory = factory;
    }

    public CommandResult execute(SelectionKey key, String clientInput) {
        try {
            return factory.newCommand(key, clientInput).execute();
        } catch (PasswordVaultException e) {
            ErrorHandler.writeToErrorLog(e);
            return new CommandResult(Status.ERROR, e.getMessage());
        } catch (Exception e) {
            ErrorHandler.writeToErrorLog(e);
            return new CommandResult(Status.ERROR, "Internal server error");
        }
    }

    public UserRepository getRepository() {
        return factory.getRepository();
    }
}
