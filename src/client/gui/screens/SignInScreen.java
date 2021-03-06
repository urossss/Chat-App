package client.gui.screens;

import client.gui.components.CustomTextField;
import client.gui.components.CustomPasswordField;
import client.ClientWrapper;
import client.gui.ClientFrame;
import client.gui.GuiSettings;
import client.gui.components.CustomButton;
import client.gui.components.CustomLabel;

/**
 *
 * @author uross
 */
public class SignInScreen extends javax.swing.JPanel {

    /**
     * Creates new form SignInScreen
     *
     * @param _clientFrame
     * @param _clientWrapper
     */
    public SignInScreen(ClientFrame _clientFrame, ClientWrapper _clientWrapper) {
        initComponents();

        setBackground(GuiSettings.COLOR_BACKGROUND);

        clientFrame = _clientFrame;
        clientWrapper = _clientWrapper;

        requiredUsernameLabel.setForeground(GuiSettings.COLOR_BACKGROUND);
        requiredPasswordLabel.setForeground(GuiSettings.COLOR_BACKGROUND);
        errorLabel.setForeground(GuiSettings.COLOR_BACKGROUND);

        CustomTextField username = (CustomTextField) usernameField;
        username.addDesignatedLabel(requiredUsernameLabel);
        username.addDesignatedLabel(errorLabel);

        CustomPasswordField password = (CustomPasswordField) passwordField;
        password.addDesignatedLabel(requiredPasswordLabel);
        password.addDesignatedLabel(errorLabel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logoLabel = new javax.swing.JLabel();
        usernameField = new CustomTextField("Username");
        signinButton = new CustomButton();
        signupLabel = new CustomLabel();
        passwordField = new CustomPasswordField("Password");
        requiredUsernameLabel = new javax.swing.JLabel();
        requiredPasswordLabel = new javax.swing.JLabel();
        errorLabel = new javax.swing.JLabel();
        logoLabel1 = new javax.swing.JLabel();

        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/gui/res/logo.png"))); // NOI18N

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(400, 550));

        usernameField.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        usernameField.setForeground(GuiSettings.COLOR_TEXT_LIGHT);
        usernameField.setText("Username");
        usernameField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        usernameField.setMargin(new java.awt.Insets(2, 15, 2, 2));

        signinButton.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        signinButton.setText("Sign In");
        signinButton.setFocusPainted(false);
        signinButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signinButtonActionPerformed(evt);
            }
        });

        signupLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        signupLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        signupLabel.setText("Don't have an account? Sign Up");
        signupLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signupLabelMouseClicked(evt);
            }
        });

        passwordField.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        passwordField.setForeground(GuiSettings.COLOR_TEXT_LIGHT);
        passwordField.setText("Password");
        passwordField.setEchoChar('\u0000');
        passwordField.setMargin(new java.awt.Insets(2, 15, 2, 2));

        requiredUsernameLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        requiredUsernameLabel.setForeground(GuiSettings.COLOR_ERROR);
        requiredUsernameLabel.setText("Required");

        requiredPasswordLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        requiredPasswordLabel.setForeground(GuiSettings.COLOR_ERROR);
        requiredPasswordLabel.setText("Required");

        errorLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        errorLabel.setForeground(GuiSettings.COLOR_ERROR);
        errorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        errorLabel.setText("Username or password is incorrect.");
        errorLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        errorLabel.setPreferredSize(new java.awt.Dimension(155, 18));

        logoLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/gui/res/logo.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(passwordField)
                    .addComponent(requiredPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(requiredUsernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(signinButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(errorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addComponent(signupLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logoLabel1)
                .addGap(115, 115, 115))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(logoLabel1)
                .addGap(12, 12, 12)
                .addComponent(errorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(requiredUsernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(requiredPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(signinButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(signupLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(164, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void signupLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signupLabelMouseClicked
        clientFrame.setActiveScreen(ScreenType.SIGNUP);
    }//GEN-LAST:event_signupLabelMouseClicked

    private void signinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signinButtonActionPerformed
        boolean inputValid = true;
        CustomTextField customUsernameField = (CustomTextField) usernameField;
        if (!customUsernameField.isInputValid()) {
            requiredUsernameLabel.setForeground(GuiSettings.COLOR_ERROR);
            inputValid = false;
        }

        CustomPasswordField customPasswordField = (CustomPasswordField) passwordField;
        if (!customPasswordField.isInputValid()) {
            requiredPasswordLabel.setForeground(GuiSettings.COLOR_ERROR);
            inputValid = false;
        }

        if (inputValid) {
            String response = clientWrapper.signIn(usernameField.getText(), String.valueOf(passwordField.getPassword()));
            if (response.equals("OK")) {
                clientFrame.getHomeScreen().setIcon();
                clientFrame.setActiveScreen(ScreenType.HOME);

                errorLabel.setForeground(GuiSettings.COLOR_BACKGROUND);
            } else {
                errorLabel.setForeground(GuiSettings.COLOR_ERROR);
            }
        }
    }//GEN-LAST:event_signinButtonActionPerformed

    private ClientFrame clientFrame;
    private ClientWrapper clientWrapper;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel errorLabel;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JLabel logoLabel1;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel requiredPasswordLabel;
    private javax.swing.JLabel requiredUsernameLabel;
    private javax.swing.JButton signinButton;
    private javax.swing.JLabel signupLabel;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}
