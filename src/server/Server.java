package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server extends Thread {

    private final int PORT = 4000;

    public final String ROOT = System.getProperty("user.dir") + "\\Root";
    private final String USERS = ROOT + "\\Users", CHATS = ROOT + "\\Chats";
    private final String USERS_TXT = USERS + "\\users.txt";
    public final String DEFAULT_PROFILE_PICTURE = USERS + "\\defaultProfilePicture.png";

    private ServerSocket serverSocket;
    private Map<Integer, ServerRequestHandler> handlers = new HashMap<>();
    private List<UserInformation> userInfos = new ArrayList<>();

    public Server() {
        if (!Files.isDirectory(Paths.get(ROOT))) {
            initializeFileHierarchy();
        }
        loadUserInfosFromTxt();

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeFileHierarchy() {
        try {
            Files.createDirectory(Paths.get(ROOT));
            Files.createDirectory(Paths.get(USERS));
            Files.createDirectory(Paths.get(CHATS));

            Files.createFile(Paths.get(USERS_TXT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUserInfosFromTxt() {
        try {
            List<String> users = Files.readAllLines(Paths.get(USERS_TXT));
            for (String user : users) {
                String[] info = user.split("#");
                UserInformation userInfo = new UserInformation(Integer.parseInt(info[0]), info[1], info[2], info[3], info[4]);
                userInfo.setRootFolderPath(USERS + "\\" + userInfo.getId());
                
                String infoFilePath = userInfo.getRootFolderPath() + "\\profilePictureName.txt";
                if (Files.exists(Paths.get(infoFilePath))) {
                    String profilePicturePath = Files.readAllLines(Paths.get(infoFilePath)).get(0);
                    userInfo.setProfilePicturePath(profilePicturePath);
                }
                
                userInfos.add(userInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized UserInformation signIn(ServerRequestHandler handler, String username, String password) throws ServerException {
        for (UserInformation user : userInfos) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    // TODO: prevent multiple logins from one user at the same time
                    handlers.put(user.getId(), handler);
                    handler.setUser(user);
                    return user;
                } else {
                    break;
                }
            }
        }
        throw new ServerException("Username or password is incorrect.");
    }

    public synchronized UserInformation signUp(ServerRequestHandler handler, UserInformation userInfo) throws ServerException {
        for (UserInformation user : userInfos) {
            if (user.getId() == userInfo.getId()) {
                throw new ServerException("Internal server error: user id already exists.");
            }
            if (user.getUsername().equals(userInfo.getUsername())) {
                throw new ServerException("Username already exists.");
            }
        }

        userInfo.setId(userInfos.size() + 1);
        handlers.put(userInfo.getId(), handler);
        handler.setUser(userInfo);

        try {
            Files.write(Paths.get(USERS_TXT), (userInfo.serialize() + "\n").getBytes(), StandardOpenOption.APPEND);
            String rootFolder = USERS + "\\" + userInfo.getId();
            Files.createDirectory(Paths.get(rootFolder));
            userInfo.setRootFolderPath(rootFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        userInfos.add(userInfo);
        return userInfo;
    }
    
    public synchronized UserInformation getUserInformation(int userId) {
        return userInfos.get(userId);
    }

    @Override
    public void run() {
        System.out.println("Server started...");

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Request accepted: " + clientSocket.getRemoteSocketAddress().toString());
                new ServerRequestHandler(this, clientSocket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //new Server().start();
        Server server = new Server();
        server.start();
    }
}
