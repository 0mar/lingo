package gui;

import core.GameHost;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class LingoTimer extends JPanel {

    final private int duration;
    private AtomicInteger timer;
    final private int unitsize=20;
    private TimeBlock[] blox;
    public JButton timeUp;
    boolean active;
    final Lock lock = new ReentrantLock();
    final Condition isStarted = lock.newCondition();
    final Condition isStopped = lock.newCondition();
    
    public class TimeBlock extends JPanel {
        boolean on;

        final Color oncolor = Color.BLACK;
        final Color offcolor = Color.GRAY;
        
        
        public TimeBlock() {
            this.setBackground(offcolor);
            this.setBorder(BorderFactory.createEtchedBorder());
        }
        
        public void setOn(boolean on) {
            this.on = on;
            if (on) {
                this.setBackground(oncolor);
            } else {
                this.setBackground(offcolor);
            }
        }
    }
    
    
    public void addActionListener(GameHost lucille) {
        timeUp.addActionListener(lucille);
    }
    
    
    public LingoTimer(int duration) {
        this.duration  = duration;
        timer = new AtomicInteger();
        initialize();
    }
    
    private void initialize() {
        this.setSize(duration*unitsize, unitsize);
        this.setLayout(new GridLayout(1,duration));
        blox = new TimeBlock[duration];
        timeUp = new JButton("Not visible");
        for (int i=0;i<duration;i++) {
            blox[i] = new TimeBlock();
            this.add(blox[i]);
        }
    }

    public void runTimer() {
        this.reset();
        for (;;) {
            try {
                Thread.sleep((int)(1000*duration/unitsize));
                blox[timer.get()].setOn(true);
                if (timer.incrementAndGet() >=duration) {
                    timeUp.doClick();
                    System.out.println("Fire.");
                } 
            } catch (InterruptedException ex) {
                break;
            }
        }
    }
    
    public void reset() {
        timer.set(0);
        for (TimeBlock block:blox) {
            block.setOn(false);
        }
        System.out.println("Tread resetted");
    }
    
    public void startTimer() throws InterruptedException {
        lock.lock();
        try {
            while (active) {
                isStopped.await();
            }
            this.reset();
            this.runTimer();
            isStarted.signal();
        } finally {
            lock.unlock();
        }
    }
    
    public void stopTimer() {
    }
    
    public static void main(String[] args) {
        JFrame test = new JFrame("test");
        test.setVisible(true);
        test.setSize(800,800);
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LingoTimer lingotimer = new LingoTimer(10);
        test.add(lingotimer);
        
    }
    
}