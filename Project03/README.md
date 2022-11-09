# Dungeon Model

## Overview

The following program represents a dungeon model that contains a network of tunnels and caves that
are interconnected so that player can explore the entire world by traveling from cave to cave
through the tunnels that connect them.

A location in the dungeon where a player can explore, can be connected to at most four (4) other
locations: one to the north, one to the east, one to the south, and one to the west. A location can
further be classified as tunnel (which has exactly 2 entrances) or a cave (which has 1, 3 or 4
entrances).

The program simulates these basic properties of an adventure game where the player can move from one
location to other and collecting the treasures available in each location.

## List of Features of the Program

* The dungeon is generated randomly each time the game is played.
* Dungeon is represented as a 2D grid.
* There is a path from every cave in the dungeon to every other cave in the dungeon.
* Each dungeon can be constructed with a degree of interconnectivity. We define an interconnectivity
  = 0 when there is exactly one path from every cave in the dungeon to every other cave in the
  dungeon. Increasing the degree of interconnectivity increases the number of paths between caves.
* Dungeon can wrap itself around the edges.
* One cave is randomly selected as the start and one cave is randomly selected to be the end. The
  path between the start and the end locations is least of length 5.
* Each Cave can support three types of treasure: diamonds, rubies, and sapphires.
* Treasure are added to a specified percentage of caves. A cave can have more than one treasure.
* Player to enter the dungeon at the start.
* Player to move from their current location.
* Player to pick up treasure that is located in their same location.
* Game ends when player reached the end location.

## How to Run

1) The res/ folder contains a Project03.jar file which can be run directly in IntelliJ or Eclipse
   Ide. The following command line arguments are required:
    * rows: Number of rows in the dungeon. Should be at least 6.
    * columns: Number of Columns in the dungeon. Should be at least 6.
    * isWrapped: Whether the dungeon is wrapped around its end
    * degree: The degree of interconnectivity for the dungeon.
    * percentage: The percentage of caves having treasure in it.
2) The arguments should be in this specific order: rows columns isWrapped degree percentage
3) Use the following command: java -jar Project03.jar rows columns isWrapped degree percentage.
4) example-> java -jar Project03.jar 6 8 false 10 50

## How to Use

1) The dungeon will be randomly generated based on the command line inputs.
2) Once the dungeon is created, a dumped dungeon is displayed on the screen for reference.
    1) The Player's current location is (P).
    2) Each location is represented as ( ).
    3) The end location is (&).
    4) A location is connected to other location via '|' or '——'.
3) Player can select one of the following commands to interact with dungeon:  'move', 'pick', '
   quit', 'visitAll', '
   shortestPath'. If 'move' is picked, player need to enter a direction: 'north','east','south','
   west'.
4) Game will end once the player reaches the end point or player enters 'quit'.

## Description of Examples

<ins>Run1 - output_1_NonWrapped.txt</ins>

1) Creates a random NonWrapped Dungeon with the following constraints:
   rows = 6, columns = 8, isWrapped = false, degree = 10, percent = 50.
2) Player(marked as (P)) starts moving from initialPosition(#). Picking treasures and directions are
   taken as user input('move' and 'pick').
3) Game ends as player reaches the endPoint(marked as &).

<ins>Run2 - output_2_Wrapped.txt</ins>

1) Creates a random wrapped Dungeon with the following constraints:
   rows = 6, columns = 8, isWrapped = true, degree = 10, percent = 50.
2) Player(marked as (P)) starts moving from initialPosition(#). Picking treasures and directions are
   taken as user input('move' and 'pick').
3) Game ends as player reaches the endPoint(marked as &).

<ins>Run3 - output_3_VisitAll.txt</ins>

1) Creates a random NonWrapped Dungeon with the following constraints:
   rows = 6, columns = 8, isWrapped = false, degree = 10, percent = 50.
2) User enters special command: 'visitAll'
3) Player automatically moves to all the locations and collecting treasures from them.
4) Once all the locations are visited, player reaches the endpoint and finishes the game.

<ins>Run4 - output_4_ShortestPath.txt</ins>

1) Creates a random wrapped Dungeon with the following constraints:
   rows = 6, columns = 8, isWrapped = true, degree = 10, percent = 50.
2) User enters special command: 'shortestPath'
3) Player automatically follows the shortest path to the end point collecting treasures in the path.
4) Once all the locations are visited, player reaches the endpoint and finishes the game.

## Design Changes

In the original models, I have assumed various things which were increasing time or space
complexity.

1) I made Tunnel a subclass of Cave as most of the functionalities of Tunnel and Cave were similar
   and tunnel can be considered as a specific type of cave.
2) Added enum for direction. Instead of int to get directions, now I store a Map of direction with
   Location for getting adjacent connected locations.
3) Many private methods were added to break down complex code in each function. This makes each
   function do a specific task.
4) The dungeon is created in constructor itself.
5) The only public method is the DungeonModel. All other interfaces and classes are package private.
   User can only interact with DungeonModel.

## Assumptions

1) The treasures are distributed in the caves with the following probabilities:
    1) 1 treasure: 50%
    2) 2 treasures: 30%
    3) 3 treasures: 20%
2) The minimum rows and columns of the dungeon could be 6.
3) The game ends if player enter the end dungeon.
4) Player will collect all the treasures present in the cave.

## Limitations

1) The player cannot directly move to the cave connected to current cave. Player will stop at
   adjacent tunnel first.
2) Visiting all the locations function with 'visitAll' just gives a list of directions to visit all
   locations. It is not optimised to give the minimum steps to visit all locations.

## Citations

1. Modifying Kruskal's Algorithm to Build the Dungeon.
   https://northeastern.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=7b3154e5-7740-4130-954f-adc201647fc8&start=3.952948