import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

        int numberToGuess = rand.nextInt(100) + 1;  // 1 to 100
        int attempts = 7;
        boolean hasWon = false;

        System.out.println("🎉 Welcome to the Number Guessing Game!");
        System.out.println("I'm thinking of a number between 1 and 100.");
        System.out.println("You have " + attempts + " attempts to guess it.");

        for (int i = 1; i <= attempts; i++) {
            System.out.print("Attempt " + i + ": Enter your guess: ");
            int guess = scanner.nextInt();

            if (guess == numberToGuess) {
                hasWon = true;
                break;
            } else if (guess < numberToGuess) {
                System.out.println("Too low!");
            } else {
                System.out.println("Too high!");
            }
        }

        if (hasWon) {
            System.out.println("🎉 Congratulations! You guessed the number.");
        } else {
            System.out.println("❌ Sorry! You ran out of attempts.");
            System.out.println("The number was: " + numberToGuess);
        }

        scanner.close();
    }
}

