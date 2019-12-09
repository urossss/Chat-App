package server;

import java.util.List;

public class ChatInformation {

    private int chatId;
    private String chatName;
    private List<Integer> users;

    public ChatInformation(int _chatId, String _chatName, List<Integer> _users) {
        chatId = _chatId;
        chatName = _chatName;
        users = _users;
    }

    public int getChatId() {
        return chatId;
    }

    public List<Integer> getUsers() {
        return users;
    }

    private String getSerializedUserIds() {
        StringBuilder sb = new StringBuilder();

        for (int userId : users) {
            sb.append("#" + userId);
        }
        return sb.toString();
    }

    public String serialize() {
        return chatId + "#" + chatName.length() + "#" + chatName + getSerializedUserIds();
    }
}
