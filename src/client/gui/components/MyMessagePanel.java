package client.gui.components;

import client.gui.GuiSettings;
import java.awt.Dimension;

public class MyMessagePanel extends MessagePanel {
    
    private static final int RIGHT = 5;

    public MyMessagePanel(String message) {
        super(message, GuiSettings.COLOR_BUBBLE_BACKGROUND_RIGHT, GuiSettings.COLOR_BUBBLE_TEXT_RIGHT);
    }

    @Override
    public void repack(int bubbleWidth, int bubbleHeight) {
        setLayout(null);
        
        int width = this.getWidth(), height = bubbleHeight;

        Dimension d = new Dimension(width, height);
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);

        timeLabel.setBounds(width - timeLabel.getWidth() - bubbleWidth - RIGHT, height - timeLabel.getHeight(), timeLabel.getWidth(), timeLabel.getHeight());
        chatBubblePanel.setBounds(width - bubbleWidth - RIGHT, 0, bubbleWidth, bubbleHeight);

        revalidate();
        repaint();
    }

}
