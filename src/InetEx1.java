import java.net.*;

public class InetEx1 {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        System.out.println("InetAddress of the Local Host : "+address);
        // To get and print host name of the Local Host
        String hostName=address.getHostName();

        System.out.println("\nHost name of the Local Host : "+hostName);
    }
}
