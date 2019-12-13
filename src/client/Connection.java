package client;

import common.FileHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {

    private static final String SERVER_IP = "94.189.225.68";
    //private static final String SERVER_IP = "192.168.0.30";
    private static final int SERVER_PORT = 4444;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public void establishConnection() throws IOException {
        socket = new Socket(SERVER_IP, SERVER_PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void closeConnection() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String request) {
        out.println(request);
    }

    public void sendImage(String path) {
        try {
            FileHelper.sendFile(socket, in, out, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveMessage() {
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    public String receiveImage(String path, int length) {
        try {
            FileHelper.receiveFile(socket, in, out, path, length);
            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

}
