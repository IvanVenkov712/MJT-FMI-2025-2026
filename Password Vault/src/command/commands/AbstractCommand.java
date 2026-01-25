package command.commands;

import command.Command;
import server.repository.UserRepository;
import server.session.Session;

public abstract class AbstractCommand implements Command {
    private final Session session;
    private final UserRepository repository;

    public AbstractCommand(UserRepository repository, Session session) {
        this.session = session;
        this.repository = repository;
    }

    protected Session getSession() {
        return session;
    }

    protected UserRepository getRepository() {
        return repository;
    }
}
