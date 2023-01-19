package game;

import game.Square.State;

import java.util.ArrayList;

public class ReversiBoard {

    public enum GameState {SETTING_UP, PLAYER_ONE, PLAYER_TWO, GAME_OVER}

    private GameState gameState = GameState.SETTING_UP;

    public Square[][] board;
    private int dim;
    private ArrayList<Square> validMoves = new ArrayList<>();
    private ArrayList<Integer> moveList = new ArrayList<Integer>();

    public ReversiBoard(int dim) {
        this.dim = dim;
        board = new Square[dim][dim];
        reset();
    }

    public void start() {
        gameState = GameState.PLAYER_ONE;
        findValidMoves();
        System.out.println(gameState);
    }

    public void undo() {
        int index = moveList.size() - 1;
        board[moveList.get(index - 1)][moveList.get(index)] = null;
        moveList.remove(index);
        moveList.remove(index - 1);
    }

    public boolean move(int i, int j) {
        System.out.println(gameState);
        Square movedSquare = board[i][j];

        switch (gameState) {
            case SETTING_UP -> {
                if (movedSquare.getState() == State.EMPTY) {
                    movedSquare.setState(State.BLOCKED);
                    return true;
                } else if (movedSquare.getState() == State.BLOCKED) {
                    movedSquare.setState(State.EMPTY);
                    return true;
                }
            }
            case PLAYER_ONE -> {
                if (movedSquare.getState() == State.EMPTY) {

                    ArrayList<Square> reversedSquares = reverse(State.PLAYER_ONE, State.PLAYER_TWO, i, j);
                    if (reversedSquares == null || reversedSquares.isEmpty()) {
                        return false;
                    }

                    movedSquare.setState(State.PLAYER_ONE);
                    for (Square reversedSquare : reversedSquares) {
                        reversedSquare.setState(State.PLAYER_ONE);
                    }
                    gameState = GameState.PLAYER_TWO;
                    findValidMoves();
                    if (validMoves.isEmpty()) {
                        gameState = GameState.PLAYER_ONE;
                        findValidMoves();
                        if (validMoves.isEmpty()) {
                            gameState = GameState.GAME_OVER;
                        }
                    }
                    return true;
                }
            }
            case PLAYER_TWO -> {
                if (movedSquare.getState() == State.EMPTY) {

                    ArrayList<Square> reversedSquares = reverse(State.PLAYER_TWO, State.PLAYER_ONE, i, j);
                    if (reversedSquares == null || reversedSquares.isEmpty()) {
                        return false;
                    }
                    movedSquare.setState(State.PLAYER_TWO);
                    for (Square reversedSquare : reversedSquares) {
                        reversedSquare.setState(State.PLAYER_TWO);
                    }
                    gameState = GameState.PLAYER_ONE;
                    findValidMoves();
                    if (validMoves.isEmpty()) {
                        gameState = GameState.PLAYER_TWO;
                        findValidMoves();
                        if (validMoves.isEmpty()) {
                            gameState = GameState.GAME_OVER;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private ArrayList<Square> reverse(State moveState, State reverseState, int moveI, int moveJ) {

        ArrayList<Square> reversedSquares = new ArrayList<>();

        reverseUp(moveI, moveJ, moveState, reverseState, reversedSquares);
        reverseDown(moveI, moveJ, moveState, reverseState, reversedSquares);
        reverseLeft(moveI, moveJ, moveState, reverseState, reversedSquares);
        reverseRight(moveI, moveJ, moveState, reverseState, reversedSquares);
        reverseUpLeft(moveI, moveJ, moveState, reverseState, reversedSquares);
        reverseUpRight(moveI, moveJ, moveState, reverseState, reversedSquares);
        reverseDownLeft(moveI, moveJ, moveState, reverseState, reversedSquares);
        reverseDownRight(moveI, moveJ, moveState, reverseState, reversedSquares);

        return reversedSquares;
    }

    private void reverseUp(int moveI, int moveJ, State moveState, State reverseState, ArrayList<Square> reversedSquares) {
        ArrayList<Square> temp = new ArrayList<>();
        int i = moveI - 1;
        while (i >= 0) {
            Square square = board[i][moveJ];
            switch (searchReverse(square, moveState, reverseState)) {
                case MIDDLE -> temp.add(square);
                case END -> {
                    reversedSquares.addAll(temp);
                    return;
                }
                case INVALID -> {
                    return;
                }
            }
            i--;
        }
    }

    private void reverseDown(int moveI, int moveJ, State moveState, State reverseState, ArrayList<Square> reversedSquares) {
        ArrayList<Square> temp = new ArrayList<>();
        int i = moveI + 1;
        while (i < dim) {
            Square square = board[i][moveJ];
            switch (searchReverse(square, moveState, reverseState)) {
                case MIDDLE -> temp.add(square);
                case END -> {
                    reversedSquares.addAll(temp);
                    return;
                }
                case INVALID -> {
                    return;
                }
            }
            i++;
        }
    }

    private void reverseLeft(int moveI, int moveJ, State moveState, State reverseState, ArrayList<Square> reversedSquares) {
        ArrayList<Square> temp = new ArrayList<>();
        int j = moveJ - 1;
        while (j >= 0) {
            Square square = board[moveI][j];
            switch (searchReverse(square, moveState, reverseState)) {
                case MIDDLE -> temp.add(square);
                case END -> {
                    reversedSquares.addAll(temp);
                    return;
                }
                case INVALID -> {
                    return;
                }
            }
            j--;
        }
    }

    private void reverseRight(int moveI, int moveJ, State moveState, State reverseState, ArrayList<Square> reversedSquares) {
        ArrayList<Square> temp = new ArrayList<>();
        int j = moveJ + 1;
        while (j < dim) {
            Square square = board[moveI][j];
            switch (searchReverse(square, moveState, reverseState)) {
                case MIDDLE -> temp.add(square);
                case END -> {
                    reversedSquares.addAll(temp);
                    return;
                }
                case INVALID -> {
                    return;
                }
            }
            j++;
        }
    }

    private void reverseUpLeft(int moveI, int moveJ, State moveState, State reverseState, ArrayList<Square> reversedSquares) {
        ArrayList<Square> temp = new ArrayList<>();
        int i = moveI - 1;
        int j = moveJ - 1;
        while (i >= 0 && j >= 0) {
            Square square = board[i][j];
            switch (searchReverse(square, moveState, reverseState)) {
                case MIDDLE -> temp.add(square);
                case END -> {
                    reversedSquares.addAll(temp);
                    return;
                }
                case INVALID -> {
                    return;
                }
            }
            i--;
            j--;
        }
    }

    private void reverseUpRight(int moveI, int moveJ, State moveState, State reverseState, ArrayList<Square> reversedSquares) {
        ArrayList<Square> temp = new ArrayList<>();
        int i = moveI - 1;
        int j = moveJ + 1;
        while (i >= 0 && j < dim) {
            Square square = board[i][j];
            switch (searchReverse(square, moveState, reverseState)) {
                case MIDDLE -> temp.add(square);
                case END -> {
                    reversedSquares.addAll(temp);
                    return;
                }
                case INVALID -> {
                    return;
                }
            }
            i--;
            j++;
        }
    }

    private void reverseDownLeft(int moveI, int moveJ, State moveState, State reverseState, ArrayList<Square> reversedSquares) {
        ArrayList<Square> temp = new ArrayList<>();
        int i = moveI + 1;
        int j = moveJ - 1;
        while (i < dim && j >= 0) {
            Square square = board[i][j];
            switch (searchReverse(square, moveState, reverseState)) {
                case MIDDLE -> temp.add(square);
                case END -> {
                    reversedSquares.addAll(temp);
                    return;
                }
                case INVALID -> {
                    return;
                }
            }
            i++;
            j--;
        }
    }

    private void reverseDownRight(int moveI, int moveJ, State moveState, State reverseState, ArrayList<Square> reversedSquares) {
        ArrayList<Square> temp = new ArrayList<>();
        int i = moveI + 1;
        int j = moveJ + 1;
        while (i < dim && j < dim) {
            Square square = board[i][j];
            switch (searchReverse(square, moveState, reverseState)) {
                case MIDDLE -> temp.add(square);
                case END -> {
                    reversedSquares.addAll(temp);
                    return;
                }
                case INVALID -> {
                    return;
                }
            }
            i++;
            j++;
        }
    }

    private enum ReverseResult {
        MIDDLE,
        END,
        INVALID
    }

    private ReverseResult searchReverse(Square square, State moveState, State reverseState) {
        if (square.getState() == reverseState) {
            return ReverseResult.MIDDLE;
        } else if (square.getState() == moveState) {
            return ReverseResult.END;
        } else {
            return ReverseResult.INVALID;
        }

    }

    public ArrayList<Square> getValidMoves() {
        return validMoves;
    }

    private void findValidMoves() {
        validMoves.clear();
        State moveState = null;
        State reverseState = null;

        switch (gameState) {
            case SETTING_UP:
            case GAME_OVER:
                return;
            case PLAYER_ONE:
                moveState = State.PLAYER_ONE;
                reverseState = State.PLAYER_TWO;
                break;
            case PLAYER_TWO:
                moveState = State.PLAYER_TWO;
                reverseState = State.PLAYER_ONE;
                break;
        }

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {

                if (board[i][j].getState() == State.EMPTY && !reverse(moveState, reverseState, i, j).isEmpty()) {
                    validMoves.add(board[i][j]);
                }
            }
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public void reset() {
        moveList.clear();
        validMoves.clear();
        gameState = GameState.SETTING_UP;

        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++) board[i][j] = new Square(State.EMPTY, i, j);

        board[dim / 2 - 1][dim / 2 - 1].setState(State.PLAYER_ONE);
        board[dim / 2][dim / 2].setState(State.PLAYER_ONE);
        board[dim / 2 - 1][dim / 2].setState(State.PLAYER_TWO);
        board[dim / 2][dim / 2 - 1].setState(State.PLAYER_TWO);
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                str += board[i][j];
            }
            str += "\n";
        }
        return str;
    }


}