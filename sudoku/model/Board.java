package sudoku.model;

/**
 * An abstraction of Sudoku puzzle.
 */
public class Board {

    /**
     * Size of this board (number of columns/rows).
     */
    public final int size;
    private int[][] board;
    private int fullBoard;

    /**
     * Create a new board of the given size.
     */
    public Board(int size) {
        this.size = size;
        // WRITE YOUR CODE HERE ...
        board = new int[size][size];
        fullBoard = size * size;
    }

    /**
     * Return the size of this board.
     */
    public int size() {
        return size;
    }

    public boolean insert(int x, int y, int toInsert) {
        if (validateInput(x, y, toInsert)) {
            if (board[x][y] == 0) {
                fullBoard--;
            }
            board[x][y] = toInsert;
            return true;
        }
        return false;
    }

    private boolean validateInput(int x, int y, int toInsert) {
        for (int i = 0; i < size; i++) {
            if (board[x][i] == toInsert) {
                return false;
            }
            if (board[i][y] == toInsert) {
                return false;
            }
        }
        for (int i = 0; i < Math.sqrt(size); i++) {
            for (int j = 0; j < Math.sqrt(size); j++) {
                if (board[x - (x % (int) Math.sqrt(size)) + i][y - (y % (int) Math.sqrt(size)) + j] == toInsert) {
                    return false;
                }
            }
        }
        return true;
    }

    public void remove(int x, int y) {
        board[x][y] = 0;
        fullBoard++;
    }

    public boolean fullBoard() {
        return fullBoard == 0;
    }

}
