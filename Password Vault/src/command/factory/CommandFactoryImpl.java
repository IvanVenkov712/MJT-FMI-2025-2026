package command.factory;

import command.Command;
import command.CommandResult;
import command.Status;
import command.commands.AddPasswordCommand;
import command.commands.GeneratePasswordCommand;
import command.commands.LoginCommand;
import command.commands.LogoutCommand;
import command.commands.RegisterCommand;
import command.commands.RemovePasswordCommand;
import command.commands.RetrieveCredentialsCommand;
import server.repository.UserRepository;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.List;

public class CommandFactoryImpl implements CommandFactory {
    private final UserRepository repository;

    public CommandFactoryImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Command newCommand(SelectionKey key, String clientInput) {
        List<String> tokens = getCommandArguments(clientInput);
        String[] args = tokens.subList(1, tokens.size()).toArray(new String[0]);

        return newCommand(key, tokens.get(0), args);
    }

    @Override
    public UserRepository getRepository() {
        return repository;
    }

    // straight out of https://stackoverflow.com/a/14656159 with small enhancement
    private static List<String> getCommandArguments(String input) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        boolean insideQuote = false;

        for (char c : input.toCharArray()) {
            if (c == '"') {
                insideQuote = !insideQuote;
            }
            if (c == ' ' && !insideQuote) { // when space is not inside quote split
                tokens.add(sb.toString().replace("\"", "")); // token is ready, let's add it to list
                sb.delete(0, sb.length()); // and reset StringBuilder`s content
            } else {
                sb.append(c); //else add character to token
            }
        }
        // let's not forget about last token that doesn't have space after it
        tokens.add(sb.toString().replace("\"", ""));

        return tokens;
    }

    private Command newCommand(SelectionKey selectionKey, String commandName, String[] commandArgs) {

        return switch (commandName) {
            case LoginCommand.COMMAND_STRING -> new LoginCommand(repository, selectionKey, commandArgs);
            case RegisterCommand.COMMAND_STRING -> new RegisterCommand(repository, selectionKey, commandArgs);
            case LogoutCommand.COMMAND_STRING -> new LogoutCommand(repository, selectionKey, commandArgs);
            case AddPasswordCommand.COMMAND_STRING -> new AddPasswordCommand(repository, selectionKey, commandArgs);
            case GeneratePasswordCommand.COMMAND_STRING ->
                new GeneratePasswordCommand(repository, selectionKey, commandArgs);
            case RetrieveCredentialsCommand.COMMAND_STRING ->
                new RetrieveCredentialsCommand(repository, selectionKey, commandArgs);
            case RemovePasswordCommand.COMMAND_STRING ->
                new RemovePasswordCommand(repository, selectionKey, commandArgs);
            case null -> () -> new CommandResult(Status.ERROR, "null command name");
            default -> () -> new CommandResult(Status.ERROR, "Not recognised command");
        };
    }
}
