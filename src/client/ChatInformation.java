package client;

import client.gui.ClientFrame;
import client.gui.GuiSettings;
import client.gui.components.FriendsMessagePanel;
import client.gui.components.MessagePanel;
import client.gui.components.MyMessagePanel;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;
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
    private Map<Integer, UserInformation> friends;  // other users in the chat

    // last message info
    private int lastDate, lastSender;
    private String lastTime, lastMessage;

    // gui elements
    private ClientFrame clientFrame;
    private ImageIcon chatImage;
    private JPanel messagesPanel;

    public ChatInformation(int _chatId, String _chatName, UserInformation _user, Map<Integer, UserInformation> _friends, ImageIcon _chatImage, ClientFrame _clientFrame) {
        chatId = _chatId;
        chatName = _chatName;
        user = _user;
        friends = _friends;
        chatImage = _chatImage;
        clientFrame = _clientFrame;
    }

    public void addMessage(int senderId, int date, String time, String message) {
        // FIX
        if (messagesPanel == null) {
            return;
        }

        if (date != lastDate) {
            insertDateLabel(date);
            insertPadding();
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
            messagePanel = new MyMessagePanel(time, message);
        } else {
            UserInformation sender = friends.get(senderId);
            messagePanel = new FriendsMessagePanel(time, message, sender.getFirstName() + " " + sender.getLastName(), sender.getProfilePicture(), clientFrame);
        }
        messagesPanel.add(messagePanel);
        insertPadding();
    }

    private void insertDateLabel(int date) {
        int year = date / 10000;
        int month = (date / 100) % 100;
        int day = date % 100;

        JPanel datePanel = new JPanel();
        datePanel.setBackground(GuiSettings.COLOR_BACKGROUND);

        Dimension size = new Dimension(371, 20);
        datePanel.setPreferredSize(size);
        datePanel.setMaximumSize(size);
        datePanel.setMinimumSize(size);
        datePanel.setSize(size);

        JLabel dateLabel = new JLabel(String.format("%d.%d.%d.", day, month, year));
        dateLabel.setFont(new Font("Sergoe UI", Font.PLAIN, 12));
        dateLabel.setForeground(GuiSettings.COLOR_TEXT_DARK);
        datePanel.add(dateLabel);

        messagesPanel.add(datePanel);
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
