package client.gui.screens;

import client.gui.components.ProfilePicturePanel;
import client.UserInformation;
import client.gui.ClientFrame;
import client.gui.components.CustomLabel;
import javax.swing.ImageIcon;

/**
 *
 * @author uross
 */
public class ProfileScreen extends javax.swing.JPanel {

    /**
     * Creates new form ProfileScreen
     *
     * @param _clientFrame
     */
    public ProfileScreen(ClientFrame _clientFrame) {
        initComponents();

        clientFrame = _clientFrame;
    }

    public void setInfo(String name, ImageIcon image, ScreenType screen) {
        ((ProfilePicturePanel) profilePicturePanel).setImage(image);
        nameLabel.setText(name);
        previousScreen = screen;
        
        revalidate();
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        profilePicturePanel = new ProfilePicturePanel();
        nameLabel = new javax.swing.JLabel();
        backLabel = new CustomLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(400, 550));
        setMinimumSize(new java.awt.Dimension(400, 550));
        setName(""); // NOI18N

        javax.swing.GroupLayout profilePicturePanelLayout = new javax.swing.GroupLayout(profilePicturePanel);
        profilePicturePanel.setLayout(profilePicturePanelLayout);
        profilePicturePanelLayout.setHorizontalGroup(
            profilePicturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        profilePicturePanelLayout.setVerticalGroup(
            profilePicturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        nameLabel.setBackground(new java.awt.Color(34, 156, 243));
        nameLabel.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        backLabel.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        backLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backLabel.setText("←");
        backLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(profilePicturePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(75, 75, 75))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(profilePicturePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(backLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(223, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void backLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backLabelMouseClicked
        clientFrame.setActiveScreen(previousScreen);
    }//GEN-LAST:event_backLabelMouseClicked

    private ClientFrame clientFrame;
    private ScreenType previousScreen = ScreenType.HOME;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JPanel profilePicturePanel;
    // End of variables declaration//GEN-END:variables
}
