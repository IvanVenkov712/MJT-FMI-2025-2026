package command;

public interface Command {

    CommandResult execute();

    static void validateArgs(String[] commandArgs, int argsCount) {
        if (commandArgs == null) {
            throw new IllegalArgumentException("args is null");
        }
        if (commandArgs.length != argsCount) {
            throw new IllegalArgumentException("argument count mismatch");
        }
    }
}
