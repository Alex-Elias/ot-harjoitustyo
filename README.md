# Spaced Repetition Flashcard App

With the help of this app the user is able to create and study different flashcards in a spaced repetition. This app is able to save multiple users with multiple decks for each user.

This application was created for the Ohjelmistotekniikka class at the University of Helsinki during the autumn of 2020.

## Documentation
[Vaatimusmäärittely](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

[Arkkitehtuurikuvaus](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Käyttöohje](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Testausdocumentti](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/dokumentaatio/testaus.md)


## Releases

### Week 5

[Week 5 release](https://github.com/Alex-Elias/ot-harjoitustyo/releases/tag/viikko5)

### Week 6

[Week 6 release](https://github.com/Alex-Elias/ot-harjoitustyo/releases/tag/viikko6)

### Final deadline

[Final deadline](https://github.com/Alex-Elias/ot-harjoitustyo/releases/tag/v1.0)

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


### JavaDocs

JavaDocs can be generated with the command

`mvn javadoc:javadoc`

The generated JavaDocs can be found at *target/site/apidocs/index.html*
