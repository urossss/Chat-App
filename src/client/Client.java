package client;

import common.FileHelper;
import common.RequestType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;

public class Client {

    private final String ROOT = System.getProperty("user.dir") + "\\tmp";
    private final String PICTURES_INFO = ROOT + "\\profilePictureInformation.txt";

    private ClientRequestHandler handler;

    private UserInformation user;

    public ImageIcon defaultProfilePicture;
    private Map<Integer, ImageIcon> profilePictures = new HashMap<>();
    private Map<Integer, UserInformation> users = new HashMap<>();

    public Client() {
        try {
            if (!Files.exists(Paths.get(ROOT))) {
                initializeFileHierarchy();
            } else {
                loadProfilePictures();
                defaultProfilePicture = new ImageIcon(getClass().getResource("/client/gui/res/default.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        handler = new ClientRequestHandler();
    }
    
    public UserInformation getUser() {
        return user;
    }

    public ImageIcon getProfilePicture() {
        return user.getProfilePicture();
    }

    private void initializeFileHierarchy() throws IOException {
        Files.createDirectory(Paths.get(ROOT));
        Files.createFile(Paths.get(PICTURES_INFO));
    }

    private void loadProfilePictures() throws IOException {
        List<String> users = Files.readAllLines(Paths.get(PICTURES_INFO));
        for (String user : users) {
            String[] info = user.split("#");
            int userId = Integer.parseInt(info[0]);
            String profilePicturePath = info[1];

            ImageIcon profilePicture = new ImageIcon(profilePicturePath);
            profilePictures.put(userId, profilePicture);
        }
    }

    public void establishConnection() throws IOException {
        handler.establishConnection();
        handler.start();
    }

    private void saveProfilePictureInformation(int id, String path) {
        try {
            Files.write(Paths.get(PICTURES_INFO), (id + "#" + path + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String signIn(String username, String password) {
        // send username and password
        String request = RequestType.SIGNIN.name() + "#" + username + "#" + password;

        int requestId = handler.addRequest(request);
        String response = handler.getResponse(requestId);

        // server returns error if username or password is incorrect
        if (response.startsWith("ERROR")) {
            return response.substring(5);
        }

        // otherwise, it returns basic information about user: user id, first name, last name and profile picture status
        String info[] = response.split("#");
        user = new UserInformation(
                Integer.parseInt(info[0]),
                info[1],
                info[2]
        );
        users.put(user.getId(), user);

        if (profilePictures.containsKey(user.getId())) {    // profile picture is cached
            user.setProfilePicture(profilePictures.get(user.getId()));
        } else if (info[3].equals("1")) {   // user has uploaded a profile picture to the server
            // get the profile picture from the server
            String pathWithoutExtension = ROOT + "\\" + user.getId();
            request = "GET_PROFILE_PICTURE#" + user.getId() + "#" + pathWithoutExtension;

            requestId = handler.addRequest(request);
            response = handler.getResponse(requestId);

            if (response.startsWith("ERROR")) { // if any error occurs, just use the default profile picture
                user.setProfilePicture(defaultProfilePicture);
            } else {    // otherwise create new profile picture
                user.setProfilePicture(new ImageIcon(response));
            }

            // save information about a picture - it is already cached in ROOT folder, just save its path
            saveProfilePictureInformation(user.getId(), response);

        } else {    // no profile picture uploaded, use the default profile picture
            user.setProfilePicture(defaultProfilePicture);
        }
        profilePictures.put(user.getId(), user.getProfilePicture());

        return "OK";
    }

    public String signUp(String username, String password, String firstName, String lastName, String profilePicturePath) {
        int pictureLength = FileHelper.tryGetFileLengthInBytes(profilePicturePath);
        if (pictureLength >= 64000) {
            return "Image size is too big.";
        }

        // try to create a new account
        String request = "SIGNUP#" + username + "#" + password + "#" + firstName + "#" + lastName + "#" + pictureLength;

        int requestId = handler.addRequest(request);
        String response = handler.getResponse(requestId);

        // server returns error if username already exists
        if (response.startsWith("ERROR")) {
            return response.substring(5);
        }

        // otherwise, it returns basic information about user: user id, first name, last name and profile picture status
        String info[] = response.split("#");
        user = new UserInformation(
                Integer.parseInt(info[0]),
                info[1],
                info[2]
        );
        users.put(user.getId(), user);

        if (pictureLength > 0 && info[3].equals("2")) { // user wants to upload a picture and server is expecting it
            System.out.println("Uploading the picture...");

            // upload profile picture to the server
            request = "UPLOAD_IMAGE#" + profilePicturePath;
            handler.addRequest(request);

            System.out.println("Uploaded the picture.");

            // cache profile picture in tmp folder for the next sign in
            String tmpPath = ROOT + "\\" + user.getId() + FileHelper.getFileExtension(profilePicturePath);
            try {
                FileHelper.cropImage(profilePicturePath, tmpPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            saveProfilePictureInformation(user.getId(), tmpPath);

            user.setProfilePicture(new ImageIcon(tmpPath));
            System.out.println("Copied the picture.");
        } else {
            user.setProfilePicture(defaultProfilePicture);
        }
        profilePictures.put(user.getId(), user.getProfilePicture());

        System.out.println("About to read data");

        try {
            readUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "OK";
    }

    private void readUserData() throws IOException {
//        System.out.println("Waiting for data...");
//
//        String basicInfo = in.readLine();
//        System.out.println("Read: " + basicInfo);
//        String info[] = basicInfo.split("#");
//        int id = Integer.parseInt(info[1]);
//        String firstName = info[2], lastName = info[3];
//
//        out.println("true");
//
//        String imageInfo = in.readLine();
//        info = imageInfo.split("#");
//        int length = Integer.parseInt(info[1]);
//        String extension = info[2];
//        String path = "C:\\Users\\uross\\Desktop\\" + id + extension;
//
//        System.out.println("Read: " + imageInfo);
//
//        if (length > 0) {
//            System.out.println("Receiving profile picture...");
//            FileHelper.receiveFile(socket, in, out, path, length);
//        }
    }
}
