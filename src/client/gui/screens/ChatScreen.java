package client.gui.screens;

import client.ChatInformation;
import client.gui.ClientFrame;
import client.gui.GuiSettings;
import client.gui.components.CustomLabel;
import client.gui.components.CustomTextField;
import client.gui.components.ProfilePicturePanel;

/**
 *
 * @author uross
 */
public class ChatScreen extends javax.swing.JPanel {

    /**
     * Creates new form ChatScreen
     * @param _clientFrame
     */
    public ChatScreen(ClientFrame _clientFrame) {
        initComponents();
        
        clientFrame = _clientFrame;
    }
    
    public void setChatInfo(ChatInformation _chatInfo) {
        chatInfo = _chatInfo;
        
        nameLabel.setText(chatInfo.getChatName());
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

        nameLabel = new javax.swing.JLabel();
        backLabel = new CustomLabel();
        profilePicturePanel = new ProfilePicturePanel(1);
        messagesScrollPane = new javax.swing.JScrollPane();
        messagesPanel = new javax.swing.JPanel();
        jTextField1 = new CustomTextField("Type a message...");
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(400, 550));
        setMinimumSize(new java.awt.Dimension(400, 550));

        nameLabel.setBackground(new java.awt.Color(255, 255, 255));
        nameLabel.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        nameLabel.setText("Chat name here");

        backLabel.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        backLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backLabel.setText("←");
        backLabel.setMaximumSize(new java.awt.Dimension(50, 50));
        backLabel.setMinimumSize(new java.awt.Dimension(50, 50));
        backLabel.setPreferredSize(new java.awt.Dimension(50, 50));
        backLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backLabelMouseClicked(evt);
            }
        });

        profilePicturePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profilePicturePanelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout profilePicturePanelLayout = new javax.swing.GroupLayout(profilePicturePanel);
        profilePicturePanel.setLayout(profilePicturePanelLayout);
        profilePicturePanelLayout.setHorizontalGroup(
            profilePicturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
        );
        profilePicturePanelLayout.setVerticalGroup(
            profilePicturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        messagesScrollPane.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout messagesPanelLayout = new javax.swing.GroupLayout(messagesPanel);
        messagesPanel.setLayout(messagesPanelLayout);
        messagesPanelLayout.setHorizontalGroup(
            messagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 389, Short.MAX_VALUE)
        );
        messagesPanelLayout.setVerticalGroup(
            messagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 447, Short.MAX_VALUE)
        );

        messagesScrollPane.setViewportView(messagesPanel);

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField1.setForeground(GuiSettings.COLOR_TEXT_LIGHT);
        jTextField1.setText("Type a message...");
        jTextField1.setMargin(new java.awt.Insets(2, 10, 2, 2));

        jButton1.setText("Send");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(messagesScrollPane)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(backLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(profilePicturePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(profilePicturePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(backLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messagesScrollPane)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void backLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backLabelMouseClicked
        clientFrame.setActiveScreen(ScreenType.HOME);
    }//GEN-LAST:event_backLabelMouseClicked

    private void profilePicturePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profilePicturePanelMouseClicked
        clientFrame.getProfileScreen().setInfo(chatInfo.getChatName(), chatInfo.getChatImage(), ScreenType.CHAT);
        clientFrame.setActiveScreen(ScreenType.PROFILE);
    }//GEN-LAST:event_profilePicturePanelMouseClicked

    private ClientFrame clientFrame;
    private ChatInformation chatInfo;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel messagesPanel;
    private javax.swing.JScrollPane messagesScrollPane;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JPanel profilePicturePanel;
    // End of variables declaration//GEN-END:variables
}
