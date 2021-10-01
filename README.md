## Robot Warehouse



#### Gradle command to run app
```
\robot-warehouse> gradlew run
```
#### gradle command to run test
```
\robot-warehouse> gradlew test
```
Test report location: build/reports/tests/test/index.html

### Examples
1.Move the robot in a full square, command sequence: N E S W
```
> Task :run
Welcome to the Robot Warehouse
Please enter the commands (Type 'exit' for shutdown): 
N E S W
Processing started, commands=[N, E, S, W], Robot start position=[0, 9]
Robot moved to North, position=[0, 8]
Robot moved to east, position=[1, 8]
Robot moved to south, position=[1, 9]
Robot moved to west, position=[0, 9]
Processing completed, Robot position=[0, 9]

Please enter the commands (Type 'exit' for shutdown): 
exit
 Shutting down the robot, Thanks you :)
```
2. Grab the crate from position [5,5] and drop in to the position [5,9], command sequence: N E N E N E N E E G S S S S D

```
> Task :run
Welcome to the Robot Warehouse
Please enter the commands (Type 'exit' for shutdown): 
N E N E N E N E E G S S S S D
Processing started, commands=[N, E, N, E, N, E, N, E, E, G, S, S, S, S, D], Robot start position=[0, 9]
Robot moved to North, position=[0, 8]
Robot moved to east, position=[1, 8]
Robot moved to North, position=[1, 7]
Robot moved to east, position=[2, 7]
Robot moved to North, position=[2, 6]
Robot moved to east, position=[3, 6]
Robot moved to North, position=[3, 5]
Robot moved to east, position=[4, 5]
Robot moved to east, position=[5, 5]
Crate grabbed successfully, position=[5, 5]
Robot moved to south, position=[5, 6]
Robot moved to south, position=[5, 7]
Robot moved to south, position=[5, 8]
Robot moved to south, position=[5, 9]
Crate dropped successfully, position=[5, 9]
Processing completed, Robot start position=[5, 9]

Please enter the commands (Type 'exit' for shutdown): 
exit

 Shutting down the robot, Thanks you :)
```