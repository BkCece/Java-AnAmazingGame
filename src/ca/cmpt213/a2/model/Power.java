package ca.cmpt213.a2.model;

import java.util.Random;

/**
 * Class to manage power-ups
 * only one is placed in the maze at a time
 * placed randomly
 */
public class Power{
    //If the player has picked up the power
    private int isObtained;

    //If the player has used the power
    private int isDepleted;

    ///Power-up Location
    private int row;
    private int col;

    public int getIsObtained() {
        return isObtained;
    }

    public void setIsObtained(int isObtained) {
        this.isObtained = isObtained;
    }

    public int getIsDepleted() {
        return isDepleted;
    }

    public void setIsDepleted(int isDepleted) {
        this.isDepleted = isDepleted;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Sets a random location for a power
     * Re-runs placement if on a wall or on the player
     */
    public void setRandomLocation(int[][] maze, int rows, int cols){
        //Place first power, randomly on the maze
        Random r;
        int randRow;
        int randCol;
        do{
            //((max - min) + 1) + min
            r = new Random();
            //position 0 and (size - 1) are reserved for walls
            randRow = r.nextInt(((rows - 2) - 1) + 1) + 1;
            randCol = r.nextInt(((cols - 2) - 1) + 1) + 1;

            //repeat until not placed on player or wall
        }while(maze[randRow][randCol] == 1 || (randRow == 1 && randCol == 1));

        setRow(randRow);
        setCol(randCol);
    }
}
