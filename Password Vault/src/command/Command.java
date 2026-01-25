package command;

public interface Command {
    CommandResult execute();

    static Command of(String commandStr) {

    }
}
