package sudoku.model;
import java.util.Random;

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
        board = GenerateSudoku();
        board = RemoveNumbers(board);

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
    public int[][] RemoveNumbers(int[][] numbers) {
        Random randomnNumbers = new Random();
        int toRemove = 10;// 4 es quitamos 10 y en el 9 51
        if (size == 9) {
            toRemove = 51;
        }
        for (int i = 0; i < toRemove;) {
            int column = randomnNumbers.nextInt(numbers.length);
            int row = randomnNumbers.nextInt(numbers.length);
            if (numbers[row][column] != 0) {
                numbers[row][column] = 0;
                i++;
            }

        }

        return numbers;
    }

    public int[][] GenerateSudoku() {
        for (int r = 0; r <= size - 1; r++) {
            int startNum = (int) (Math.sqrt(size) * (r % Math.sqrt(size)) + (r / Math.sqrt(size)));
            for (int c = 0; c <= size - 1; c++) {
                board[r][c] = ((startNum + c) % size) + 1;
            }
        }
        return board;
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

    public int getValueAtCoordinates(int x, int y){
        return board[x][y];
    }

}
