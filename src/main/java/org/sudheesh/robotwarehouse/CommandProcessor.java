package org.sudheesh.robotwarehouse;

import org.sudheesh.robotwarehouse.exception.CommandProcessingException;

import java.util.Arrays;
import java.util.List;

import static org.sudheesh.robotwarehouse.Constants.*;

public class CommandProcessor {
    private WarehouseGrid warehouseGrid;


    public CommandProcessor(WarehouseGrid warehouseGrid) {
        this.warehouseGrid = warehouseGrid;
    }

    public void process(List<Command> commands) {
        System.out.println("Processing started, commands=" + commands
                + ", Robot position=" + Arrays.toString(warehouseGrid.getRobotPosition()));
        StringBuffer commandsExecuted = new StringBuffer();
        try {
            for (Command command : commands) {
                process(command);
                commandsExecuted.append(command.name() + " ");
            }
            System.out.println("Processing completed, Robot start position=" + Arrays.toString(warehouseGrid.getRobotPosition()));
        } catch (IllegalArgumentException e) {
            throw new CommandProcessingException(e.getMessage()
                    + ". Commands Executed=" + commandsExecuted
                    + ", Robot position=" + Arrays.toString(warehouseGrid.getRobotPosition()));
        }

    }

    private void process(Command command) {
        switch (command) {
            case N: {
                moveToNorth();
                break;
            }
            case S: {
                moveToSouth();
                break;
            }
            case E: {
                moveToEast();
                break;
            }
            case W: {
                moveToWest();
                break;
            }
            case G: {
                grabCrate();
                break;
            }
            case D: {
                dropCrate();
                break;
            }
        }
    }

    private void dropCrate() {
        if (warehouseGrid.getRobotState() == null || warehouseGrid.getRobotState() == Command.D) {
            throw new IllegalArgumentException("Failed, Robot not holding the crate to drop");
        }
        int[] robotPosition = warehouseGrid.getRobotPosition();
        if (warehouseGrid.getGridValue(robotPosition[X_AXIS], robotPosition[Y_AXIS])) {
            throw new IllegalArgumentException("Failed, the position already contains a crate");
        } else {
            warehouseGrid.setRobotState(Command.D);
            warehouseGrid.setGridValue(robotPosition[X_AXIS], robotPosition[Y_AXIS], true);
            System.out.println("Crate dropped successfully, position=" + Arrays.toString(robotPosition));
        }
    }

    private void grabCrate() {
        if (warehouseGrid.getRobotState() == Command.G) {
            throw new IllegalArgumentException("Failed, Robot already holding a crate");
        }
        int[] robotPosition = warehouseGrid.getRobotPosition();
        if (warehouseGrid.getGridValue(robotPosition[X_AXIS], robotPosition[Y_AXIS])) {
            warehouseGrid.setRobotState(Command.G);
            warehouseGrid.setGridValue(robotPosition[X_AXIS], robotPosition[Y_AXIS], false);
            System.out.println("Crate grabbed successfully, position=" + Arrays.toString(robotPosition));
        } else {
            throw new IllegalArgumentException("Failed, no crate in the position");
        }
    }

    private void moveToEast() {
        int[] robotPosition = warehouseGrid.getRobotPosition();
        if ((robotPosition[X_AXIS] + 1) >= X_DIMENSION) {
            throw new IllegalArgumentException("Failed to move to East, out of grid");
        }
        robotPosition[X_AXIS] = robotPosition[X_AXIS] + 1;
        System.out.println("Robot moved to east, position=" + Arrays.toString(robotPosition));
    }

    private void moveToWest() {
        int[] robotPosition = warehouseGrid.getRobotPosition();
        if ((robotPosition[X_AXIS] - 1) < 0) {
            throw new IllegalArgumentException("Failed to move to west, out of grid");
        }
        robotPosition[X_AXIS] = robotPosition[X_AXIS] - 1;
        System.out.println("Robot moved to west, position=" + Arrays.toString(robotPosition));
    }

    private void moveToSouth() {
        int[] robotPosition = warehouseGrid.getRobotPosition();
        if ((robotPosition[Y_AXIS] + 1) >= Y_DIMENSION) {
            throw new IllegalArgumentException("Failed to move to south, out of grid");
        }
        robotPosition[Y_AXIS] = robotPosition[Y_AXIS] + 1;
        System.out.println("Robot moved to south, position=" + Arrays.toString(robotPosition));
    }

    private void moveToNorth() {
        int[] robotPosition = warehouseGrid.getRobotPosition();
        if ((robotPosition[Y_AXIS] - 1) < 0) {
            throw new IllegalArgumentException("Failed to move to north, out of grid");
        }
        robotPosition[Y_AXIS] = robotPosition[Y_AXIS] - 1;
        System.out.println("Robot moved to North, position=" + Arrays.toString(robotPosition));
    }
}
