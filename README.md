# Ohjelmistotekniikka syksy 2020

This is the README.md document for the repository ot-harjoitustyo for the class **Ohjelmistotekniikka** at the *University of Helsinki*. 

## Documentation
[Vaatimusmäärittely](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/Alex-Elias/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)


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


### Checkstyle

Checkstyle can be run with the command


`mvn jxr:jxr checkstyle:checkstyle`


The possible fixes can be found at *target/site/checkstyle.html*
