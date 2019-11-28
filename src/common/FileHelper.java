package common;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
import javax.imageio.ImageIO;

public class FileHelper {

    public static void sendFile(Socket socket, BufferedReader in, PrintWriter out, String path) throws IOException {
        OutputStream os = socket.getOutputStream();
        FileInputStream fis = new FileInputStream(path);

        String extension = getFileExtension(path);
        int length = tryGetFileLengthInBytes(path);

        String request = "UPLOAD_IMAGE#" + length + "#" + extension;
        out.println(request);
        out.flush();
        in.readLine();

        byte bytes[] = new byte[length];
        fis.read(bytes, 0, length);
        os.write(bytes, 0, length);
        os.flush();
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

    /**
     * Crops original image so that only central square part is left. The width
     * and height of the output image are equal to minimum of width and height
     * of original image. The smaller dimension stays the same, but from the
     * bigger one only central part is saved.
     *
     * @param sourcePath - original image path
     * @param targetPath - cropped image desired path
     *
     * @throws IOException
     */
    public static void cropImage(String sourcePath, String targetPath) throws IOException {
        // load original image
        BufferedImage originalImage = ImageIO.read(new File(sourcePath));

        // calculate new dimensions
        int size, top, left;
        int width = originalImage.getWidth(), height = originalImage.getHeight();
        if (width < height) {
            size = width;
            left = 0;
            top = (height - width) / 2;
        } else {
            size = height;
            top = 0;
            left = (width - height) / 2;
        }

        // create output image
        BufferedImage croppedImage = new BufferedImage(size, size, originalImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = croppedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, size, size, left, top, left + size, top + size, null);
        g2d.dispose();

        // extract extension of output file (without a dot)
        String extension = getFileExtension(targetPath).substring(1);

        // write to output file
        ImageIO.write(croppedImage, extension, new File(targetPath));
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
