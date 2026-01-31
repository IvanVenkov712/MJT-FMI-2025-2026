package server.command;

import server.command.factory.CommandFactory;
import exception.PasswordVaultException;
import server.logger.FileLogger;
import server.logger.Logger;
import server.repository.UserRepository;

import java.nio.channels.SelectionKey;

public class CommandExecutor {
    private final CommandFactory factory;
    private final Logger logger;

    public CommandExecutor(CommandFactory factory, Logger logger) {
        this.factory = factory;
        this.logger = logger;
    }

    public CommandExecutor(CommandFactory factory) {
        this(factory, new FileLogger());
    }

    public CommandResult execute(SelectionKey key, String clientInput) {
        try {
            return factory.newCommand(key, clientInput).execute();
        } catch (PasswordVaultException e) {
            logger.log(e);
            return new CommandResult(Status.ERROR, e.getMessage());
        } catch (Exception e) {
            logger.log(e);
            return new CommandResult(Status.ERROR, "Internal server error");
        }
    }

    public UserRepository getRepository() {
        return factory.getRepository();
    }
}
