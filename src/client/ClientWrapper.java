package client;

import client.gui.ClientFrame;
import java.io.IOException;
import java.util.Timer;

public class ClientWrapper {

    private Client client;

    private boolean busy = false;
    private int number = 0, next = 1;

    public ClientWrapper(ClientFrame clientFrame) {
        client = new Client(clientFrame);
    }

    public void establishConnection() throws IOException {
        client.establishConnection();

        ServerPoller poller = new ServerPoller(this);
        new Timer().schedule(poller, 1000, 300);
    }

    public UserInformation getUser() {
        return client.getUser();
    }

    public synchronized String signIn(String username, String password) {
        int turn = ++number;
        while (turn != next || busy) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        next++;
        notifyAll();

        return client.signIn(username, password);
    }

    public synchronized String signUp(String username, String password, String firstName, String lastName, String profilePicturePath) {
        int turn = ++number;
        while (turn != next || busy) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        next++;
        notifyAll();

        return client.signUp(username, password, firstName, lastName, profilePicturePath);
    }

    public synchronized void getChatMessages(int chatId) {
        int turn = ++number;
        while (turn != next || busy) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        next++;
        notifyAll();

        client.getChatMessages(chatId);
    }

    public synchronized void sendMessage(String message, int chatId) {
        int turn = ++number;
        while (turn != next || busy) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        next++;
        notifyAll();

        client.sendMessage(message, chatId);
    }

    public synchronized void checkForUpdates() {
        busy = true;

        client.checkForUpdates();

        busy = false;
    }

}
