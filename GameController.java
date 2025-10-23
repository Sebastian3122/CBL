import java.awt.*;
import java.awt.event.*;

public class GameController {
    boolean gameOver = false; // game over
    boolean started = false;
    int clicked = 0; // to remember how many tiles we clicked on

    Board board;
    GameTimer gameTimer;
    StatusBar statusBar;
    GameFrame gameFrame;

    public GameController() {
        board = new Board();
        gameTimer = new GameTimer();
        statusBar = new StatusBar();
        gameFrame = new GameFrame();

        // Set up timer callback to update status bar
        gameTimer.setOnTimeUpdate(() -> statusBar.updateLabel(gameTimer.getTimeString()));

        makeBoard();
        setMines();
    }

    void makeBoard() {
        gameFrame.getBoardPanel().removeAll();
        board.resetBoard();
        gameOver = false;
        started = false;
        clicked = 0;
        statusBar.setFlags(25);
        gameTimer.resetTimer();

        statusBar.updateLabel();

        if (gameTimer.timer != null) {
            gameTimer.stopTimer();
        }

        for (int r = 0; r < gameFrame.getRow(); r++) {
            for (int c = 0; c < gameFrame.getCol(); c++) {
                MineButton tile = new MineButton(r, c);
                board.board[r][c] = tile;
                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 45));
                tile.setEnabled(true);
                tile.setText("");
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) { // if the tile is clicked
                        if (gameOver) {
                            return; // if the game is over u cant click anymore;
                        }
                        if (!started) {
                            started = true;
                            gameTimer.startTimer();
                        }
                        MineButton tile = (MineButton) e.getSource();

                        if (e.getButton() == MouseEvent.BUTTON1) { // left click, to reveal tile;
                            if (tile.getText().equals("")) {
                                if (board.mineList.contains(tile)) {
                                    revealMines();
                                } else {
                                    board.checkMine(tile.r, tile.c); 
                                    // check how many mines are nearby;
                                    checkWin();
                                }
                            }
                        }
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            // right click on a flag to remove the flag;
                            if (tile.getText().equals("🚩")) {
                                tile.setText("");
                                statusBar.incrementFlags();
                                statusBar.updateLabel(gameTimer.getTimeString());
                            } else if (tile.getText().equals("") 
                                && statusBar.getFlags() != 0 && tile.isEnabled()) {
                                // right click on an empty tile to put a flag;
                                tile.setText("🚩");
                                statusBar.decrementFlags();
                            }
                            statusBar.updateLabel(gameTimer.getTimeString());
                        }
                    }
                });
                gameFrame.getBoardPanel().add(tile);
                // here we have the 8x8 grid with 64 clickable buttons;
            }
        }
        gameFrame.getBoardPanel().revalidate();
        gameFrame.getBoardPanel().repaint();
    }

    void setMines() { // place the mines
        board.setMines();
    }

    void revealMines() {
        // go through the ArrayList and the the tiles with mines to the bomb emoji;
        board.revealMines();
        statusBar.setGameOverText(gameTimer.getTimeString());
        gameOver = true;
        // stop stopwatch
        if (gameTimer.timer != null) {
            gameTimer.stopTimer();
        }
    }

    void checkWin() {
        if (board.isWin()) {
            gameOver = true;
            if (gameTimer.timer != null) {
                gameTimer.stopTimer();
            }

            int currentTimeSeconds = gameTimer.getElapsedTimeSeconds();
            if (currentTimeSeconds < statusBar.getPersonalBest()) {
                statusBar.setPersonalBest(currentTimeSeconds);
            }

            statusBar.setWinText(gameTimer.getTimeString());
        }
    }

    void restartGame() {
        if (gameTimer.timer != null) {
            gameTimer.stopTimer();
        }
        makeBoard();
        setMines();
    }

    public void start() {
        gameFrame.getFrame().add(statusBar.getTextPanel(), java.awt.BorderLayout.NORTH);
        statusBar.getRestartButton().addActionListener(e -> restartGame());
    }
}
