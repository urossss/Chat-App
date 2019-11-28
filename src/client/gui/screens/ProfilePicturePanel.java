package client.gui.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ProfilePicturePanel extends JPanel {

    private Image profilePicture;
    
    public void setImage(ImageIcon originalIcon) {
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        profilePicture = scaledImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (profilePicture != null) {
            g2d.drawImage(profilePicture, 0, 0, this);
        }

        Shape outer = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
        Shape inner = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
        Area area = new Area(outer);
        area.subtract(new Area(inner));

        g2d.setColor(Color.white);
        g2d.fill(area);

        g2d.dispose();
    }
}
