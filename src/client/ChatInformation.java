package client;

import java.util.List;

public class ChatInformation {

    private int chatId;
    private String chatName;

    private UserInformation user;   // user that is signed in
    private List<Integer> friends;  // other users in the chat

    public ChatInformation(int _chatId, String _chatName, UserInformation _user, List<Integer> _friends) {
        chatId = _chatId;
        chatName = _chatName;
        user = _user;
        friends = _friends;
    }
}
