package client.gui.screens;

import client.Client;
import client.gui.ClientFrame;
import java.awt.Color;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author uross
 */
public class SignUpScreen extends javax.swing.JPanel {

    /**
     * Creates new form LogIn
     *
     * @param _clientFrame
     * @param _client
     */
    public SignUpScreen(ClientFrame _clientFrame, Client _client) {
        initComponents();

        clientFrame = _clientFrame;
        client = _client;

        signupButton.setContentAreaFilled(false);
        signupButton.setOpaque(true);
        signupButton.setBorder(new LineBorder(new Color(153, 153, 153)));

        requiredFirstLabel.setForeground(Color.white);
        requiredLastLabel.setForeground(Color.white);
        requiredPasswordLabel.setForeground(Color.white);
        requiredUsernameLabel.setForeground(Color.white);
        errorLabel.setForeground(Color.white);

        CustomTextField textField = (CustomTextField) usernameField;
        textField.addDesignatedLabel(requiredUsernameLabel);
        textField.addDesignatedLabel(errorLabel);

        textField = (CustomTextField) firstNameField;
        textField.addDesignatedLabel(requiredFirstLabel);
        textField.addDesignatedLabel(errorLabel);

        textField = (CustomTextField) lastNameField;
        textField.addDesignatedLabel(requiredLastLabel);
        textField.addDesignatedLabel(errorLabel);

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

        usernameField = new CustomTextField("Username");
        signupButton = new javax.swing.JButton();
        signinLabel = new javax.swing.JLabel();
        logoLabel = new javax.swing.JLabel();
        passwordField = new CustomPasswordField("Password");
        firstNameField = new CustomTextField("First name");
        lastNameField = new CustomTextField("Last name");
        errorLabel = new javax.swing.JLabel();
        requiredUsernameLabel = new javax.swing.JLabel();
        requiredPasswordLabel = new javax.swing.JLabel();
        requiredFirstLabel = new javax.swing.JLabel();
        requiredLastLabel = new javax.swing.JLabel();
        profilePictureLabel = new javax.swing.JLabel();
        profilePictureNameLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(400, 550));

        usernameField.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        usernameField.setForeground(new java.awt.Color(153, 153, 153));
        usernameField.setText("Username");
        usernameField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        usernameField.setMargin(new java.awt.Insets(2, 15, 2, 2));

        signupButton.setBackground(new java.awt.Color(34, 156, 243));
        signupButton.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        signupButton.setForeground(new java.awt.Color(255, 255, 255));
        signupButton.setText("Sign Up");
        signupButton.setFocusPainted(false);
        signupButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signupButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signupButtonMouseExited(evt);
            }
        });
        signupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signupButtonActionPerformed(evt);
            }
        });

        signinLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        signinLabel.setForeground(new java.awt.Color(34, 156, 243));
        signinLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        signinLabel.setText("Already have an account? Sign In");
        signinLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signinLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signinLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signinLabelMouseExited(evt);
            }
        });

        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/gui/res/logo.png"))); // NOI18N

        passwordField.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        passwordField.setForeground(new java.awt.Color(153, 153, 153));
        passwordField.setText("Password");
        passwordField.setEchoChar('\u0000');
        passwordField.setMargin(new java.awt.Insets(2, 15, 2, 2));

        firstNameField.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        firstNameField.setForeground(new java.awt.Color(153, 153, 153));
        firstNameField.setText("First name");
        firstNameField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        firstNameField.setMargin(new java.awt.Insets(2, 15, 2, 2));

        lastNameField.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        lastNameField.setForeground(new java.awt.Color(153, 153, 153));
        lastNameField.setText("Last name");
        lastNameField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        lastNameField.setMargin(new java.awt.Insets(2, 15, 2, 2));

        errorLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        errorLabel.setForeground(new java.awt.Color(255, 0, 0));
        errorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        errorLabel.setText("Username already exists.");
        errorLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        errorLabel.setPreferredSize(new java.awt.Dimension(134, 18));

        requiredUsernameLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        requiredUsernameLabel.setForeground(new java.awt.Color(255, 0, 0));
        requiredUsernameLabel.setText("Required");

        requiredPasswordLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        requiredPasswordLabel.setForeground(new java.awt.Color(255, 0, 0));
        requiredPasswordLabel.setText("Required");

        requiredFirstLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        requiredFirstLabel.setForeground(new java.awt.Color(255, 0, 0));
        requiredFirstLabel.setText("Required");
        requiredFirstLabel.setPreferredSize(new java.awt.Dimension(40, 12));

        requiredLastLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        requiredLastLabel.setForeground(new java.awt.Color(255, 0, 0));
        requiredLastLabel.setText("Required");
        requiredLastLabel.setPreferredSize(new java.awt.Dimension(40, 12));

        profilePictureLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        profilePictureLabel.setForeground(new java.awt.Color(34, 156, 243));
        profilePictureLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        profilePictureLabel.setText("Add profile picture");
        profilePictureLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profilePictureLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                profilePictureLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                profilePictureLabelMouseExited(evt);
            }
        });

        profilePictureNameLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        profilePictureNameLabel.setForeground(new java.awt.Color(80, 80, 80));
        profilePictureNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        profilePictureNameLabel.setMaximumSize(new java.awt.Dimension(190, 23));
        profilePictureNameLabel.setMinimumSize(new java.awt.Dimension(190, 23));
        profilePictureNameLabel.setPreferredSize(new java.awt.Dimension(190, 23));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(115, Short.MAX_VALUE)
                .addComponent(logoLabel)
                .addGap(115, 115, 115))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(signinLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(profilePictureNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(profilePictureLabel))
                            .addComponent(requiredLastLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(requiredFirstLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(requiredPasswordLabel)
                            .addComponent(requiredUsernameLabel)
                            .addComponent(errorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(usernameField)
                            .addComponent(passwordField)
                            .addComponent(firstNameField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lastNameField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(signupButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(logoLabel)
                .addGap(12, 12, 12)
                .addComponent(errorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(requiredUsernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(requiredPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(requiredFirstLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(firstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(requiredLastLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lastNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(profilePictureNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(profilePictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(signupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(signinLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void signinLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signinLabelMouseEntered
        signinLabel.setForeground(new Color(34, 115, 242));
    }//GEN-LAST:event_signinLabelMouseEntered

    private void signinLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signinLabelMouseExited
        signinLabel.setForeground(new Color(34, 156, 243));
    }//GEN-LAST:event_signinLabelMouseExited

    private void signinLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signinLabelMouseClicked
        clientFrame.setActiveScreen(ScreenType.SIGNIN);
    }//GEN-LAST:event_signinLabelMouseClicked

    private void profilePictureLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profilePictureLabelMouseClicked
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png"));
        fileChooser.showOpenDialog(this);
        File file = fileChooser.getSelectedFile();
        if (file != null) {
            System.out.println(file.getName());
            profilePictureNameLabel.setText(file.getName());
            profilePicturePath = file.getAbsolutePath();
        } else {
            System.out.println("null");
            profilePictureNameLabel.setText("");
            profilePicturePath = "";
        }
    }//GEN-LAST:event_profilePictureLabelMouseClicked

    private void profilePictureLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profilePictureLabelMouseEntered
        profilePictureLabel.setForeground(new Color(34, 115, 242));
    }//GEN-LAST:event_profilePictureLabelMouseEntered

    private void profilePictureLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profilePictureLabelMouseExited
        profilePictureLabel.setForeground(new Color(34, 156, 243));
    }//GEN-LAST:event_profilePictureLabelMouseExited

    private void signupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signupButtonActionPerformed
        boolean inputValid = true;
        CustomTextField customUsernameField = (CustomTextField) usernameField;
        if (!customUsernameField.isInputValid()) {
            requiredUsernameLabel.setForeground(Color.red);
            inputValid = false;
        }
        CustomTextField customFirstNameField = (CustomTextField) firstNameField;
        if (!customFirstNameField.isInputValid()) {
            requiredFirstLabel.setForeground(Color.red);
            inputValid = false;
        }
        CustomTextField customLastNameField = (CustomTextField) lastNameField;
        if (!customLastNameField.isInputValid()) {
            requiredLastLabel.setForeground(Color.red);
            inputValid = false;
        }

        CustomPasswordField customPasswordField = (CustomPasswordField) passwordField;
        if (!customPasswordField.isInputValid()) {
            requiredPasswordLabel.setForeground(Color.red);
            inputValid = false;
        }

        if (inputValid) {
            String response = client.signUp(
                    usernameField.getText(),
                    String.valueOf(passwordField.getPassword()),
                    firstNameField.getText(),
                    lastNameField.getText(),
                    profilePicturePath
            );
            if (response.equals("OK")) {
                clientFrame.setActiveScreen(ScreenType.HOME);
                errorLabel.setForeground(Color.white);
            } else {
                errorLabel.setText(response);
                errorLabel.setForeground(Color.red);
            }
        }
    }//GEN-LAST:event_signupButtonActionPerformed

    private void signupButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signupButtonMouseEntered
        signupButton.setBackground(new Color(104,185,243));
    }//GEN-LAST:event_signupButtonMouseEntered

    private void signupButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signupButtonMouseExited
        signupButton.setBackground(new Color(34, 156, 243));
    }//GEN-LAST:event_signupButtonMouseExited

    private ClientFrame clientFrame;
    private Client client;

    private String profilePicturePath = "";

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel errorLabel;
    private javax.swing.JTextField firstNameField;
    private javax.swing.JTextField lastNameField;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel profilePictureLabel;
    private javax.swing.JLabel profilePictureNameLabel;
    private javax.swing.JLabel requiredFirstLabel;
    private javax.swing.JLabel requiredLastLabel;
    private javax.swing.JLabel requiredPasswordLabel;
    private javax.swing.JLabel requiredUsernameLabel;
    private javax.swing.JLabel signinLabel;
    private javax.swing.JButton signupButton;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}
