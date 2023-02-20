package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Client {

    public static void main(String[] args) throws IOException {
        int port = 9999;
        InetAddress serverAddress = InetAddress.getByName("localhost");

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(serverAddress, port));

        socket.setKeepAlive(true);
        socket.setTcpNoDelay(true);
        socket.setTrafficClass(0b01100000);


        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        String line;
        while ((line = console.readLine()) != null) {
            output.println(line);
            System.out.println("Sent: " + line);
            System.out.println("Received: " + input.readLine());
        }

        socket.close();
    }

}
