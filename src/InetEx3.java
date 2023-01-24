import java.net.MalformedURLException;
import java.net.URL;

public class InetEx3 {
    public static void main(String[] args) throws MalformedURLException {

        URL url = new URL(
                "https://write.penghai.net/#experience");

        // to get and print the protocol of the URL
        String protocol = url.getProtocol();

        System.out.println("Protocol : " + protocol);

        // to get and print the hostName of the URL
        String host = url.getHost();

        System.out.println("HostName : " + host);

        // to get and print the file name of the URL
        String fileName = url.getFile();

        System.out.println("File Name : " + fileName);
    }
}
