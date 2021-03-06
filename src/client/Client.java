package client;

import client.gui.ClientFrame;
import common.DateTimeUtil;
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

    private Connection connection;

    private ClientFrame frame;
    private UserInformation user;

    private ImageIcon defaultProfilePicture, defaultGroupPicture;
    private Map<Integer, ImageIcon> profilePictures = new HashMap<>();
    private Map<Integer, UserInformation> users = new HashMap<>();
    private Map<Integer, ChatInformation> chats = new HashMap<>();

    public Client(ClientFrame _frame) {
        frame = _frame;

        try {
            if (!Files.exists(Paths.get(ROOT))) {
                initializeFileHierarchy();
            } else {
                loadProfilePictures();
            }
            defaultProfilePicture = new ImageIcon(getClass().getResource("/client/gui/res/defaultProfilePicture.png"));
            defaultGroupPicture = new ImageIcon(getClass().getResource("/client/gui/res/defaultGroupPicture.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        connection = new Connection();
    }

    public UserInformation getUser() {
        return user;
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
        connection.establishConnection();
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

        connection.sendMessage(request);
        String response = connection.receiveMessage();

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

        setUserProfilePicture(user, info[3].equals("1"));

        for (int i = 4; i < info.length; i++) {
            getChatInfo(Integer.parseInt(info[i]));
        }

        return "OK";
    }

    public String signUp(String username, String password, String firstName, String lastName, String profilePicturePath) {
        int pictureLength = FileHelper.tryGetFileLengthInBytes(profilePicturePath);
        if (pictureLength >= 64000) {
            return "Image size is too big.";
        }

        // try to create a new account
        String request = RequestType.SIGNUP.name() + "#" + username + "#" + password + "#" + firstName + "#" + lastName + "#" + pictureLength;

        connection.sendMessage(request);
        String response = connection.receiveMessage();

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
            //request = "UPLOAD_IMAGE#" + profilePicturePath;
            //handler.sendMessage(request);
            connection.sendImage(profilePicturePath);

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

        return "OK";
    }

    private void setUserProfilePicture(UserInformation user, boolean hasPictureOnServer) {
        if (profilePictures.containsKey(user.getId())) {    // profile picture is cached
            user.setProfilePicture(profilePictures.get(user.getId()));
            return;
        } else if (hasPictureOnServer) {   // user has uploaded a profile picture to the server
            String request, response;

            // get the profile picture from the server
            String pathWithoutExtension = ROOT + "\\" + user.getId();

            request = RequestType.GET_PROFILE_PICTURE.name() + "#" + user.getId();
            connection.sendMessage(request);
            response = connection.receiveMessage();

            if (!response.startsWith("ERROR")) {
                String[] info = response.split("#");
                int length = Integer.parseInt(info[1]);
                String extension = info[2];
                String profilePicturePath = pathWithoutExtension + extension;

                connection.sendMessage("OK");
                response = connection.receiveImage(profilePicturePath, length);
            }

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
    }

    private ChatInformation getChatInfo(int chatId) {
        if (chats.containsKey(chatId)) {
            return chats.get(chatId);
        }

        String request, response;

        request = RequestType.GET_CHAT_INFO.name() + "#" + chatId;
        connection.sendMessage(request);
        response = connection.receiveMessage();

        if (response.startsWith("ERROR")) {
            return null;
        }

        String info[] = response.split("#");

        boolean personalChat = info[1].equals("0");
        String chatName = info[2];
        ImageIcon chatImage = defaultGroupPicture;

        Map<Integer, UserInformation> friends = new HashMap<>();

        for (int i = 3; i < info.length; i++) {
            int userId = Integer.parseInt(info[i]);
            if (userId == user.getId()) {
                continue;
            }

            UserInformation userInfo = getUserInfo(userId);
            friends.put(userId, userInfo);

            if (personalChat) {
                chatName = userInfo.getFirstName() + " " + userInfo.getLastName();
                chatImage = userInfo.getProfilePicture();
            }
        }

        ChatInformation chatInfo = new ChatInformation(chatId, chatName, this.user, friends, chatImage, frame);
        chats.put(chatId, chatInfo);

        frame.getHomeScreen().addChatInfo(chatInfo);

        return chatInfo;
    }

    private UserInformation getUserInfo(int userId) {
        if (users.containsKey(userId)) {
            return users.get(userId);
        }

        String request, response;

        request = RequestType.GET_USER_INFO.name() + "#" + userId;
        connection.sendMessage(request);
        response = connection.receiveMessage();

        if (response.startsWith("ERROR")) {
            return null;
        }

        String info[] = response.split("#");
        UserInformation userInfo = new UserInformation(
                Integer.parseInt(info[0]),
                info[1],
                info[2]
        );

        setUserProfilePicture(userInfo, info[3].equals("1"));

        users.put(userId, userInfo);
        return userInfo;
    }

    public void getChatMessages(int chatId) {
        ChatInformation chatInfo = chats.get(chatId);

        String request, response;

        request = RequestType.GET_CHAT_MESSAGES.name() + "#" + chatId;
        connection.sendMessage(request);
        response = connection.receiveMessage();

        if (response.startsWith("ERROR")) {
            return;
        }

        String messages = response;
        while (!messages.isEmpty()) {
            String info[] = messages.split("#", 5);

            int senderId = Integer.parseInt(info[0]);
            int date = Integer.parseInt(info[1]);
            String time = info[2];
            int length = Integer.parseInt(info[3]);
            String message = info[4].substring(0, length);

            messages = info[4].substring(length);

            chatInfo.addMessage(senderId, date, time, message);
        }
    }

    public void sendMessage(String message, int chatId) {
        String request, response;

        int date = DateTimeUtil.getDateNumber();
        String time = DateTimeUtil.getTimeStringHM();
        String serializedMessage = user.getId() + "#" + date + "#" + time + "#" + message.length() + "#" + message;

        request = RequestType.SEND_MESSAGE.name() + "#" + chatId + "#" + serializedMessage;
        connection.sendMessage(request);
//        response = handler.receiveMessage(requestId);
//
//        if (response.startsWith("ERROR")) {
//            return;
//        }

        //ChatInformation chatInfo = chats.get(chatId);
        //chatInfo.addMessage(user.getId(), date, time, message);
    }

    public void checkForUpdates() {
        String request, response;

        request = "POLL#";
        connection.sendMessage(request);
        response = connection.receiveMessage();

        while (!response.isEmpty()) {
            String parts[] = response.split("#", 2);

            int length = Integer.parseInt(parts[0]);
            request = parts[1].substring(0, length);
            response = parts[1].substring(length);

            parts = request.split("#", 2);
            String requestTypeName = parts[0];
            String requestContent = parts[1];

            RequestType requestType = RequestType.valueOf(requestTypeName);

            switch (requestType) {
                case NEW_MESSAGE:
                    receiveNewMessage(requestContent);
                    break;
                case NEW_CHAT:
                    throw new UnsupportedOperationException();
                    //break;
            }
        }
    }

    private void receiveNewMessage(String serializedMessage) {
        String info[] = serializedMessage.split("#", 6);

        int chatId = Integer.parseInt(info[0]);
        int senderId = Integer.parseInt(info[1]);
        int date = Integer.parseInt(info[2]);
        String time = info[3];
        int length = Integer.parseInt(info[4]);
        String message = info[5];

        ChatInformation chatInfo = chats.get(chatId);
        chatInfo.addMessage(senderId, date, time, message);
    }

}
