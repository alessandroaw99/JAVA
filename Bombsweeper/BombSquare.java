import java.util.Random;

public class BombSquare extends GameSquare {
    private boolean clicked = false;              //  to track if this square has been clicked
    private boolean thisSquareHasBomb = false;    //  to indicate if this square contains a bomb
    public static final int MINE_PROBABILITY = 10;

    public BombSquare(int x, int y, GameBoard board) {        // Constructor to initialize a BombSquare
        super(x, y, "images/blank.png", board);                  // Call the constructor of the superclass (GameSquare)

        Random r = new Random();                                  // Use Random to determine if this square contains a bomb
        thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
    }
   // Method called when the square is clicked
    public void clicked() {
        if (clicked) {                      // If the square has already been clicked, return
            return;
        }

        clicked = true;

        if (thisSquareHasBomb) {            // If this square has a bomb, set the image to display a bomb
            // image
            setImage("images/bomb.png");
        } else {
            int bombCount = countBombsInSurroundingSquares();        // Count the number of bombs in surrounding squares
            if (bombCount > 0) {                                  // If there are bombs nearby, set the image to display the count
                setImage("images/" + bombCount + ".png");
            } else {
                revealEmptySpace();                        // If no bombs nearby, reveal empty space
            }
        }
    }
      // Method that counts the number of bombs in surrounding squares
    private int countBombsInSurroundingSquares() {
        int bombCount = 0;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int newX = xLocation + dx;          // Calculating the coordinates of the adjacent square
                int newY = yLocation + dy;

                GameSquare adjacentSquare = board.getSquareAt(newX, newY);

                if (adjacentSquare instanceof BombSquare &&
                    ((BombSquare) adjacentSquare).thisSquareHasBomb) {
                    bombCount++;
                }
            }
        }

        return bombCount;
    }
      // Method to reveal empty space using recursion
    private void revealEmptySpace() {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int newX = xLocation + dx;
                int newY = yLocation + dy;

                GameSquare adjacentSquare = board.getSquareAt(newX, newY);

                if (adjacentSquare instanceof BombSquare && !((BombSquare) adjacentSquare).clicked) {
                    ((BombSquare) adjacentSquare).clicked();
                }
            }
        }
    }
}
