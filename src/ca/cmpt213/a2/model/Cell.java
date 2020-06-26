package ca.cmpt213.a2.model;

/**
 * Class to manage all features of an individual cell
 * Not going to use it, maybe
 * seems redundant
 */
public class Cell {
    //each cell has position in array
    //fields for xPosition & yPosition
    private int xPos;
    private int yPos;

    //each cell has content
    private boolean isHero;
    private boolean isMonster;
    private boolean isPower;
    private boolean isWall;

    public Cell(int xPos, int yPos, boolean isHero, boolean isMonster, boolean isPower, boolean isWall) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.isHero = isHero;
        this.isMonster = isMonster;
        this.isPower = isPower;
        this.isWall = isWall;
    }

    //if isEdge is true, then isWall must be true ALWAYS
    //private int isEdge;

    //toggle if should be shown or hidden
        //isDiscovered
        //overwritten to show character or power
        //only undiscovered icon if not a character or edge



}

