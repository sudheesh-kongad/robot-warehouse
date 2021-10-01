package org.sudheesh.robotwarehouse;

public class Application {
    public static void main(String[] args) {
        System.out.println("Welcome to the Robot Warehouse");
        Warehouse warehouse = new Warehouse();
        warehouse.run();
    }
}
