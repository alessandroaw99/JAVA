import java.util.Scanner;

public class revision {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is your name?");
        String name = scanner.nextLine();

        System.out.println("your name is" + name + "Thanks for applying");
        scanner.close();
    }
}
