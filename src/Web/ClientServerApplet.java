import java.applet.Applet;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientServerApplet extends Applet {
    private static final long serialVersionUID = 1L;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private JButton sendButton;
    private JTextField messageField;
    private JTextArea messageArea;

    public void init() {
        try {
            socket = new Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            sendButton = new JButton("Send");
            messageField = new JTextField(20);
            messageArea = new JTextArea(10, 30);
            JScrollPane scrollPane = new JScrollPane(messageArea);

            JPanel messagePanel = new JPanel();
            messagePanel.add(messageField);
            messagePanel.add(sendButton);

            setLayout(new BorderLayout());
            add(scrollPane, BorderLayout.CENTER);
            add(messagePanel, BorderLayout.SOUTH);

            sendButton.addActionListener(e -> sendMessage());
            messageField.addActionListener(e -> sendMessage());

            Thread receiveThread = new Thread(this::receiveMessages);
            receiveThread.start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error connecting to server: " + e.getMessage());
        }
    }

    private void sendMessage() {
        String message = messageField.getText();
        if (message.trim().length() > 0) {
            out.println(message);
            messageField.setText("");
        }
    }

    private void receiveMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                messageArea.append("Received message: " + message + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error receiving message: " + e.getMessage());
        }
    }

    public void stop() {
        try {
            socket.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error closing socket: " + e.getMessage());
        }
    }
}


