package client.gui.components;

import client.gui.GuiSettings;
import java.awt.Dimension;
import javax.swing.ImageIcon;

public class FriendsMessagePanel extends MessagePanel {

    private final int PADDING = 5;
    private ProfilePicturePanel profilePicturePanel;

    public FriendsMessagePanel(String message, ImageIcon profilePicture) {
        super(message, GuiSettings.COLOR_BUBBLE_BACKGROUND_LEFT, GuiSettings.COLOR_BUBBLE_TEXT_LEFT);

        profilePicturePanel = new ProfilePicturePanel(profilePicture, new Dimension(35, 35), 1);
        add(profilePicturePanel);
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