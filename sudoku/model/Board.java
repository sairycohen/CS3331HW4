package sudoku.model;
import java.util.Random;
import sudoku.Solver.Square;
import sudoku.Solver.Strategy;

/**
 * An abstraction of Sudoku puzzle.
 */
public class Board{

    /**
     * Size of this board (number of columns/rows).
     */
    public final int size;
    private Square[][] board;
    private int fullBoard;
    private int emptySquares;
    private Strategy s;
    //private boolean solver;

    /**
     * Create a new board of the given size.
     */
    public Board(int size) {
        this.size = size;
        // WRITE YOUR CODE HERE ...
        board = new Square[size][size];
        for (int i = 0; i< size; i++){
            for(int j=0; j< size; j++){
                board[i][j] = new Square(0,size);
            }
        }
        fullBoard = size * size;
        emptySquares = size*size;
        board = GenerateSudoku();
        board = RemoveNumbers(board);

    }

    public Board(int s, boolean solver){
        size = s;
        board = new Square[size][size];
    }

    //void setSize(int s){size = s;}

    /**
     * Return the size of this board.
     */
    public int size() {
        return size;
    }

    public boolean insert(int x, int y, int toInsert) {
        if (validateInput(x, y, toInsert)) {
            if (board[x][y].getVal() == 0) {
                fullBoard--;
            }
            board[x][y].insert(toInsert);
            return true;
        }
        return false;
    }
    public Square[][] RemoveNumbers(Square[][] numbers) {
        Random randomNumbers = new Random();
        int toRemove = 10;// 4 es quitamos 10 y en el 9 51
        if (size == 9) {
            toRemove = 51;
        }
        for (int i = 0; i < toRemove;) {
            int column = randomNumbers.nextInt(numbers.length);
            int row = randomNumbers.nextInt(numbers.length);
            if (numbers[row][column].getVal() != 0) {
                numbers[row][column].insert(0);
                i++;
            }
        }

        return numbers;
    }

    public Square[][] GenerateSudoku() {
        for (int r = 0; r <= size - 1; r++) {
            int startNum = (int) (Math.sqrt(size) * (r % Math.sqrt(size)) + (r / Math.sqrt(size)));
            for (int c = 0; c <= size - 1; c++) {
                board[r][c].insert(((startNum + c) % size) + 1);
            }
        }
        return board;
    }


    protected boolean validateInput(int x, int y, int toInsert) {
        for (int i = 0; i < size; i++) {
            //if(board[][].getVal() != 0; )
            if (board[x][i].getVal() == toInsert) {
                return false;
            }
            if (board[i][y].getVal() == toInsert) {
                return false;
            }
        }
        for (int i = 0; i < Math.sqrt(size); i++) {
            for (int j = 0; j < Math.sqrt(size); j++) {
                if (board[x - (x % (int) Math.sqrt(size)) + i][y - (y % (int) Math.sqrt(size)) + j].getVal() == toInsert) {
                    return false;
                }
            }
        }
        return true;
    }

    public void solve(){
        s = new Strategy();
        s.solveSudoku(this);
    }


    public void remove(int x, int y) {
        board[x][y].insert(0);
        fullBoard++;
    }

    public Square getSquare(int x, int y){return board[x][y];}

    public boolean fullBoard() {
        return fullBoard == 0;
    }

    public int getValueAtCoordinates(int x, int y){
        return board[x][y].getVal();
    }


}
