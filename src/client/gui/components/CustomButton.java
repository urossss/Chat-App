package client.gui.components;

import client.gui.GuiSettings;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class CustomButton extends JButton {

    private Color mouseEnteredColor = GuiSettings.COLOR_MAIN_LIGHT, mouseExitedColor = GuiSettings.COLOR_MAIN;

    public CustomButton() {
        setBackground(mouseExitedColor);
        setForeground(GuiSettings.COLOR_BACKGROUND);

        setContentAreaFilled(false);
        setOpaque(true);
        setBorder(new LineBorder(GuiSettings.COLOR_TEXT_LIGHT));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(mouseEnteredColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(mouseExitedColor);
            }
        });
    }

}
