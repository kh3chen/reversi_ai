package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReversiFrame extends JFrame implements ActionListener {

    public static final String ACTION_START = "Start";
    public static final String ACTION_UNDO = "Undo";
    public static final String ACTION_RESET = "Reset";
    public static final String ACTION_EXIT = "Exit";
    private static final String TITLE = "Reversi AI";
    private static final int MENU_HEIGHT = 55;

    private ReversiPanel reversiPanel;

    public ReversiFrame() {
        super(TITLE);
        reversiPanel = new ReversiPanel();
        this.getContentPane().add(reversiPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        JButton startButton = new JButton(ACTION_START);
        startButton.addActionListener(this);
        buttonPanel.add(startButton);

        JButton undoButton = new JButton(ACTION_UNDO);
        undoButton.addActionListener(this);
        buttonPanel.add(undoButton);

        JButton resetButton = new JButton(ACTION_RESET);
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);

        JButton exitButton = new JButton(ACTION_EXIT);
        exitButton.addActionListener(this);
        buttonPanel.add(exitButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        // Set window parameters
        setSize(ReversiPanel.SQUARE_SIZE_PX * ReversiPanel.BOARD_SIZE + 16, ReversiPanel.SQUARE_SIZE_PX * ReversiPanel.BOARD_SIZE + 8 + MENU_HEIGHT);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override

    public void actionPerformed(ActionEvent e) {
        switch (((JButton) e.getSource()).getActionCommand()) {
            case ACTION_START -> {
                reversiPanel.start();
            }
            case ACTION_UNDO -> {
                reversiPanel.undo();
            }
            case ACTION_RESET -> reversiPanel.reset();
            case ACTION_EXIT -> System.exit(0);
        }
    }
}