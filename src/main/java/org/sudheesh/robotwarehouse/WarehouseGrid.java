package org.sudheesh.robotwarehouse;


import java.util.ArrayList;
import java.util.List;

import static org.sudheesh.robotwarehouse.Constants.X_AXIS;
import static org.sudheesh.robotwarehouse.Constants.Y_AXIS;

public class WarehouseGrid {

    private boolean[][] grid;

    private int[] robotPosition;

    private Command robotState;

    private WarehouseGrid() {
    }

    public int[] getRobotPosition() {
        return robotPosition;
    }

    public Command getRobotState() {
        return robotState;
    }

    public void setRobotState(Command robotState) {
        this.robotState = robotState;
    }

    public boolean getGridValue(int x, int y) {
        return grid[x][y];
    }

    public void setGridValue(int x, int y, boolean value) {
        grid[x][y] = value;
    }

    public static class Builder {

        private int xDimension;
        private int yDimension;
        private int[] robotPosition;
        private List<Integer[]> cratePositions = new ArrayList<>();

        public Builder setxDimension(int xDimension) {
            this.xDimension = xDimension;
            return Builder.this;
        }

        public Builder setyDimension(int yDimension) {
            this.yDimension = yDimension;
            return Builder.this;
        }

        public Builder setInitialRobotPosition(int xDimension, int yDimension) {
            this.robotPosition = new int[]{xDimension, yDimension};
            return Builder.this;
        }

        public Builder addInitialCratePosition(int xDimension, int yDimension) {
            this.cratePositions.add(new Integer[]{xDimension, yDimension});
            return Builder.this;
        }

        public WarehouseGrid build() {
            WarehouseGrid warehouseGrid = new WarehouseGrid();
            warehouseGrid.grid = new boolean[xDimension][yDimension];
            warehouseGrid.robotPosition = this.robotPosition;
            warehouseGrid.robotState = null;
            for (Integer[] cratePosition : cratePositions) {
                warehouseGrid.grid[cratePosition[X_AXIS]][cratePosition[Y_AXIS]] = true;
            }
            return warehouseGrid;
        }
    }


}
