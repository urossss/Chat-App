package server;

import common.DateTimeUtil;
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

    private final int PORT = 4444;

    public final String ROOT = System.getProperty("user.dir") + "\\Root";
    private final String LOGS = ROOT + "\\Logs";
    private final String CURRENT_LOGS, SERVER_LOG;
    private final String USERS = ROOT + "\\Users";
    private final String USERS_TXT = USERS + "\\users.txt";
    private final String CHATS = ROOT + "\\Chats";

    private Logger logger;

    private ServerSocket serverSocket;
    private final Map<Integer, ServerRequestHandler> handlers = new HashMap<>();
    private final Map<Integer, ChatInformation> chatInfos = new HashMap<>();
    private final Map<Integer, UserInformation> userInfos = new HashMap<>();

    public Server() {
        CURRENT_LOGS = LOGS + "\\" + DateTimeUtil.getDateAndTimeString();
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
                String rootPath = USERS + "\\" + userInfo.getId();
                userInfo.setRootFolderPath(rootPath);

                String infoFilePath = rootPath + "\\profilePictureName.txt";
                if (Files.exists(Paths.get(infoFilePath))) {
                    String profilePicturePath = Files.readAllLines(Paths.get(infoFilePath)).get(0);
                    userInfo.setProfilePicturePath(profilePicturePath);
                }

                List<String> chatIds = Files.readAllLines(Paths.get(rootPath + "\\chats.txt"));
                for (String chatId : chatIds) {
                    userInfo.addChatId(Integer.parseInt(chatId));
                }

                userInfos.put(userInfo.getId(), userInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.log(String.format("Number of users: %d", userInfos.size()), 0);
    }

    public UserInformation signIn(ServerRequestHandler handler, String username, String password) throws ServerException {
        synchronized (userInfos) {
            logger.log(String.format("signIn handlerId=%d username=%s, password=%s", handler.getHandlerId(), username, password), 0);

            for (UserInformation user : userInfos.values()) {
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
    }

    public UserInformation signUp(ServerRequestHandler handler, UserInformation userInfo) throws ServerException {
        synchronized (userInfos) {
            logger.log(String.format("signIn handlerId=%d username=%s, password=%s firstName=%s lastName=%s",
                    handler.getHandlerId(), userInfo.getUsername(), userInfo.getPassword(), userInfo.getFirstName(), userInfo.getLastName()), 0);

            for (UserInformation user : userInfos.values()) {
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
                userInfo.setRootFolderPath(rootFolder);

                Files.createDirectory(Paths.get(rootFolder));
                Files.createFile(Paths.get(rootFolder + "\\chats.txt"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            userInfos.put(userInfo.getId(), userInfo);

            logger.log(String.format("signIn handlerId=%d SUCCESS", handler.getHandlerId()), 0);
            return userInfo;
        }
    }

    public UserInformation getUserInformation(int userId) {
        synchronized (userInfos) {
            return userInfos.get(userId);
        }
    }

    public ChatInformation getChatInformation(int chatId) {
        synchronized (chatInfos) {
            logger.log(String.format("getChatInformation chatId=%d", chatId), 0);

            if (chatInfos.containsKey(chatId)) {
                return chatInfos.get(chatId);
            }

            String chatPath = CHATS + "\\" + chatId;
            String usersPath = chatPath + "_info.txt";
            String messagesPath = chatPath + "_messages.txt";

            if (!Files.exists(Paths.get(usersPath)) || !Files.exists(Paths.get(messagesPath))) {
                return null;
            }

            List<Integer> userIds = new ArrayList<>();
            String chatName = "";
            try {
                List<String> userIdsStr = Files.readAllLines(Paths.get(usersPath));

                if (userIdsStr.get(0).startsWith("Group=")) {
                    chatName = userIdsStr.get(0).substring(6);
                }

                for (int i = 1; i < userIdsStr.size(); i++) {
                    userIds.add(Integer.parseInt(userIdsStr.get(i)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            ChatInformation chatInfo = new ChatInformation(chatId, chatName, userIds);
            chatInfos.put(chatId, chatInfo);
            return chatInfo;
        }
    }

    public List<String> getChatMessages(int chatId) throws ServerException {
        logger.log(String.format("getChatMessages chatId=%d", chatId), 0);

        if (!chatInfos.containsKey(chatId)) {
            logger.log(String.format("getChatMessages ERROR=Invalid chat id"), 0);
            throw new ServerException("Invalid chat id.");
        }

        String chatPath = CHATS + "\\" + chatId;
        String messagesPath = chatPath + "_messages.txt";

        try {
            List<String> messages = Files.readAllLines(Paths.get(messagesPath));

            logger.log(String.format("getChatMessages SUCCESS messageCount=%d", messages.size()), 0);

            return messages;
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.log(String.format("getChatMessages ERROR=Couldn't read messages."), 0);
        throw new ServerException("Internal server error: couldn't read messages.");
    }

    public void removeHandler(int userId, int handlerId) {
        synchronized (handlers) {
            handlers.remove(userId);
        }
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
