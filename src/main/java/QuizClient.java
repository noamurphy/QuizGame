import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * A client to access a quiz server and play a quiz game.
 */
public class QuizClient {
    static final String DELIMITER = "~";
    private String address;
    private int port;
    private Scanner socketScan, consoleScan;
    private PrintWriter out;

    /**
     * QuizClient constructor
     * @param address The address of the quiz server.
     * @param port The port of the quiz server.
     * @param consoleScan a user input scanner.
     * @throws IOException
     */
    public QuizClient(String address, int port, Scanner consoleScan) throws IOException {

        this.address = address;
        this.port = port;
        this.consoleScan = consoleScan;
        //Construct socket
        Socket s = new Socket(address, port);
        out = new PrintWriter(s.getOutputStream());
        socketScan = new Scanner(s.getInputStream());
        socketScan.useDelimiter(DELIMITER);
    }

    /**
     * Communicates with the quiz server to play quiz game
     */
    public void quiz(){
        String code = ""; //code from server
        String msg = "";  //response to server
        while (!(code.equals("PLAYAGAIN") && !(msg.toUpperCase().equals("Y")))){
            //Receive code from the server
            code = socketScan.next();
            //Handle code cases and return user input (msg) to server
            switch (code) {
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

