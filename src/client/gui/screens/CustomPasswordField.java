package client.gui.screens;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class CustomPasswordField extends JPasswordField {

    private Color defaultTextColor = new Color(153, 153, 153);
    private Color textColor = new Color(80, 80, 80);
    private String defaultText;
    private boolean typing;
    private List<JLabel> designatedLabels = new ArrayList<>();

    public CustomPasswordField(String _defaultText) {
        defaultText = _defaultText;
        setEchoChar('\u0000');

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                if (!typing) {
                    setCaretPosition(0);
                }
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (!typing) {
                    setCaretPosition(0);
                }
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                String previousPassword = String.valueOf(getPassword());
                if (previousPassword.equals(defaultText) && !typing) {
                    setText("");
                    setForeground(textColor);
                    setEchoChar('\u25cf');
                    typing = true;
                    for (JLabel designatedLabel : designatedLabels) {
                        designatedLabel.setForeground(Color.white);
                    }
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String password = String.valueOf(getPassword());
                if (password.isEmpty()) {
                    setText(defaultText);
                    setCaretPosition(0);
                    setForeground(defaultTextColor);
                    setEchoChar('\u0000');
                    typing = false;
                } else if (password.equals(defaultText) && !typing) {
                    setCaretPosition(0);
                }
            }
        });
    }

    public void addDesignatedLabel(JLabel label) {
        designatedLabels.add(label);
    }

    public boolean isInputValid() {
        return typing;
    }
}
