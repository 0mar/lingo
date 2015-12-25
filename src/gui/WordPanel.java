package gui;

import core.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;

/**
 * GUI object where the words are placed. Consists of a collection of Panels.
 * @author omar
 */
public class WordPanel extends JPanel implements ActionListener {

    private int length, guesses;
    HintPanel[][] hintpanels;
    public final static int SIZE = 70;
    public JButton con;
    private int rowsfilled, columnsfilled;
    Timer drawTimer;
    Word guess,solWord;
    Hint[] hints,solHints;

    /**
     * Build a WordPanel. Only one panel is required per player per application.
     * @param length Number of letters per word
     * @param guesses Number of guesses per player
     */
    public WordPanel(int length, int guesses) {
        rowsfilled = 0;
        solHints = new Hint[length];
        hints = new Hint[length];
        Arrays.fill(hints, Hint.WRONG);
        Arrays.fill(solHints, Hint.WRONG);
        guess = Word.emptyWord(length);
        solWord = Word.emptyWord(length);
        this.guesses = guesses;
        this.length = length;
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
    }

    /**
     * Adds guess to the screen and colors the panels according to the hint
     * @param guess User validated guess
     * @param cHints Hint array corresponding to solution
     */
    public void addWord(Word guess, Hint[] cHints) {
        this.guess = guess;
        this.hints = cHints;
        columnsfilled = 0;
        drawTimer.start();
    }

    /**
     * Change the current word to display all known letters (called after a hint)
     * Up for refactoring: (TODO)
     */
    public void addNextLetters() {
        boolean cont = false;
        for (Hint h : hints) {
            cont |= h != Hint.CORRECT;
        }
        if (cont) {
            for (int i = 0; i < length; i++) {
                if (solHints[i] == Hint.CORRECT) {
                    hintpanels[rowsfilled][i].setPanel(solWord.toArray()[i]);
                }
            }
        }
    }
    
    public Hint[] getCurrentHints() {
        return solHints;
    }
    
    /**
     * Add a new letter to the guess (todo: Refactor)
     * @param bonushints
     * @param bonusword 
     */
    public void addHint(Hint[] bonushints, Word bonusword) {
        solHints = bonushints;
        solWord = bonusword;
        this.addNextLetters();
    }
     
    /**
     * Reset game screen to original state
     * Todo: Refactor to init method
     */
    public void reset() {
        rowsfilled = 0;
        columnsfilled=0;
        Arrays.fill(solHints, Hint.WRONG);
        Arrays.fill(hints, Hint.WRONG);
        solWord = Word.emptyWord(length);
        for (HintPanel[] hparray:hintpanels) {
            for (HintPanel hp:hparray) {
                hp.clear();
            }
        }
    }

    /**
     * Listener method (Todo: Change guess length bug)
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        hintpanels[rowsfilled][columnsfilled].setPanel(guess.toArray()[columnsfilled], hints[columnsfilled]);
        if (hints[columnsfilled]==Hint.CORRECT) {
            solHints[columnsfilled] = solHints[columnsfilled].upgradeHint(Hint.CORRECT);
            solWord = Word.changeLetter(solWord, columnsfilled, guess.toArray()[columnsfilled]);
        }
        columnsfilled++;
        if (columnsfilled == length) {
            drawTimer.stop();
            rowsfilled++;
            addNextLetters();
            con.doClick();
        }
    }
}
