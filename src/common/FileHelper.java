package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper {

    public static void sendFile(Socket socket, BufferedReader in, PrintWriter out, String path) throws IOException {
        OutputStream os = socket.getOutputStream();
        FileInputStream fis = new FileInputStream(path);

        String extension = getFileExtension(path);
        int length = tryGetFileLengthInBytes(path);

        String request = "UPLOAD_IMAGE#" + length + "#" + extension;
        out.println(request);
        in.readLine();

        byte bytes[] = new byte[length];
        fis.read(bytes, 0, length);
        os.write(bytes, 0, length);
        System.out.println("Sent");
        fis.close();
    }

    public static void receiveFile(Socket socket, BufferedReader in, PrintWriter out, String path, int length) throws IOException {
        InputStream is = socket.getInputStream();
        FileOutputStream fos = new FileOutputStream(path);
        byte bytes[] = new byte[length];
        is.read(bytes, 0, length);
        System.out.println("Received");
        fos.write(bytes, 0, length);
        System.out.println("Done");
        fos.close();
    }

    public static void copyFile(String sourcePath, String targetPath) {
        try {
            Files.copy(Paths.get(sourcePath), Paths.get(targetPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getFileLengthInBytes(String path) {
        return (int) new File(path).length();
    }

    public static int tryGetFileLengthInBytes(String path) {
        if (path == null || path.isEmpty()) {
            return -1;
        }
        if (!Files.exists(Paths.get(path))) {
            return -1;
        }
        return getFileLengthInBytes(path);
    }

    public static String getFileExtension(String path) {
        int lastDot = path.lastIndexOf('.');
        return path.substring(lastDot);
    }

}
