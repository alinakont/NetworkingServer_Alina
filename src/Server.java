import java.net.*;
import java.io.*;
public class Server {

    public static void main(String [] args) {
        ServerSocket server = null;
        Socket client;

        // default portnumber
        int portnumber = 1234;
        if (args.length >= 1) {
            portnumber = Integer.parseInt(args[0]);
        }

        // create server side socket
        try {
            server = new ServerSocket(portnumber);
        } catch (IOException ie) {
            System.out.println("Can not open socket."+ie);
            System.exit(1);
        }
        System.out.println("ServerSocket is created "+server);

        //wait for data from client and reply
        while(true) {
            try {
                //listen for connection
                // this socket accepts data, method blocks till connection is made
                System.out.println("Waiting for connect request...");
                client = server.accept();

                System.out.println("Connect request is accepted...");
                String clientHost = client.getInetAddress().getHostAddress();
                int clientPort = client.getPort();
                System.out.println("Client host = "+clientHost+"Client port = "+clientPort);

                // read data from client
                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
                String msgFromClient = br.readLine();
                System.out.println("Message recieved from client = "+msgFromClient);

                //send response to client
                if (msgFromClient != null && ! msgFromClient.equalsIgnoreCase("bye")) {
                    OutputStream clientOut = client.getOutputStream();
                    PrintWriter pw = new PrintWriter(clientOut, true);
                    String ansMsg = "Hello, "+msgFromClient;
                    pw.println(ansMsg);
                }

                    // close socket
                if (msgFromClient != null && ! msgFromClient.equalsIgnoreCase("bye")) {
                    server.close();

                    client.close();
                    break;
                }
            } catch (IOException ie) {
                System.out.println("Error, please try again...");
            }
        }
    }
}
