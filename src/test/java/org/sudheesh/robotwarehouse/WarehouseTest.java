package org.sudheesh.robotwarehouse;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sudheesh.robotwarehouse.exception.CommandProcessingException;

import java.util.List;

import static org.sudheesh.robotwarehouse.Constants.X_AXIS;
import static org.sudheesh.robotwarehouse.Constants.Y_AXIS;

public class WarehouseTest {
    private WarehouseGrid warehouseGrid;
    private CommandProcessor commandProcessor;

    @BeforeEach
    public void initialise() {
        warehouseGrid = new WarehouseGrid.Builder()
                .setxDimension(Constants.X_DIMENSION)
                .setyDimension(Constants.Y_DIMENSION)
                .setInitialRobotPosition(Constants.INITIAL_ROBOT_POSITION_X, Constants.INITIAL_ROBOT_POSITION_Y)
                .addInitialCratePosition(Constants.INITIAL_CRATE_POSITION_X, Constants.INITIAL_CRATE_POSITION_Y)
                .addInitialCratePosition(Constants.INITIAL_CRATE_POSITION_2_Y, Constants.INITIAL_CRATE_POSITION_2_Y)
                .build();
        commandProcessor = new CommandProcessor(warehouseGrid);
    }

    @Test
    @DisplayName("Test the robot moves back to origin on command N E S W ")
    public void testIfRobotMovesBackToOrigin() throws Exception {
        List<Command> commands = Command.parse("N E S W");
        commandProcessor.process(commands);
        int[] robotPosition = warehouseGrid.getRobotPosition();
        Assertions.assertEquals(Constants.INITIAL_ROBOT_POSITION_X, robotPosition[X_AXIS]);
        Assertions.assertEquals(Constants.INITIAL_ROBOT_POSITION_Y, robotPosition[Y_AXIS]);
    }

    @Test
    @DisplayName("Test the crate should moved from position (5,5) to (5,9)")
    public void testGrabAndDropWorksAsExpected() throws Exception {
        Assertions.assertTrue(warehouseGrid.getGridValue(5, 5));

        List<Command> commands = Command.parse("N E N E N E N E E G S S S S D");
        commandProcessor.process(commands);

        int[] robotPosition = warehouseGrid.getRobotPosition();
        Assertions.assertEquals(5, robotPosition[X_AXIS]);
        Assertions.assertEquals(9, robotPosition[Y_AXIS]);
        Assertions.assertFalse(warehouseGrid.getGridValue(5, 5));
        Assertions.assertTrue(warehouseGrid.getGridValue(5, 9));
    }

    @Test
    @DisplayName("Test robot should not go out of grid")
    public void testOutOfGridException() throws Exception {
        List<Command> commands = Command.parse("N E S W S");
        CommandProcessingException exception = Assertions.assertThrows(CommandProcessingException.class, () -> {
            commandProcessor.process(commands);
        });
        Assertions.assertEquals("Failed to move to south, out of grid. Commands Executed=N E S W , Robot position=[0, 9]", exception.getMessage());
    }

    @Test
    @DisplayName("Test robot should not try and lift a crate if it already lifting one")
    public void testShouldNotLiftIfAlreadyLiftingOne() throws Exception {
        List<Command> commands = Command.parse("N E N E N E N E E G S G");
        CommandProcessingException exception = Assertions.assertThrows(CommandProcessingException.class, () -> {
            commandProcessor.process(commands);
        });
        Assertions.assertEquals(Command.G, warehouseGrid.getRobotState());
        Assertions.assertEquals("Failed, Robot already holding a crate. Commands Executed=N E N E N E N E E G S , Robot position=[5, 6]", exception.getMessage());
    }

    @Test
    @DisplayName("Test robot should not lift a crate if there is not one present")
    public void testShouldNotLiftIfNotOnePresent() throws Exception {
        Assertions.assertFalse(warehouseGrid.getGridValue(Constants.INITIAL_ROBOT_POSITION_X, Constants.INITIAL_ROBOT_POSITION_Y));

        List<Command> commands = Command.parse("G");
        CommandProcessingException exception = Assertions.assertThrows(CommandProcessingException.class, () -> {
            commandProcessor.process(commands);
        });
        Assertions.assertEquals("Failed, no crate in the position. Commands Executed=, Robot position=[0, 9]", exception.getMessage());
    }

    @Test
    @DisplayName("Test robot should not drop a crate on another crate")
    public void testShouldNotDropCrateOnAnotherCrate() throws Exception {
        Assertions.assertTrue(warehouseGrid.getGridValue(Constants.INITIAL_CRATE_POSITION_X, Constants.INITIAL_CRATE_POSITION_Y));
        Assertions.assertTrue(warehouseGrid.getGridValue(Constants.INITIAL_CRATE_POSITION_2_X, Constants.INITIAL_CRATE_POSITION_2_Y));

        List<Command> commands = Command.parse("N E N E N E N E E G N E N E N E N E N D");
        CommandProcessingException exception = Assertions.assertThrows(CommandProcessingException.class, () -> {
            commandProcessor.process(commands);
        });
        Assertions.assertFalse(warehouseGrid.getGridValue(Constants.INITIAL_CRATE_POSITION_2_X, Constants.INITIAL_CRATE_POSITION_2_Y));
        Assertions.assertEquals(Command.G, warehouseGrid.getRobotState());
        Assertions.assertEquals("Failed, the position already contains a crate. Commands Executed=N E N E N E N E E G N E N E N E N E N , Robot position=[9, 0]", exception.getMessage());
    }
}
