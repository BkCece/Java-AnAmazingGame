package ca.cmpt213.a2.model;

/**
 * Class to manage the monster
 * Can move only WASD: 4 directions
 * Moves pseudo random: remember previous cell and choose next randomly
 */
public class Monster implements Character{
    //Monster position in the maze
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

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    //function to randomly choose move
    //each monster as separate object, called in Model Class
    //use random class to choose between 0-3
    //call movement functions
        //verify
        //move

    //isAlive attribute
    //get and set for xPosition & yPosition

    //function to check if valid movement
    //takes choice and compares it to maze options
    public boolean verifyMovement(int direction, int[][] maze, int monstRow, int monstCol){

        return false;
    }

    //moves character from start to end position, as given
    public void move(int startRow, int startCol, int direction, int[][] maze){

    }

    //changes character's status (alive or dead)
    //also decides to change status or not
    public void changeStatus(boolean isAlive){

    }

}
