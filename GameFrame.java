import java.awt.*;
import javax.swing.*;

public class GameFrame {
    int tileSize = 70; // size of a tile;
    int row = 12; // number of rows;
    int col = 12; // number of columns;
    int width = col * tileSize; // the width of the board;
    int height = row * tileSize; // the height of the board;

    JFrame frame = new JFrame("Minesweeper");
    JPanel boardPanel = new JPanel();

    public GameFrame() {
        makeFrame();
    }

    void makeFrame() {
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null); // open the game at the center of the screen;
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // if exit close the program;
        frame.setLayout(new BorderLayout());

        boardPanel.setLayout(new GridLayout(row, col)); // make the grid
        boardPanel.setBackground(Color.green); // make the game background green;
        frame.add(boardPanel);
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getTileSize() {
        return tileSize;
    }
}

