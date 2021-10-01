package org.sudheesh.robotwarehouse;

import org.sudheesh.robotwarehouse.exception.CommandProcessingException;

import java.util.List;

public class Warehouse {
    private WarehouseGrid warehouseGrid;
    private UserInputHandler inputHandler;
    private CommandProcessor commandProcessor;

    private void init() {
        warehouseGrid = new WarehouseGrid.Builder()
                .setxDimension(Constants.X_DIMENSION)
                .setyDimension(Constants.Y_DIMENSION)
                .setInitialRobotPosition(Constants.INITIAL_ROBOT_POSITION_X, Constants.INITIAL_ROBOT_POSITION_Y)
                .addInitialCratePosition(Constants.INITIAL_CRATE_POSITION_X, Constants.INITIAL_CRATE_POSITION_Y)
                .addInitialCratePosition(Constants.INITIAL_CRATE_POSITION_2_X, Constants.INITIAL_CRATE_POSITION_2_Y)
                .build();
        inputHandler = new UserInputHandler();
        commandProcessor = new CommandProcessor(warehouseGrid);
    }

    public void run() {
        init();
        while (!inputHandler.isExit()) {
            List<Command> commands = inputHandler.readCommands();
            if (commands != null && !commands.isEmpty()) {
                try {
                    commandProcessor.process(commands);
                } catch (CommandProcessingException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }


}
