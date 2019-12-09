package client.gui.components;

import client.gui.GuiSettings;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class CustomLabel extends JLabel {

    private Color mouseEnteredColor = GuiSettings.COLOR_MAIN_DARK, mouseExitedColor = GuiSettings.COLOR_MAIN;

    public CustomLabel() {
        setBackground(GuiSettings.COLOR_BACKGROUND);
        setForeground(mouseExitedColor);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(mouseEnteredColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(mouseExitedColor);
            }
        });
    }

}
