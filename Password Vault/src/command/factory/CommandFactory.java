package command.factory;

import command.Command;
import server.repository.UserRepository;

import java.nio.channels.SelectionKey;

public interface CommandFactory {
    Command newCommand(SelectionKey key, String clientInput);

    UserRepository getRepository();
}
