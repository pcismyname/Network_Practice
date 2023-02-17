package AppChat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements  Runnable{

    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;

    public Server() {
        connections = new ArrayList<>();
        done = false;

    }

    @Override
    public void run(){

        try {
            server = new ServerSocket(9999);
            pool = Executors.newCachedThreadPool();
            while(!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
                pool.execute(handler);
            }
        } catch (IOException e) {
            shutdown();
        }


    }

    public void broadcast(String message){
        for(ConnectionHandler ch : connections){
            if( ch != null){
                ch.sendMessage(message);
            }
        }
    }

    public void shutdown() {
        try {
            done = true;
            pool.shutdown();
            if (!server.isClosed()) {
                server.close();
            }
            for(ConnectionHandler ch : connections){
                ch.shutdown();
            }
        }catch (IOException e){
            shutdown();
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
                System.out.println(name + " connect!");
                broadcast(name + " joined!");
                String message;
                while((message = in.readLine()) != null){
                    if(message.startsWith("/name")){
                        String[] messageSplit =message.split(" ", 2);
                        if(messageSplit.length == 2){
                            broadcast(name + " rename to " + messageSplit[1]);
                            System.out.println(name + " rename to " + messageSplit[1]);
                            name = messageSplit[1];
                            out.println("succesfully change name to " + name);
                        }
                        else{
                            System.out.println("No nickname provided");
                        }
                    }
                    else if (message.startsWith("/quit")){
                        broadcast(name + "left the chat");
                        shutdown();
                    } else {
                        broadcast(name +": "+ message);
                    }
                }
            } catch (IOException e) {
                shutdown();
            }
        }

        public void sendMessage(String message){
            out.println(message);
        }

        public void shutdown(){
            try {
                in.close();
                out.close();
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e){

            }
        }

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

}
