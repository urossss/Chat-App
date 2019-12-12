package client.gui.components;

import client.gui.ClientFrame;
import client.gui.GuiSettings;
import client.gui.screens.ScreenType;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class FriendsMessagePanel extends MessagePanel {

    private final int PADDING = 5;
    private ProfilePicturePanel profilePicturePanel;

    public FriendsMessagePanel(String time, String message, String name, ImageIcon profilePicture, ClientFrame clientFrame) {
        super(time, message, GuiSettings.COLOR_BUBBLE_BACKGROUND_LEFT, GuiSettings.COLOR_BUBBLE_TEXT_LEFT);

        profilePicturePanel = new ProfilePicturePanel(profilePicture, new Dimension(35, 35), 1);
        add(profilePicturePanel);

        profilePicturePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clientFrame.getProfileScreen().setInfo(name, profilePicture, ScreenType.CHAT);
                clientFrame.setActiveScreen(ScreenType.PROFILE);
            }
        });
    }

    @Override
    public void repack(int bubbleWidth, int bubbleHeight) {
        setLayout(null);

        int width = this.getWidth(), height = bubbleHeight;

        Dimension d = new Dimension(width, height);
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);

        profilePicturePanel.setBounds(PADDING, PADDING, profilePicturePanel.getWidth(), profilePicturePanel.getHeight());
        chatBubblePanel.setBounds(profilePicturePanel.getWidth() + 2 * PADDING, 0, bubbleWidth, bubbleHeight);
        timeLabel.setBounds(profilePicturePanel.getWidth() + bubbleWidth + 2 * PADDING, height - timeLabel.getHeight(), timeLabel.getWidth(), timeLabel.getHeight());

        revalidate();
        repaint();
    }

}
