package client;

import java.util.TimerTask;

public class ServerPoller extends TimerTask {

    private ClientRequestHandler handler;

    public ServerPoller(ClientRequestHandler _handler) {
        handler = _handler;
    }

    @Override
    public void run() {
        handler.addRequest("POLL#");
    }

}
