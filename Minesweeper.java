public class Minesweeper {
    GameController gameController;

    Minesweeper() {
        gameController = new GameController();
        gameController.start();
    }


    public static void main(String[] args) {
        new Minesweeper();
    }
}