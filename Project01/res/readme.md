#Primates
##Overview:
The following program represents a Sanctuary System of Primates and provides various functionalities required to keep track of the records. This project focuses the working of a specific Sanctuary: [Jungle Friends Primate Sanctuary](https://www.junglefriends.org/monkeys/) that provides a permanent home and high-quality sanctuary care for New World primates.

The Sanctuary consists of two different types of housing:

* **Isolation** is used to keep monkeys when they first arrive at the sanctuary and anytime they are receiving medical attention. Isolation consists of a series of cages each of which can house a single animal.
* **Enclosures** are much larger and can host a single troop (i.e., a group) of monkeys. Each troop consists of a single species that is found in the New World.

##List of Features of the Program
* The program simulates both the type of Housing for the Sanctuary and the details of the Monkey in each of them.
* An isolation can only contain 1 Monkey at a time.
* Enclosures can have multiple Monkeys(Troops) but all the monkeys should be of same species.
* If an enclosure is empty, it can be used to house different species
* The capacity of an enclosure is dependent upon the size of the enclosure and the size of the monkeys in the enclosure.
* Monkeys that are received by the sanctuary must go into isolation where they are assessed and given medical attention. At this time, each individual monkey is given a name, a species designation, a sex, a size, a weight, an approximate age, and a favorite food. The choices of favorite food include eggs, fruits, insects, leaves, nuts, seeds, and tree sap.
* Monkeys that have received medical attention may be moved to an available enclosure if there is room. The Jungle Friends Primate Sanctuary also has exchange agreements with several other primate sanctuaries that can take healthy monkeys and often uses these agreements to find the best suitable home for monkeys it receives but cannot house.
* Sanctuary can expand if the needs and funds allow.
* The program can report the species that are currently being housed in alphabetical order. The list includes where in the sanctuary each species is (both in enclosures and in isolation).
* The program can look up a particular species that is currently housed.
* The program can produce a sign for a given enclosure that lists each individual monkey that is currently housed there. For each individual monkey, the sign includes their name, sex, and favorite food.
* The program can produce an alphabetical list (by name) of all of the monkeys housed in the Sanctuary with their housing names.
* The program can produce a shopping list of the favorite foods of the inhabitants of the Sanctuary.

##How to Run
The res/ folder contains a Project1.jar file which can be run directly in IntelliJ or Eclipse Ide.
The jar file currently contains a driver run of the program without taking any input from the user.
It simulates all the features of the Sanctuary. The output of the same program is stored as DriverOutput.txt in res/ folder.

##Description of Examples
<ins>Run1 - DriverOutput.txt</ins>

1) Adds the Species and Monkeys in free isolations in sanctuary.
2) Moves the monkeys from isolation to enclosures and vice-versa. The results of invalid moving of Monkeys is also included.
3) Calls different methods and provides the output of each one of them.

##Design Changes
In the original models, I have assumed various things which were increasing time or space complexity.
1) Originally, I did not add Interfaces for Sanctuary or Monkey. To make the code more readable and seperate the declaration and definition of methods, I created Sanctuary, SancuaryImpl and Monkey, MonkeyImpl.
2) Originally, my idea was to print the required format string in Sanctuary class, but since based the design pattern I implemented was MVC, I moved all the print statements to SanctuaryDriver class and made classes under structured data package: MonkeyData, MonkeySign, ShoppingData, ShoppingList. The purpose of these classes is to have a structured data under Model and print these structured data under View part.

##Assumptions
1) The Sanctuary currently has only two types of housing: Enclosure or Isolation.
2) Monkey can fall under 3 categories: Small, Medium and Large. And similar sized monkey require same housing space and food supplies.
3) When a new species is discovered, the Sanctuary has to add it to species list before adding a new monkey of that species to the Sanctuary.
4) There is always a space somewhere in other sanctuary to house the monkey.

##Limitations
1) The Sanctuary can only host Monkeys. Addition of new type of Animal will require design changes.
2) Even if an enclosure is empty, a new monkey cannot be added to it.
3) It does not capture the differences between same category sized monkeys.