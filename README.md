# Declarative Jupiter

##### A Project to Demonstrate a Declarative Approach to Using Junit 5 Jupiter

---

## Prerequisites

For anyone looking to play along, the following need to be installed:

- A Java IDE (i.e. [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/))
- Maven
- Java 11 JDK  (i.e. [Adopt Open JDK 11](https://adoptopenjdk.net/))
- A local clone of this Project

---

## Project Details

This repository is a single maven module with a fizz buzz
application [package](./src/main/java/io/github/jkomoroski/declarativejupiter). The application is only used to provide an interface
to test. The test folder contains the demonstration of Junit 5. The test folder is separated into two packages. The
[first](./src/test/java/io/github/jkomoroski/declarativejupiter)
containing example test classes used to demonstrate the same tests written in different ways.
The [second](./src/test/java/io/github/jkomoroski/declarativejupiter/testutils) folder contains the various testing utility
implementations.

The project POM declares a few dependencies, and uses
[base-pom-minimal](https://github.com/basepom/basepom/tree/master/minimal) to enable and set sane default for various maven plugins.
These plugins generally fail the build in the event of a failure (check style, bad dependency declarations, find bugs errors,
unit/integration test failures, etc).

--- 

## Using the project from the commandline

To build the project and run all the tests run:

```
mvn clean install
```

To build without running the tests:

```
mvn clean install -D skipTests
```

To run tests from a single test class:

```
mvn test -D test=ExampleTest
```

To run a single test method:

```
mvn test -D test=ExampleTest#testStaticMethods
```

To Run the application after building it:

```
java -jar -Dkey=value ./target/declarative-jupiter.jar
```

To Run the application passing in your own number:

```
java -jar -Dkey=value ./target/declarative-jupiter.jar 1 2 3 4 5 6 7
```

---

## Other Resources

- Junit 5 User Guide:
    + [https://junit.org/junit5/docs/current/user-guide/#overview]()
- Using Junit5 Blog Series:
    + [https://nipafx.dev/junit-5-basics/]()
- Example Extensions from Junit Pioneer:
    + [https://github.com/junit-pioneer/junit-pioneer]()
- Junit 5 Extensions Overview:
    + [https://www.baeldung.com/junit-5-extensions]()
