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
    private int guessAmount;
    private int guessCounter;
    private int length;
    private Checker checker;
    private GameScreen screen;
    private WordPopper words;

    /**
     * Constructor of GameHost. Starts a new application.
     *
     * @param word_length length of words in Lingo game
     */
    public GameHost(int word_length) {
        this.length = word_length;
        words = new WordPopper(length);
        this.solution = new Word(words.getNextLingoWord());
        System.out.println(this.solution);
        guessAmount = 6;
        checker = new Checker(this.solution);
        screen = new GameScreen(length, guessAmount);
        screen.lingo.setSolution(solution);
        screen.lingo.addHint(checker.getNextHint(screen.lingo.getCurrentHints(), false));
        screen.submit.addActionListener(this);
        screen.nextGuess.addActionListener(this);
        screen.restart.addActionListener(this);
        //screen.lingotimer.addActionListener(this);
        screen.lingo.con.addActionListener(this);
    }

    /**
     * Starts a new game. Might be neat to refactor this into an "init" method
     * and call it in the constructor as well. (TODO)
     */
    public void restartGame() {
        this.solution = new Word(words.getNextLingoWord());
        guessCounter = 0;
        checker = new Checker(this.solution);
        screen.reset();
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
            if (screen.nextGuess.getText().length() == length) {
                screen.stopTimer();
                currentGuess = new Word(screen.nextGuess.getText());
                screen.nextGuess.setText("");
                guessCounter++;
                screen.lingo.addWord(currentGuess, checker.evaluateWord(currentGuess));
                if (checker.wordIsCorrect(currentGuess)) {
                    screen.win(25);
                }
            }
        } else if (e.getSource() == screen.restart) {
            screen.stopTimer();
            this.restartGame();
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
        GameHost lucille = new GameHost(5);
    }
}
