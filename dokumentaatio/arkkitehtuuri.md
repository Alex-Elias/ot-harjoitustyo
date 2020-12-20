# Architecture


## Structure
The application's structure follows the following structure:

![packages](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/Images/Package.png)

The package main just contains a call to the package, gui, to start the graphical user interface. The gui package contains the graphica user interface created with JavaFX libraries. The controller package contains the interface between the graphical userinterface and the spaced repetition and database classes. The database package contains all the classes which read and write to the database. The srs package contains the class with the spaced repetition functionality.

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

The Cards table stores all the cards that have already been learnt and are waiting for review, the table stores the id of the card, the front word, the front sentence, the back word, the back sentence, the deck ID which the card belongs to the date of review and the interval between reviews.

The Users table stores the id of the user and the name of the user.

the Decks table stores the id of the deck, the name of the deck and the user id which the deck belongs to.

The NewCards table stores all the cards which have not yet been seen and are waiting to be studied. The table store the id of the card, the front word, the front sentence, the back sentence and the ID of the deck which the card belongs to.

The Learning table stores all the cards which are in the process of being learnt. The table stores the id of the card, the front word, the front sentence, the back word, the back sentence, the ID of the deck which the card belongs to and the interval between reviews.

## Main functionalities

The following sections show the main functions of the application.

### User login

![user selection](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/Images/userSelection.png)

To login the user clicks on the select user button, the GUI then calls the setUser method with the parameter of the username, the controller class calls the method getDecks which returns all the names of the decks belonging to the selected user. The controller class returns the list of names to the GUI which is then displayed on the deck scene.

### User creation

![user creation](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/Images/createUser.png)

To create a new user the user clicks on the add new user button which promts the gui class to call the addUser method from the controller class with the parameter of the new username. The controller class then calls the addUser method from the user class with the parameter of the new username. The gui class clears the text field in preparation for the next name.

### Deck creation

![deck creation](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/Images/addDeck.png)

To create a new deck the user clicks on the add deck button which prompts the gui class to call the addDeck method from the controller class with the parameter of the name of the new deck. The controller class then calls the function addDeck from the deck class with the parameters the userID and the name of the new deck.

### Card creation

![card creation](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/Images/addCard.png)

To create a new card the user clicks on the add card button which prompts the gui class to call the method setDeck with the parameter of the selected deck and the addNewCard method with the parameters front, sentence, back, back sentence. The controller class then call the method addNewCard from the card class with the following parameters, front, sentence, back, back sentence, and deck ID.

### Card review

![card review](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/Images/studyDeck.png)

To review a card the user click on the study deck button which then the gui class calls the setDeck method from the controller class with the parameter being the name of the deck. The gui class also calls the initSRS method fron the controller class which initializes the srs class. The gui class also calls the nextCard method from the controller class which then calls the nextCard method from the srs class. The srs class then returns the next card to the controller class which then returns it the the gui class. The gui class changes the scene to the card scene and displays the contents of the card to the user.
