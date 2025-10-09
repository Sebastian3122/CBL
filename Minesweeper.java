import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Minesweeper {

    private class Mine extends JButton { // make another class with
        // the properties of the JButton ,but we add the row and col number;
        int r; // row
        int c; // col
        int pulamea = 800;
        public Mine(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    int nrf = 5; // variable to see how many flags we have left;
    int tileSize = 70; // size of a tile;
    int row = 8; // number of rows;
    int col = 8; // number of columns;
    int cnt = col * row;
    int width = col * tileSize; // the width of the board;
    int height = row * tileSize; // the height of the board;
    JFrame frame = new JFrame("Minesweeper");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel flagCounter = new JPanel();
    JPanel boardPanel = new JPanel();
    JLabel textLabel2 = new JLabel(); // for You Lost;
    JPanel textPanel2 = new JPanel(); // for You Lost;
    JLabel textLabel3 = new JLabel(); // for You WIN;
    JPanel textPanel3 = new JPanel(); // for You WIN;

    Mine[][] board = new Mine[row][col];
    ArrayList<Mine> mineList; // Array for generating the mines;

    Minesweeper() {
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null); // open the game at the center of the screen;
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // if exit close the program;
        frame.setLayout(new BorderLayout());
        // here we have a white screen centered in the middle;
        textLabel2.setFont(new Font("Arial", Font.BOLD, 25)); // set the font for lose
        textLabel3.setFont(new Font("Arial", Font.BOLD, 25)); // set the font for win
        textLabel.setFont(new Font("Arial", Font.BOLD, 25)); // set the font
        textLabel.setHorizontalAlignment(JLabel.LEFT);
        textLabel2.setHorizontalAlignment(JLabel.CENTER);
        textLabel3.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText(" Flags:" + nrf + "              Minesweeper");
        textLabel2.setText("You Lose, Try again!");
        textLabel3.setText("YOU WIN");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        textPanel2.add(textLabel2);
        textPanel3.add(textLabel3);
        frame.add(textPanel, BorderLayout.NORTH); // add "Minesweeper" title to
        // the window and center it at the top;

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
                        Mine tile = (Mine) e.getSource();

                        if (e.getButton() == MouseEvent.BUTTON1) { // left click, to reveal tile;
                            if (tile.getText() == "") {
                                if (mineList.contains(tile)) {
                                    revealMines();
                                } else {
                                    checkMine(tile.r, tile.c); // check how many mines are nearby;
                                    cnt--;
                                }
                            }
                        }
                        if (e.getButton() == MouseEvent.BUTTON3) { // right click on a flag
                            // to remove the flag;
                            if (tile.getText() == "🚩") {
                                tile.setText("");
                                nrf++;
                                textLabel.setText(" Flags:" + nrf + "              Minesweeper");

                            } else if (tile.getText() == "" && nrf != 0) { // right click on an
                                // empty tile to put a flag;
                                tile.setText("🚩");
                                nrf--;
                                textLabel.setText(" Flags:" + nrf + "              Minesweeper");
                            }
                        }
                    }
                });
                boardPanel.add(tile);
                // here we have the 8x8 grid with 64 clickable buttons;
            }
        }
        // frame.setVisible(true); daca nu scoate toate tile urile;

        setMines();
    }

    void setMines() { // place the mines
        mineList = new ArrayList<Mine>();
        mineList.add(board[2][2]);
        mineList.add(board[2][3]);
        mineList.add(board[5][6]);
        mineList.add(board[3][4]);
        mineList.add(board[1][1]);

        
    }

    void revealMines() { // go through the ArrayList and the the tiles with mines to the bomb emoji;
        for (int i = 0; i < mineList.size(); i++) {
            Mine tile = mineList.get(i);
            tile.setText("💣");
            frame.add(textPanel2, BorderLayout.NORTH);
            textPanel.setVisible(false);
        }
    }

    void checkMine(int r, int c) {
        Mine tile = board[r][c];
        tile.setEnabled(false);
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
            checkMine(r - 1, c + 1); // check for mines bottom right;
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
