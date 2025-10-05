import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Minesweeper {

    private class Mine extends JButton { //make another class with the properties of the JButton ,but we add the row and col number;
      int r; //row
      int c; //col
      public Mine(int r, int c) {
        this.r = r;
        this.c = c;
      }  
    }
    int tileSize = 70;// size of a tile;
    int row = 8;      //number of rows;
    int col = 8;      //number of columns;
    int width = col*tileSize; // the width of the board;
    int height = row*tileSize; // the height of the board;
    JFrame frame = new JFrame("Minesweeper");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    Mine[][] board = new Mine[row][col];

    Minesweeper() {
        frame.setVisible(true);
        frame.setSize(width,height);
        frame.setLocationRelativeTo(null);  //open the game at the center of the screen;
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//if exit close the program;
        frame.setLayout(new BorderLayout());
            //here we have a white screen centered in the middle;
        textLabel.setFont(new Font("Arial", Font.BOLD, 25));  // set the font
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Minesweeper");
        textLabel.setOpaque(true);
        
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel); 
        frame.add(textPanel, BorderLayout.NORTH); // add "Minesweeper" title to the window and center it at the top;

        boardPanel.setLayout(new GridLayout(row,col)); //make 8x8 grid
        boardPanel.setBackground(Color.green); // make the game backgroudn green;
        frame.add(boardPanel);

        for(int r=0;r<row;r++) {
            for(int c=0;c<col;c++) {
                Mine tile = new Mine(r, c);
                board[r][c] = tile;
                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 45));
                tile.setText("1");
                boardPanel.add(tile);
                //here we have the 8x8 grid with 64 clickable buttons;
            }
        }

    }
}
