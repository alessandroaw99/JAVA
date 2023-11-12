import java.util.Scanner;

public class Driver
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object to take user input

        System.out.println("Enter the width of the game board:");   //  to enter the width of the game board
        int width = scanner.nextInt();

        System.out.println("Enter the height of the game board:");    //  to enter the height of the game board
        int height = scanner.nextInt();

        GameBoard b = new GameBoard("BombSweeper", width, height); // Create a new GameBoard instance with the specified width and height
    }
}
