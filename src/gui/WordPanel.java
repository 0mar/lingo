package gui;

import core.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;

/**
 * GUI object where the words are placed. Consists of a collection of Panels.
 *
 * @author omar
 */
public class WordPanel extends JPanel implements ActionListener {

    private int length, guesses;
    HintPanel[][] hintpanels;
    public final static int SIZE = 70;
    public JButton con;
    private int rowsfilled, columnsfilled;
    Timer drawTimer;
    Word guess, solution;
    Hint[] hintsForGuess, accumulatedHints;

    /**
     * Build a WordPanel. Only one panel is required per player per application.
     *
     * @param length Number of letters per word
     * @param guesses Number of guesses per player
     */
    public WordPanel(int length, int guesses) {
        this.guesses = guesses;
        this.length = length;
        hintsForGuess = new Hint[length];
        accumulatedHints = new Hint[length];
        hintpanels = new HintPanel[guesses][length];
        drawTimer = new Timer(300, this);
        con = new JButton("Continue (not visible)");
        this.setLayout(new GridLayout(guesses, length));
        for (int i = 0; i < guesses; i++) {
            for (int j = 0; j < length; j++) {
                hintpanels[i][j] = new HintPanel();
                this.add(hintpanels[i][j]);
            }
        }
        this.initNewGame();
    }

    /**
     * Reset word panel to original state.
     */
    public void initNewGame() {
        rowsfilled = 0;
        columnsfilled = 0;
        Arrays.fill(hintsForGuess, Hint.WRONG);
        Arrays.fill(accumulatedHints, Hint.WRONG);
        solution = Word.emptyWord(length);
        for (HintPanel[] hparray : hintpanels) {
            for (HintPanel hp : hparray) {
                hp.clear();
            }
        }
    }

    public void setSolution(Word solution) {
        this.solution = solution;
    }

    /**
     * Adds guess to the screen and colors the panels according to the hint
     *
     * @param guess User validated guess
     * @param cHints Hint array corresponding to solution
     */
    public void addWord(Word guess, Hint[] cHints) {
        this.guess = guess;
        this.hintsForGuess = cHints;
        columnsfilled = 0;
        drawTimer.start();
    }

    /**
     * Display all the known letters in a word
     */
    public void addNextLetters() {
        boolean cont = false;
        for (Hint h : hintsForGuess) {
            cont |= h != Hint.CORRECT;
        }
        if (cont) {
            for (int i = 0; i < length; i++) {
                if (accumulatedHints[i] == Hint.CORRECT) {
                    hintpanels[rowsfilled][i].setPanel(solution.toArray()[i]);
                }
            }
        }
    }

    public Hint[] getCurrentHints() {
        return accumulatedHints;
    }

    /**
     * Add a new letter to the known letters. This letter has not been guessed
     * yet
     *
     * @param char_location location of the new hint in the word
     */
    public void addHint(int char_location) {
        accumulatedHints[char_location] = Hint.CORRECT;
        this.addNextLetters();
    }

    /**
     * Listener method. Draws guesses and reveals accumulated hints.
     *
     * @param e Action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        hintpanels[rowsfilled][columnsfilled].setPanel(guess.toArray()[columnsfilled], hintsForGuess[columnsfilled]);
        if (hintsForGuess[columnsfilled] == Hint.CORRECT) {
            accumulatedHints[columnsfilled] = Hint.CORRECT;
        }
        columnsfilled++;
        if (columnsfilled == length) {
            drawTimer.stop();
            if (rowsfilled < guesses - 1) {
                rowsfilled++;
                addNextLetters();
                con.doClick();
            }

        }
    }
}
