package client;

import java.util.TimerTask;

public class ServerPoller extends TimerTask {

    private ClientWrapper client;

    public ServerPoller(ClientWrapper _client) {
        client = _client;
    }

    @Override
    public void run() {
        client.checkForUpdates();
    }

}
