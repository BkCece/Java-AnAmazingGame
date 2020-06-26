package ca.cmpt213.a2.model;

import java.util.Random;

/**
 * Class to manage overall model related concepts to be displayed,
 * including: maze (and its visability), monster, hero, power
 * Contains majority of maze logic
 */
public class Model {
    //OBJECTS of classes


    private static int NUMBER_OF_MONSTERS = 3;
    private Maze currentMaze;
    private Hero modelHero;
    private Monster[] modelMonsters;
    private Power modelPower;

    public int getNumberOfMonsters() {
        return NUMBER_OF_MONSTERS;
    }

    public Maze getCurrentMaze() {
        return currentMaze;
    }

    public void setCurrentMaze(Maze currentMaze) {
        this.currentMaze = currentMaze;
    }

    public Hero getModelHero() {
        return modelHero;
    }

    public void setModelHero(Hero modelHero) {
        this.modelHero = modelHero;
    }

    public Monster[] getModelMonsters() {
        return modelMonsters;
    }

    public void setModelMonsters(Monster[] modelMonsters) {
        this.modelMonsters = modelMonsters;
    }

    public Power getModelPower() {
        return modelPower;
    }

    public void setModelPower(Power modelPower) {
        this.modelPower = modelPower;
    }

    /**
     * Initializes the positions for key characters and items in the maze
     */
    public void initializeCharacters(){
        //Place hero at start
        setModelHero(new Hero());
        getModelHero().setRow(1);
        getModelHero().setCol(1);
        getModelHero().setAlive(true);

        //Place monsters in corners
        setModelMonsters(new Monster[]{
                new Monster(), new Monster(), new Monster()
        });

        //Set first monster
        getModelMonsters()[0].setRow(getCurrentMaze().getMazeRows() - 2);
        getModelMonsters()[0].setCol(getCurrentMaze().getMazeColumns() - 2);
        getModelMonsters()[0].setIsAlive(true);

        //Set next 2
        if (getNumberOfMonsters() == 3){
            getModelMonsters()[1].setRow(1);
            getModelMonsters()[1].setCol(getCurrentMaze().getMazeColumns() - 2);
            getModelMonsters()[1].setIsAlive(true);

            getModelMonsters()[2].setRow(getCurrentMaze().getMazeRows() - 2);
            getModelMonsters()[2].setCol(1);
            getModelMonsters()[2].setIsAlive(true);
        }

        //Set the power in the maze
        setModelPower(new Power());
        getModelPower().setRandomLocation(
                getCurrentMaze().getMaze(),
                getCurrentMaze().getMazeRows(),
                getCurrentMaze().getMazeColumns()
        );

    }

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

    /**
     * Creates and returns the maze as a 2D array
     */
    public Maze createMazeModel(){
        //Create a new maze
        //Generate maze walls
        do{
            setCurrentMaze(new Maze());

            //Generate maze until it meets requirements
            //check for paths in corners, no zeroes, and at least 2 cycles
        }while(getCurrentMaze().verifyMaze() == false || getCurrentMaze().addCycles() < 3);

        //currentMaze.displayMaze();

        //determine cell contents
        //walls or empty
        //hero corner
        //monsters and powers

        return getCurrentMaze();
    }

    //Return the maze as a 2D array
    public int[][] getMainMaze(){
        return getCurrentMaze().getMaze();
    }

}
