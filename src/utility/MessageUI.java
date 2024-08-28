package utility;

import java.util.Scanner;

/**
 *
 * @author Team
 */
public class MessageUI {
  
  
  public static void displayInvalidChoiceMessage() {
    System.err.println("\nInvalid choice");
  }

  public static void displayExitMessage() {
    System.out.println("\nExiting system");
  }
  public static void pressAnyKeyToContinue() {
        System.out.println("Press any key to continue...");
        try {
            // Using Scanner to read a single line input to simulate "press any key"
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("An error occurred while waiting for input.");
        }
    }
}
