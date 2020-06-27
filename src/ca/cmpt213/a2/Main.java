package ca.cmpt213.a2;

import ca.cmpt213.a2.model.Model;
import ca.cmpt213.a2.textui.MazeUI;
import ca.cmpt213.a2.textui.TextUI;

/**
 * Main Class to run the code
 * Accesses model and textui
 * Handles player choices relative to maze model and UI
 */
public class Main {
    public static Model mainModel;
    public static MazeUI mainMazeUI;
    public static TextUI mainTextUI;
    public static int[][] mainMaze;

    public static void main(String[] args){
        //boolean cheatMode = false;
        mainModel = new Model();
        mainMazeUI = new MazeUI();
        mainTextUI = new TextUI();

        mainModel.setTotalNumberOfMonsters(3);
        initializeMaze(mainModel.getTotalNumberOfMonsters());

        mainModel.setCurrNumberOfMonsters(3);

        //make sure only walls, characters, and powers are shown
        //make sure surrounding 8 cells are displayed fully around the hero
        //use MazeUI
        do {
            renderMazeUpdates();

            int directionChoice;
            do {
                //Request and get user input with TestUI
                directionChoice = mainTextUI.getUserInput(
                        mainModel.getTotalNumberOfMonsters(),
                        mainModel.getCurrNumberOfMonsters(),
                        mainModel.getCurrNumberOfPowers()
                );

                //stop requesting input if user chose a non-direction option
                if(directionChoice == 4){
                    //Display game text
                    mainTextUI.printInstructions(
                            mainMazeUI.getHeroIcon(),
                            mainMazeUI.getMonsterIcon(),
                            mainMazeUI.getPowerIcon(),
                            mainMazeUI.getWallIcon(),
                            mainMazeUI.getUnexploredIcon()
                    );

                }else if (directionChoice == 5){
                    //Display full maze
                    //Set maze mapping to fully visible
                    mainModel.fullMazeVisibility();
                    mainMazeUI.displayMazeUI(mainMaze, mainModel.getMazeMapping());

                }else if(directionChoice == 6){
                    //Cheat Mode
                    mainModel.setTotalNumberOfMonsters(1);
                    initializeMaze(mainModel.getTotalNumberOfMonsters());

                    mainTextUI.enterCheatMode();
                    renderMazeUpdates();

                }

                //loop while direction is unverified
            } while (!mainModel.getModelHero().verifyMovement(
                    directionChoice,
                    mainMaze,
                    mainModel.getModelHero().getRow(),
                    mainModel.getModelHero().getCol()
            ));

            //Only run if player chose to move hero
            if(directionChoice >= 0 && directionChoice < 4){
                //move player based on direction choice
                mainModel.getModelHero().move(
                        mainModel.getModelHero().getRow(),
                        mainModel.getModelHero().getCol(),
                        directionChoice,
                        mainMaze
                );

                //this triggers all subsequent movements/actions
                //Move each monster
               for(int i = 0; i < mainModel.getCurrNumberOfMonsters(); i++){
                   if(mainModel.getModelMonsters()[i].isAlive()){
                       //if the monster is alive, move them
                       mainModel.getModelMonsters()[i].move(
                               mainModel.getModelMonsters()[i].getRow(),
                               mainModel.getModelMonsters()[i].getCol(),
                               -1,
                               mainMaze
                       );
                   }
               }
            }

        }while (mainModel.getCurrNumberOfMonsters() != 0);

        mainTextUI.endGame();
    }

    /**
     * Sets initial state for maze, based on the required number of monsters
     */
    public static void initializeMaze(int numMonsters){
        //trigger maze generation and get the maze array
        mainModel.createMazeModel();
        mainMaze = mainModel.getMainMaze();

        //Place the characters and items
        mainModel.setTotalNumberOfMonsters(numMonsters);
        mainModel.initializeCharacters();

        mainModel.setCurrNumberOfMonsters(numMonsters);
        mainModel.setTotalNumberOfPowers(numMonsters);
        mainModel.setCurrNumberOfPowers(0);

        //Display game text
        mainTextUI.printInstructions(
                mainMazeUI.getHeroIcon(),
                mainMazeUI.getMonsterIcon(),
                mainMazeUI.getPowerIcon(),
                mainMazeUI.getWallIcon(),
                mainMazeUI.getUnexploredIcon()
        );
    }

    /**
     * Renders the maze fully with all characters and powers
     * Maze starts hidden, except for adjacent cells
     * Player can see hero, monster(s), and one power at a time
     *
     */
    public static void renderMazeUpdates(){
        //If monster is dead, set row and col to -1 and don't display
        for (int i = 0; i < mainModel.getTotalNumberOfMonsters(); i++){
            //Check if monster is dead
            if(!mainModel.getModelMonsters()[i].isAlive()){
                mainModel.getModelMonsters()[i].setRow(-1);
                mainModel.getModelMonsters()[i].setCol(-1);
            }

        }
        if(mainModel.getCurrNumberOfPowers() == mainModel.getTotalNumberOfPowers()){
            //if all powers have been obtained
            //Don't display power
            mainModel.getModelPower().setRow(-2);
            mainModel.getModelPower().setCol(-2);

        }else{
            //only check for power if the hero doesn't have them all yet
            if (mainModel.checkForPowerPickup()){
                mainTextUI.powerObtained();
            }
        }

        //Display characters to map
        mainMazeUI.placeCharacters(
                mainMaze,
                mainModel.getModelHero().getRow(),
                mainModel.getModelHero().getCol(),
                mainModel.getModelMonsterRows(),
                mainModel.getModelMonsterCols(),
                mainModel.getModelPower().getRow(),
                mainModel.getModelPower().getCol()
        );

        //Display maze
        mainModel.setMazeVisibility();
        mainMazeUI.displayMazeUI(mainMaze, mainModel.getMazeMapping());
    }

}
