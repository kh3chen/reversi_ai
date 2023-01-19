package game;

public class Square {

    public Square(State state, int row, int col) {
        this.state = state;
        this.row = row;
        this.col = col;
    }
    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public enum State {EMPTY, BLOCKED, PLAYER_ONE, PLAYER_TWO}

    private State state;
    private int row;
    private int col;
}
