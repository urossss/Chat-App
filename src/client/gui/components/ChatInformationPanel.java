package client.gui.components;

import client.ChatInformation;
import client.gui.ClientFrame;
import client.gui.GuiSettings;
import client.gui.screens.ScreenType;
import java.awt.Dimension;

/**
 *
 * @author uross
 */
public class ChatInformationPanel extends javax.swing.JPanel {

    /**
     * Creates new form ChatInformationPanel
     *
     * @param _chatInfo
     * @param _clientFrame
     */
    public ChatInformationPanel(ChatInformation _chatInfo, ClientFrame _clientFrame) {
        initComponents();

        clientFrame = _clientFrame;
        chatInfo = _chatInfo;

        chatNameLabel.setText(chatInfo.getChatName());
        lastMessageLabel.setText("");   // FIX - add information about this inside ChatInformation
        ((ProfilePicturePanel) profilePicturePanel).setImage(chatInfo.getChatImage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        profilePicturePanel = new ProfilePicturePanel(new Dimension(55, 55), 1);
        infoPanel = new javax.swing.JPanel();
        newMessagesLabel = new javax.swing.JLabel();
        lastMessageLabel = new javax.swing.JLabel();
        chatNameLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        profilePicturePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profilePicturePanelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout profilePicturePanelLayout = new javax.swing.GroupLayout(profilePicturePanel);
        profilePicturePanel.setLayout(profilePicturePanelLayout);
        profilePicturePanelLayout.setHorizontalGroup(
            profilePicturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 55, Short.MAX_VALUE)
        );
        profilePicturePanelLayout.setVerticalGroup(
            profilePicturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        infoPanel.setBackground(new java.awt.Color(255, 255, 255));
        infoPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, GuiSettings.COLOR_TEXT_LIGHT));
        infoPanel.setMaximumSize(new java.awt.Dimension(324, 55));
        infoPanel.setMinimumSize(new java.awt.Dimension(324, 55));
        infoPanel.setPreferredSize(new java.awt.Dimension(324, 55));
        infoPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infoPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                infoPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                infoPanelMouseExited(evt);
            }
        });

        newMessagesLabel.setBackground(new java.awt.Color(255, 255, 255));
        newMessagesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newMessagesLabel.setText("0");

        lastMessageLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lastMessageLabel.setText("Ovo je neka prethodna poruka");
        lastMessageLabel.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        chatNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        chatNameLabel.setText("Uros Stojiljkovic");

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chatNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lastMessageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newMessagesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                .addContainerGap())
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoPanelLayout.createSequentialGroup()
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(newMessagesLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addComponent(chatNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(lastMessageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(profilePicturePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(infoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(infoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(profilePicturePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void infoPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoPanelMouseEntered
        infoPanel.setBackground(GuiSettings.COLOR_CHAT_INFO_SELECTED);
    }//GEN-LAST:event_infoPanelMouseEntered

    private void infoPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoPanelMouseExited
        infoPanel.setBackground(GuiSettings.COLOR_CHAT_INFO_DEFAULT);
    }//GEN-LAST:event_infoPanelMouseExited

    private void infoPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoPanelMouseClicked
        // TODO: open chat screen
        clientFrame.getChatScreen().setChatInfo(chatInfo);
        clientFrame.setActiveScreen(ScreenType.CHAT);
    }//GEN-LAST:event_infoPanelMouseClicked

    private void profilePicturePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profilePicturePanelMouseClicked
        clientFrame.getProfileScreen().setInfo(chatInfo.getChatName(), chatInfo.getChatImage(), ScreenType.HOME);
        clientFrame.setActiveScreen(ScreenType.PROFILE);
    }//GEN-LAST:event_profilePicturePanelMouseClicked

    private ClientFrame clientFrame;
    private ChatInformation chatInfo;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel chatNameLabel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel lastMessageLabel;
    private javax.swing.JLabel newMessagesLabel;
    private javax.swing.JPanel profilePicturePanel;
    // End of variables declaration//GEN-END:variables
}
