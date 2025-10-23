import java.util.ArrayList;
import java.util.Random;

public class Board {
    int row = 12; // number of rows;
    int col = 12; // number of columns;
    boolean[][] mineStorage = new boolean[row][col];
    ArrayList<MineButton> mineList; // Array for generating the mines;
    MineButton[][] board = new MineButton[row][col];


    public void setMines() { // place the mines
        mineList = new ArrayList<MineButton>();
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

    public void revealMines() {
        // go through the ArrayList and the the tiles with mines to the bomb emoji;
        for (int i = 0; i < mineList.size(); i++) {
            MineButton tile = mineList.get(i);
            tile.setText("💣");
        }
    }

    public void checkMine(int r, int c) {
        if (r < 0 || r >= row || c < 0 || c >= col) { // if its out of bound,exit ;
            return;
        }
        MineButton tile = board[r][c];
        if (!tile.isEnabled()) { // if tile is clicked on;
            return;
        }
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
            checkMine(r + 1, c + 1); // check for mines bottom right;
        }
    }

    public int countMine(int r, int c) {
        if (r < 0 || r >= row || c < 0 || c >= col) { // if the neighbour is out of bounds return 0;
            return 0;
        }
        if (mineList.contains(board[r][c])) { // if the neighbour has a bomb return 1;
            return 1;
        }
        return 0; // if the neighbour doesnt have a bomb return 0;
    }

    public void resetBoard() {
        mineStorage = new boolean[row][col];
        board = new MineButton[row][col];
    }

    public boolean isMine(int r, int c) {
        return mineList.contains(board[r][c]);
    }

    public int getClickedCount() {
        int clicked = 0;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (!board[r][c].isEnabled()) {
                    clicked++;
                }
            }
        }
        return clicked;
    }

    public boolean isWin() {
        return getClickedCount() == row * col - mineList.size();
    }
}

