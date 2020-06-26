package ca.cmpt213.a2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class to generate the maze
 * Need to check for correct rendering during each game
 * Generates maze recursively
 * Maze algorithm uses Recursive Methods and is a variation on Prim's Algorithm
 */
public class Maze {
    //Makes each new cell object while generating
    //Stores maze in 2D array of set size
    //Maze is 20x15
    //Leaves 18x13 inside to explore
    private static final int MAZE_COLUMNS = 20;
    private static final int MAZE_ROWS = 15;
    private int[][] maze;

    public Maze() {
        //size is 20x15, but traversal space is 18x3
        //this.
        maze = new int[getMazeRows()][getMazeColumns()];
        createMaze(1, 1);
    }

    public int getMazeColumns() {
        return MAZE_COLUMNS;
    }

    public int getMazeRows() {
        return MAZE_ROWS;
    }

    public int[][] getMaze() {
        return maze;
    }

    //Create maze
    //Empty: 0, Wall: 1, Path: 2
    public void createMaze(int currRow, int currCol){
        //Set edges and interior of maze
        for (int i = 0; i < getMazeRows(); i++){
            for(int j = 0; j < getMazeColumns(); j++){
                if(i == 0 || j == 0 || i == (getMazeRows() - 1) || j == (getMazeColumns() - 1)){
                    //set walls
                    maze[i][j] = 1;
                }else{
                    //set inner cells empty
                    maze[i][j] = 0;
                }
            }
        }

        //Set starting square as path
        maze[currRow][currCol] = 2;

        //Put adjacent cells to start into a list
        List<int[]> adjCells = new ArrayList<>();
        adjCells.add(new int[]{currRow + 1 , currCol});
        adjCells.add(new int[]{currRow , currCol + 1});

        findPath(maze, adjCells);
    }

    //Recursive function
    public void findPath(int[][] reMaze, List<int[]> reList){
        //Stop!
        if(reList.isEmpty()){
            return;
        }

        //Chose random direction
        Random r = new Random();
        int max = reList.size() - 1;
        //((max - min) + 1) + min
        int randAdjCell = r.nextInt((max - 0) + 1) + 0;
        int[] adjCell = reList.get(randAdjCell);

        int count = 0;

        //Check four directions for paths
        if(reMaze[adjCell[0] + 1][adjCell[1]] == 2)
            count++;
        if(reMaze[adjCell[0] - 1][adjCell[1]] == 2)
            count++;
        if(reMaze[adjCell[0]][adjCell[1] + 1] == 2)
            count++;
        if(reMaze[adjCell[0]][adjCell[1] - 1] == 2)
            count++;

        //If 2 or more paths, must be a wall
        if(count >= 2){
            reMaze[adjCell[0]][adjCell[1]] = 1;
            reList.remove(randAdjCell);
            findPath(reMaze, reList);
        }
        //If only 1 path, make current cell a path
        if(count == 1){
            reMaze[adjCell[0]][adjCell[1]] = 2;
            reList.remove(randAdjCell);

            //Check four directions for empty space
            //If empty, add to the adjacent cells list
            if(reMaze[adjCell[0] + 1][adjCell[1]] == 0)
                reList.add(new int[]{adjCell[0] + 1, adjCell[1]});
            if(reMaze[adjCell[0] - 1][adjCell[1]] == 0)
                reList.add(new int[]{adjCell[0] - 1, adjCell[1]});
            if(reMaze[adjCell[0]][adjCell[1] + 1] == 0)
                reList.add(new int[]{adjCell[0], adjCell[1] + 1});
            if(reMaze[adjCell[0]][adjCell[1] - 1] == 0)
                reList.add(new int[]{adjCell[0], adjCell[1] - 1});

            //Recursion!
            findPath(reMaze, reList);
        }
    }

    //Method to check that the created maze fits requirements
    //Returns true if verified
    //Returns false if needs to be regenerated
    public boolean verifyMaze(){
        //Check for any zeroes
        for (int i = 0; i < getMazeRows(); i++){
            for (int j = 0; j < getMazeColumns(); j++){
                if (maze[i][j] == 0)
                    return false;
            }
        }

        //Make sure corners are twos
        if (maze[1][1] == 1)
            return false;
        if (maze[1][getMazeColumns()-2] == 1)
            return false;
        if (maze[getMazeRows()-2][1] == 1)
            return false;
        if (maze[getMazeRows()-2][getMazeColumns()-2] == 1)
            return false;

        //Maze meets requirements
        return true;
    }

    //Add cycles to the maze
    //Returns number of cycles added
    public int addCycles(){
        int count = 0;
        for (int i = 2; i < getMazeRows() - 2; i++){
            for (int j = 2; j < getMazeColumns() - 2; j++){
                if(maze[i][j] == 1){
                    //top and bottom are walls
                    if(maze[i + 1][j] == 1 && maze[i - 1][j] == 1){
                        //sides are paths
                        if(maze[i][j + 1] == 2 && maze[i][j - 1] == 2){
                            //make a cycle
                            maze[i][j] = 2;
                            count++;
                        }
                    //top and bottom are paths
                    }else if(maze[i + 1][j] == 2 && maze[i - 1][j] == 2){
                        //sides are walls
                        if(maze[i][j + 1] == 1 && maze[i][j - 1] == 1){
                            //make a cycle
                            maze[i][j] = 2;
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }

    /**
     * Used in testing that a proper maze can be displayed
     * Should not be implemented in fully developed version of the application
     * Displays maze as 2D array elements with Path: 2, Wall: 1
     */
    public void displayMaze(){
        for (int i = 0; i < getMazeRows(); i++){
            for (int j = 0; j < getMazeColumns(); j++){
                System.out.print(maze[i][j] + " ");
            }
            System.out.println();
        }
    }


}
