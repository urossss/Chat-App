package client;

import client.gui.components.FriendsMessagePanel;
import client.gui.components.MessagePanel;
import client.gui.components.MyMessagePanel;
import java.awt.Dimension;
import java.util.List;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChatInformation {

    // chat info
    private int chatId;
    private String chatName;

    // chat memebers
    private UserInformation user;   // user that is signed in
    private List<Integer> friends;  // other users in the chat

    // last message info
    private int lastDate, lastSender;
    private String lastTime, lastMessage;

    // designated gui elements
    private ImageIcon chatImage;
    private JPanel messagesPanel;

    public ChatInformation(int _chatId, String _chatName, UserInformation _user, List<Integer> _friends, ImageIcon _chatImage) {
        chatId = _chatId;
        chatName = _chatName;
        user = _user;
        friends = _friends;
        chatImage = _chatImage;
    }

    public void addMessage(int senderId, int date, String time, String message) {
        // FIX
        if (messagesPanel == null) {
            return;
        }

        if (date != lastDate) {
            insertPadding();
            insertDateLabel(date);
            insertPadding();
        } else if (senderId != lastSender) {
            insertPadding();
        }

        lastDate = date;
        lastTime = time;
        lastMessage = message;
        lastSender = senderId;

        MessagePanel messagePanel;
        if (senderId == user.getId()) {
            messagePanel = new MyMessagePanel(message);
        } else {
            messagePanel = new FriendsMessagePanel(message, chatImage);
        }
        messagesPanel.add(messagePanel);
        insertPadding();
    }

    private void insertDateLabel(int date) {
        int year = date / 10000;
        int month = (date / 100) % 100;
        int day = date % 100;
        messagesPanel.add(new JLabel(String.format("%d.%d.%d.", day, month, year)));
    }

    private void insertPadding() {
        messagesPanel.add(Box.createRigidArea(new Dimension(0, 5)));
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

    public void setMessagesPanel(JPanel messagesPanel) {
        this.messagesPanel = messagesPanel;
    }

    public JPanel getMessagesPanel() {
        return messagesPanel;
    }
}
