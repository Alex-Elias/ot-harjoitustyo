# Architecture


## Structure
The application's structure follows the format:

![packages](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/Images/Package.png)

The package main just contains a call to the package, gui, to start the graphical user interface. The gui package contains the graphical user interface created with JavaFX libraries. The controller package contains the interface between the graphical userinterface and the spaced repetition and database classes. The database package contains all the classes which read and write to the database and tThe srs package contains the class with the spaced repetition functionality.

## Graphical User Interface

The graphical user interface contains six different scenes

* User selection
* New user creation
* Deck Selection
* New deck creation
* New Card creation
* Deck review

The graphical user interface is created in the class GUI.gui.

The GUI is created in such a way that it is seperate from the application functionality it only talks to the controller class.

## Data storage

All the classes related to data storage are in the database package. The database class creates and deletes the tables in the database, the user class reads and writes to the Users table, the deck class reads and writes to the Decks table and the cards class reads and writes to the Cards, NewCards and Learning tables.

### Database

The database consists of five tables.

![tables](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/Images/tables.png)

The Cards table stores all the cards that have already been learnt and are waiting for review, the table stores the id of the card, the front word, the front sentence, the back word, the back sentence, the ID of the deck which the card belongs to, the date of review and the interval between reviews.

The Users table stores the id of the user and the name of the user.

the Decks table stores the id of the deck, the name of the deck and the user id which the deck belongs to.

The NewCards table stores all the cards which have not yet been seen and are waiting to be studied. The table store the ID of the card, the front word, the front sentence, the back sentence and the ID of the deck which the card belongs to.

The Learning table stores all the cards which are in the process of being learnt. The table stores the id of the card, the front word, the front sentence, the back word, the back sentence, the ID of the deck which the card belongs to and the interval between reviews.

## Main functionalities

The following sections show the main functions of the application.

### User login

![user selection](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/Images/userSelection.png)

To login the user clicks on the select user button, the GUI then calls the setUser method with the parameter of the username, the controller class calls the method getDecks which returns all the names of the decks belonging to the selected user. The controller class returns the list of names to the GUI which is then displayed on the deck scene.

### User creation

![user creation](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/Images/createUser.png)

To create a new user the user clicks on the add new user button which promts the GUI class to call the addUser method from the Controller class with the parameter of the new username. The Controller class then calls the addUser method from the User class with the parameter of the new username. The GUI class clears the text field in preparation for the next name.

### Deck creation

![deck creation](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/Images/addDeck.png)

To create a new deck the user clicks on the add deck button which prompts the GUI class to call the addDeck method from the Controller class with the parameter of the name of the new deck. The Controller class then calls the method addDeck from the Deck class with the parameters, the userID and the name of the new deck.

### Card creation

![card creation](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/Images/addCard.png)

To create a new card the user clicks on the add card button which prompts the GUI class to call the method setDeck with the parameters being the selected deck. The GUI class also calls the addNewCard method with the parameters front, sentence, back and back sentence. The Controller class then calls the method addNewCard from the Card class with the following parameters, front, sentence, back, back sentence, and deck ID.

### Card review

![card review](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/Images/studyDeck.png)

To review a card the user clicks on the study deck button. The GUI class then calls the setDeck method from the Controller class with the parameter being the name of the deck. The GUI class also calls the initSRS method fron the Controller class which initializes the SRS class. The GUI class also calls the nextCard method from the Controller class which then calls the nextCard method from the SRS class. The SRS class then returns the next card to the Controller class which then returns it the the GUI class. The GUI class changes the scene to the card scene and displays the contents of the card to the user.

## Architectual weaknesses

### Graphical user interface

The GUI is all contained within one class and most of the functionality is within the start method. An improvement to the structure of the graphical user interface would be to seperate all six different scenes into their own class or method. The variable names are lacking a consistant and concise scheme which leads to an overall lack of clarity.

### SRS class

The SRS class is in a weird middle ground where it does not fit entirely into its own package but is also seperate from the other classes. It would need to be reworked so that both the Controller class and the SRS class would fit into a new logic package.
