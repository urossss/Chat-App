package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server extends Thread {

    private final int PORT = 4444;

    public final String ROOT = System.getProperty("user.dir") + "\\Root";
    private final String LOGS = ROOT + "\\LOGS";
    private final String CURRENT_LOGS, SERVER_LOG;
    private final String USERS = ROOT + "\\Users", CHATS = ROOT + "\\Chats";
    private final String USERS_TXT = USERS + "\\users.txt";

    private Logger logger;

    private ServerSocket serverSocket;
    private Map<Integer, ServerRequestHandler> handlers = new HashMap<>();
    private List<UserInformation> userInfos = new ArrayList<>();

    public Server() {
        CURRENT_LOGS = LOGS + "\\" + getDateAndTime();
        SERVER_LOG = CURRENT_LOGS + "\\Server.log";

        if (!Files.isDirectory(Paths.get(ROOT))) {
            initializeFileHierarchy();
        }
        initializeLogDirectory();
        logger = new Logger(SERVER_LOG);

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
            Files.createDirectory(Paths.get(LOGS));
            Files.createDirectory(Paths.get(USERS));
            Files.createDirectory(Paths.get(CHATS));

            Files.createFile(Paths.get(USERS_TXT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getDateAndTime() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY), minute = cal.get(Calendar.MINUTE), second = cal.get(Calendar.SECOND);
        int year = cal.get(Calendar.YEAR), month = cal.get(Calendar.MONTH) + 1, day = cal.get(Calendar.DAY_OF_MONTH);
        return String.format("%d%02d%02d_%2d-%02d-%02d", year, month, day, hour, minute, second);
    }

    private void initializeLogDirectory() {
        try {
            Files.createDirectory(Paths.get(CURRENT_LOGS));
            Files.createFile(Paths.get(SERVER_LOG));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUserInfosFromTxt() {
        logger.log(String.format("Loading user information."), 0);

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

        logger.log(String.format("Number of users: %d", userInfos.size()), 0);
    }

    public synchronized UserInformation signIn(ServerRequestHandler handler, String username, String password) throws ServerException {
        logger.log(String.format("signIn handlerId=%d username=%s, password=%s", handler.getHandlerId(), username, password), 0);

        for (UserInformation user : userInfos) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    // TODO: prevent multiple logins from one user at the same time
                    handlers.put(user.getId(), handler);
                    handler.setUser(user);

                    logger.log(String.format("signIn handlerId=%d SUCCESS", handler.getHandlerId()), 0);
                    return user;
                } else {
                    break;
                }
            }
        }

        logger.log(String.format("signIn handlerId=%d ERROR=Username or password is incorrect.", handler.getHandlerId()), 0);
        throw new ServerException("Username or password is incorrect.");
    }

    public synchronized UserInformation signUp(ServerRequestHandler handler, UserInformation userInfo) throws ServerException {
        logger.log(String.format("signIn handlerId=%d username=%s, password=%s firstName=%s lastName=%s",
                handler.getHandlerId(), userInfo.getUsername(), userInfo.getPassword(), userInfo.getFirstName(), userInfo.getLastName()), 0);

        for (UserInformation user : userInfos) {
            if (user.getId() == userInfo.getId()) {
                logger.log(String.format("signIn handlerId=%d ERROR=User id already exists.", handler.getHandlerId()), 0);
                throw new ServerException("Internal server error: user id already exists.");
            }
            if (user.getUsername().equals(userInfo.getUsername())) {
                logger.log(String.format("signIn handlerId=%d ERROR=Username already exists.", handler.getHandlerId()), 0);
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

        logger.log(String.format("signIn handlerId=%d SUCCESS", handler.getHandlerId()), 0);
        return userInfo;
    }

    public synchronized UserInformation getUserInformation(int userId) {
        return userInfos.get(userId);
    }

    public synchronized void removeHandler(int userId, int handlerId) {
        handlers.remove(userId);
        logger.log(String.format("Request handler %d stopped.", handlerId), 0);
    }

    @Override
    public void run() {
        logger.log(String.format("Sever started."), 0);

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();

                logger.log(String.format("Request accepted: %s", clientSocket.getRemoteSocketAddress().toString()), 0);

                ServerRequestHandler handler = new ServerRequestHandler(this, clientSocket);

                String logPath = CURRENT_LOGS + "\\" + handler.getHandlerId() + ".log";
                Files.createFile(Paths.get(logPath));

                handler.setLogger(new Logger(logPath));
                handler.start();

                logger.log(String.format("Request handler %d started.", handler.getHandlerId()), 0);
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
