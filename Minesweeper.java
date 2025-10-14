import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Minesweeper {

    private class Mine extends JButton {
        // make another class with the properties of the JButton ,but we add the row
        // and col number;
        int r; // row
        int c; // col

        public Mine(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    Timer timer;
    boolean go = false; // game over
    boolean started = false;
    int clicked = 0; // to remember how many tiles we clicked on
    int nrf = 25; // variable to see how many flags we have left;
    int tileSize = 70; // size of a tile;
    int row = 12; // number of rows;
    int col = 12; // number of columns;
    int width = col * tileSize; // the width of the board;
    int height = row * tileSize; // the height of the board;
    int elapsedTime = 0;
    int seconds = 0;
    int minutes = 0;
    JFrame frame = new JFrame("Minesweeper");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel flagCounter = new JPanel();
    JPanel boardPanel = new JPanel();
    boolean[][] mineStorage = new boolean[row][col];

    String secondsString = String.format("%02d", seconds);
    String minutesString = String.format("%02d", minutes);

    Mine[][] board = new Mine[row][col];
    ArrayList<Mine> mineList; // Array for generating the mines;

    Minesweeper() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                mineStorage[i][j] = false;
            }
        } // Array that will be used for putting bombs.
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null); // open the game at the center of the screen;
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // if exit close the program;
        frame.setLayout(new BorderLayout());
        // here we have a white screen centered in the middle;
        textLabel.setFont(new Font("Arial", Font.BOLD, 25)); // set the font
        textLabel.setHorizontalAlignment(JLabel.LEFT);
        textLabel.setText(" Flags:" + nrf
                + "                                 Time: "
                + minutesString + ":"
                + secondsString);
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);
        // add "Minesweeper" title to the window and center it at the top;

        boardPanel.setLayout(new GridLayout(row, col)); // make 8x8 grid
        boardPanel.setBackground(Color.green); // make the game background green;
        frame.add(boardPanel);

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                Mine tile = new Mine(r, c);
                board[r][c] = tile;
                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 45));
                // tile.setText("💣");
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) { // if the tile is clicked
                        if (go) {
                            return; // if the game is over u cant click anymore;
                        }
                        if (!started) {
                            started = true;
                            timer = new Timer(1000, new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    elapsedTime = elapsedTime + 1000;
                                    minutes = (elapsedTime / 60000) % 60;
                                    seconds = (elapsedTime / 1000) % 60;
                                    secondsString = String.format("%02d", seconds);
                                    minutesString = String.format("%02d", minutes);
                                    textLabel.setText(" Flags:" + nrf +
                                            "                                 Time: "
                                            + minutesString + ":"
                                            + secondsString);
                                }
                            });
                            timer.start();
                        }
                        Mine tile = (Mine) e.getSource();

                        if (e.getButton() == MouseEvent.BUTTON1) { // left click, to reveal tile;
                            if (tile.getText() == "") {
                                if (mineList.contains(tile)) {
                                    revealMines();
                                } else {
                                    checkMine(tile.r, tile.c); // check how many mines are nearby;
                                }
                            }
                        }
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            // right click on a flag to remove the flag;
                            if (tile.getText() == "🚩") {
                                tile.setText("");
                                nrf++;
                                textLabel.setText(" Flags:" + nrf
                                        + "               Time: " + minutesString + ":"
                                        + secondsString);
                            } else if (tile.getText() == "" && nrf != 0) {
                                // right click on an empty tile to put a flag;
                                tile.setText("🚩");
                                nrf--;
                                textLabel.setText(" Flags:" + nrf
                                        + "                                 Time: "
                                        + minutesString + ":"
                                        + secondsString);
                            }
                        }
                    }
                });
                boardPanel.add(tile);
                // here we have the 8x8 grid with 64 clickable buttons;
            }
        }
        frame.setVisible(true); // daca nu scoate toate tile urile;

        setMines();
    }

    void setMines() { // place the mines
        mineList = new ArrayList<Mine>();
        Random random = new Random();
        for (int i = 0; i < 25; i++) {
            int r = random.nextInt(row);
            int c = random.nextInt(col);
            if (!mineStorage[r][c]) {
                mineList.add(board[r][c]);
                mineStorage[r][c] = true;
            } else {
                i--;
            }
        }

    }

    void revealMines() {
        // go through the ArrayList and the the tiles with mines to the bomb emoji;
        for (int i = 0; i < mineList.size(); i++) {
            Mine tile = mineList.get(i);
            tile.setText("💣");
            textLabel.setText("Time: " + minutesString
                    + ":" + secondsString + "                      You Lost! Try again!");
            textLabel.setHorizontalAlignment(JLabel.LEFT);
            go = true;
            if (timer != null) {
                timer.stop();
            }
        }
    }

    void checkMine(int r, int c) {

        if (r < 0 || r >= row || c < 0 || c >= col) { // if its out of bound,exit ;
            return;
        }
        Mine tile = board[r][c];
        if (!tile.isEnabled()) { // if tile is clicked on;
            return;
        }
        tile.setEnabled(false);
        clicked = clicked + 1;
        int minesFound = 0;
        minesFound = minesFound + countMine(r - 1, c - 1); // neighbour top left;
        minesFound = minesFound + countMine(r - 1, c); // neighbour top;
        minesFound = minesFound + countMine(r - 1, c + 1); // neighbour top right;
        minesFound = minesFound + countMine(r, c - 1); // neighbour same row but to the left;
        minesFound = minesFound + countMine(r, c + 1); // neighbour same row but to the right;
        minesFound = minesFound + countMine(r + 1, c - 1); // neighbour bottom left;
        minesFound = minesFound + countMine(r + 1, c); // neighbour bottom under;
        minesFound = minesFound + countMine(r + 1, c + 1); // neighbour bottom right;

        if (minesFound > 0) {
            tile.setText(Integer.toString(minesFound));
        } else {
            tile.setText("");
            checkMine(r - 1, c - 1); // check for mines top left;
            checkMine(r - 1, c); // check for mines top ;
            checkMine(r - 1, c + 1); // check for mines top right;
            checkMine(r, c - 1); // check for mines same row to the left;
            checkMine(r, c + 1); // check for mines same row to the right;
            checkMine(r + 1, c - 1); // check for mines bottom left;
            checkMine(r + 1, c); // check for mines bottom;
            checkMine(r + 1, c + 1); // check for mines bottom right;
        }

        if (clicked == row * col - mineList.size()) {
            go = true;
            textLabel.setText("Time: " + minutesString
                    + ":" + secondsString + "          You Won!");
            textLabel.setHorizontalAlignment(JLabel.LEFT);
            if (timer != null) {
                timer.stop();
            }
        }
    }

    int countMine(int r, int c) {
        if (r < 0 || r >= row || c < 0 || c >= col) { // if the neighbour is out of bounds return 0;
            return 0;
        }
        if (mineList.contains(board[r][c])) { // if the neighbour has a bomb return 1;
            return 1;
        }
        return 0; // if the neighbour doesnt have a bomb return 0;
    }

    public static void main(String[] args) {
        new Minesweeper();
    }
}