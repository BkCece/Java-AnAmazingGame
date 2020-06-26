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

    public static void main(String[] args){
        //boolean cheatMode = false;
        Model mainModel = new Model();
        MazeUI mainMazeUI = new MazeUI();
        TextUI mainTextUI = new TextUI();
        int[][] mainMaze;

        //trigger maze generation and get the maze array
        mainModel.createMazeModel();
        mainMaze = mainModel.getMainMaze();

        //Place the characters and items
        mainModel.setNumberOfMonsters(3);
        mainModel.initializeCharacters();

        //Display game text
        mainTextUI.printInstructions(
                mainMazeUI.getHeroIcon(),
                mainMazeUI.getMonsterIcon(),
                mainMazeUI.getPowerIcon(),
                mainMazeUI.getWallIcon(),
                mainMazeUI.getUnexploredIcon()
        );

        //make sure only walls, characters, and powers are shown
        //make sure surrounding 8 cells are displayed fully around the hero
        //use MazeUI
        //FIX THIS LATER SO IT'S BETTER ENCAPSULATED INTO MONSTER
        List<Integer> monsterRows = new ArrayList<>();
        List<Integer> monsterCols = new ArrayList<>();
        for(int i = 0; i < mainModel.getNumberOfMonsters(); i++){
            monsterRows.add(mainModel.getModelMonsters()[i].getRow());
            monsterCols.add(mainModel.getModelMonsters()[i].getCol());
        }

        do {
            mainMazeUI.placeCharacters(
                    mainMaze,
                    mainModel.getModelHero().getRow(),
                    mainModel.getModelHero().getCol(),
                    monsterRows,
                    monsterCols,
                    mainModel.getModelPower().getRow(),
                    mainModel.getModelPower().getCol()
            );

            //Display maze
            mainModel.setMazeVisibility();
            mainMazeUI.displayMazeUI(mainMaze, mainModel.getMazeMapping());

            int directionChoice;
            do {
                //Request and get user input with TestUI
                directionChoice = mainTextUI.getUserInput();

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
                    break;

                }else if (directionChoice == 5){
                    //Display full maze
                    //Set maze mapping to fully visible
                    mainModel.fullMazeVisibility();
                    mainMazeUI.displayMazeUI(mainMaze, mainModel.getMazeMapping());

                }else if(directionChoice == 6){
                    //Cheat Mode
                    //IDK YET ?????????
                    mainModel.setNumberOfMonsters(1);
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



        }while (true);
    }

}
