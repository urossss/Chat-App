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

public class ServerRequestHandler extends Thread {

    private static int ID = 1;

    private int handlerId = ID++;
    private Logger logger;

    private Server server;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private UserInformation user;

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

    private void sendBasicData(int userId, boolean shouldSendProfilePicture) {
        logger.log(String.format("sendBasicData: info userId=%d sendPicture=%b", userId, shouldSendProfilePicture), handlerId);

        UserInformation user = this.user;
        if (userId != this.user.getId()) {
            user = server.getUserInformation(userId);
        }

        String profilePictureResponse = "0";    // user doesn't have a profile picture
        if (shouldSendProfilePicture) {
            profilePictureResponse = "2";       // user should upload profile picture now
        } else if (user.hasProfilePicture()) {
            profilePictureResponse = "1";       // user has a profile picture
        }
        String basicInfo = user.getId() + "#" + user.getFirstName() + "#" + user.getLastName() + "#" + profilePictureResponse;
        out.println(basicInfo);
        out.flush();

        logger.log(String.format("sendBasicData: sent userId=%d firstName=%s lastName=%s pictureResponse=%s",
                user.getId(), user.getFirstName(), user.getLastName(), profilePictureResponse), handlerId);
    }

    private void sendBasicData(int userId) {
        sendBasicData(userId, false);
    }

    private void sendBasicData() {
        sendBasicData(user.getId(), false);
    }

    public void closeSocket() {
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
                            sendBasicData();
                        }
                        break;
                    case SIGNUP:
                        response = signUp(requestContent);
                        if (response.startsWith("ERROR")) {
                            out.println(response);
                            out.flush();
                        } else {
                            sendBasicData(user.getId(), response.equals("IMAGE"));
                        }
                        break;
                    case UPLOAD_IMAGE:
                        receiveProfilePicture(requestContent);
                        break;
                    case GET_BASIC_INFO:
                        userId = Integer.parseInt(requestContent);
                        sendBasicData(userId);
                        break;
                    case GET_PROFILE_PICTURE:
                        userId = Integer.parseInt(requestContent);
                        sendProfilePicture(userId);
                        break;
                    default:
                        logger.log(String.format("Unknown operation: %s", request), handlerId);
                        throw new SocketException();
                }
            } catch (SocketException e) {
                if (user != null) {
                    server.removeHandler(user.getId(), handlerId);
                }
                closeSocket();

                logger.log("Connection reset.", handlerId);
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
