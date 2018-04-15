package sudoku.dialog;

import sudoku.model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A special panel class to display a Sudoku board modeled by the
 * {@link Board} class. You need to write code for
 * the paint() method.
 *
 * @author Yoonsik Cheon
 * @see Board
 */
@SuppressWarnings("serial")
public class BoardPanel extends JPanel {

    /**
     * Background color of the board.
     */
    private static final Color boardColor = new Color(61, 245, 202);
    /**
     * Board to be displayed.
     */
    private Board board;
    /**
     * Width and height of a square in pixels.
     */
    private int squareSize;

    /**
     * Create a new board panel to display the given board.
     */
    BoardPanel(Board board, ClickListener listener) {
        this.board = board;
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int xy = locateSquaree(e.getX(), e.getY());
                if (xy >= 0) {
                    listener.clicked(xy / 100, xy % 100);
                }
            }
        });
    }

    /**
     * Set the board to be displayed.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Given a screen coordinate, return the indexes of the corresponding square
     * or -1 if there is no square.
     * The indexes are encoded and returned as x*100 + y,
     * where x and y are 0-based column/row indexes.
     */
    private int locateSquaree(int x, int y) {
        if (x < 0 || x > board.size * squareSize
                || y < 0 || y > board.size * squareSize) {
            return -1;
        }
        int xx = x / squareSize;
        int yy = y / squareSize;
        return xx * 100 + yy;
    }

    /**
     * Draw the associated board.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // determine the square size
        Dimension dim = getSize();
        squareSize = Math.min(dim.width, dim.height) / board.size;
        // draw background
        // final Color oldColor = g.getColor();
        g.setColor(boardColor);
        g.fillRect(0, 0, squareSize * board.size, squareSize * board.size);

        // WRITE YOUR CODE HERE ...
        // i.e., draw grid and squares.
        g.setColor(new Color(0));
        for (int i = 0; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                g.drawRect(i * squareSize, j * squareSize, squareSize, squareSize);
            }
        }
        g.setColor(new Color(255));
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        for (int i = 0; i < (int) Math.sqrt(board.size); i++) {
            for (int j = 0; j < (int) Math.sqrt(board.size); j++) {
                g2.drawRect(i * squareSize * (int) Math.sqrt(board.size), j * squareSize * (int) Math.sqrt(board.size) + 1, squareSize * (int) Math.sqrt(board.size), squareSize * (int) Math.sqrt(board.size));
            }
        }
    }

    public void updateBoard(int toInsert, int x, int y) {
        Graphics g = getGraphics();
        g.setColor(boardColor);
        g.fillRect(x * squareSize + 3, y * squareSize + 3, squareSize - 5, squareSize - 5);
        g.setColor(new Color(0));
        if (toInsert > 0) {
            if (board.size == 9) {
                g.setFont(new Font("TimesRoman", Font.PLAIN, 13));
                g.drawString(Integer.toString(toInsert), x * squareSize + board.size + 3, y * squareSize + board.size + 11);
            } else {
                g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
                g.drawString(Integer.toString(toInsert), x * squareSize + board.size + 20, y * squareSize + board.size + 38);
            }
        }
    }

    public interface ClickListener {

        /**
         * Callback to notify clicking of a square.
         *
         * @param x 0-based column index of the clicked square
         * @param y 0-based row index of the clicked square
         */
        void clicked(int x, int y);
    }

}
