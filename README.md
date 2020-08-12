# Java-AnAmazingGame
A Maze Game created for SFU's CMPT213, taught by Victor Cheung, Summer 2020.The course focus was on Object-Oriented Design (OOD) using Java Programming. The course included inheritance, polymorphism, interfaces, and abstract classes, as well as best practices for code construction and an introduction to programming event driven graphical user interfaces (GUI).

This repository includes assignment 2 from the course, roughly two months into the 4 month term.

## Game Details
- The user collects powers while traversing a maze, in order to kill monsters
- To collect a power of kill a monster, the player must occupy the same space as the object of choice
- Monsters move pseudo-randomly (avoid backtracking) without knowledge of the hero's location
- The maze has a top down display with walls initially hidden
  - Maze walls become visible when the player is adjacent to them 

## My Maze Generation Logic

**Maze Generation Rules:**

    1. Monsters spawn in the four corners, so no walls can be placed there
    2. There must be multiple paths through the maze
    3. Closed-off rooms must be removed: only paths are allowed
    4. There must not be a 2x2 space of walls or of cells
    
**My Maze Generation Algorithm**

References:
- [Prim's Algorithm](https://en.wikipedia.org/wiki/Prim%27s_algorithm)
- [Maze Generation Algorithms](https://en.wikipedia.org/wiki/Maze_generation_algorithm)

My Algorithm:
- A variation of Prim's Algorithm, using a 2D-Array and Recursion
- I define untouched space as 0, walls as 1 and paths as 2
- I set the outer border's walls and label the rest as untoched space
- I get the "current cell" and store any adjacent, non-wall cells in a list
- My findPath() method recurses:
  - It randomly chooses an adjacent cell from the list and checks its adjacent cells for a valid path 
  - I increment a counter if the path is valid
  - Any empty cells are added to a list
  - The recursion ends when the first list is empty
- My maze is verified (to fit the rules) through the verifyMaz() method
  - Rule violations lead to maze re-generation
  
  
  

## Components and Tools
- This Repository includes **Class-Responsibility-Collaborator (CRC) Cards** for this project's **Object-Oriented Design (OOD)**
![CRC Cards](docs/CRCcards.JPG)

- I also created a **Unified Modeling Language (UML)** Class Diagram
![UML Class Diagram](docs/AnAmazingGameUML.jpg)

## Version Specifics
*gson library from com.google.code.gson*

Uses:
- Java **14.0.1**
- Json ~~gson~~ **2.8.6**
- IntelliJ IDEA Community Edition **2020.1.1**

## Example Gameplay - User Interface (UI)
### Game Startup and Menu
![Game Start](images/Maze_StartUI.JPG)

### A Fully Generated Maze Layout
![Example Maze](images/Maze_Generation.JPG)

### Power Up Interaction
![Power Up](images/Maze_PowerUp.JPG)

### Cheat Mode
![Cheat Mode](images/Maze_Cheat.JPG)

### A Finished Game - Win
![Game Win](images/Maze_Win.JPG)

