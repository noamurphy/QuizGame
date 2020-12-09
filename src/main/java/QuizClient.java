import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class QuizClient {
    static final String DELIMITER = "~";
    public static void main(String[] args) throws IOException {

        Scanner consoleScan = new Scanner(System.in);
        System.out.println("Enter address: ");
        String address = consoleScan.next();
        consoleScan.nextLine();
        System.out.println("Enter port: ");
        int port = consoleScan.nextInt();
        consoleScan.nextLine();

        Socket s = new Socket(address, port);
        PrintWriter out = new PrintWriter(s.getOutputStream());
        Scanner socketScan = new Scanner(s.getInputStream());
        socketScan.useDelimiter("~");

        String code = "";
        String msg = "";
        while (!(code.equals("PLAYAGAIN") && !(msg.toUpperCase().equals("Y")))){
            //Receive code from the server
            code = socketScan.next();
            //Handle code cases
            switch (code){
                case "MSG":
                    System.out.println(socketScan.next());
                    break;
                case "PLAYAGAIN":
                    System.out.println(socketScan.next());
                    msg = consoleScan.nextLine().toUpperCase();
                    out.write(msg);
                    out.write(DELIMITER);
                    out.flush();
                    break;
                default:
                    //Covers "QUESTION" and "NAME" cases.
                    System.out.println(socketScan.next());
                    msg = consoleScan.nextLine();
                    out.write(msg);
                    out.write(DELIMITER);
                    out.flush();
                    break;
            }
        }
    }
}
