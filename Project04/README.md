# Dungeon Model

## Overview

The following program represents a dungeon model that contains a network of tunnels and caves that
are interconnected so that player can explore the entire world by traveling from cave to cave
through the tunnels that connect them.

A location in the dungeon where a player can explore, can be connected to at most four (4) other
locations: one to the north, one to the east, one to the south, and one to the west. A location can
further be classified as tunnel (which has exactly 2 entrances) or a cave (which has 1, 3 or 4
entrances).

Location can also contain Monsters like [Otyughs](https://forgottenrealms.fandom.com/wiki/Otyugh)
that are extremely smelly creatures that lead solitary lives and would eat player if player enters
its cave. Player can collect arrows and shoot at a direction and distance in hope to kill an Otyugh.

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
* Each Location can have Crooked Arrows based on the same percentage.
* Player to enter the dungeon at the start.
* There is always at least one Otyugh in the dungeon located at the specially designated end cave.
* There is never an Otyugh at the start.
* Otyugh can be detected by smell.
* Player entering cave with living Otyugh will be killed.
* Player starts with 3 crooked arrows.
* It takes 2 hits to kill an Otyugh. Players has a 50% chance of escaping if the Otyugh if they
  enter a cave of an injured Otyugh that has been hit by a single crooked arrow.
* Player can move from their current location.
* Player can pick up treasure or arrow that is located in their same location.
* A player that has arrows, can attempt to slay an Otyugh by specifying a direction and distance in
  which to shoot their crooked arrow.
* The player wins by reaching the end location.
* The player loses by being eaten by an Otyugh.

## How to Run

1) The res/ folder contains a Project04.jar file which can be run directly in IntelliJ or Eclipse
   Ide. The following command line arguments are required:
    * rows: Number of rows in the dungeon. Should be at least 6.
    * columns: Number of Columns in the dungeon. Should be at least 6.
    * isWrapped: Whether the dungeon is wrapped around its end
    * degree: The degree of interconnectivity for the dungeon.
    * percentage: The percentage of caves having treasure in it. Should be between 0 and 100.
    * numOtyughs: Number of Otyughs in the dungeon. Should be at least 1.
2) The arguments should be in this specific order: rows columns isWrapped degree percentage
   numOtyughs
3) Use the following command: java -jar Project03.jar rows columns isWrapped degree percentage
   numOtyughs.
4) example-> java -jar Project03.jar 6 8 false 10 50 10

## How to Use

1) The dungeon will be randomly generated based on the command line inputs.
2) The description of the current location with Possible directions and the items in the location
   will be shown.
3) Player has Option to select one of the commands: (Move) M, (Pick) P, (Shoot) S.
4) Selecting one of the options will lead to their corresponding prompts.
    1) Move will require a direction: (North) N, (East) E, (South) S, (West) W
    2) Pick will require an item: (Diamond) D, (Ruby) R, (Sapphire) S, (Arrow) A
    3) Shoot will require a direction and a distance(between 1 and 5)';
5) Follow the prompts to explore the dungeon and reach the end location without dying to win.

## Description of Examples

<ins>Run1 - output_1_Invalid_Inputs.txt</ins>

1) Creates a random NonWrapped Dungeon with the following constraints:
   rows = 6, columns = 8, isWrapped = false, degree = 10, percent = 50, numOtyughs = 10.
2) Player enters all the different invalid inputs possible from the keyboard and respective error
   messages are displayed in the console.

<ins>Run2 - output_2_Killed.txt</ins>

1) Creates a random NonWrapped Dungeon with the following constraints:
   rows = 6, columns = 8, isWrapped = false, degree = 10, percent = 50, numOtyughs = 10.
2) Player moves through the dungeon without shooting to the nearby Otyugh and get killed.

<ins>Run3 - output_3_Saved_Then_Killed.txt</ins>

1) Creates a random NonWrapped Dungeon with the following constraints:
   rows = 6, columns = 8, isWrapped = false, degree = 10, percent = 50, numOtyughs = 10.
2) Player moves through the dungeon and shoots to hit an Otyugh.
3) Player enters the cave of the Otyugh. The Oyugh is weak to attack the player.
4) Player leaves and enters the cave again. Otyugh attacks this time and game gets over.

<ins>Run4 - output_4_Win.txt</ins>

1) Creates a random NonWrapped Dungeon with the following constraints:
   rows = 6, columns = 8, isWrapped = false, degree = 10, percent = 50, numOtyughs = 10.
2) Player explores the dungeon, by moving to different locations, picking up items, shooting arrows
   to sometimes hit and sometimes miss the Otyugh.
3) Player reaches the end location by slaying the present Otyugh and wins the Game.

## Design Changes

The following are the design changes from the original documents

1) Main change was to add Command interface so that each command like Move and Pick is a subclass of
   Command.
2) Added enum for HitStatus, GameStatus and SmellLevel to communicate with Controller in a better
   way.
3) Added model as an ar argument of DungeonControllerImpl constructor so that start() can be called
   without any Model argument.

## Assumptions

1) The treasures are distributed in the caves with the following probabilities:
    1) 1 treasure: 50%
    2) 2 treasures: 30%
    3) 3 treasures: 20%
2) The minimum rows and columns of the dungeon could be 6.
3) Player can find upto 3 arrows in a location.

## Limitations

1) The player cannot directly move to the cave connected to current cave. Player will stop at
   adjacent tunnel first.
2) Player can pick only one item at a time. For user to pick all items, separate commands need to be
   entered.

## Citations

1. Modifying Kruskal's Algorithm to Build the Dungeon.
   https://northeastern.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=7b3154e5-7740-4130-954f-adc201647fc8&start=3.952948