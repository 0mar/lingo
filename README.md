# lingo

This is a Java implementation of the Dutch game show [Lingo]. Its GUI is built on the Swing framework.
The goal of the game is to guess the word with a limited number of attempts. Each guess will be evaluated to indicate which letters and which positions are correct.

![Image example of lingo](https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Lingo_voorbeeld.PNG/200px-Lingo_voorbeeld.PNG)

*A Lingo game example. Courtesy of WikiMedia*

An (English) example can be seen [here][Lingo-Movie].
The scope of the current implementation is limited: it only allows one team per game. However, most extensions should not be difficult.
As it stands, the game is made significantly easier than it's television counterpart; no time limit is implemented, and guesses are not checked in the dictionary.

In the repository, a validated example list of (Dutch) words is present. The word length can be selected in the main Java file.

### Dependencies

Lingo does not use any external libraries.

### Installation
Clone the repo with `git clone https://github.com/0mar/lingo.git`, or download and unpack the ZIP file.
The `build.xml` file is included, so I guess most IDEs will recognize the compiling instructions. If not, for any machine with JVM, building a JAR executable and running it should be as simple as
```bash
cd lingo
ant
java -jar dist/Lingo.jar
```
The first time Lingo is run, it creates a dictionary file for words with the desired length. Restart afterwards.

*Remark: when running the `jar` file, make sure the dictionary is located in the working directory.*

### Version
1.0

### Development
Any pull requests are welcome. Specifically with respect to the GUI. I am *not* a designer.
### Known issues
I tried implementing a timer using Threading, however, I did not succeed (yet). 

### Possible extensions
- A timer
- 'that part where they draw balls'
- Sound effects
- Adding an extra player

License
----

GNU

[Lingo]: <https://en.wikipedia.org/wiki/Lingo_%28Dutch_game_show%29>
[Lingo-movie]: <https://www.youtube.com/watch?v=sC0kie6dPjo>


