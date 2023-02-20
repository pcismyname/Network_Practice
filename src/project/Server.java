package project;

import java.io.*;
import java.net.*;
public class Server {
    public static void main(String[] args) throws IOException {
        int PORT = 9999;

        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.println("Server listening on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection from " + clientSocket.getInetAddress());

            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            String line;
            while ((line = input.readLine()) != null) {
                System.out.println("Received: " + line);
                output.println(line);
            }

            clientSocket.close();
        }
    }

}
