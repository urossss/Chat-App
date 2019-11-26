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

    public void setUser(UserInformation user) {
        this.user = user;
    }

    private String signIn(String request) {
        System.out.println("Sign in called: " + request);
        String[] info = request.split("#");
        String username = info[0], password = info[1];
        try {
            server.signIn(this, username, password);
        } catch (ServerException e) {
            return "ERROR" + e.getMessage();
        }
        return "OK";
    }

    private String signUp(String request) {
        System.out.println("Sign up called: " + request);
        String[] info = request.split("#");
        String username = info[0], password = info[1], firstName = info[2], lastName = info[3];
        int pictureLength = Integer.parseInt(info[4]);
        try {
            server.signUp(this, new UserInformation(username, password, firstName, lastName));
        } catch (ServerException e) {
            return "ERROR" + e.getMessage();
        }
        return pictureLength > 0 ? "IMAGE" : "OK";
    }

    private void receiveProfilePicture(String request) throws IOException {
        System.out.println("Receive profile picture called: " + request);

        String[] info = request.split("#");
        int length = Integer.parseInt(info[0]);
        String extension = info[1];
        String profilePicturePath = user.getRootFolderPath() + "\\" + user.getId() + extension;

        out.println("OK");
        FileHelper.receiveFile(socket, in, out, profilePicturePath, length);

        String infoFilePath = user.getRootFolderPath() + "\\profilePictureName.txt";
        if (!Files.exists(Paths.get(infoFilePath))) {
            Files.createFile(Paths.get(infoFilePath));
        }
        PrintWriter pw = new PrintWriter(infoFilePath);
        pw.close();
        Files.write(Paths.get(infoFilePath), profilePicturePath.getBytes());

        System.out.println("Profile picture saved.");
        user.setProfilePicturePath(profilePicturePath);
    }

    private void sendProfilePicture(int userId) {
        UserInformation user = this.user;
        if (userId != this.user.getId()) {
            user = server.getUserInformation(userId);
        }

        try {
            String userPath = user.getProfilePicturePath();
            if (userPath != null) {
                FileHelper.sendFile(socket, in, out, userPath);
            } else {
                System.out.println("No profile picture for user " + userId);
                out.println("ERROR");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBasicData(int userId, boolean shouldSendProfilePicture) {
        UserInformation user = this.user;
        if (userId != this.user.getId()) {
            user = server.getUserInformation(userId);
        }

        String profilePictureResponse = "0";    // user doesn't have a profile picture
        if (shouldSendProfilePicture) {
            profilePictureResponse = "2";       // user shoul upload profile picture now
        } else if (user.hasProfilePicture()) {
            profilePictureResponse = "1";       // user has a profile picture
        }
        String basicInfo = user.getId() + "#" + user.getFirstName() + "#" + user.getLastName() + "#" + profilePictureResponse;
        out.println(basicInfo);
    }

    private void sendBasicData(int userId) {
        sendBasicData(userId, false);
    }

    private void sendBasicData() {
        sendBasicData(user.getId(), false);
    }

    @Override
    public void run() {
        System.out.println("Request handler " + handlerId + " started.");

        while (!socket.isInputShutdown()) {
            try {
                String request = in.readLine(), response = "";
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
                        } else {
                            sendBasicData();
                        }
                        break;
                    case SIGNUP:
                        response = signUp(requestContent);
                        if (response.startsWith("ERROR")) {
                            out.println(response);
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
                }
                /*System.out.println("[" + id + "] Request: " + request);
                if (request.equals("stop")) {
                    out.println("stop");
                    break;
                }

                String response = "Next number " + ++messageNumber;
                System.out.println("[" + id + "] Response: " + response);
                 */

                //if (!response.equals("")) {
                //    out.println(response);
                //}
            } catch (SocketException e) {
                e.printStackTrace();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("Request handler " + handlerId + " stopped.");
    }
}
