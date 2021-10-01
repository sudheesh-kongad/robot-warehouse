package org.sudheesh.robotwarehouse;

import java.util.ArrayList;
import java.util.List;

public enum Command {
    N("North"),
    S("South"),
    E("East"),
    W("West"),
    G("Grab"),
    D("Drop");

    private String value;

    Command(String value) {
        this.value = value;
    }

    public static List<Command> parse(String commandSequence) {
        if (commandSequence == null || commandSequence.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }
        return parse(commandSequence.split(" "));
    }

    private static List<Command> parse(String[] commandSequence) {
        List<Command> commands = new ArrayList(commandSequence.length);
        for (String command : commandSequence) {
            try {
                commands.add(Command.valueOf(command));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid command " + command);
            }
        }
        return commands;
    }

    public String getValue() {
        return value;
    }
}
