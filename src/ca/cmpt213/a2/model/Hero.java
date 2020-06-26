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

    //function to check if valid movement
    //takes choice and compares it to maze options
    public boolean verifyMovement(char choice){

        return false;
    }

    //moves character from start to end position, as given
    public void move(int startRow, int endRow, int startCol, int endCol){

    }

    //changes character's status (alive or dead)
    //also decides to change status or not
    public void changeStatus(boolean isAlive){

    }

}
