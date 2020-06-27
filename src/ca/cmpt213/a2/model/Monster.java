package ca.cmpt213.a2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private int previousMove;


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

    public int getPreviousMove() {
        return previousMove;
    }

    public void setPreviousMove(int previousMove) {
        this.previousMove = previousMove;
    }

    //function to randomly choose move
    //each monster as separate object, called in Model Class
    //use random class to choose between 0-3
    //call movement functions
        //verify
        //move

    //isAlive attribute
    //get and set for xPosition & yPosition

    /**
     *
     * Randomly chooses a valid path for the monster to take
     * Monster only moves over one cell left, right, up, or down
     *
     */
    public boolean verifyMovement(int direction, int[][] maze, int monstRow, int monstCol){

        //Check given direction for validity
        switch (direction){
            case 0:
                //UP is a wall
                if(maze[monstRow - 1][monstCol] == 1)
                    return false;
                else
                    return true;

            case 1:
                //LEFT is a wall
                if(maze[monstRow][monstCol - 1] == 1)
                    return false;
                else
                    return true;

            case 2:
                //DOWN is a wall
                if(maze[monstRow + 1][monstCol] == 1)
                    return false;
                else
                    return true;

            case 3:
                //RIGHT is a wall
                if(maze[monstRow][monstCol + 1] == 1)
                    return false;
                else
                    return true;

            default:
                return false;
        }
    }

    /**
     *
     * Moves single monster from current position
     * Moves to a random valid direction
     *
     */
    //moves character from start to end position, as given
    public void move(int startRow, int startCol, int direction, int[][] maze){
        //Check each direction and store valid choices in verified move list
        List<Integer> validMoves = new ArrayList<>();

        do {
            //Check each of the four directions
            //UP: 0, LEFT: 1, DOWN: 2, RIGHT: 3
            for (int i = 0; i < 4; i++) {
                if (verifyMovement(i, maze, startRow, startCol)) {
                    //Given direction is not a wall
                    //Add to valid moves list
                    validMoves.add(i);
                }
            }

            //If there is only one choice, choose it
            if(validMoves.size() == 1){
                direction = validMoves.get(0);

            }else{
                //check for backtracking and drop from the list
                for (int i = 0; i < validMoves.size(); i++){
                    if(checkForBacktracking(validMoves.get(i))){
                        validMoves.remove(i);
                        break;
                    }
                }

                //if only 1 item left, choose it
                if (validMoves.size() == 1){
                    direction = validMoves.get(0);

                }else{
                    //otherwise, randomly select
                    //Randomly pick a move from list
                    Random r = new Random();
                    int max = validMoves.size() - 1;
                    //((max - min) + 1) + min
                    int randChoice = r.nextInt((max - 0) + 1) + 0;

                    //Move to chosen adjacent cell
                    direction = validMoves.get(randChoice);
                }
            }

            //move to next spot
            if (direction == 0) {
                //move up
                setRow(startRow - 1);

            } else if (direction == 1) {
                //move left
                setCol(startCol - 1);

            } else if (direction == 2) {
                //move down
                setRow(startRow + 1);

            } else if (direction == 3) {
                //move right
                setCol(startCol + 1);
            }

            //Set previous location to a path
            maze[startRow][startCol] = 2;

            //Set previous location
            setPreviousMove(direction);

            //loop until a valid direction can be chosen and moved to
        }while (direction == -1);

    }

    //changes character's status (alive or dead)
    //also decides to change status or not
    public void checkStatus(boolean isAlive){

    }

    /**
     * Checks for monster backtracking
     * return true if backtracks
     *
     */
    public boolean checkForBacktracking(int direction){
        if((direction == 0 && getPreviousMove() == 2) || (direction == 2 && getPreviousMove() == 0)){
            //UP and DOWN
            return true;
        } else if((direction == 1 && getPreviousMove() == 3) || (direction == 3 && getPreviousMove() == 1)){
            //LEFT and RIGHT
            return true;
        }

        return false;
    }

    /**
     * Compares hero and 1 monster location to pick up the power
     * Returns 0: monster killed 1: hero killed, 2: no monster
     * If 0, sets monster to dead and power to depleted (decrease power count)
     * If 1, sets hero to dead and ends game
     * If 2, don't change anything: no encounter
     *
     */
    public int checkForMonster(Monster monster, int numPower, Hero hero){
        System.out.println("hero r: " + hero.getRow() + ", c: " + hero.getCol());
        System.out.println("mons c: " + monster.getRow() + " c: " + monster.getCol());

        if((hero.getRow() == monster.getRow()) && (hero.getCol() == monster.getCol())){
            //if hero finds monster
            if(numPower > 0){
                return 0;

            }else{
                //hero killed
                return 1;
            }
        }

        //hero doesn't find monster
        return 2;
    }
}
