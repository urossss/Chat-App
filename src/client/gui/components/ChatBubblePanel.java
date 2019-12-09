package client.gui.components;

import client.gui.GuiSettings;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.View;

public class ChatBubblePanel extends JPanel {

    private Color backgroundColor;
    private JTextArea textArea;
    private boolean sizeSet = false;
    private final int cornerRadius = 10, delta = 2;
    private int panelWidth, panelHeight, textWidth, textHeight;

    public ChatBubblePanel(String text, Color background, Color foreground) {
        setBackground(GuiSettings.COLOR_BACKGROUND);
        backgroundColor = background;
        
        textArea = new JTextArea(text);
        textArea.setFont(new Font("Sergoe UI", Font.PLAIN, 14));
        textArea.setBackground(background);
        textArea.setForeground(foreground);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        int cols = Math.min(text.length(), 20);
        textArea.setColumns(cols);
        textArea.setSize(1, 1);

        add(textArea);

        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!sizeSet) {
            super.paintComponent(g);

            System.out.println(textArea.getSize());
            sizeSet = true;

            textWidth = textArea.getWidth();
            textHeight = textArea.getHeight();

            setLayout(null);

            panelWidth = textWidth + 2 * cornerRadius + 2 * delta;
            panelHeight = textHeight + 2 * cornerRadius;

            Dimension size = new Dimension(panelWidth, panelHeight);
            setPreferredSize(size);
            setMaximumSize(size);
            setMinimumSize(size);

            textArea.setBounds(cornerRadius + delta, cornerRadius, textWidth, textHeight);
            textArea.revalidate();
            textArea.repaint();

            MessagePanel parent = (MessagePanel) getParent();
            parent.repack(panelWidth, panelHeight);

            //getParent().revalidate();
            //getParent().repaint();
//            revalidate();
//            repaint();
            return;
        }
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //g2d.drawImage(topLeftI, 0, 0, this);
        g2d.setColor(backgroundColor);
        g2d.fillArc(0, 0, 2 * cornerRadius, 2 * cornerRadius, 90, 90);  // top left
        g2d.fillArc(panelWidth - 2 * cornerRadius, 0, 2 * cornerRadius, 2 * cornerRadius, 0, 90);   // top right
        g2d.fillArc(panelWidth - 2 * cornerRadius, panelHeight - 2 * cornerRadius, 2 * cornerRadius, 2 * cornerRadius, 270, 90);    // bottom right
        g2d.fillArc(0, panelHeight - 2 * cornerRadius, 2 * cornerRadius, 2 * cornerRadius, 180, 90);    // bottom left
        g2d.fillRect(cornerRadius, 0, textWidth + 2 * delta, cornerRadius);   // top
        g2d.fillRect(cornerRadius, panelHeight - cornerRadius, textWidth + 2 * delta, cornerRadius);   // bottom
        g2d.fillRect(0, cornerRadius, cornerRadius + delta, textHeight);   // left
        g2d.fillRect(panelWidth - cornerRadius - delta, cornerRadius, cornerRadius + delta, textHeight);   // right
    }

}
