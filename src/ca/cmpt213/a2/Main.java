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
        initializeMazeFromModel(mainModel.getTotalNumberOfMonsters());

        //make sure only walls, characters, and powers are shown
        //make sure surrounding 8 cells are displayed fully around the hero
        //use MazeUI
        renderModelUpdates();
        mainMazeUI.displayMazeUI(mainMaze, mainModel.getMazeMapping());

        do {
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
                    mainTextUI.printPlayerInstructions(
                            mainMazeUI.getHeroIcon(),
                            mainMazeUI.getMonsterIcon(),
                            mainMazeUI.getPowerIcon(),
                            mainMazeUI.getWallIcon(),
                            mainMazeUI.getUnexploredIcon()
                    );

                }else if (directionChoice == 5){
                    //Display full maze
                    //Set maze mapping to fully visible
                    mainModel.enableFullMazeVisibility();
                    mainMazeUI.displayMazeUI(mainMaze, mainModel.getMazeMapping());

                }else if(directionChoice == 6){
                    //Cheat Mode
                    mainModel.setTotalNumberOfMonsters(1);
                    initializeMazeFromModel(mainModel.getTotalNumberOfMonsters());

                    mainTextUI.printCheatModeInstructions();
                    renderModelUpdates();
                    mainMazeUI.displayMazeUI(mainMaze, mainModel.getMazeMapping());

                }
                //renderModelUpdates();

                //loop while direction is unverified
            } while (!mainModel.getModelHero().verifyMovement(
                    directionChoice,
                    mainMaze,
                    mainModel.getModelHero().getRow(),
                    mainModel.getModelHero().getCol()
            ));

            //RENDER AGAIN TO CHECK MONSTER ENCOUNTER
            //Need to check before the monster moves
            //renderModelUpdates();

            //Only run if player chose to move hero
            if(directionChoice >= 0 && directionChoice < 4){
                //move player based on direction choice
                mainModel.getModelHero().move(
                        mainModel.getModelHero().getRow(),
                        mainModel.getModelHero().getCol(),
                        directionChoice,
                        mainMaze
                );

                renderModelUpdates();

                //this triggers all subsequent movements/actions
                //Move each monster
               for(int i = 0; i < mainModel.getTotalNumberOfMonsters(); i++){
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

            //Display maze
            renderModelUpdates();
            mainMazeUI.displayMazeUI(mainMaze, mainModel.getMazeMapping());

            //Continue running while hero is alive and there are heroes left
            //Ends game if player dies or all monsters are dead
        }while (mainModel.getCurrNumberOfMonsters() > 0 && mainModel.getModelHero().isAlive());

        if(mainModel.getCurrNumberOfMonsters() == 0 && mainModel.getModelHero().isAlive()){
            mainTextUI.printWinMessage();
        }else if(mainModel.getCurrNumberOfMonsters() > 0 && !mainModel.getModelHero().isAlive()){
            mainTextUI.printLoseMessage();
        }

    }

    /**
     * Sets initial state for maze, based on the required number of monsters
     */
    private static void initializeMazeFromModel(int numMonsters){
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
        mainTextUI.printPlayerInstructions(
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
    private static void renderModelUpdates(){
        //If monster is dead, set row and col to -1 and don't display
        for (int i = 0; i < mainModel.getTotalNumberOfMonsters(); i++){
            //Check if monster is dead
            if(!mainModel.getModelMonsters()[i].isAlive()){
                mainModel.getModelMonsters()[i].setRow(-1);
                mainModel.getModelMonsters()[i].setCol(-1);

            }else{
                if(mainModel.getModelMonsters()[i].getRow() > 0 && mainModel.getModelMonsters()[i].getCol() > 0){
                    int monsterEncounterResult = mainModel.getModelMonsters()[i].getBattleResult(
                            mainModel.getModelMonsters()[i],
                            mainModel.getCurrNumberOfPowers(),
                            mainModel.getModelHero()
                    );

                    if(monsterEncounterResult == 0){
                        //If 0, monster is dead: reduce monster count
                        //monster killed
                        mainModel.setCurrNumberOfPowers(mainModel.getCurrNumberOfPowers() - 1);
                        mainModel.setCurrNumberOfMonsters(mainModel.getCurrNumberOfMonsters() - 1);

                        mainModel.getModelMonsters()[i].setAlive(false);
                        mainModel.getModelMonsters()[i].setRow(-1);
                        mainModel.getModelMonsters()[i].setCol(-1);
                        mainTextUI.printMonsterKilled();

                    }else if(monsterEncounterResult == 1){
                        //If 1, sets hero to dead and ends game
                        mainModel.getModelHero().setAlive(false);
                    }
                }
            }
        }
        if(mainModel.getCurrNumberOfPowers() == mainModel.getTotalNumberOfPowers()){
            //if all powers have been obtained
            //Don't display power
            mainModel.getModelPower().setRow(-2);
            mainModel.getModelPower().setCol(-2);

        }else{
            if (mainModel.getModelPower().getRow() >= 0 || mainModel.getModelPower().getCol() >= 0){
                //only check for power if the hero doesn't have them all yet
                if (mainModel.getModelPower().checkIfPowerObtained(mainModel)){
                    mainTextUI.printPowerObtained();
                }
            }

        }

        //Display characters to map
        mainMazeUI.placeCharacters(
                mainMaze,
                mainModel.getModelHero().getRow(),
                mainModel.getModelHero().getCol(),
                mainModel.getModelMonsters(),
                mainModel.getTotalNumberOfMonsters(),
                mainModel.getModelPower().getRow(),
                mainModel.getModelPower().getCol()
        );

        //Display maze
        mainModel.setMazeVisibility();
        //mainMazeUI.displayMazeUI(mainMaze, mainModel.getMazeMapping());
    }

}
