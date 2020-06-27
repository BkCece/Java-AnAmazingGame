package ca.cmpt213.a2.model;

/**
 * Class to manage a character (type: hero or monster) movement around the maze
 * Gets the next cell, dependent on the character type
 * Must be implemented by other classes
 * https://www.geeksforgeeks.org/interfaces-in-java/
 */
public interface Character {
    //does not use it's own methods
    //class is used by Hero and Monster Classes
    //include prototypes here and provide implementation in other classes

    //abstract methods

    //function to check if valid movement
    boolean verifyMovement(int d, int[][] m, int r, int c);

    //moves character
    void move(int r, int c, int d, int[][] m);



}
