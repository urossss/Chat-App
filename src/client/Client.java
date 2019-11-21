package client;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;

public class Client extends Thread {

    private static final String SERVER_IP = "94.189.225.68";
    private static final int SERVER_PORT = 4000;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Client() {
    }

    public void establishConnection() throws IOException {
        socket = new Socket(SERVER_IP, SERVER_PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public String signIn(String username, String password) {
        String request = "SIGNIN#" + username + "#" + password;
        out.println(request);

        String response = "";
        try {
            response = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String signUp(String username, String password, String firstName, String lastName) {
        String request = "SIGNUP#" + username + "#" + password + "#" + firstName + "#" + lastName;
        out.println(request);

        String response = "";
        try {
            response = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void sendImage() throws IOException {
        /*OutputStream outputStream = socket.getOutputStream();
        BufferedImage image = ImageIO.read(new File("C:\\Users\\uross\\Desktop\\test.jpg"));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        
        int size = byteArrayOutputStream.size();
        out.println(String.valueOf(size));
        System.out.println(size);
        
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.flush();
        System.out.println("Flushed: " + System.currentTimeMillis());*/

        OutputStream os = socket.getOutputStream();
        FileInputStream fis = new FileInputStream("C:\\Users\\uross\\Desktop\\test.jpg");
        int length = (int) new File("C:\\Users\\uross\\Desktop\\test.jpg").length();
        System.out.println("Length = " + length);

        String request = "IMAGE#" + length;
        out.println(request);
        String response = in.readLine();

        byte bytes[] = new byte[length];
        fis.read(bytes, 0, length);
        os.write(bytes, 0, length);
        System.out.println("Sent");
        fis.close();
    }

    @Override
    public void run() {
        System.out.println("Client started.");
        try {
            out.println("LOGIN#perica#pericacar");

            System.out.println("waiting");
            String message = in.readLine();
            System.out.println("done: " + message);

            socket.close();

            /*while (true) {
                sleep((int) (Math.random() * 2000));

                String request = Math.random() < 0.9 ? "next" : "stop";
                out.println(request);
                System.out.println("Request: " + request);
                System.out.println("Waiting for response...");

                String message = in.readLine();
                if (message.equals("stop")) {
                    break;
                }
                System.out.println("Response: " + message);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Client stopped.");
    }

    public static void main(String[] args) {
        new Client().start();
    }
}
