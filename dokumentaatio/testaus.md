# Testing document

The application is tested with the help of the JUnit test library

## Controller class tests

The Controller class tests test just the close method because the rest of the methods are simplistic and only pass the parameters to the appropriate method in a different class.

## Database tests

The database tests are more thorough and test a greater range of different use cases and exceptions.

## SRS tests

The SRS tests focus mainly on the getNext method.

## Test coverage

With the GUI class excluded the test coverage of the application is at 78 percent.

![test coverage](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/Images/testCoverage1.png)

The class that contributes most to the lack of coverage is the controller class where most of the methods are getter/setter methods. The other detriment to the testing coverage comes from the database classes where most of the catch statements are not tested.

## GUI testing

There is no automatic tests for the GUI class so all the testing was done manually.

The GUI is created in such a way that it is difficult to cause errors.

## Quality problems in the application

When trying to create an already existing user, the application does not allow it but no message is thrown to the user.

When deleting a user the decks and cards belonging to the user are not deleted. If another user is created with the same ID then the other user's decks and cards will appear to that user. The same is true for deleting a deck.
