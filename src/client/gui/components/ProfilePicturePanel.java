package client.gui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ProfilePicturePanel extends JPanel {

    private Image profilePicture;
    private final int maxOffset;
    private int offset = 1;

    public ProfilePicturePanel() {
        this(0);
    }

    public ProfilePicturePanel(int _offset) {
        offset = maxOffset = _offset;
    }

    public void setImage(ImageIcon originalIcon) {
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        profilePicture = scaledImage;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                offset = 0;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                offset = maxOffset;
                repaint();
            }
        });
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
        Shape inner = new Ellipse2D.Double(offset, offset, getWidth() - 2 * offset, getHeight() - 2 * offset);
        Area area = new Area(outer);
        area.subtract(new Area(inner));

        g2d.setColor(Color.white);
        g2d.fill(area);

        g2d.dispose();
    }
}
