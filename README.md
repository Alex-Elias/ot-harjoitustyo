# Spaced Repetition Flashcard App

With the help of this app the user is able to create and study different flashcards in a spaced repetition. This app is able to save multiple users with multiple decks for each user.

## Documentation
[Vaatimusmäärittely](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)


## Releases

### Week 5

[Week 5 release](https://github.com/Alex-Elias/ot-harjoitustyo/releases/tag/viikko5)


## Commands

### Testing

The tests can be run with the command


`mvn test`


The test coverage reports can be generated with the command


`mvn jacoco:report`


The coverage reports can be found at *taget/site/jacoco/index.html*

### Running the Code

The Project can be run with the command


`mvn compile exec:java -Dexec.mainClass=main.Main`


The Jar file can be created with the command


`mvn package`


The generated jar file can be found in the *target* directory under the name **Flashcard-1.0-SNAPSHOT.jar**


### Checkstyle

Checkstyle can be run with the command


`mvn jxr:jxr checkstyle:checkstyle`


The possible fixes can be found at *target/site/checkstyle.html*
