package sudoku.dialog;

//import oracle.jvm.hotspot.jfr.JFR;
import sudoku.model.Board;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * A dialog template for playing simple Sudoku games.
 * You need to write code for three callback methods:
 * newClicked(int), numberClicked(int) and boardClicked(int,int).
 *
 * @author Yoonsik Cheon
 */
@SuppressWarnings("serial")
public class SudokuDialog extends JFrame {

    /**
     * Default dimension of the dialog.
     */
    private final static Dimension DEFAULT_SIZE = new Dimension(310, 430);
    private final static String IMAGE_DIR = "/image/";
    private final static String SOUND_DIR = "/sound/";
    private int currentClickedNumber = -1;
    /**
     * Sudoku board.
     */
    private Board board;

    JButton newGame, exitGame, solveNow, isSolvable;

    private JMenuItem menuNewGame, menuItemExit;

    /**
     * Special panel to display a Sudoku board.
     */
    private BoardPanel boardPanel;

    /**
     * Message bar to display various messages.
     */
    private JLabel msgBar = new JLabel("");

    /**
     * Create a new dialog.
     */
    public SudokuDialog() {
        this(DEFAULT_SIZE);
    }

    /**
     * Create a new dialog of the given screen dimension.
     */
    private SudokuDialog(Dimension dim) {
        super("Sudoku");
        setSize(dim);
        board = new Board(9);
        boardPanel = new BoardPanel(board, this::boardClicked);
        configureUI();
        //setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        //setResizable(false);
    }

    public static void main(String[] args) {
        new SudokuDialog();
    }

    /**
     * Callback to be invoked when a square of the board is clicked.
     *
     * @param x 0-based row index of the clicked square.
     * @param y 0-based column index of the clicked square.
     */
    private void boardClicked(int x, int y) {
        // WRITE YOUR CODE HERE ...
        //
        while(!board.fullBoard()) {
            if (currentClickedNumber == -1) {
                showMessage("First select a number!");
            } else {
                if (board.insert(x, y, currentClickedNumber)) {
                    showMessage(String.format("Inserted number %d into position: x = %d, y = %d", currentClickedNumber, x, y));
                    boardPanel.updateBoard(currentClickedNumber, x, y);
/*                    if (board.fullBoard()) {
                        playAudio("SkeletorLaugh.wav");
                        JOptionPane.showMessageDialog(this, "You Won!");
                    }*/
                } else if (currentClickedNumber == 0) {
                    board.remove(x, y);
                    showMessage("Removed a number.");
                    boardPanel.updateBoard(currentClickedNumber, x, y);
                } else {
                    playAudio("errorSound.wav");
                    showMessage("Can't insert number in selected position.");
                }
                // currentClickedNumber = -1;
            }
        }
        playAudio("SkeletorLaugh.wav");
        JOptionPane.showMessageDialog(this, "You Won!");
    }

    /**
     * Callback to be invoked when a number button is clicked.
     *
     * @param number Clicked number (1-9), or 0 for "X".
     */
    private void numberClicked(int number) {
        // WRITE YOUR CODE HERE ...
        //
        currentClickedNumber = number;
        if (number > 0) {
            showMessage("Inserting Number: " + number);
        } else {
            showMessage("Removing Numbers");
        }
    }

    /**
     * Callback to be invoked when a new button is clicked.
     * If the current game is over, start a new game of the given size;
     * otherwise, prompt the user for a confirmation and then proceed
     * accordingly.
     *
     * @param size Requested puzzle size, either 4 or 9.
     */
    private void newClicked(int size) {
        // WRITE YOUR CODE HERE ...
        //
        int i = JOptionPane.showConfirmDialog(this, "Do you want to start a new game?");
        switch (i) {
            case 0:
                newGame(size);
                break;
            default:
                showMessage("Canceled New Game" + i);
                break;
        }
        //showMessage("New clicked: " + i);
    }

    /**
     * Resets the UI for a new game
     *
     * @param size Size of the board
     */
    private void newGame(int size) {
        getContentPane().removeAll();
        board = new Board(size);
        boardPanel = new BoardPanel(board, this::boardClicked);
        configureUI();
        repaint();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        showMessage("New Game ");
    }

    /**
     * Display the given string in the message bar.
     *
     * @param msg Message to be displayed.
     */
    private void showMessage(String msg) {

        msgBar.setText(msg);
    }

    /**
     * Configure the UI.
     */
    private void configureUI() {
        setIconImage(Objects.requireNonNull(createImageIcon("sudoku.png")).getImage());
        setLayout(new BorderLayout());

        JPanel center = new JPanel();
        center.setLayout(new BorderLayout());

        JPanel North = new JPanel();
        North.setLayout(new BorderLayout());

        JPanel menu = Menu();
        menu.setBorder(BorderFactory.createEmptyBorder(10,16,0,16));
        add(menu, BorderLayout.NORTH);

        JPanel toolBar = ToolBar();
        toolBar.setBorder(BorderFactory.createEmptyBorder(10,16,0,16));
        North.add(toolBar, BorderLayout.NORTH);

        JPanel buttons = makeControlPanel();
        // boarder: top, left, bottom, right
        buttons.setBorder(BorderFactory.createEmptyBorder(20, 16, 0, 16));
        center.add(buttons, BorderLayout.NORTH);

        JPanel board = new JPanel();
        board.setBorder(BorderFactory.createEmptyBorder(10, 16, 0, 16));
        board.setLayout(new GridLayout(1, 1));
        board.add(boardPanel);
        center.add(board, BorderLayout.CENTER);
        msgBar.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 0));
        add(msgBar, BorderLayout.SOUTH);
        add(center, BorderLayout.CENTER);
        add(North, BorderLayout.NORTH);
        setupActions();
    }


        private JPanel Menu() {
    	JPanel menus = new JPanel();;
		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu("Menu");
		menu.setMnemonic(KeyEvent.VK_G);
		menu.getAccessibleContext().setAccessibleDescription("Game menu");
		menuBar.add(menu);

		menuNewGame = new JMenuItem("New Game", KeyEvent.VK_N);
		menuNewGame.setIcon(createImageIcon("new.png"));
		menuNewGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
		menuNewGame.getAccessibleContext().setAccessibleDescription("Play a new game");

		menuItemExit = new JMenuItem("Exit Game", KeyEvent.VK_C);
		menuItemExit.setIcon(createImageIcon("checkGame.png"));
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		menuItemExit.getAccessibleContext().setAccessibleDescription("Quit Game");

		menu.add(menuNewGame);
		menu.add(menuItemExit);
		setJMenuBar(menuBar);
		setVisible(true);
		return menus;
    }

    private JPanel ToolBar() {
    	JPanel panel = new JPanel();
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        panel.add(toolBar, BorderLayout.PAGE_START);

		newGame = new JButton(createImageIcon("play.png"));
		newGame.setToolTipText("Play new game");
		toolBar.add(newGame);

		exitGame = new JButton(createImageIcon("endgamepng.png"));
		exitGame.setToolTipText("End current game");
		toolBar.add(exitGame);

		solveNow = new JButton(createImageIcon("solveable.png"));
		solveNow.setToolTipText("solves board");
		toolBar.add(solveNow);

        isSolvable = new JButton(createImageIcon("isSolv.png"));
        isSolvable.setToolTipText("Checks if current board is solvable");
        toolBar.add(isSolvable);

        return panel;
    }


    private void setupActions() {

        newGame.addActionListener(this::actionNewGame);

        exitGame.addActionListener(this::actionCloseGame);

        solveNow.addActionListener(this::actionSolveGame);

        isSolvable.addActionListener(this::actionIsSolvable);

        menuNewGame.addActionListener(this::actionNewGame);

        menuItemExit.addActionListener(this::actionCloseGame);


    }

    public void actionNewGame(ActionEvent click) {
        int input = JOptionPane.showConfirmDialog(null, "Start New Game?", "New Game?", JOptionPane.YES_NO_OPTION);
        if (input == JOptionPane.YES_OPTION) {
            board = new Board(board.size);
            boardPanel.setBoard(board);
            boardPanel.repaint();
        }
    }

    public void actionSolveGame(ActionEvent click) {
        int input = JOptionPane.showConfirmDialog(null, "Do you want to solve the game?", "Solve it all?", JOptionPane.YES_NO_OPTION);
        if (input == JOptionPane.YES_OPTION) {
            board.solve();
            boardPanel.repaint();
        }
    }

    public void actionCloseGame(ActionEvent click) {
        int input = JOptionPane.showConfirmDialog(null, "Close Current Game?", "End Game?", JOptionPane.YES_NO_OPTION);
        if (input == JOptionPane.YES_OPTION) {
            System.exit(1);
        }
    }


    public void actionIsSolvable(ActionEvent click) {
        Board copy= board.copy();
        copy.solve();
        if(copy.fullBoard()){
            int input = JOptionPane.showConfirmDialog(null, "This game IS solvable", "is Solvable?", JOptionPane.OK_OPTION);
        }else{
            int input2 = JOptionPane.showConfirmDialog(null, "This game IS NOT solvable. " +
                    "start new game? ", "is Solvable?", JOptionPane.YES_NO_OPTION);
            if (input2 == JOptionPane.YES_OPTION) {
                board = new Board(board.size);
                boardPanel.setBoard(board);
                boardPanel.repaint();
            }
        }

    }

    // A convenience method for creating menu items.
    public static JMenuItem menuItem(String label,
                                     ActionListener listener, String command,
                                     int mnemonic, int acceleratorKey) {
        JMenuItem item = new JMenuItem(label);
        item.addActionListener(listener);
        item.setActionCommand(command);
        if (mnemonic != 0) item.setMnemonic((char) mnemonic);
        if (acceleratorKey != 0)
            item.setAccelerator(KeyStroke.getKeyStroke(acceleratorKey,
                    java.awt.Event.CTRL_MASK));
        return item;
    }



    /**
     * Create a control panel consisting of new and number buttons.
     */
    private JPanel makeControlPanel() {
        JPanel newButtons = new JPanel(new FlowLayout());
        JButton new4Button = new JButton("New (4x4)");
        for (JButton button : new JButton[]{new4Button, new JButton("New (9x9)")}) {
            button.setFocusPainted(false);
            button.addActionListener(e -> newClicked(e.getSource() == new4Button ? 4 : 9));
            newButtons.add(button);
        }
        newButtons.setAlignmentX(LEFT_ALIGNMENT);

        // buttons labeled 1, 2, ..., 9, and X.
        JPanel numberButtons = new JPanel(new FlowLayout());
        int maxNumber = board.size() + 1;
        for (int i = 1; i <= maxNumber; i++) {
            int number = i % maxNumber;
            JButton button = new JButton(number == 0 ? "X" : String.valueOf(number));
            button.setFocusPainted(false);
            button.setMargin(new Insets(0, 2, 0, 2));
            button.addActionListener(e -> numberClicked(number));
            numberButtons.add(button);
        }
        numberButtons.setAlignmentX(LEFT_ALIGNMENT);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
        content.add(newButtons);
        content.add(numberButtons);
        return content;
    }

    /**
     * Create an image icon from the given image file.
     */
    private ImageIcon createImageIcon(String filename) {
        URL imageUrl = getClass().getResource(IMAGE_DIR + filename);
        if (imageUrl != null) {
            return new ImageIcon(imageUrl);
        }
        return null;
    }

    /**
     * Play the given audio file. Inefficient because a file will be (re)loaded
     * each time it is played.
     */
    private void playAudio(String filename) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(SOUND_DIR + filename));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
