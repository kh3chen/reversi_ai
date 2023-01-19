package game;

import java.util.ArrayList;

public class ReversiBoard {

    public enum GameState {SETTING_UP, PLAYER_ONE, PLAYER_TWO, GAME_OVER}

    private GameState gameState = GameState.SETTING_UP;

    public enum Square {BLOCKED, PLAYER_ONE, PLAYER_TWO}

    public Square[][] field;
    private int dim;
    private ArrayList<Integer> moveList = new ArrayList<Integer>();

    public ReversiBoard(int dim) {
        this.dim = dim;
        field = new Square[dim][dim];
        reset();
    }

    public void start() {
        gameState = GameState.PLAYER_ONE;
        System.out.println(gameState);
    }

    public void undo() {
        int index = moveList.size() - 1;
        field[moveList.get(index - 1)][moveList.get(index)] = null;
        moveList.remove(index);
        moveList.remove(index - 1);
    }

    public boolean move(int i, int j) {
        System.out.println(gameState);

        switch (gameState) {
            case SETTING_UP -> {
                if (field[i][j] == null) {
                    field[i][j] = Square.BLOCKED;
                    return true;
                } else if (field[i][j] == Square.BLOCKED) {
                    field[i][j] = null;
                    return true;
                }
            }
            case PLAYER_ONE -> {
                if (field[i][j] == null) {
                    field[i][j] = Square.PLAYER_ONE;
                    reverse(Square.PLAYER_ONE, i, j);
                    gameState = GameState.PLAYER_TWO;
                    return true;
                }
            }
            case PLAYER_TWO -> {
                if (field[i][j] == null) {
                    field[i][j] = Square.PLAYER_TWO;
                    reverse(Square.PLAYER_ONE, i, j);
                    gameState = GameState.PLAYER_ONE;
                    return true;
                }
            }
        }
        return false;
    }

    private void reverse(Square player, int i, int j) {
        // TODO

    }

    public GameState getGameState() {
        return gameState;
    }

    public void reset() {
        moveList.clear();
        gameState = GameState.SETTING_UP;

        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++) field[i][j] = null;

        field[dim / 2 - 1][dim / 2 - 1] = Square.PLAYER_ONE;
        field[dim / 2][dim / 2] = Square.PLAYER_ONE;
        field[dim / 2 - 1][dim / 2] = Square.PLAYER_TWO;
        field[dim / 2][dim / 2 - 1] = Square.PLAYER_TWO;
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                str += field[i][j];
            }
            str += "\n";
        }
        return str;
    }


}