package org.sudheesh.robotwarehouse;

import java.util.List;
import java.util.Scanner;

public class UserInputHandler {
    Scanner scanner = new Scanner(System.in);
    private boolean exit = false;

    public List<Command> readCommands() {
        System.out.println("\nPlease enter the commands (Type 'exit' for shutdown): ");
        String input = scanner.nextLine();
        if("exit".equalsIgnoreCase(input.trim())) {
            setExitFlagTrue();
        } else {
           return parseInput(input);
        }
        return null;
    }

    private void setExitFlagTrue() {
        exit = true;
        scanner.close();
        System.out.println("\n Shutting down the robot, Thanks you :)");
    }

    private List<Command> parseInput(String commandSequence) {
        List<Command> commands = null;
        try {
            commands = Command.parse(commandSequence);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid command, Error:" + e.getMessage());
        }
        return commands;
    }

    public boolean isExit() {
        return exit;
    }

}
