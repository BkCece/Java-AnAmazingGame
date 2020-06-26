package ca.cmpt213.a2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class to manage overall model related concepts to be displayed,
 * including: maze (and its visability), monster, hero, power
 * Contains majority of maze logic
 */
public class Model {
    private int currNumberOfMonsters;
    private int totalNumberOfMonsters;

    //OBJECTS of classes
    private Maze currentMaze;
    private Hero modelHero;
    private Monster[] modelMonsters;
    private Power modelPower;

    //Sets whether a cell is discovered and should be displayed
    //Make mapping the same size as the main maze
    private boolean[][] mazeMapping;

    public int getCurrNumberOfMonsters() {
        return currNumberOfMonsters;
    }

    public void setCurrNumberOfMonsters(int currNumberOfMonsters) {
        this.currNumberOfMonsters = currNumberOfMonsters;
    }

    public int getTotalNumberOfMonsters() {
        return totalNumberOfMonsters;
    }

    public void setTotalNumberOfMonsters(int totalNumberOfMonsters) {
        this.totalNumberOfMonsters = totalNumberOfMonsters;
    }

    public List<Integer> getModelMonsterRows(){
        List<Integer> monsterRows = new ArrayList<>();
        for(int i = 0; i < getCurrNumberOfMonsters(); i++){
            monsterRows.add(getModelMonsters()[i].getRow());
        }
        return monsterRows;
    }

    public List<Integer> getModelMonsterCols(){
        List<Integer> monsterCols = new ArrayList<>();
        for(int i = 0; i < getCurrNumberOfMonsters(); i++){
            monsterCols.add(getModelMonsters()[i].getCol());
        }
        return monsterCols;
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

    //Return the maze as a 2D array
    public int[][] getMainMaze(){
        return getCurrentMaze().getMaze();
    }

    public boolean[][] getMazeMapping() {
        return mazeMapping;
    }

    public void setMazeMapping(boolean[][] mazeMapping) {
        this.mazeMapping = mazeMapping;
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

        if(getTotalNumberOfMonsters() == 1){
            //Place monster in far corner
            setModelMonsters(new Monster[]{ new Monster() });

        }else if (getTotalNumberOfMonsters() == 3){
            //Place monsters in corners
            setModelMonsters(new Monster[]{
                    new Monster(), new Monster(), new Monster()
            });
        }

        //Set first monster
        getModelMonsters()[0].setRow(getCurrentMaze().getMazeRows() - 2);
        getModelMonsters()[0].setCol(getCurrentMaze().getMazeColumns() - 2);
        getModelMonsters()[0].setIsAlive(true);

        //Set next 2
        if (getTotalNumberOfMonsters() == 3){
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
     * Also calls initializeMazeVisibility()
     */
    public Maze createMazeModel(){
        //Create a new maze
        //Generate maze walls
        do{
            setCurrentMaze(new Maze());

            //Generate maze until it meets requirements
            //check for paths in corners, no zeroes, and at least 2 cycles
        }while(getCurrentMaze().verifyMaze() == false || getCurrentMaze().addCycles() < 3);

        //determine cell contents
        initializeMazeVisibility();
        return getCurrentMaze();
    }


    /**
     * Sets beginning state of maze mapping
     * All cells are undiscovered, except walls
     *
     */
    public void initializeMazeVisibility() {
       setMazeMapping(new boolean[getCurrentMaze().getMazeRows()][getCurrentMaze().getMazeColumns()]);

        for (int i = 0; i < getCurrentMaze().getMazeRows(); i++){
            for (int j = 0; j < getCurrentMaze().getMazeColumns(); j++){
                if(i == 0 || j == 0 || (i == getCurrentMaze().getMazeRows() - 1) || (j == getCurrentMaze().getMazeColumns() - 1)){
                    //make visible if cell is on the edges of the maze
                    getMazeMapping()[i][j] = true;
                }else{
                    //set all others to undiscovered
                    getMazeMapping()[i][j] = false;
                }
            }
        }
    }

    /**
     *
     * Updates the maze mapping to the current position of the hero
     * Makes adjacent cells visible
     * Previously discovered cells remain visible
     *
     */
    public void setMazeVisibility(){
        for (int i = 0; i < getCurrentMaze().getMazeRows(); i++){
            for (int j = 0; j < getCurrentMaze().getMazeColumns(); j++){
                if((i == getModelHero().getRow() - 1) && j == (getModelHero().getCol())){
                    //make visible if adjacent to hero: top
                    getMazeMapping()[i][j] = true;

                }else if((i == getModelHero().getRow()) && j == (getModelHero().getCol() - 1)){
                    //make visible if adjacent to hero: left
                    getMazeMapping()[i][j] = true;

                }else if((i == getModelHero().getRow() + 1) && j == (getModelHero().getCol())){
                    //make visible if adjacent to hero: bottom
                    getMazeMapping()[i][j] = true;

                }else if((i == getModelHero().getRow()) && j == (getModelHero().getCol() + 1)){
                    //make visible if adjacent to hero: right
                    getMazeMapping()[i][j] = true;

                }else if(i == getModelHero().getRow() && j == getModelHero().getCol()){
                    //make visible if where current hero is
                    getMazeMapping()[i][j] = true;
                }
            }
        }
    }

    /**
     * Make the entire maze visible
     */
    public void fullMazeVisibility() {
        setMazeMapping(new boolean[getCurrentMaze().getMazeRows()][getCurrentMaze().getMazeColumns()]);

        for (int i = 0; i < getCurrentMaze().getMazeRows(); i++){
            for (int j = 0; j < getCurrentMaze().getMazeColumns(); j++){
                //make every cell visible
                getMazeMapping()[i][j] = true;
            }
        }
    }

    /**
     * Compares hero and power locations to pick up the power
     * Returns true if power is obtained, false if not
     * If true, sets power values to obtained and not depleted
     * Boolean is used to notfy the player of the pick-up
     * Only checks for pickup if not yet obtained
     * If the power has been depleted, place in a new location & reset it
     *
     */
    public boolean checkForPowerPickup(){
        if(getModelPower().getIsDepleted()){
            //Power is depleted
            getModelPower().setRandomLocation(getMainMaze(), getCurrentMaze().getMazeRows(), getCurrentMaze().getMazeColumns());
            getModelPower().setIsDepleted(false);
            getModelPower().setIsObtained(false);
        }else{
            if (!getModelPower().getIsObtained()){
                //If the power has not been picked up yet
                if ((getModelHero().getRow() == getModelPower().getRow()) && (getModelHero().getCol() == getModelPower().getCol())){
                    //if the player has reached the power
                    getModelPower().setIsObtained(true);
                    getModelPower().setIsDepleted(false);
                    return true;
                }
            }
        }

        return false;
    }
}
