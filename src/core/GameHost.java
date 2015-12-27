package core;

import gui.*;
import input.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * Main lingo file. Has main method. Initializes GUI, starts game and listens
 * for input
 *
 * @author omar
 */
public class GameHost implements ActionListener {

    private Word solution;
    private Word currentGuess;
    private final int guessAmount;
    private int guessCounter;
    private final int length;
    private Checker checker;
    private final GameScreen screen;
    private final WordPopper words;

    /**
     * Constructor of GameHost. Starts a new application.
     *
     * @param wordLength length of words in Lingo game
     * @param numberOfGuesses number of guesses a player gets
     */
    public GameHost(int wordLength, int numberOfGuesses) {
        this.length = wordLength;
        guessAmount = numberOfGuesses;
        words = new WordPopper(length);
        screen = new GameScreen(length, guessAmount);

        screen.submit.addActionListener(this);
        screen.nextGuess.addActionListener(this);
        screen.restart.addActionListener(this);
        //screen.lingotimer.addActionListener(this);
        screen.lingo.con.addActionListener(this);

        this.initNewGame();
    }

    /**
     * Starts a new game. Resets current progress.
     */
    public void initNewGame() {
        guessCounter = 0;
        this.solution = new Word(words.getNextLingoWord());
        checker = new Checker(this.solution);
        screen.initNewGame();
        screen.lingo.setSolution(solution);
        screen.lingo.addHint(checker.getNextHint(screen.lingo.getCurrentHints(), false));
    }

    /**
     * Handles the events from the game screen. Started with a timer in this
     * method, did not manage to pull of the threading.
     *
     * @param e ActionEvent provided by Swing interface.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == screen.submit || e.getSource() == screen.nextGuess) {
            String word_attempt = screen.nextGuess.getText();
            if (words.validate_word(word_attempt)) {
                screen.stopTimer();
                currentGuess = new Word(word_attempt);
                screen.nextGuess.setText("");
                guessCounter++;
                screen.lingo.addWord(currentGuess, checker.evaluateWord(currentGuess));
                if (checker.wordIsCorrect(currentGuess)) {
                    screen.win(25);
                }
            }
        } else if (e.getSource() == screen.restart) {
            screen.stopTimer();
            this.initNewGame();
        } else if (e.getSource() == screen.lingotimer.timeUp) {
            screen.stopTimer();
            int bonusletter = checker.getNextHint(screen.lingo.getCurrentHints(), true);
            screen.lingo.addHint(bonusletter);
        } else if (e.getSource() == screen.lingo.con) {
            if (guessCounter == guessAmount - 1) {
                screen.lose();
                screen.lingo.addWord(solution, checker.evaluateWord(solution));
            }
        }
    }

    public static void main(String[] args) {
        int wordLength = 6;
        int numberOfGuesses = 6;
        GameHost lucille = new GameHost(wordLength, numberOfGuesses);
    }
}
