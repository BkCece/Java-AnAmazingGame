package ca.cmpt213.a2;

import ca.cmpt213.a2.model.Model;
import ca.cmpt213.a2.textui.MazeUI;
import ca.cmpt213.a2.textui.TextUI;

import java.util.ArrayList;
import java.util.List;

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
                directionChoice = mainTextUI.getUserInput(mainModel.getTotalNumberOfMonsters(), mainModel.getCurrNumberOfMonsters());

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

                //this triggers all subsequent movements/actions using Model
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
        System.out.println("curr: " + mainModel.getCurrNumberOfPowers());
        System.out.println("tot: " + mainModel.getTotalNumberOfPowers());
        if(mainModel.getCurrNumberOfPowers() == mainModel.getTotalNumberOfPowers()){
            //if all powers have been obtained
            //Don't display power
            mainMazeUI.placeCharacters(
                    mainMaze,
                    mainModel.getModelHero().getRow(),
                    mainModel.getModelHero().getCol(),
                    mainModel.getModelMonsterRows(),
                    mainModel.getModelMonsterCols(),
                    -1,
                    -1
            );
        }else{
            //Display power to map
            mainMazeUI.placeCharacters(
                    mainMaze,
                    mainModel.getModelHero().getRow(),
                    mainModel.getModelHero().getCol(),
                    mainModel.getModelMonsterRows(),
                    mainModel.getModelMonsterCols(),
                    mainModel.getModelPower().getRow(),
                    mainModel.getModelPower().getCol()
            );

            //only check for power if the hero doesn't have them all yet
            if (mainModel.checkForPowerPickup())
                mainTextUI.powerObtained();
        }



        //Display maze
        mainModel.setMazeVisibility();
        mainMazeUI.displayMazeUI(mainMaze, mainModel.getMazeMapping());
    }

}
