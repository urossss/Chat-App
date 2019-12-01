package client.gui.components;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CustomTextField extends JTextField {

    private Color defaultTextColor = new Color(153, 153, 153);
    private Color textColor = new Color(80, 80, 80);
    private String defaultText;
    private boolean typing;
    private List<JLabel> designatedLabels = new ArrayList<>();

    public CustomTextField(String _defaultText) {
        defaultText = _defaultText;

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
                String previousText = getText();
                if (previousText.equals(defaultText) && !typing) {
                    setText("");
                    setForeground(textColor);
                    typing = true;
                }
                for (JLabel designatedLabel : designatedLabels) {
                    designatedLabel.setForeground(Color.white);
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (getText().isEmpty()) {
                    setText(defaultText);
                    setCaretPosition(0);
                    setForeground(defaultTextColor);
                    typing = false;
                } else if (getText().equals(defaultText) && !typing) {
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
