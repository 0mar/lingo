package gui;

import core.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * GUI object for displaying correctness of letters.
 * Included in game screen
 * @author omar
 */
class HintPanel extends JPanel {

    private char letter;
    private final JLabel label;
    private Hint state;
    private final static Color LBLUE = new Color(81, 92, 154);
    private final static Color LRED = new Color(208, 56, 19);
    private final static Color LYELLOW = new Color(234, 170, 0);


    /**
     * Constructor. The panel assumes three colors, depending on the Hint state.
     * Default state is WRONG, since it has the same color as unfilled letters.
     */
    public HintPanel() {
        label = new JLabel();
        this.setLayout(new GridBagLayout());
        this.add(label);
        label.setFont(new Font("Arial", Font.PLAIN, 40));
        label.setForeground(Color.WHITE);
        Border border = BorderFactory.createEtchedBorder(Color.WHITE, Color.BLACK);
        this.setBorder(border);
        this.state = Hint.WRONG;
        this.setBackground(LBLUE);
    }

    public void setState(Hint state) {
        if (this.state != state) {
            this.state = state;
            this.repaint();
        }
    }

    /**
     * Draw the panel in it's new state.
     * @param g Graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        double alpha = 0.1;
        switch (state) {
            case WRONG:
                g.setColor(LBLUE);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                break;

            case CLOSE:
                g.setColor(LYELLOW);
                g.fillOval((int)(alpha/2*this.getWidth()), (int)(alpha/2*this.getHeight()),
                        (int)((1-alpha)*this.getWidth()), (int)((1-alpha)*this.getHeight()));
                break;

            case CORRECT:
                g.setColor(LRED);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                break;
        }
    }

    /**
     * Set letter on panel without changing state
     * @param letter 
     */
    public void setPanel(char letter) {
        this.letter = letter;
        this.label.setText(String.valueOf(this.letter));
    }

    /**
     * Set letter on panel and change state
     * @param letter
     * @param state 
     */
    public void setPanel(char letter, Hint state) {
        this.setState(state);
        this.setPanel(letter);
    }

    /**
     * Clear panel and reset state.
     */
    public void clear() {
        this.setState(Hint.WRONG);
        this.label.setText("");
    }
}
