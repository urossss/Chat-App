package client.gui.components;

import client.gui.GuiSettings;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public abstract class MessagePanel extends JPanel {

    protected ChatBubblePanel chatBubblePanel;
    protected JLabel timeLabel;

    public MessagePanel(String time, String message, Color bubbleBackground, Color bubbleForeground) {
        setBackground(GuiSettings.COLOR_BACKGROUND);

        chatBubblePanel = new ChatBubblePanel(message, bubbleBackground, bubbleForeground);

        timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("Sergoe UI", Font.PLAIN, 10));
        timeLabel.setBorder(new EmptyBorder(2, 4, 2, 4));
        timeLabel.setForeground(GuiSettings.COLOR_TEXT_LIGHT);

        add(timeLabel);
        add(chatBubblePanel);
    }

    public abstract void repack(int bubbleWidth, int bubbleHeight);

}
