package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * GUI representing the playing field.
 * Contains one wordpanel and one score panel. And maybe a timer panel
 * @author omar
 */
public class GameScreen extends JPanel {

    private JFrame game;
    public WordPanel lingo;
    public JTextField nextGuess;
    public JButton submit, restart;
    private JPanel confpanel, enterpanel, pointpanel;
    private JTextArea info, pointArea;
    public LingoTimer lingotimer;
    //public Thread timerThread;
    int length = 0;
    int guessnumber = 0;
    int points;
    int duration = 20;

    public GameScreen(int length, int guessnumber) {
        super();
        this.length = length;
        this.guessnumber = guessnumber;
        lingotimer = new LingoTimer(duration);
        initializeFrame();
    }

    /**
     * Initializes the frame and underlying panels.
     */
    private void initializeFrame() {
        game = new JFrame("Lingo");
        game.setVisible(true);
        game.setSize(WordPanel.SIZE * 2 * length, WordPanel.SIZE * guessnumber);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new GridLayout(1, 2));
        enterpanel = new JPanel();
        pointpanel = new JPanel();
        nextGuess = new JTextField();
        nextGuess.setColumns(length * 2);
        submit = new JButton("Submit");
        restart = new JButton("Next Word");
        info = new JTextArea(String.format("You've got %d attempts. Correct word has %d letters\n\n", guessnumber, length));
        pointArea = new JTextArea("Score: " + points);
        info.setFont(new Font("Arial", Font.PLAIN, 24));
        info.setLineWrap(true);
        info.setColumns(20);
        info.setRows(4);
        
        
        enterpanel.add(nextGuess);
        enterpanel.add(submit);
        pointpanel.add(restart);
        pointpanel.add(pointArea);
        //enterpanel.add(info);
        confpanel = new JPanel();
        confpanel.setLayout(new GridLayout(3, 1));
        confpanel.add(lingotimer);
        confpanel.add(enterpanel);
        confpanel.add(pointpanel);
        lingo = new WordPanel(length, guessnumber);

        this.add(confpanel);
        this.add(lingo);
        game.add(this);
        game.getContentPane().validate();
        game.getContentPane().repaint();
    }

    /**
     * Process a win
     * @param winnings Points for winning
     */
    public void win(int winnings) {
        points += winnings;
        this.setBackground(Color.GREEN);
        submit.setEnabled(false);
        nextGuess.setEditable(false);
        info.setText("Keurig!");
        pointArea.setText("Punten: " + points);
    }

    /**
     * Process a loss
     */
    public void lose() {
        submit.setEnabled(false);
        nextGuess.setEditable(false);
        nextGuess.setEnabled(false);
    }

    /**
     * Not implemented yet
     */
    public void startTimer() {
    }

    /**
     * Not implemented yet
     */
    public void stopTimer() {
    }

    /**
     * Reset the game screen.
     */
    public void reset() {
        submit.setEnabled(true);
        nextGuess.setEditable(true);
        nextGuess.setEnabled(true);
        lingo.reset();
    }

}
