package ca.cmpt213.a2.textui;

import java.util.Scanner;

/**
 * Handles displaying of the text-based user interface
 * Manages menu and input
 * Run in IntelliJ Terminal
 */
public class TextUI {
    //Import UI
    Scanner input = new Scanner(System.in);

    //Determine player inputs
    private static final char UP_KEY = 'W';
    private static final char LEFT_KEY = 'A';
    private static final char DOWN_KEY = 'S';
    private static final char RIGHT_KEY = 'D';
    private static final char HELP_KEY = '?';
    private static final char MAZE_KEY = 'M';
    private static final char CHEAT_KEY = 'C';

    /**
     * Method to display maze game instructions
     * Display directions: "Kill 3 Monsters!"
     * Display Legend: get icons from MazeUI
     * Display Moves: get from main
     */
    public void printInstructions(char hero, char monster, char power, char wall, char unexplored){
        System.out.println("DIRECTIONS:");
        System.out.println("      Kill all 3 Monsters!");
        System.out.println("LEGEND:");
        System.out.println("      " + hero + ": You (The Hero)");
        System.out.println("      " + monster + ": Monster");
        System.out.println("      " + power + ": Power");
        System.out.println("      " + wall + ": Wall");
        System.out.println("      " + unexplored + ": Unexplored Space");

        //Request user input
        System.out.println("Moves:");
        System.out.println("      Use "
                + UP_KEY + " (up), "
                + LEFT_KEY + " (left), "
                + DOWN_KEY + " (down), "
                + RIGHT_KEY + " (right) to move. "
        );

        //Specify: "You must press enter after each move" - nextLine
        System.out.println("      (You must press enter to submit you movement choice).");
        System.out.println();
    }

    /**
     * Method to interact with user input
     * Returns Up: 0, Left: 1, Down: 2, Right: 3
     * Returns -1 upon error
     */
    public int getUserInput(){
        char charChoice;
        do{
            System.out.println();
            System.out.println("Total number of monsters to be killed: ");
            System.out.println("Number of powers currently in possession: ");
            System.out.println("Number of monsters alive: ");
            System.out.println("Enter your move [WASD]: ");
            String choice = input.nextLine();

            //make sure input isn't case sensitive
            //trim to remove any extra spaces
            charChoice = choice.trim().toUpperCase().charAt(0);

            //check if the choice is an acceptable input
        }while(!validateChoice(charChoice));

        switch (charChoice){
            //Return direction
            case UP_KEY:
                return 0;

            case LEFT_KEY:
                return 1;

            case DOWN_KEY:
                return 2;

            case RIGHT_KEY:
                return 3;

            //Other commands
            case HELP_KEY:
                return 4;

            case MAZE_KEY:
                return 5;

            case CHEAT_KEY:
                return 6;

            default:
                return -1;
        }
    }

    /**
     * Checks if the input is equal to at least one direction option
     * Returns true if valid input, false if not
     */
    public boolean validateChoice(char charChoice){
        //Movement choices
        if(charChoice == UP_KEY)
            return true;

        if(charChoice == LEFT_KEY)
            return true;

        if(charChoice == DOWN_KEY)
            return true;

        if(charChoice == RIGHT_KEY)
            return true;

        //Other commands
        if(charChoice == HELP_KEY)
            return true;

        if(charChoice == MAZE_KEY)
            return true;

        if(charChoice == CHEAT_KEY)
            return true;

        return false;
    }
}
