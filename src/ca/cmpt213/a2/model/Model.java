package ca.cmpt213.a2.model;

/**
 * Class to manage overall model related concepts to be displayed,
 * including: maze (and its visability), monster, hero, power
 * Contains majority of maze logic
 */
public class Model {
    //OBJECTS of classes

    //Maze is 20x15
    //Leaves 18x13 inside to explore
    private static final int MAZE_COLUMNS = 20;
    private static final int MAZE_ROWS = 15;

    //Generate maze until it meets requirements
    //Make new hero
    //Make new monster x 3
        //Make new power x 3
    //setCurrentCell

    //Contains functions for all possible maze actions
    //implement each as turns, with player movement
    //move Player, likely formatted as hero.verifyMove() then hero.Move()
        //setCurrentCell
        //move Monster
        //check Power
        //check Positions
            //if
        //check Monster status x 3
        //get Current Maze
        //update Maze cells

    public static void main(String[] args){
        Maze currentMaze;
        int count = 0;
        do{
            currentMaze = new Maze(MAZE_ROWS, MAZE_COLUMNS);
            System.out.println(count++);
        }while(currentMaze.verifyMaze() == false);

        currentMaze.displayMaze();

        //currentMaze.divideMaze(0, 0, MAZE_WIDTH, MAZE_HEIGHT, currentMaze.horizontalBisection(MAZE_WIDTH, MAZE_HEIGHT));
        //generate maze walls
            //stored in 2D array as zeroes and ones
        //determine cell contents
            //walls or empty
            //hero corner
            //monsters and powers
    }

}
