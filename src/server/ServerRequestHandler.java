package server;

import common.RequestType;
import common.FileHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ServerRequestHandler extends Thread {

    private static int ID = 1;

    private int handlerId = ID++;
    private Logger logger;

    private Server server;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private UserInformation user;

    private final List<String> pendingRequests = new ArrayList<>();

    public ServerRequestHandler(Server _server, Socket _socket) {
        server = _server;
        socket = _socket;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setUser(UserInformation user) {
        this.user = user;
    }

    public int getHandlerId() {
        return handlerId;
    }

    public void newMessageRequest(int chatId, String serializedMessage) {
        logger.log(String.format("newMessageRequest chatId=%d serializedMessage=%s", chatId, serializedMessage), handlerId);

        String request;
        if (user.getChatIds().contains(chatId)) {
            request = RequestType.NEW_MESSAGE.name() + "#" + chatId + "#" + serializedMessage;

            logger.log("newMessageRequest requestType=NEW_MESSAGE", handlerId);
        } else {
            request = RequestType.NEW_CHAT.name() + "#" + chatId;

            logger.log("newMessageRequest requestType=NEW_CHAT", handlerId);
        }

        synchronized (pendingRequests) {
            pendingRequests.add(request);
        }
    }

    private String signIn(String request) {
        String[] info = request.split("#");
        String username = info[0], password = info[1];

        logger.log(String.format("signIn username=%s password=%s", username, password), handlerId);

        try {
            server.signIn(this, username, password);
        } catch (ServerException e) {
            logger.log(String.format("signIn ERROR=%s", e.getMessage()), handlerId);
            return "ERROR" + e.getMessage();
        }
        return "OK";
    }

    private String signUp(String request) {
        String[] info = request.split("#");
        String username = info[0], password = info[1], firstName = info[2], lastName = info[3];

        logger.log(String.format("signIn username=%s password=%s firstName=%s lastName=%s", username, password, firstName, lastName), handlerId);

        int pictureLength = Integer.parseInt(info[4]);
        try {
            server.signUp(this, new UserInformation(username, password, firstName, lastName));
        } catch (ServerException e) {
            logger.log(String.format("signUp ERROR=%s", e.getMessage()), handlerId);
            return "ERROR" + e.getMessage();
        }
        return pictureLength > 0 ? "IMAGE" : "OK";
    }

    private void receiveProfilePicture(String request) throws IOException {
        String[] info = request.split("#");
        int length = Integer.parseInt(info[0]);
        String extension = info[1];

        logger.log(String.format("receiveProfilePicture: info length=%d extension=%s", length, extension), handlerId);

        String profilePictureTmpPath = user.getRootFolderPath() + "\\tmp" + user.getId() + extension;
        String profilePicturePath = user.getRootFolderPath() + "\\" + user.getId() + extension;

        out.println("OK");
        out.flush();
        FileHelper.receiveFile(socket, in, out, profilePictureTmpPath, length);

        logger.log("receiveProfilePicture: picture received", handlerId);

        FileHelper.cropImage(profilePictureTmpPath, profilePicturePath);
        Files.deleteIfExists(Paths.get(profilePictureTmpPath));

        logger.log("receiveProfilePicture: picture cropped", handlerId);

        String infoFilePath = user.getRootFolderPath() + "\\profilePictureName.txt";
        if (!Files.exists(Paths.get(infoFilePath))) {
            Files.createFile(Paths.get(infoFilePath));
        }
        PrintWriter pw = new PrintWriter(infoFilePath);
        pw.close();
        Files.write(Paths.get(infoFilePath), profilePicturePath.getBytes());

        user.setProfilePicturePath(profilePicturePath);

        logger.log("receiveProfilePicture: picture saved", handlerId);
    }

    private void sendProfilePicture(int userId) {
        logger.log(String.format("sendProfilePicture: info userId=%d", userId), handlerId);

        UserInformation user = this.user;
        if (userId != this.user.getId()) {
            user = server.getUserInformation(userId);
        }

        try {
            String userPath = user.getProfilePicturePath();
            if (userPath != null) {
                FileHelper.sendFile(socket, in, out, userPath);

                logger.log("sendProfilePicture: pricture sent", handlerId);
            } else {
                logger.log("sendProfilePicture: pricture not found", handlerId);

                out.println("ERROR");
                out.flush();
            }
        } catch (IOException e) {
            logger.log(String.format("sendProfilePicture: IOException=%s", e.getMessage()), handlerId);
            e.printStackTrace();
        }
    }

    private void sendUserInfo(int userId, boolean shouldSendProfilePicture) {
        logger.log(String.format("sendUserInfo: info userId=%d sendPicture=%b", userId, shouldSendProfilePicture), handlerId);

        String serializedChatIds = "";

        UserInformation user = this.user;
        if (userId == this.user.getId()) {
            serializedChatIds = user.getSerializedChatIds();
        } else {
            user = server.getUserInformation(userId);
        }

        String profilePictureResponse = "0";    // user doesn't have a profile picture
        if (shouldSendProfilePicture) {
            profilePictureResponse = "2";       // user should upload profile picture now
        } else if (user.hasProfilePicture()) {
            profilePictureResponse = "1";       // user has a profile picture
        }
        String basicInfo = user.getId() + "#" + user.getFirstName() + "#" + user.getLastName() + "#" + profilePictureResponse + serializedChatIds;
        out.println(basicInfo);
        out.flush();

        logger.log(String.format("sendUserInfo: sent userId=%d firstName=%s lastName=%s pictureResponse=%s",
                user.getId(), user.getFirstName(), user.getLastName(), profilePictureResponse), handlerId);
    }

    private void sendUserInfo(int userId) {
        sendUserInfo(userId, false);
    }

    private void sendUserInfo() {
        sendUserInfo(user.getId(), false);
    }

    private void sendChatInfo(int chatId) {
        logger.log(String.format("sendChatInfo chatId=%d", chatId), handlerId);

        ChatInformation chatInfo = server.getChatInformation(chatId);
        if (chatInfo == null) {
            logger.log("sendChatInfo ERROR", handlerId);
            out.println("ERROR");
            return;
        }

        String toSend = chatInfo.serialize();
        out.println(toSend);

        if (!user.getChatIds().contains(chatId)) {
            user.getChatIds().add(chatId);
        }

        logger.log(String.format("sendChatInfo: sent %s", toSend), handlerId);
    }

    private void sendChatMessages(int chatId) {
        logger.log(String.format("sendChatMessages chatId=%d", chatId), handlerId);

        try {
            List<String> messages = server.getChatMessages(chatId);

            StringBuilder sb = new StringBuilder();
            for (String message : messages) {
                sb.append(message);
            }

            out.println(sb.toString());
            logger.log(String.format("sendChatMessages sent messageCount=%d", messages.size()), handlerId);
        } catch (ServerException e) {
            out.println("ERROR");
            logger.log("sendChatMessages ERROR", handlerId);
        }
    }

    private void receiveNewMessage(String request) {
        String info[] = request.split("#", 2);

        int chatId = Integer.parseInt(info[0]);
        String serializedMessage = info[1];

        logger.log(String.format("receiveNewMessage chatId=%d serializedMessage=%s", chatId, serializedMessage), handlerId);

        server.processNewMessage(user.getId(), chatId, serializedMessage);
        //out.println("OK");

        logger.log("receiveNewMessage SUCCESS", handlerId);
    }

    private void sendPendingRequests() {
        StringBuilder sb = new StringBuilder();
        synchronized (pendingRequests) {
            if (pendingRequests.isEmpty()) {
                out.println("");
                return;
            }

            logger.log(String.format("sendPendingRequests count=%d", pendingRequests.size()), handlerId);

            for (String request : pendingRequests) {
                sb.append(String.format("%d#%s", request.length(), request));
            }

            pendingRequests.clear();
        }

        String response = sb.toString();
        out.println(response);

        logger.log(String.format("sendPendingRequests sent response=%s", response), handlerId);
    }

    private void closeSocket() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        logger.log("Request handler " + handlerId + " started.", handlerId);

        while (!socket.isInputShutdown()) {
            try {
                String request = in.readLine(), response;
                if (request == null) {
                    break;
                }

                String[] requestParts = request.split("#", 2);
                String requestTypeName = requestParts[0];
                String requestContent = requestParts[1];

                RequestType requestType = RequestType.valueOf(requestTypeName);
                int userId;

                switch (requestType) {
                    case SIGNIN:
                        response = signIn(requestContent);
                        if (response.startsWith("ERROR")) {
                            out.println(response);
                            out.flush();
                        } else {
                            sendUserInfo();
                        }
                        break;
                    case SIGNUP:
                        response = signUp(requestContent);
                        if (response.startsWith("ERROR")) {
                            out.println(response);
                            out.flush();
                        } else {
                            sendUserInfo(user.getId(), response.equals("IMAGE"));
                        }
                        break;
                    case UPLOAD_IMAGE:
                        receiveProfilePicture(requestContent);
                        break;
                    case GET_USER_INFO:
                        userId = Integer.parseInt(requestContent);
                        sendUserInfo(userId);
                        break;
                    case GET_PROFILE_PICTURE:
                        userId = Integer.parseInt(requestContent);
                        sendProfilePicture(userId);
                        break;
                    case GET_CHAT_INFO:
                        sendChatInfo(Integer.parseInt(requestContent));
                        break;
                    case GET_CHAT_MESSAGES:
                        sendChatMessages(Integer.parseInt(requestContent));
                        break;
                    case SEND_MESSAGE:
                        receiveNewMessage(requestContent);
                        break;
                    case POLL:
                        sendPendingRequests();
                        break;
                    default:
                        logger.log(String.format("Unknown operation: %s", request), handlerId);
                        throw new SocketException("Unknown operation");
                }
            } catch (SocketException e) {
                if (user != null) {
                    server.removeHandler(user.getId(), handlerId);
                }
                closeSocket();

                logger.log(e.getMessage(), handlerId);
                System.err.println(e.getMessage());
                break;
            } catch (Exception e) {
                logger.log(String.format("Unknown exception: %s", e.getMessage()), handlerId);
                e.printStackTrace();
            }
        }

        logger.log("Request handler " + handlerId + " stopped.", handlerId);
    }
}
