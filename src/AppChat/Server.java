package AppChat;
#https://www.youtube.com/watch?v=hIc_9Wbn704&ab_channel=NeuralNine
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;

public class Server implements  Runnable{

    private ArrayList<ConnectionHandler> connections;

    @Override
    public void run(){

        try {
            ServerSocket server = new ServerSocket(9999);
            Socket client = server.accept();
            ConnectionHandler handler = new ConnectionHandler(client);
            connections.add(handler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void broadcast(String message){
        for(ConnectionHandler ch : connections){
            if( ch != null){
                ch.sendMessage(message);
            }
        }
    }

    class ConnectionHandler implements Runnable {

        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String name;


        public ConnectionHandler(Socket client){
            this.client = client;
        }


        @Override
        public void run () {

            try {
                out = new PrintWriter(client.getOutputStream(),true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("please Enter a name");
                name = in.readLine();
                System.out.println(name + "connect!");
                broadcast(name + "joined!");
                String message;
                while((message = in.readLine()) != null){
                    if(message.startsWith("/name")){
                        String[] messageSplit =
                    }
                    else if (message.startsWith("/quit")){

                    } else {
                        broadcast(name +": "+ message);
                    }
                }
            } catch (IOException e) {

            }
        }

        public void sendMessage(String message){
            out.println(message);
        }
    }

}
