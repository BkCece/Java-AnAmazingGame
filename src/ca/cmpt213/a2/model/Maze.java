package ca.cmpt213.a2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class to generate the maze
 * Need to check for correct rendering during each game
 * Generates maze recursively
 */
public class Maze {
    //Makes each new cell object while generating
    //Stores maze in 2D array of set size
    private final int row;
    private final int col;
    private final int[][] maze;

    public Maze(int row, int col) {
        this.row = row;
        this.col = col;
        //size is 20x15, but traversal space is 18x3
        //this.
        maze = new int[this.row][this.col];
        createMaze(1, 1);
    }

    //Create maze
    //Empty: 0, Wall: 1, Path: 2, Adjacent: 3
    public void createMaze(int currRow, int currCol){
        //Set edges and interior of maze
        for (int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(i == 0 || j == 0 || i == (row-1) || j == (col-1)){
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
        List<Integer[]> adjCells = new ArrayList<>();
        adjCells.add(new Integer[]{currRow + 1 , currCol});
        adjCells.add(new Integer[]{currRow , currCol + 1});

        findPath(maze, adjCells);
    }

    //Recursive function
    public void findPath(int[][] reMaze, List<Integer[]> reList){
        //Stop!
        if(reList.isEmpty()){
            return;
        }

    //Chose random direction
        Random r = new Random();
        int max = reList.size() - 1;
        //((max - min) + 1) + min
        int randAdjCell = r.nextInt((max - 0) + 1) + 0;
        Integer[] adjCell = reList.get(randAdjCell);

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
                reList.add(new Integer[]{adjCell[0] + 1, adjCell[1]});
            if(reMaze[adjCell[0] - 1][adjCell[1]] == 0)
                reList.add(new Integer[]{adjCell[0] - 1, adjCell[1]});
            if(reMaze[adjCell[0]][adjCell[1] + 1] == 0)
                reList.add(new Integer[]{adjCell[0], adjCell[1] + 1});
            if(reMaze[adjCell[0]][adjCell[1] - 1] == 0)
                reList.add(new Integer[]{adjCell[0], adjCell[1] - 1});

            //Recursion!
            findPath(reMaze, reList);
        }

    }

    //Method to check that the created maze fits requirements
    //Returns true if verified
    //Returns false if needs to be regenerated
    public boolean verifyMaze(){
        //Check for any zeroes
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                if (maze[i][j] == 0)
                    return false;
            }
        }

        //Make sure corners are twos
        if (maze[1][1] == 1)
            return false;
        if (maze[1][col-2] == 1)
            return false;
        if (maze[row-2][1] == 1)
            return false;
        if (maze[row-2][col-2] == 1)
            return false;

        //Maze meets requirements
        return true;
    }

    public void addCycles(){
        
    }

    public void displayMaze(){
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                System.out.print(maze[i][j] + " ");
            }
            System.out.println();
        }
    }

}
