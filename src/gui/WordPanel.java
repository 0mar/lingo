package gui;

import core.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;

public class WordPanel extends JPanel implements ActionListener {

    private int length, guesses;
    HintPanel[][] hintpanels;
    public final static int SIZE = 70;
    public JButton con;
    private int rowsfilled, columnsfilled;
    Timer drawTimer;
    Word guess,solWord;
    Hint[] hints,solHints;

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

    public void addWord(Word guess, Hint[] cHints) {
        this.guess = guess;
        this.hints = cHints;
        columnsfilled = 0;
        drawTimer.start();
    }

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
    
    public void addHint(Hint[] bonushints, Word bonusword) {
        solHints = bonushints;
        solWord = bonusword;
        this.addNextLetters();
    }
     
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
