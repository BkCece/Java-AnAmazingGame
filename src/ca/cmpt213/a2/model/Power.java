package ca.cmpt213.a2.model;

import java.util.Random;

/**
 * Class to manage power-ups
 * only one is placed in the maze at a time
 * placed randomly
 */
public class Power{
    //If the player has picked up the power
    //private boolean isObtained;

    //If the player has used the power
    //private boolean isDepleted;

    ///Power-up Location
    private int row;
    private int col;

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

    /**
     * Compares hero and power locations to pick up the power
     * Returns true if power is obtained, false if not
     * If true, sets power values to obtained and not depleted
     * Boolean is used to notify the player of the pick-up
     * Only checks for pickup if not yet obtained
     * If the power has been depleted, place in a new location & reset it
     *
     */
    public boolean checkForPowerPickup(Model model){
        //If the power has not been picked up yet
        if ((model.getModelHero().getRow() == getRow()) && (model.getModelHero().getCol() == getCol())){
            //if the player has reached the power
            //increment number of powers
            model.setCurrNumberOfPowers(model.getCurrNumberOfPowers() + 1);

            //Check is maz number of powers reached
            if(model.getCurrNumberOfPowers() == model.getTotalNumberOfPowers()){
                setRow(-2);
                setCol(-2);
            }else{
                //If not max number of powers, generate new power
                //set location for new power in maze
                setRandomLocation(model.getMainMaze(), model.getCurrentMaze().getMazeRows(), model.getCurrentMaze().getMazeColumns());
            }

            return true;
        }

        return false;
    }
}
