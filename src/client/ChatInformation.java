package client;

import java.util.List;
import javax.swing.ImageIcon;

public class ChatInformation {

    private int chatId;
    private String chatName;

    private UserInformation user;   // user that is signed in
    private List<Integer> friends;  // other users in the chat

    private ImageIcon chatImage;

    public ChatInformation(int _chatId, String _chatName, UserInformation _user, List<Integer> _friends, ImageIcon _chatImage) {
        chatId = _chatId;
        chatName = _chatName;
        user = _user;
        friends = _friends;
        chatImage = _chatImage;
    }

    public int getChatId() {
        return chatId;
    }

    public String getChatName() {
        return chatName;
    }

    public ImageIcon getChatImage() {
        return chatImage;
    }
}
