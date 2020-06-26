package ca.cmpt213.a2.model;

/**
 * Class to manage the hero
 * Can move only WASD: 4 directions
 *
 */
public class Hero implements Character {
    //Player position in the maze
    //Row and column
    private int row;
    private int col;
    private boolean isAlive;

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

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    /**
     * Check if movement choice is a valid option
     * Compares choice it to maze path options
     * Returns true if valid more, false if not
     *
     */
    public boolean verifyMovement(int direction, int[][] maze, int heroRow, int heroCol){
        if (direction == 0){
            //Move up
            if(maze[heroRow - 1][heroCol] == 1)
                return false;
            else
                return true;

        }else if (direction == 1){
            //Move left
            if(maze[heroRow][heroCol - 1] == 1)
                return false;
            else
                return true;

        }else if(direction == 2){
            //Move down
            if(maze[heroRow + 1][heroCol] == 1)
                return false;
            else
                return true;

        }else if (direction == 3){
            //Move right
            if(maze[heroRow][heroCol + 1] == 1)
                return false;
            else
                return true;
        }

        return false;
    }

    /**
     *
     * Moves character from start to chosen direction, as given
     * Move direction has been previously verified as a correct choice
     *
     */
    public void move(int startRow, int startCol, int direction, int[][] maze){
        //set prev location to 2
        maze[startRow][startCol] = 2;

        //move to next spot
        if(direction == 0){
            //move up
            setRow(startRow - 1);

        }else if (direction == 1){
            //move left
            setCol(startCol - 1);

        }else if (direction == 2){
            //move down
            setRow(startRow + 1);

        }else if (direction == 3){
            //move right
            setCol(startCol + 1);
        }

    }

    //changes character's status (alive or dead)
    //also decides to change status or not
    public void checkStatus(boolean isAlive){

    }

}
