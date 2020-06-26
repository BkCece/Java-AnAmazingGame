package ca.cmpt213.a2.textui;

import java.util.List;

/**
 * Class for displaying the text-based maze UI
 * strictly displays and gets/sets display icon
 * does not choose what is shown or when
 * no logic: only does what it is told
 * Run in IntelliJ Terminal
 */
public class MazeUI {
    private static final char heroIcon = '♥';
    private static final char monsterIcon = '♦';
    private static final char powerIcon = '♪';
    private static final char wallIcon = '█';
    private static final char unexploredIcon = '░';
    private static final char exploredIcon = ' ';

    //Set and get all icons for characters and powers
    public char getHeroIcon() {
        return heroIcon;
    }

    public char getMonsterIcon() {
        return monsterIcon;
    }

    public char getPowerIcon() {
        return powerIcon;
    }

    //Set and get all icons used for maze structure
    public char getWallIcon() {
        return wallIcon;
    }

    public char getUnexploredIcon() {
        return unexploredIcon;
    }

    public static char getExploredIcon() {
        return exploredIcon;
    }

    /**
     * Displays the full maze using current UI choices
     * Creates maze from the 2D array generated in the Model Class, passed from main
     * Wall: 1, Unexplored: 2
     * Hero: 3, Monster: 4, Power: 5
     *
     */
    public void displayMazeUI(int[][] maze, boolean[][] map){
        int numRows = maze.length;
        int numCols = maze[0].length;

        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                if (maze[i][j] == 1){
                    if(map[i][j] == true) {
                        //only display if the map says to
                        System.out.print(getWallIcon() + " ");
                    }else{
                        System.out.print(getUnexploredIcon() + " ");
                    }
                }else if(maze[i][j] == 2){
                    if(map[i][j] == true){
                        //only display if the map says to
                        System.out.print(getExploredIcon() + " ");
                    }else{
                        System.out.print(getUnexploredIcon() + " ");
                    }
                }else if(maze[i][j] == 3){
                    System.out.print(getHeroIcon() + " ");
                }else if(maze[i][j] == 4){
                    System.out.print(getMonsterIcon() + " ");
                }else if(maze[i][j] == 5){
                    System.out.print(getPowerIcon() + " ");
                }
            }
            System.out.println();
        }

        //Use mapping to determine if spaces should be undiscovered icon or not
    }

    /**
     * Display characters and items
     */
    public void placeCharacters(int[][]maze, int heroRow, int heroCol, List<Integer> monsterRows, List<Integer> monsterCols, int powerRow, int powerCol){
        if (powerRow != -1 || powerCol != -1){
            //place power ups first
            //only place if not yet obtained and not all powers have been obtained
            maze[powerRow][powerCol] = 5;
        }

        //can overwrite with monsters
        for(int i = 0; i < monsterRows.size(); i++){
            //if row and col aren't negative
            //make sure not to print if monster is dead
            if(monsterRows.get(i) != -1 && monsterCols.get(i) != -1)
                maze[monsterRows.get(i)][monsterCols.get(i)] = 4;
        }

        //heroes take precedence over all else
        maze[heroRow][heroCol] = 3;

    }
}
