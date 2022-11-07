# Battle Game

## Overview

The following program represents a battle System specifically for a turn based role-playing game as
per the requirements of Jumptastic Games. Turn-based two-player battles generally start by pitting
two players against one another. The battle begins by determining which player will go first and
then proceeds as follows:

1) Player 1 attacks using the weapon that they have in-hand by taking a swing at player 2 who tries
   to avoid the attack. If player 1 hits player 2, then player 2 potentially takes damage.
2) Player 2 attacks using the weapon that they have in-hand by taking a swing at player 1 who tries
   to avoid the attack. If player 2 hits player 1, then player 1 potentially takes damage.
3) Turns continue back and forth until one of the players has taken a total damage that is greater
   than or equal to their health.
4) The player who did not take a total damage greater than or equal to their health is declared the
   victor. It is possible for a battle to end in a draw.

The program simulates these basic properties of an RPG.

## List of Features of the Program

* The Game consists of two players who fight in a battle.
* Players have four different abilities:
    * <b>Strength</b> affects how effective the player is at striking their opponent.
    * <b>Constitution</b> affects how much damage a player can take when they are hit in battle.
    * <b>Dexterity</b> affects how effective the player is at avoiding a strike from their opponent.
    * <b>Charisma</b> affects how their opponent views them.
* Each ability can range between 6 and 18.
* Players' abilities can be temporarily affected, either positively or negatively, by the gear that
  they use (details of how is left as a design decision for the student):
    * <b>Headgear</b> is worn on the player's head and affects the player's constitution. Since a player
      has one head, they can only wear one piece of headgear.
    * <b>Potions</b> are consumed by the player before entering the field of battle. They can temporarily
      affect any of the player's abilities. There is no limit to the number of these that the player
      can drink.
    * <b>Belts</b> come in three sizes -- small, medium, and large -- and are worn around the player's
      torso affecting up to two of the player's abilities. Players have the ability to wear 10 "
      units" of belts where small belts count as 1 unit, medium as 2 units, and large as 4 units.
    * <b>Footwear</b> is worn on the player's feet and affects the player's dexterity. Footwear always
      comes in pairs and a player can only wear one piece of footwear at a time.
* The player can wield a sword, an axe, or a flail as their weapon of choice:
    * Swords come in three varieties:
        * <b>Katanas</b> are lightweight curved swords that come in pairs. They can do a base of 4-6 points
          of damage when they hit. They are so light that a player can carry two of them (which
          attack separately).
        * <b>Broadswords</b> are a good medium weapon that can do 6-10 points of damage when they hit.
        * <b>Two-handed swords</b> are a heavy sword that can only be effectively wielded by players with
          strength greater than 14, but they can do 8-12 points of damage when they hit. If the player
          does not have the strength to wield a two-handed sword, the sword only does half damage.
    * <b>Axes</b> are great general weapons doing 6-10 points of damage when they hit.
    * <b>Flails</b> are also great general weapons but they can only be effectively wielded by players with
      a dexterity greater than 14. They do 8-12 points of damage when they hit. If the player does
      not have the dexterity to wield a flail, the flail only does half damage.
* The player with the higher charisma dazzles their opponent and gets in the first strike.
* Each battle can last 100 turns. If both players are still standing, it results in a draw.
* Players can play a rematch. 

## How to Run

1) The res/ folder contains a Project02.jar file which can be run directly in IntelliJ or Eclipse Ide.
2) The jar file currently contains a driver run of the program. No arguments are needed to run the jar file.

## How to Use
1) Running the program directly leads to creating avatars, assigning weapons and gears and starts a battle between the two players.
2) Once a battle is completed user gets an option to press (Y/N) to rematch between the characters and assigning them new weapons and gears.
3) Pressing Y will simulate a new battle and will again give the option to press (Y/N) for rematch.
Pressing N will end the program.
Pressing any other input will ask the user to enter a correct response.

## Description of Examples

<ins>Run1 - output.txt</ins>

1) Creates a battle environment and creates two avatars.
2) Players equip gears and weapons and prints the details of the players.
3) battle happens with turn by turn basis. Details of each turn is provided.
4) For the first battle, Player-2 wins.
5) For the second battle after rematch, Player-2 wins
6) For the third battle, Player-1 wins.

<ins>Run2 - output2.txt</ins>

1) Creates a battle environment and creates two avatars.
2) Players equip gears and weapons and prints the details of the players.
3) battle happens with turn by turn basis. Details of each turn is provided.
4) For the first battle, 100 turns are passed and both players are still standing. Thus draw.
5) For the second battle after rematch, Player-2 wins.
6) For the third battle, Player-1 wins.


## Design Changes

In the original models, I have assumed various things which were increasing time or space
complexity.

1) Originally, I did not add Interface for Player. To make the code more readable and
   separate the declaration and definition of methods, I created Player and PlayerImpl.
2) I put different gears to different variables initially like List of HeadGEar, Potion, Belt and Footwear. After considering the requirements, I changed code such that all the Gears are stored under single variable of gearList.
3) I originally did not have any way to end the battle if none of the players are taking any damage. I changed it such that maximum turns for a battle be 100

## Assumptions

1) This simulation assumes that all the gears equipped by the player will last till the duration of the battle.
2) This program assumes that 25% of all the available gears in the bag are poisoned. And these are selected randomly by the player. Thus player does not know if the gear is poisoned or not before selecting.
3) The rematch decision is a mutual decision between two players.

## Limitations

1) Since the program randomly selects gears for Player. These are not optimised to the player needs. Player may have better headgear with them, but due to random selection, player gets another gear.
2) The weapons are also randomly selected so player cannot select the weapon that best fits him.
3) Player cannot change any gears or weapons in between the battle.

## Citations
No citations were made for this project.