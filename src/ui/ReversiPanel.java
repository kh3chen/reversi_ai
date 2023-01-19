package ui;

import game.ReversiBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class ReversiPanel extends JPanel {
    public static final int SQUARE_SIZE_PX = 60;
    public static final int BOARD_SIZE = 8;

    private static final Color BROWN_ONE = new Color(153, 136, 119);
    private static final Color BROWN_TWO = new Color(153, 119, 85);
    private static final Color PINK = new Color(255, 187, 187);
    private static final Color GREEN = new Color(204, 255, 85);

    ReversiBoard reversiBoard;

    ReversiPanel() {
        reversiBoard = new ReversiBoard(BOARD_SIZE);

        addMouseListener(new MouseAdapter() {

            int pressedCellX;
            int pressedCellY;
            int releasedCellX;
            int releasedCellY;

            public void mousePressed(MouseEvent e) {
                if (reversiBoard.getGameState() == ReversiBoard.GameState.GAME_OVER)
                    return;
                //CellX -> getY because rows and columns of matrix are opposite to coordinates
                pressedCellX = e.getY() / SQUARE_SIZE_PX;
                pressedCellY = e.getX() / SQUARE_SIZE_PX;
            }

            public void mouseReleased(MouseEvent e) {
                if (reversiBoard.getGameState() == ReversiBoard.GameState.GAME_OVER)
                    return;


                releasedCellX = e.getY() / SQUARE_SIZE_PX;
                releasedCellY = e.getX() / SQUARE_SIZE_PX;

                if (pressedCellX == releasedCellX && pressedCellY == releasedCellY) {
                    boolean validMove = reversiBoard.move(pressedCellX, pressedCellY);
                    if (validMove) {
                        repaint();
                    }
                }
            }
        });
    }

    public void start() {
        reversiBoard.start();
    }

    public void undo() {
        reversiBoard.undo();
        repaint();
    }

    public void reset() {
        reversiBoard.reset();
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBoard(g);
        drawBlocked(g);
        drawPlayerOnePieces(g);
        drawPlayerTwoPieces(g);
    }

    private void drawBoard(Graphics g) {
        setBackground(BROWN_ONE);

        g.setColor(BROWN_TWO);
        for (int y = 1; y < BOARD_SIZE; y = y + 2) {
            for (int x = 0; x < BOARD_SIZE; x = x + 2) {
                g.fillRect(x * SQUARE_SIZE_PX, y * SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX);
            }
        }
        for (int y = 0; y < BOARD_SIZE; y = y + 2) {
            for (int x = 1; x < BOARD_SIZE; x = x + 2) {
                g.fillRect(x * SQUARE_SIZE_PX, y * SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX);
            }
        }
    }

    private void drawBlocked(Graphics g) {
        g.setColor(Color.BLACK);
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (reversiBoard.field[y][x] == ReversiBoard.Square.BLOCKED)
                    g.fillRect(x * SQUARE_SIZE_PX, y * SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX);
            }
        }
    }

    private void drawPlayerOnePieces(Graphics g) {
        g.setColor(PINK);
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (reversiBoard.field[y][x] == ReversiBoard.Square.PLAYER_ONE)
                    g.fillRoundRect(x * SQUARE_SIZE_PX, y * SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX, 3, 3);
            }
        }
    }

    private void drawPlayerTwoPieces(Graphics g) {
        g.setColor(GREEN);
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (reversiBoard.field[y][x] == ReversiBoard.Square.PLAYER_TWO)
                    g.fillRoundRect(x * SQUARE_SIZE_PX, y * SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX, 3, 3);
            }
        }
    }

}
