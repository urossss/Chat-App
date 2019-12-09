package client;

import common.FileHelper;
import common.RequestType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import javafx.util.Pair;

public class ClientRequestHandler extends Thread {

    private static final String SERVER_IP = "94.189.225.68";
    //private static final String SERVER_IP = "192.168.0.30";
    private static final int SERVER_PORT = 4444;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private int requestCount = 0;
    private final Queue<Pair<Integer, String>> requests = new LinkedList<>();
    private final Map<Integer, String> responses = new HashMap<>();

    public ClientRequestHandler() {
        ServerPoller poller = new ServerPoller(this);
        new Timer().schedule(poller, 1000, 1000);
    }

    public void establishConnection() throws IOException {
        socket = new Socket(SERVER_IP, SERVER_PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public int addRequest(String request) {
        int requestId;
        synchronized (requests) {
            requestId = ++requestCount;
            requests.add(new Pair(requestId, request));
            requests.notify();
        }
        return requestId;
    }

    public String getResponse(int id) {
        try {
            synchronized (responses) {
                while (!responses.containsKey(id)) {
                    responses.wait();
                }
            }
            // Improvement: remove this response from the list if it is only used this time
            return responses.get(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        System.out.println("Server handler started.");

        while (true) {
            try {
                Pair<Integer, String> requestPair;
                synchronized (requests) {
                    if (requests.isEmpty()) {
                        requests.wait();
                    }
                    requestPair = requests.remove();
                }
                if (requestPair == null) {
                    continue;
                }

                int requestId = requestPair.getKey();
                String requestMessage = requestPair.getValue();

                String[] requestParts = requestMessage.split("#", 2);
                String requestTypeName = requestParts[0];
                String requestContent = requestParts[1];
                RequestType requestType = RequestType.valueOf(requestTypeName);

                String response;

                switch (requestType) {
                    case SIGNIN:
                    case SIGNUP:
                        out.println(requestMessage);
                        out.flush();
                        response = in.readLine();
                        synchronized (responses) {
                            responses.put(requestId, response);
                            responses.notifyAll();
                        }
                        break;
                    case UPLOAD_IMAGE:
                        FileHelper.sendFile(socket, in, out, requestContent);
                        break;
                    case GET_PROFILE_PICTURE:
                        String parts[] = requestContent.split("#");

                        out.println(requestTypeName + "#" + parts[0]);
                        out.flush();
                        response = in.readLine();

                        if (!response.startsWith("ERROR")) {
                            String[] info = response.split("#");
                            int length = Integer.parseInt(info[1]);
                            String extension = info[2];
                            String profilePicturePath = parts[1] + extension;

                            out.println("OK");
                            out.flush();
                            FileHelper.receiveFile(socket, in, out, profilePicturePath, length);

                            response = profilePicturePath;
                        }

                        synchronized (responses) {
                            responses.put(requestId, response);
                            responses.notifyAll();
                        }
                        break;
                    case GET_CHAT_INFO:
                    case GET_USER_INFO:
                        out.println(requestMessage);
                        response = in.readLine();

                        synchronized (responses) {
                            responses.put(requestId, response);
                            responses.notifyAll();
                        }
                        break;
                    case POLL:
                        out.println(requestMessage);
                        // TODO
                        break;
                }
            } catch (IOException | InterruptedException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

}
