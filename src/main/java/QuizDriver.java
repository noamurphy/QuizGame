import java.io.IOException;
import java.util.Scanner;

/**
 * Driver class for quiz game client
 */
public class QuizDriver {
    public static void main(String[] args) {

        //Receive info from user for QuizClient socket
        Scanner consoleScan = new Scanner(System.in);
        System.out.println("Enter address: ");
        String address = consoleScan.next();
        consoleScan.nextLine();
        System.out.println("Enter port: ");
        int port = consoleScan.nextInt();
        consoleScan.nextLine();

        //Create quiz client and play quiz
        try {
            QuizClient client = new QuizClient(address, port, consoleScan);
            client.quiz();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server connection unsuccessful.");
        }

    }
}
