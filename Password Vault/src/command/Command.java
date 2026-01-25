package command;

import java.util.ArrayList;
import java.util.List;

public interface Command {
    CommandResult execute();

    static Command of(String commandName, String[] commandArgs) {

    }

    static Command of(String clientInput) {
        List<String> tokens = getCommandArguments(clientInput);
        String[] args = tokens.subList(1, tokens.size()).toArray(new String[0]);

        return Command.of(tokens.get(0), args);
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

    public static void validateArgs(String[] commandArgs, int argsCount) {
        if (commandArgs == null) {
            throw new IllegalArgumentException("args is null");
        }
        if (commandArgs.length != argsCount) {
            throw new IllegalArgumentException("argument count mismatch");
        }
    }
}
